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
package org.ogcs.netty.handler;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.ogcs.app.DefaultSession;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.app.Sessions;
import org.ogcs.concurrent.ConcurrentEvent;
import org.ogcs.concurrent.ConcurrentEventFactory;
import org.ogcs.concurrent.ConcurrentHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TinyZ on 2015/10/22.
 */
public abstract class DisruptorAdapterBy41xHandler<O> extends SimpleChannelInboundHandler<O> {

    protected static final ConcurrentEventFactory FACTORY = new ConcurrentEventFactory();

    protected static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    protected static final ThreadLocal<Disruptor<ConcurrentEvent>> THREAD_LOCAL = new ThreadLocal<Disruptor<ConcurrentEvent>>() {
        @Override
        protected Disruptor<ConcurrentEvent> initialValue() {
            Disruptor<ConcurrentEvent> disruptor = new Disruptor<>(FACTORY, 1024, CACHED_THREAD_POOL, ProducerType.SINGLE, new BlockingWaitStrategy());
            disruptor.handleEventsWith(new ConcurrentHandler());
//            disruptor.handleExceptionsWith();
            disruptor.start();
            return disruptor;
        }
    };

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DefaultSession session = new DefaultSession(ctx);
        Sessions.INSTANCE.register(ctx.channel().id(), session);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, O msg) throws Exception {
        Session session = Sessions.INSTANCE.get(ctx.channel().id());
        if (null != session) {
            RingBuffer<ConcurrentEvent> ringBuffer = THREAD_LOCAL.get().getRingBuffer();
            long next = ringBuffer.next();
            try {
                ConcurrentEvent commandEvent = ringBuffer.get(next);
                commandEvent.setValues(newExecutor(session, msg));
            } finally {
                ringBuffer.publish(next);
            }
        }
    }

    protected abstract Executor newExecutor(Session session, O msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = Sessions.INSTANCE.unregister(ctx.channel().id());
        if (null != session) {
            session.release();
        }
        super.channelInactive(ctx);
    }
}
