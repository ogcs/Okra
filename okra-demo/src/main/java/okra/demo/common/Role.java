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

package okra.demo.common;

import okra.demo.common.module.Module;
import org.ogcs.app.Connector;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;

/**
 * @author TinyZ
 * @date 2017-01-13.
 */
public interface Role extends Connector {

    MemAccount get();

    void update();

    String table();

    long id();

    void lazyLoad();

    void registerModule(Module module);

    <T extends Module> T getModule(int module);
}
