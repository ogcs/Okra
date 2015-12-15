package org.ogcs.app;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Session {

    ChannelHandlerContext ctx();

    boolean isOnline();

    Player getPlayer();

    void setPlayer(Player player);

    void writeAndFlush(Object msg);

    void release();
}
