package org.ogcs.app;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Session extends Releasable {

    ChannelHandlerContext ctx();

    boolean isOnline();

    Player getPlayer();

    void setPlayer(Player player);

    void writeAndFlush(Object msg);

    void writeAndFlush(Object message, ChannelFutureListener listener);

    void offline();
}
