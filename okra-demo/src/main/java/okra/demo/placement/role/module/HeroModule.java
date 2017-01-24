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

package okra.demo.placement.role.module;

import okra.demo.placement.bean.MemTarget;
import okra.demo.common.module.AbstractModule;
import okra.demo.placement.mybatis.TargetMapper;
import okra.demo.placement.Consts;
import okra.demo.common.Role;
import org.ogcs.app.AppContext;

/**
 * 玩家拥有武将, 养成属于自己的战斗序列
 * @author TinyZ
 * @date 2017-01-13.
 */
public class HeroModule extends AbstractModule {

    private TargetMapper targetMapper = AppContext.getBean(TargetMapper.class);
    private MemTarget memTarget = new MemTarget();

    public HeroModule(Role role) {
        super(role);
    }

    @Override
    public int id() {
        return Consts.MODULE_TARGET;
    }

    @Override
    public void loadFromDB() {
        this.memTarget = targetMapper.select(role.id());
        if (this.memTarget == null) {
            this.memTarget = new MemTarget();
        }
    }

    public MemTarget getTarget() {
        return this.memTarget;
    }

    public void setTarget(MemTarget memTarget) {
        this.memTarget = memTarget;
    }

    @Override
    public void clear() {
        this.memTarget = null;
    }
}
