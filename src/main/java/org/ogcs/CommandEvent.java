package org.ogcs;

/**
 * @author TinyZ on 2015/10/22.
 */
public class CommandEvent {

    private Session session;
    private Request request;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setValues(Session session, Request request) {
        this.session = session;
        this.request = request;
    }

    void clearValues() {
        setValues(null, null);
    }
}
