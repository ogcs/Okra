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

package org.ogcs.okra.example.game.impl.placement;

import org.ogcs.app.Session;
import org.ogcs.okra.example.game.persistence.domain.MemRole;
import org.ogcs.okra.example.game.server.Role;

/**
 * @author TinyZ
 * @date 2016-07-15.
 */
public class PlaceRole extends Role {

    private static final String TABLE = "t_role_place";

    private long timeLastAtk;
    private int target;

    public PlaceRole(Session session, MemRole memRole) {
        super(session, memRole);
    }

    @Override
    public String table() {
        return TABLE;
    }




}
