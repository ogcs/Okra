package org.ogcs.app;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;

/**
 * Default session implement.
 */
public class DefaultSession implements Session {

    private ChannelHandlerContext ctx;
    private Connector connector;

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

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
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
    public void offline() {
        if (ctx != null) {
            ctx.close();
        }
    }

    @Override
    public void release() {
        ctx = null;
        if (null != connector) {
            connector.disconnect();
            connector.setSession(null);
            connector = null;
        }
    }
}
