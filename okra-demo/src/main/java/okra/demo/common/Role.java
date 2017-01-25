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
 * Game Role Interface.
 * The role instance comprise by many module and player's base information.
 * These modules manage role's optional data or information.
 * <pre>
 *     Example :
 *     Role -   Player's Base Information.
 *          |-  ItemModule
 *          |-  PVEModule
 *          |-  xxxModule
 *      note 1. ItemModule : Player have many items in some game, some don't.
 *      The ItemModule make easy to manage player's items
 * </pre>
 *
 * @author TinyZ
 * @date 2017-01-13.
 * @since 1.0
 */
public interface Role extends Connector {

    /**
     * @return Return player's base information bean.
     */
    MemAccount get();

    /**
     * Update player's base infomation.
     */
    void update();

    /**
     * @return Return the save player base information's database table name.
     */
    String table();

    /**
     * @return Return the player's unique id
     */
    long id();

    /**
     * Lazy load and init the module.
     */
    void lazyLoad();

    /**
     * Register role module.
     *
     * @param module The role's module.
     */
    void registerModule(Module module);

    /**
     * Get the module by module's unique id.
     *
     * @param module the module's unique id.
     * @param <T>    the module's type.
     * @return Return the module instance.
     */
    <T extends Module> T module(int module);

    /**
     * Get the module by module type.
     *
     * @param clz the module's type.
     * @return Return the module instance.
     */
    <T extends Module> T module(Class<T> clz);
}
