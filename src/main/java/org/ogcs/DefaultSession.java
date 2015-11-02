package org.ogcs;

import io.netty.channel.ChannelHandlerContext;

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
    public void release() {
        ctx = null;
        if (null != player) {
            player.logout();
            player.setSession(null);
            player = null;
        }
    }
}
