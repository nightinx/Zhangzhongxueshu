package org.example.sessionutils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class MySessionContext {
    private static Map<String, HttpSession> myMap = new HashMap<>();
    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            myMap.put(session.getId(), session);
            //System.out.println(session.getId()+" added");
        }
    }

    public static synchronized void removeSession(HttpSession session) {
        if (session != null) {
            myMap.remove(session.getId());
            //System.out.println(session.getId()+" removed");
        }
    }

    public static synchronized HttpSession getSession(String sessionId) {
        //System.out.println("get session");
        return myMap.get(sessionId);
    }
}
