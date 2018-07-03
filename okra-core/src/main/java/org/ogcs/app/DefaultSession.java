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
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;

/**
 * Default session implement.
 *
 * <note>
 *     Suggest use {@link NetSession} replace {@link DefaultSession}.
 * </note>
 *
 * @see NetSession
 * @since 1.0
 */
@Deprecated
public class DefaultSession implements Session {

    private volatile ChannelHandlerContext ctx;
    private volatile Connector connector;

    public DefaultSession(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext ctx() {
        return ctx;
    }

    @Override
    public Channel channel() {
        return ctx.channel();
    }

    @Override
    public boolean isActive() {
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
    public void active() {
        if (null != connector)
            connector.sessionActive();
    }

    @Override
    public void inactive() {
        if (null != connector) {
            connector.sessionInactive();
            connector = null;
        }
    }

    @Override
    public void offline() {
        if (ctx != null) {
            ctx.close();
        }
    }

    @Override
    public void close() {
        Channel channel = ctx.channel();
        if (channel != null) {
            inactive();
            if (channel.isActive())
                channel.close();
            ctx = null;
        }
    }
}