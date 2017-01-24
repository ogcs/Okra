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

import okra.demo.common.component.Component;
import okra.demo.placement.role.PmRole;
import okra.demo.common.Role;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PK组件，玩家直接互相砌磋和复仇.
 *
 * <pre>
 *     针对开启PK功能的玩家， 注册到游戏组件中， PK别人也可以被其他人PK。
 *     保证公平性，开启功能并进攻其他玩家后，关闭需要等待1天时间后操作。 否则可以立即关闭
 *     DEMO不实现战斗日志功能。
 * </pre>
 *
 * @author TinyZ
 * @date 2017-01-13.
 */
@Service
public class PkComponent implements Component {

    /**
     * 开启PK功能的玩家列表
     */
    private Map<Long/* uid */, Role> roleMap = new ConcurrentHashMap<>();
    private Map<Long/* level Or score */, List<Role>> targets = new ConcurrentHashMap<>();

    public void lookupTarget(Session session) {
        PmRole role = (PmRole) session.getConnector();
        MemAccount memAccount = role.get();

    }

    private void getPkTarget(int level, int range) {


    }


    @Override
    public String id() {
        return null;
    }
}
