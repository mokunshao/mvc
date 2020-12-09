package guaMVC.Route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import guaMVC.GuaTemplate;
import guaMVC.Request;
import guaMVC.Utility;
import guaMVC.models.Todo;
import guaMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteAjaxTodo {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/ajax/todo/index", RouteAjaxTodo::indexView);
        map.put("/ajax/todo/add", RouteAjaxTodo::add);
        map.put("/ajax/todo/all", RouteAjaxTodo::all);

        return map;
    }

    public static byte[] indexView(Request request) {
        HashMap<String, Object> d = new HashMap<>();
        String body = GuaTemplate.render(d, "ajax_todo.ftl");


        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        String jsonString = request.body;
        Utility.log("ajax add form %s", jsonString);
        JSONObject jsonForm = JSON.parseObject(jsonString);
        String content = jsonForm.getString("content");

        HashMap<String, String> form = new HashMap<>();
        form.put("content", content);
        Todo todo = TodoService.add(form);
        String jsonTodo = JSON.toJSONString(todo);
        Utility.log("json todo: %s", jsonTodo);

//        TodoService.add(form);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");
        String response = Route.responseWithHeader(200, headers, jsonTodo);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] all(Request request) {
        ArrayList<Todo> arrayList = TodoService.load();
        String jsonTodo = JSON.toJSONString(arrayList);
        Utility.log("json  all todo: %s", jsonTodo);

//        TodoService.add(form);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");
        String response = Route.responseWithHeader(200, headers, jsonTodo);

        return response.getBytes(StandardCharsets.UTF_8);
    }
}
