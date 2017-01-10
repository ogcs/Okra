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

package okra.demo.placement;

import org.ogcs.app.Session;
import okra.demo.placement.module.ItemModule;
import okra.demo.placement.module.Module;
import org.ogcs.okra.example.game.persistence.domain.MemRole;
import org.ogcs.okra.example.game.server.Role;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ
 * @date 2016-07-15.
 */
public class PlaceRole extends Role {

    private static final String TABLE = "t_role_place";

    private long timeLastAtk;
    private int target;

    private Map<Integer, Module> modules = new ConcurrentHashMap<>();

    public PlaceRole(MemRole memRole) {
        super(memRole);
    }

    public PlaceRole(Session session, MemRole memRole) {
        super(session, memRole);
    }

    @Override
    public String table() {
        return TABLE;
    }

    /**
     * 玩家登录成功后加载私人数据
     */
    public void lazyLoad() {
        registerModule(new ItemModule());
        //  initialize modules.
        for (Module module : modules.values()) {
            module.init();
        }
    }

    public void registerModule(Module module) {
        modules.put(module.id(), module);
    }

    /**
     * 获取角色的模块
     * <pre>注意使用: 避免强制类型转换错误<pre/>
     */
    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(int module) {
        return (T) modules.get(module);
    }




}
