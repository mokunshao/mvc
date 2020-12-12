package MyMVC.Route;

import MyMVC.Request;
import MyMVC.Utility;
import MyMVC.models.Todo;
import MyMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Function;

public class RoutTodo {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/todo", RoutTodo::index);
        map.put("/todo/add", RoutTodo::add);
        map.put("/todo/edit", RoutTodo::edit);
        map.put("/todo/update", RoutTodo::update);
        map.put("/todo/delete", RoutTodo::delete);
        return map;
    }

    public static byte[] index(Request request) {
        String body = Utility.html("todo_index.html");
        body = body.replace("{todos}", TodoService.todoListHtml());

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] edit(Request request) {
        HashMap<String, String> query = request.query;
        Integer todoId = Integer.valueOf(query.get("id"));
        Todo todo = TodoService.findById(todoId);

        String body = Utility.html("todo_edit.html");
        body = body.replace("{todo_id}", todoId.toString());
        body = body.replace("{todo_content}", todo.content);

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        HashMap<String, String> form = request.form;
        Utility.log("add form %s", form);
        TodoService.add(form);

        return Route.redirect("/todo");
    }

    public static byte[] update(Request request) {
        HashMap<String, String> form = request.form;
        Integer todoId = Integer.valueOf(form.get("id"));
        String todoContent = form.get("content");
        TodoService.updateContent(todoId, todoContent);

        return Route.redirect("/todo");
    }

    public static byte[] delete(Request request) {
        HashMap<String, String> query = request.query;
        Utility.log("delete query %s", query);
        Integer todoId = Integer.valueOf(query.get("id"));
        TodoService.deleted(todoId);
//        TodoService.add(form);

        return Route.redirect("/todo");
    }
    
}
