package org.ogcs;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Command<S, R> {

    /**
     * Execute command logic
     *
     * @param session The player session
     * @param request The command request
     * @throws Exception
     */
    void execute(S session, R request) throws Exception;
}
