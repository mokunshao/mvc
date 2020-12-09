package guaMVC.service;

import guaMVC.models.ModelFactory;
import guaMVC.models.Session;

import java.util.ArrayList;

// impot ../Utils

public class SessionService {
    public static Session add(String sessionId, Integer userId) {
        Session m = new Session();
        m.userId = userId;
        m.sessionId = sessionId;

        ArrayList<Session> all = load();
        all.add(m);
        save(all);
        return m;
    }

    public static void save(ArrayList<Session> list) {
        String className = Session.class.getSimpleName();
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.sessionId);
            lines.add(model.userId.toString());
            return lines;
        });
    }

    public static ArrayList<Session> load() {
        String className = Session.class.getSimpleName();
        ArrayList<Session> all = ModelFactory.load(className, 2, modelData -> {
            String sessionId = modelData.get(0);
            Integer userId = Integer.valueOf(modelData.get(1));

            Session m = new Session();
            m.userId = userId;
            m.sessionId = sessionId;
            return m;
        });

        return all;
    }
    
    
    public static Session findBySessionId(String sessionId) {
        ArrayList<Session> all = SessionService.load();
        for (int i = 0; i < all.size(); i++) {
            Session e = all.get(i);
            if (e.sessionId.equals(sessionId)) {
                return e;
            }
        }
        return null;
    }
}

