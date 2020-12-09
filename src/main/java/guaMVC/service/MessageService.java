package guaMVC.service;

import guaMVC.models.Message;
import guaMVC.models.ModelFactory;

import java.util.ArrayList;
import java.util.HashMap;


public class MessageService {
    public static Message add(HashMap<String, String> form) {
        String author = form.get("author");
        String message = form.get("message");
        Message m = new Message();
        m.author = author;
        m.message = message;

        ArrayList<Message> all = load();
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Message> list) {
        String className = Message.class.getSimpleName();
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.author);
            lines.add(model.message);
            return lines;
        });
    }

    public static ArrayList<Message> load() {
        String className = Message.class.getSimpleName();
        ArrayList<Message> all = ModelFactory.load(className, 2, modelData -> {
            String author = modelData.get(0);
            String message = modelData.get(1);

            Message m = new Message();
            m.author = author;
            m.message = message;
            return m;
        });

        return all;
    }


    public static String messageListShowString() {
        ArrayList<Message> all = load();
        StringBuilder result = new StringBuilder();

        for (Message m: all) {
            String s = String.format("%s: %s <br>", m.author, m.message);
            result.append(s);
        }
        return result.toString();
    }
}
