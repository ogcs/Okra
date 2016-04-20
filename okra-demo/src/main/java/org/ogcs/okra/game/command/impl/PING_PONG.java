package org.ogcs.okra.game.command.impl;

import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.okra.game.generated.Gpb;
import org.ogcs.okra.game.generated.Gpb.Request;

/**
 * Just use to test
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/19
 */
public class PING_PONG implements Command<Session, Request> {
    @Override
    public void execute(Session session, Request request) throws Exception {

        // do some logic content

        session.writeAndFlush(Gpb.Response.newBuilder()
                .setId(request.getId())
                .setData(request.getData())
        .build());
    }
}
