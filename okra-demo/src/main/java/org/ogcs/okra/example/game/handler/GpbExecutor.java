package org.ogcs.okra.example.game.handler;

import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.command.Commands;
import org.ogcs.okra.example.game.generated.Gpb;
import org.ogcs.okra.example.game.generated.Gpb.Request;

public class GpbExecutor implements Executor {

    protected Session session;
    protected Request request;

    public GpbExecutor(Session session, Request request) {
        this.session = session;
        this.request = request;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onExecute() {
        if (null == request) {
            throw new NullPointerException("request");
        }
        int api = request.getApi();
        if (!isLogin(session) && !Commands.INSTANCE.isCmdWithoutAuth(api)) {
            // TODO: custom wrapper to make reply easy.
            session.writeAndFlush(Gpb.Response.newBuilder()
                    .setId(request.getId())
                    .setError(Gpb.Error.newBuilder()
                            .setRet(-100)
                            .build())
                    .build());
            return;
        }
        try {
            Command command = Commands.INSTANCE.interpret(api);
            if (command != null) {
                command.execute(session, request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLogin(Session session) {
        return session.getPlayer() != null;
    }

    @Override
    public void release() {
        this.session = null;
        this.request = null;
    }
}
