package org.ogcs.app;

/**
 *
 * Command
 *
 * @author TinyZ on 2015/10/22.
 */
public interface Command<S, R> {

    /**
     * Execute cmd logic
     *
     * @param session The player session
     * @param request The cmd request
     * @throws Exception
     */
    void execute(S session, R request) throws Exception;
}
