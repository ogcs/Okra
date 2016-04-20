package org.ogcs.okra.game.command;

import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.okra.game.generated.Gpb;
import org.ogcs.okra.game.persistence.mapper.RoleMapper;
import org.ogcs.okra.game.server.SpringContext;

public abstract class AbstractCommand implements Command<Session, Gpb.Request> {

    protected RoleMapper roleMapper = (RoleMapper) AppContext.getBean(SpringContext.MAPPER_ROLE);

}
