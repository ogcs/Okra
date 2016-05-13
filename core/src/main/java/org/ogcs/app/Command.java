package org.ogcs.app;

/**
 * Command interface.
 *
 * Each user's request, it can be seen as a command.
 *
 * @author TinyZ
 * @since 1.0
 */
public interface Command<S, R> {

    /**
     * Execute cmd logic
     *
     * @param session The connector's session
     * @param request The cmd request
     * @throws Exception
     */
    void execute(S session, R request) throws Exception;
}
