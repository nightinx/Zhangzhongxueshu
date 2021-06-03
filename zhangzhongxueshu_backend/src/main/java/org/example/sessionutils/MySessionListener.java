package org.example.sessionutils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MySessionListener implements HttpSessionAttributeListener,HttpSessionListener{
    public void sessionCreate(HttpSessionEvent sessionEvent) {
        MySessionContext.addSession(sessionEvent.getSession());
        //System.out.println(sessionEvent.getSession().getId()+" added");
    }

    public void sessionDestroy(HttpSessionEvent sessionEvent) {
        MySessionContext.removeSession(sessionEvent.getSession());
        //System.out.println(sessionEvent.getSession().getId()+" removed");
    }
}
