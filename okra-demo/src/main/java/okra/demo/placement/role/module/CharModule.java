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

import okra.demo.common.Role;
import okra.demo.common.module.AbstractModule;
import okra.demo.placement.Consts;
import okra.demo.placement.mybatis.CharMapper;
import org.ogcs.app.AppContext;
import org.ogcs.okra.example.game.persistence.domain.MemChar;

/**
 * <zh-cn>
 * 管理玩家拥有的角色。
 * </zh-cn>
 *
 * @author TinyZ
 * @date 2017-01-13.
 */
public class CharModule extends AbstractModule {

    private CharMapper charMapper = AppContext.getBean(CharMapper.class);
    private MemChar memChar = new MemChar();

    public CharModule(Role role) {
        super(role);
    }

    @Override
    public int id() {
        return Consts.MODULE_TARGET;
    }

    @Override
    public void loadFromDB() {
        this.memChar = charMapper.select(role.id());
    }

    public MemChar getChar() {
        return this.memChar;
    }

    public void setTarget(MemChar memChar) {
        this.memChar = memChar;
    }

    @Override
    public void clear() {
        this.memChar = null;
    }
}
