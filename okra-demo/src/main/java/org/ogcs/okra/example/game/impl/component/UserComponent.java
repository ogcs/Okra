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

package org.ogcs.okra.example.game.impl.component;

import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.persistence.mapper.RoleMapper;
import org.ogcs.okra.example.game.server.DefaultRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ
 * @date 2017-01-11.
 */
@Service("userComponent")
public class UserComponent {

    private static final Map<Long/*uid*/, DefaultRole> uid2Role = new ConcurrentHashMap<>();
    private static final Map<String/*name*/, DefaultRole> name2Role = new ConcurrentHashMap<>();

    @Autowired
    RoleMapper roleMapper;




    public DefaultRole getRoleByUid(long uid) {
        if (uid2Role.containsKey(uid)) {
            return uid2Role.get(uid);
        } else {
            MemAccount memAccount = roleMapper.selectByUid(uid);
            if (memAccount == null) {
                uid2Role.put(uid, null);
            } else {
                DefaultRole role = new DefaultRole(memAccount);
                uid2Role.put(memAccount.getUid(), role);
                return role;
            }
        }
        return null;
    }

    public DefaultRole getRoleByUid(String name) {
        return name2Role.get(name);
    }






}
