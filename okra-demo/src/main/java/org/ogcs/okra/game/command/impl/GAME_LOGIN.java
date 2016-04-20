package org.ogcs.okra.game.command.impl;

import org.ogcs.app.Session;
import org.ogcs.okra.game.command.AbstractCommand;
import org.ogcs.okra.game.generated.Example;
import org.ogcs.okra.game.generated.Gpb;
import org.ogcs.okra.game.generated.Gpb.Request;
import org.ogcs.okra.game.persistence.domain.MemRole;
import org.ogcs.okra.game.server.Role;

/**
 * Game login command
 */
public class GAME_LOGIN extends AbstractCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        Example.MsgLogin msgLogin = Example.MsgLogin.parseFrom(request.getData());

        // Get role by account. Also we can cache role in memory.
        MemRole memRole = roleMapper.select(msgLogin.getAccount());
        if (!msgLogin.getPsw().equals(memRole.getPsw())) {
            // TODO: password is wrong
            return;
        }

        // TODO: do some logic content

        Role player = new Role(session, memRole);
        // session set player.
        // The player's function logout()  will be invoked, When the session is inactive.
        session.setPlayer(player);

        session.writeAndFlush(Gpb.Response.newBuilder()
                .setId(request.getId())
                .setData(request.getData())
                .build());
    }
}
