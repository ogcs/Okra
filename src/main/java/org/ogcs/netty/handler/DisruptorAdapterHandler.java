package org.ogcs.netty.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.channel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Executor;
import org.ogcs.app.DefaultSession;
import org.ogcs.app.Session;
import org.ogcs.concurrent.ConcurrentEvent;
import org.ogcs.concurrent.ConcurrentEventFactory;
import org.ogcs.concurrent.ConcurrentHandler;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Disruptor Adapter Handler
 * <example>
 *     <p/>
 *         Netty 4.1.x version add the ChannelId as {@link io.netty.channel.Channel}'s unique id.
 *         Use {@link UUID} replace the {@link io.netty.channel.ChannelId}.
 * </example>
 * @author TinyZ on 2015/10/22.
 */
public abstract class DisruptorAdapterHandler<O> extends SimpleChannelInboundHandler<O> {

    private static final Logger LOG = LogManager.getLogger(DisruptorAdapterHandler.class);

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

    public static final ConcurrentHashMap<UUID, Session> SESSIONS = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<Channel, UUID> CHANNEL_UUID = new ConcurrentHashMap<>();

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
        if (null != session) { // TODO: 释放无用的资源
            session.release();
//            System.out.println("channelInactive" + INACTIVE.getAndIncrement());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            LOG.info("远程主机强迫关闭了一个现有的连接 : " + ctx.channel().remoteAddress().toString() + " => " + ctx.channel().localAddress().toString());
        } else if (cause.getCause() instanceof InvalidProtocolBufferException) {
            LOG.info("Invalid protobuf message : " + cause.getMessage());
            super.exceptionCaught(ctx, cause);
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
