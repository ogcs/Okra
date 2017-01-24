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

package okra.demo.common.module.impl;

import okra.demo.common.Consts;
import okra.demo.common.Role;
import okra.demo.common.module.AbstractModule;

/**
 * <pre>不在开发计划中</pre>
 * PK开关, 记录被攻击日志， 复仇日志等
 *
 * @author TinyZ
 * @date 2017-01-13.
 */
public class PkModule extends AbstractModule {

    public PkModule(Role role) {
        super(role);
    }

    @Override
    public int id() {
        return Consts.MODULE_TARGET;
    }

    @Override
    public void loadFromDB() {

    }

    @Override
    public void clear() {

    }
}
