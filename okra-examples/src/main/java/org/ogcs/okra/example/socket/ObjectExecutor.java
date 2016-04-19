package org.ogcs.okra.example.socket;

import org.ogcs.app.Executor;
import org.ogcs.app.Session;

/**
 *
 */
public class ObjectExecutor implements Executor {

    protected Session session;
    protected Object request;

    public ObjectExecutor(Session session, Object request) {
        this.session = session;
        this.request = request;
    }

    @Override
    public void onExecute() {
        if (null == request) {
            throw new NullPointerException("request");
        }
        // TODO: Just send message back, do some logic on real
        session.writeAndFlush(String.valueOf(request));
    }

    @Override
    public void release() {
        this.session = null;
        this.request = null;
    }
}
