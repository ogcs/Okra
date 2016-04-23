package org.ogcs.okra.example.game.server;

import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.persistence.domain.MemRole;
import org.ogcs.okra.example.game.persistence.mapper.RoleMapper;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public class Role implements Connector {

    private RoleMapper roleMapper = (RoleMapper) AppContext.getBean(SpringContext.APP_CONTEXT);

    private Session session;
    private Long id;//   uid
    private MemRole memRole;

    public Role(MemRole memRole) {
        this.id = memRole.getUid();
        this.memRole = memRole;
    }

    public Role(Session session, MemRole memRole) {
        this.session = session;
        this.memRole = memRole;
        this.id = memRole.getUid();
    }

    public MemRole select() {
        return memRole;
    }

    public void update() {
        roleMapper.update(memRole);
    }

    @Override
    public boolean isConnected() {
        return session != null && session.isOnline();
    }

    @Override
    public Session session() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void disconnect() {

        System.out.println("离线:" + session.toString());
    }
}
