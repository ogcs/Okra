package org.ogcs.cmd;

import org.ogcs.Command;
import org.ogcs.app.Session;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Request {

    Command<Session, Request> command();

    int getRequestId();

    void setRequestId(int requestId);

    Object getParams();

    void setParams(Object params);

    void release();
}
