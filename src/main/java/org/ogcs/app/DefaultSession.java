package org.ogcs.app;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ on 2015/10/22.
 */
public class DefaultSession implements Session {

    private ChannelHandlerContext ctx;
    private Player player;

    public DefaultSession(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ChannelHandlerContext ctx() {
        return ctx;
    }

    @Override
    public boolean isOnline() {
        return ctx != null && ctx.channel().isActive();
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void writeAndFlush(Object msg) {
        writeAndFlush(msg, null);
    }

    @Override
    public void writeAndFlush(Object message, ChannelFutureListener listener) {
        if (null == ctx || ctx.channel() == null || !ctx.channel().isActive()) {
            return;
        }
        if (ctx.channel().isWritable()) {
            if (listener == null) {
                ctx.writeAndFlush(message, ctx.voidPromise());
            } else {
                ctx.writeAndFlush(message).addListener(listener);
            }
        } else {
            ctx.channel().eventLoop().schedule(() -> {
                writeAndFlush(message, listener);
            }, 1L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void release() {
        ctx = null;
        if (null != player) {
            player.logout();
            player.setSession(null);
            player = null;
        }
    }
}
