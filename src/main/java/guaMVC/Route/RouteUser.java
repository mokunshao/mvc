package guaMVC.Route;

import guaMVC.Request;
import guaMVC.Utility;
import guaMVC.models.User;
import guaMVC.service.SessionService;
import guaMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class RouteUser {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/login", RouteUser::login);
        map.put("/register", RouteUser::register);

        return map;
    }

    public static byte[] login(Request request) {
        String loginResult = "";
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        if (request.method.equals("POST")) {
            HashMap<String, String> data = request.form;
            boolean valid = UserService.validLogin(data);
            if (valid) {
                String username = data.get("username");
                User current = UserService.findByName(username);
                String sessionId = UUID.randomUUID().toString();
                SessionService.add(sessionId, current.id);
                header.put("Set-Cookie", String.format("session_id=%s", sessionId));
                loginResult = "登录成功";
            } else {
                loginResult = "登录失败";
            }
        }

        String body = Utility.html("login.html");
        body = body.replace("{loginResult}", loginResult);



        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] register(Request request) {
        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            data = request.form;
        }

        String registerResult = "";
        if (data != null) {
            Utility.log("register form %s", data);
            UserService.add(data);
        }

        String body = Utility.html("register.html");
        body = body.replace("{registerResult}", registerResult);

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
