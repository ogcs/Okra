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
 * Make the server side managing client's connection easier. <br/>
 * Every client connection and connector instance is one-to-one correspondence.
 * <p>
 * <pre>
 *     <lang desc="zh-cn">
 *         连接者接口. 服务端用来管理连接的bean.
 *         每一个客户端成功连接到服务端后，服务端创建一个{@link Connector}用以管理客户端连接.
 *     </lang>
 * </pre>
 *
 * @author TinyZ.
 * @since 2.0
 */
public interface Connector<S extends Session> {

    /**
     * Is the player online
     *
     * @return Return true if the session is connected and channel is active. otherwise false.
     */
    boolean isOnline();

    /**
     * Get the player's session
     *
     * @return Return player's session
     */
    S session();

    /**
     * Set player's session. After player's login operation, should set call this function to set session.
     *
     * @param session The session for player
     */
    void setSession(S session);

    /**
     * After player create connection and connected.
     */
    void sessionActive();

    /**
     * When the player lost connection to server. System will call this function.
     */
    void sessionInactive();
}
