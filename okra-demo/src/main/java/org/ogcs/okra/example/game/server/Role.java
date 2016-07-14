/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public String table() {
        return null;
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
