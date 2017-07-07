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

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

/**
 * Session.
 * <p>
 * When player connect to server. server will be create a session for everybody.
 * The session control the connection and player's data.
 * @author TinyZ.
 * @version  2017.07.07
 * @since 1.0
 */
public interface Session extends AutoCloseable {

    /**
     * Get the netty's channel
     *
     * @return return netty's channel
     */
    Channel channel();

    /**
     * Is session is active
     *
     * @return Return true if the session is active. otherwise false.
     */
    boolean isActive();

    /**
     * Get the player
     *
     * @return Return player
     */
    Connector getConnector();

    /**
     * Set {@link Connector}. After player login, should set the player.
     *
     * @param connector The player
     */
    void setConnector(Connector connector);

    /**
     * Send message back without listener.
     *
     * @param msg The message will be send
     */
    void writeAndFlush(Object msg);

    /**
     * Send message back with listener.
     *
     * @param message The message will be send
     */
    void writeAndFlush(Object message, ChannelFutureListener listener);

    /**
     * the session active event. When the netty channel active.
     * the session will be trigger active event.
     */
    void active();

    /**
     * the session inactive event.When the netty channel inactive.
     * the session will be trigger inactive event.
     */
    void inactive();

    /**
     * when player logout, call this method to clear player data.
     */
    void offline();

    /**
     * Close netty's channel.
     */
    void close();
}
