package org.ogcs.okra.example.game.command;

import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.generated.Gpb;
import org.ogcs.okra.example.game.persistence.mapper.RoleMapper;
import org.ogcs.okra.example.game.server.SpringContext;

public abstract class AbstractCommand implements Command<Session, Gpb.Request> {

    protected RoleMapper roleMapper = (RoleMapper) AppContext.getBean(SpringContext.MAPPER_ROLE);

}
