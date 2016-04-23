package org.ogcs.app;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * Session.
 * <p>
 * When player connect to server. server will be create a session for everybody.
 * The session control the connection and player's data.
 */
public interface Session extends Releasable {

    /**
     * Get the Netty's ChannelHandlerContext
     *
     * @return Return Netty's ChannelHandlerContext
     */
    ChannelHandlerContext ctx();

    /**
     * Is session is active
     *
     * @return Return true if the session is active. otherwise false.
     */
    boolean isOnline();

    /**
     * Get the player
     *
     * @return Return player
     */
    Connector getConnector();

    /**
     * Set player. After player login, should set the player.
     *
     * @param connector The player
     */
    void setConnector(Connector connector);

    /**
     * Send message back without callback.
     *
     * @param msg The message will be send
     */
    void writeAndFlush(Object msg);

    /**
     * Send message back with callback.
     *
     * @param message  The message will be send
     * @param listener The callback
     */
    void writeAndFlush(Object message, ChannelFutureListener listener);

    /**
     * Will be invoked when player offline.
     */
    void offline();
}
