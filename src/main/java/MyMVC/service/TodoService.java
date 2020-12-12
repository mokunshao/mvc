package MyMVC.service;

import MyMVC.Utility;
import MyMVC.models.ModelFactory;
import MyMVC.models.Todo;

import java.util.ArrayList;
import java.util.HashMap;

// impot ../Utils

public class TodoService {
    public static Todo add(HashMap<String, String> form) {
        String content = form.get("content");
        Todo m = new Todo();
        m.content = content;
        m.completed = false;

        ArrayList<Todo> all = load();
        if (all.size() > 0) {
            m.id = all.get(all.size() - 1).id + 1;
        } else {
            m.id = 1;
        }
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Todo> list) {
        String className = Todo.class.getSimpleName();
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.content);
            lines.add(model.completed.toString());
            return lines;
        });
    }

    public static ArrayList<Todo> load() {
        String className = Todo.class.getSimpleName();
        ArrayList<Todo> all = ModelFactory.load(className, 3, modelData -> {
            Integer id = Integer.valueOf(modelData.get(0));
            String content = modelData.get(1);
            Boolean completed = Boolean.valueOf(modelData.get(2));

            Todo m = new Todo();
            m.id = id;
            m.content = content;
            m.completed = completed;
            return m;
        });

        return all;
    }
    
    public static void deleted(Integer id) {
        Utility.log("deleted");
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                ms.remove(e);
            }
        }

        save(ms);
    }

    public static String todoListHtml() {
        ArrayList<Todo> all = TodoService.load();
        StringBuilder sb = new StringBuilder();

        for (Todo m:all) {
            String todoHtml = String.format(
                    "<h3>\n" +
                    "   %s: %s\n" +
                    "   <a href=\"/todo/edit?id=%s\">编辑</a>\n" +
                    "   <a href=\"/todo/delete?id=%s\">删除</a>\n" +
                    "</h3>",
                    m.id,
                    m.content,
                    m.id,
                    m.id
            );
            sb.append(todoHtml);
        }

        return sb.toString();
    }
    
    public static Todo findById(Integer id) {
        ArrayList<Todo> all = TodoService.load();
        for (int i = 0; i < all.size(); i++) {
            Todo e = all.get(i);
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }

    public static void updateContent(Integer id, String content) {
        ArrayList<Todo> all = TodoService.load();
        for (int i = 0; i < all.size(); i++) {
            Todo e = all.get(i);
            if (e.id.equals(id)) {
                e.content = content;
            }
        }
       save(all);
    }
}

