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

import okra.demo.common.Role;
import okra.demo.common.module.Module;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.persistence.mapper.RoleMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/3/31
 */
public class DefaultRole implements Role {

    private static final String TABLE = "tb_role";
    //  Modules
    private Map<Integer, Module> modules = new ConcurrentHashMap<>();
    private Map<Class<? extends Module>, Module> moduleByClz = new ConcurrentHashMap<>();

    private RoleMapper roleMapper;

    private final long id;      //   uid
    private Session session;
    private MemAccount memAccount;

    public Map<Integer, Map<Long, Long>> busProgressMap = new ConcurrentHashMap<>();

    public DefaultRole(MemAccount memAccount) {
        this.id = memAccount.getUid();
        this.memAccount = memAccount;
    }

    public DefaultRole(Session session, MemAccount memAccount) {
        this.session = session;
        this.memAccount = memAccount;
        this.id = memAccount.getUid();
    }

    @Override
    public MemAccount get() {
        return memAccount;
    }

    @Override
    public void update() {
        roleMapper.update(memAccount);
    }

    @Override
    public String table() {
        return TABLE;
    }

    /**
     * An unique ID.
     */
    @Override
    public long id() {
        return id;
    }

    /**
     * 玩家登录成功后加载私人数据
     */
    @Override
    public void lazyLoad() {
        //  initialize modules.
        for (Module module : modules.values()) {
            module.init();
        }
    }

    @Override
    public void registerModule(Module module) {
        modules.put(module.id(), module);
        moduleByClz.put(module.getClass(), module);
    }

    /**
     * 获取角色的模块
     * <pre>注意使用: 避免强制类型转换错误<pre/>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Module> T module(int module) {
        return (T) modules.get(module);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Module> T module(Class<T> clz) {
        if (!moduleByClz.containsKey(clz)) {
            throw new IllegalStateException("Unknown Module : " + clz.getName());
        }
        return (T) moduleByClz.get(clz);
    }

    @Override
    public boolean isOnline() {
        return session != null && session.isActive();
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
    public void sessionActive() {

    }

    @Override
    public void sessionInactive() {

        System.out.println("离线:" + session.toString());
    }
}
