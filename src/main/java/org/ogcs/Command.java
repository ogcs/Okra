package org.ogcs;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Command {

    void execute(Session session, Request request) throws Exception;
}
