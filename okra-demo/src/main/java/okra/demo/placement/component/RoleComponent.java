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

package okra.demo.placement.component;

import okra.demo.common.Role;
import okra.demo.common.annotation.PublicApi;
import okra.demo.common.component.Component;
import okra.demo.placement.Consts;
import okra.demo.placement.json.JsonSession;
import okra.demo.placement.logic.Tx;
import okra.demo.placement.role.PmRole;
import okra.demo.placement.role.module.CharModule;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.persistence.domain.MemChar;
import org.ogcs.okra.example.game.persistence.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ
 * @date 2017-01-13.
 */
@Service
public class RoleComponent implements Component {

    //  保存角色信息
    // 当角色数量过大时，避免无限制内存占有，应该使用可释放的cache，限定长度或者权重来保存. 例如Guava的Cache
    public Map<Long/* uid */, Role> uid2RoleMap = new ConcurrentHashMap<>();
    public Map<String/* account */, Role> account2RoleMap = new ConcurrentHashMap<>();
    public Map<String/* name */, Role> name2RoleMap = new ConcurrentHashMap<>();

    @Autowired
    RoleMapper roleMapper;

    @Override
    public String id() {
        return String.valueOf(Consts.COMPONENT_ROLE);
    }

    /**
     * 登录
     * @param account   帐号
     */
    @PublicApi
    public void login(JsonSession session, String account) {
        Role role = account2RoleMap.get(account);
        if (role == null) {
            MemAccount memAccount = roleMapper.select(account);
            if (memAccount != null) {
                role = new PmRole(memAccount);
                session.callback().callbackLogin(memAccount);
            }
        }
        if (role == null) {
//            session.writeAndFlush();
            return;
        }
        session.setConnector(role);

    }

    /**
     * 创建角色
     * @param uid   唯一uid
     */
    @PublicApi
    public void createRole(JsonSession session, long uid) {
        PmRole role = (PmRole) session.getConnector();
        MemChar memChar = null;
        CharModule module = role.module(Consts.MODULE_CHAR);
        if (module == null) {

            return;
        }
        memChar = module.getChar();
        if (memChar == null) {
            memChar = Tx.INSTANCE.createChar(role.id(), "", 1);
        }
        //  创建角色失败
        if (memChar == null) {

        }
        session.callback().callbackCreateRole(memChar);
    }
}
