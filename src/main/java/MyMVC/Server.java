package MyMVC;

import MyMVC.Route.RoutTodo;
import MyMVC.Route.Route;
import MyMVC.Route.RouteAjaxTodo;
import MyMVC.Route.RouteUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

class MyServlet implements Runnable {
    Request request;
    Socket connection;


    public MyServlet(Socket connection, Request request) {
        this.connection = connection;
        this.request = request;
    }

    private static byte[] responseForPath(Request reqeust) {
        HashMap<String, Function<Request, byte[]>> routeMap = new HashMap<>();
        routeMap.putAll(Route.routeMap());
        routeMap.putAll(RouteUser.routeMap());
        routeMap.putAll(RoutTodo.routeMap());
        routeMap.putAll(RouteAjaxTodo.routeMap());


        Function<Request, byte[]> function = routeMap.getOrDefault(reqeust.path, Route::route404);
        byte[] response = function.apply(reqeust);
        return response;
    }

    @Override
    public void run() {
        byte[] response = responseForPath(this.request);
        try {
            SocketOperator.socketSendAll(this.connection, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


public class Server {
    volatile boolean shutdown;
    static ExecutorService pool = Executors.newCachedThreadPool();


    public static void main(String[] args) {
        int port = 9000;
        run(port);
    }

    private static void run(Integer port) {
        // 监听请求
        // 获取请求数据
        // 发送响应数据
        // 我们的服务器使用 9000 端口
        // 不使用 80 的原因是 1024 以下的端口都要管理员权限才能使用
        // int port = 9000;
        Utility.log("服务器启动, 访问 http://localhost:%s", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                // accept 方法会一直停留在这里等待连接
                try  {
                    Socket socket = serverSocket.accept();
                    // 客户端连接上来了
                    Utility.log("client 连接成功");
                    // 读取客户端请求数据
                    String request = SocketOperator.socketReadAll(socket);
                    byte[] response;
                    if (request.length() > 0) {
                        // 输出响应的数据
                        Utility.log("请求:\n%s", request);
                        // 解析 request 得到 path
                        Request r = new Request(request);

                        MyServlet servlet = new MyServlet(socket, r);
                        Thread t = new Thread(servlet);
                        pool.execute(t);

//                        // 根据 path 来判断要返回什么数据
//                        response = responseForPath(r);
                    } else {
                        response = new byte[1];
                        Utility.log("接受到了一个空请求");
                        SocketOperator.socketSendAll(socket, response);
                    }
                } finally {
                    
                }
            }
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }



    private static byte[] responseForPath(Request reqeust) {
        HashMap<String, Function<Request, byte[]>> routeMap = new HashMap<>();
        routeMap.putAll(Route.routeMap());
        routeMap.putAll(RouteUser.routeMap());
        routeMap.putAll(RoutTodo.routeMap());
        routeMap.putAll(RouteAjaxTodo.routeMap());


        Function<Request, byte[]> function = routeMap.getOrDefault(reqeust.path, Route::route404);
        byte[] response = function.apply(reqeust);
        return response;
    }

}
