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

package okra.demo.placement.role;

import okra.demo.common.module.impl.ItemModule;
import okra.demo.common.module.impl.TargetModule;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.server.DefaultRole;

/**
 * @author TinyZ
 */
public class PmRole extends DefaultRole {

    private static final String TABLE = "t_role_place";

    private long timeLastAtk;
    private int target;
    private volatile long lastFetchReward;

    public PmRole(MemAccount memAccount) {
        super(memAccount);
    }

    public PmRole(Session session, MemAccount memAccount) {
        super(session, memAccount);
    }

    @Override
    public String table() {
        return TABLE;
    }

    @Override
    public void lazyLoad() {
        registerModule(new ItemModule(this));
        registerModule(new TargetModule(this));
        super.lazyLoad();
    }
}
