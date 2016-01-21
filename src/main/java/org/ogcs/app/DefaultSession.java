package org.ogcs.app;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.ogcs.app.Player;
import org.ogcs.app.Session;

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
        if (null != ctx) {
            Channel ch = ctx.channel();
            if (null != ch && ch.isActive()) {
                if (ch.isWritable()) {
                    ch.writeAndFlush(msg);
                } else {
                    ch.eventLoop().schedule(() -> {
                        writeAndFlush(msg);
                    }, 1L, TimeUnit.SECONDS);
                }
            }
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
