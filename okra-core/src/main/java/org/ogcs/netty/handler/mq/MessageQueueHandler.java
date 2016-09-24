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
package org.ogcs.netty.handler.mq;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.ogcs.app.DefaultSession;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于同步消息队列的单生产者的Disruptor模式
 *
 * @author TinyZ.
 * @since 1.0.
 */
@Sharable
public abstract class MessageQueueHandler<O> extends SimpleChannelInboundHandler<O> {

    private static final ConcurrentHashMap<UUID, Session> SESSIONS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Channel, UUID> CHANNEL_UUID = new ConcurrentHashMap<>();
    protected LogicProcessor processor;

    public MessageQueueHandler(LogicProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UUID uuid = UUID.randomUUID();
        CHANNEL_UUID.put(ctx.channel(), uuid);
        DefaultSession session = new DefaultSession(ctx);
        SESSIONS.put(uuid, session);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, O msg) throws Exception {
        UUID uuid = CHANNEL_UUID.get(ctx.channel());
        if (null != uuid) {
            Session session = SESSIONS.get(uuid);
            if (null != session) {
                this.processor.addRequest(newExecutor(session, msg));
            }
        }
    }

    protected abstract Executor newExecutor(Session session, O msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        UUID uuid = CHANNEL_UUID.remove(ctx.channel());
        if (null != uuid) {
            sessionInactive(SESSIONS.remove(uuid));
        }
        super.channelInactive(ctx);
    }

    protected void sessionInactive(Session session) {
        if (null != session) {
            session.release();
        }
    }
}
