package MyMVC.Route;

import MyMVC.MyTemplate;
import MyMVC.Request;
import MyMVC.Utility;
import MyMVC.models.Message;
import MyMVC.models.Session;
import MyMVC.models.User;
import MyMVC.service.MessageService;
import MyMVC.service.SessionService;
import MyMVC.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Route {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/", Route::routeIndex);
        map.put("/message", Route::routeMessage);
        map.put("/static", Route::routeImage);

        return map;
    }


    public static String responseWithHeader(int code, HashMap<String, String> headerMap, String body) {
        String header = String.format("HTTP/1.1 %s\r\r", code);

        for (String key: headerMap.keySet()) {
            String value = headerMap.get(key);
            String item = String.format("%s: %s \r\n", key, value);
            header = header + item;
        }
        String response = String.format("%s\r\n\r\n%s", header, body);
        return response;
    }

    public static byte[] redirect(String url) {
        String header = "HTTP/1.1 302 move \r\n" +
                "Location: " + url + "\r\n" +
                "Content-Type: text/html\r\n\r\n";

        return header.getBytes(StandardCharsets.UTF_8);
    }


    public static User currentUser(Request request) {
        if (request.cookies.containsKey("session_id")) {
            String sessionId = request.cookies.get("session_id");
            Session s = SessionService.findBySessionId(sessionId);
            if (s == null) {
                return UserService.guest();
            } else {
                User user = UserService.findById(s.userId);
                if (user == null) {
                    return UserService.guest();
                } else {
                    return user;
                }
            }
        } else {
            return UserService.guest();
        }
    }

    public static byte[] routeIndex(Request request) {
        Utility.log("index cookie %s", request.cookies);
        User user = currentUser(request);


        String body = Utility.html("index.html");
        body = body.replace("{username}", user.username);
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeMessage(Request request) {
        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            Utility.log("this is post");
            data = request.form;
        } else if (request.method.equals("GET")) {
            Utility.log("this is get");
            data = request.query;
        } else {
            String m = String.format("unknown method (%s)", request.method);
            throw new RuntimeException(m);
        }
        Utility.log("get request args: %s", data);
        if (data != null) {
            MessageService.add(data);
        }

        ArrayList<Message> messageList = MessageService.load();
        Utility.log("messageList: %s", messageList);
        HashMap<String, Object> d = new HashMap<>();
        d.put("messgeList", messageList);
        String body = MyTemplate.render(d, "html_basic.ftl");
      
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeMessageAdd() {
        String body = "messageAdd";
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeImage(Request request) {
        HashMap<String, String> query = request.query;
        String filename = query.get("file");
        String dir = "static";
        String path = dir + "/" + filename;
        Utility.log("routeImage: %s", path);

        String contentType = "";
        if (path.endsWith("js")) {
            contentType = "application/javascript; charset=utf-8";
        } else if (path.endsWith("css")){
            contentType = "text/css; charset=utf-8";
        } else {
            contentType = "image/gif";
        }
        // body
        String header = String.format("HTTP/1.1 200 very OK\r\n" +
                "Content-Type: %s;\r\n" +
                "\r\n", contentType);
        byte[] body = new byte[1];
        // 读取文件
        // 如果想读取 image 文件下的文件, 就用 image/doge.gif
        try (InputStream is = Utility.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] part1 = header.getBytes(StandardCharsets.UTF_8);
        byte[] response = new byte[part1.length + body.length];
        System.arraycopy(part1, 0, response, 0, part1.length);
        System.arraycopy(body, 0, response, part1.length, body.length);

        // 也可以用 ByteArrayOutputStream
        // ByteArrayOutputStream response2 = new ByteArrayOutputStream();
        // response2.write(header.getBytes());
        // response2.write(body);
        return response;
    }

    public static byte[] route404(Request request) {
        String body = "<html><body><h1>404</h1><br><img src='/static?file=doge2.gif'></body></html>";
        String response = "HTTP/1.1 404 NOT OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
