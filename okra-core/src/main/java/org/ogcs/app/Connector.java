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
package org.ogcs.app;

/**
 * The Connector Interface.
 * User should be implement this interface.
 * @author TinyZ
 */
public interface Connector {

    /**
     * Is the player online
     * @return Return true if the session is connected and channel is active. otherwise false.
     */
    boolean isConnected();

    /**
     * Get the player's session
     * @return Return player's session
     */
    Session session();

    /**
     * Set player's session. After player's login operation, should set call this function to set session.
     * @param session The session for player
     */
    void setSession(Session session);

    /**
     * When the player lost connection to server. System will call this function.
     */
    void disconnect();
}
