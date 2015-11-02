package org.ogcs;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.channel.*;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TinyZ on 2015/10/22.
 */
public class OgcsServerHandler extends SimpleChannelInboundHandler<Request> {

    public static final ThreadLocal<Disruptor<CommandEvent>> THREAD_LOCAL = new InheritableThreadLocal<Disruptor<CommandEvent>>() {
        @Override
        protected Disruptor<CommandEvent> initialValue() {
            Disruptor<CommandEvent> disruptor = new Disruptor<>(
                    new EventFactory<CommandEvent>() {
                        @Override
                        public CommandEvent newInstance() {
                            return new CommandEvent();
                        }
                    }
                    , 1024, Executors.newCachedThreadPool(), ProducerType.SINGLE, new BlockingWaitStrategy());
            disruptor.handleEventsWith(new CommandHandler());
//            disruptor.handleExceptionsWith();
            disruptor.start();
            return disruptor;
        }
    };

//    public static final ConcurrentHashMap<ChannelId, DefaultSession> SESSIONS = new ConcurrentHashMap<>();

//    public static final HashMap<Channel, String> CHANNEL_ID_MAP = new HashMap<>(1000);

    public static final AtomicInteger ACTIVE = new AtomicInteger();
    public static final AtomicInteger INACTIVE = new AtomicInteger();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DefaultSession session = new DefaultSession(ctx);
//        SESSIONS.put(ctx.channel().id(), session);
        Sessions.INSTANCE.register(ctx.channel().id(), session);
//        System.out.println("channelActive" + ACTIVE.getAndIncrement());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
//        Session session = SESSIONS.get(ctx.channel().id());
        Session session = Sessions.INSTANCE.get(ctx.channel().id());
        if (null != session) {
            RingBuffer<CommandEvent> ringBuffer = THREAD_LOCAL.get().getRingBuffer();
            long next = ringBuffer.next();
            try {
                CommandEvent commandEvent = ringBuffer.get(next);
                commandEvent.setValues(session, msg);
            } finally {
                ringBuffer.publish(next);
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        Session session = SESSIONS.remove(ctx.channel().id());
        Session session = Sessions.INSTANCE.unregister(ctx.channel().id());
        if (null != session) { // TODO: 释放无用的资源
            session.release();
//            System.out.println("channelInactive" + INACTIVE.getAndIncrement());
        }
//        Session session = SESSIONS.remove(ctx.channel().id());
//        if (null != session) { // TODO: 释放无用的资源
//            session.release();
//        }
        ctx.close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
//                    System.out.println("连接被关闭" + id);
            }
        });
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            System.out.println("远程主机强迫关闭了一个现有的连接 : " + ctx.channel().remoteAddress().toString() + " => " + ctx.channel().localAddress().toString());
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
