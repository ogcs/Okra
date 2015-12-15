package org.ogcs;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.ogcs.app.DefaultSession;
import org.ogcs.app.Session;
import org.ogcs.app.Sessions;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * 
 * @author TinyZ on 2015/10/22.
 */
public abstract class DisruptorAdapterBy41xHandler<O> extends SimpleChannelInboundHandler<O> {

    public static final ThreadLocal<Disruptor<ConcurrentEvent>> THREAD_LOCAL = new ThreadLocal<Disruptor<ConcurrentEvent>>() {
        @Override
        protected Disruptor<ConcurrentEvent> initialValue() {
            Disruptor<ConcurrentEvent> disruptor = new Disruptor<>(
                    new EventFactory<ConcurrentEvent>() {
                        @Override
                        public ConcurrentEvent newInstance() {
                            return new ConcurrentEvent();
                        }
                    }
                    , 1024, Executors.newCachedThreadPool(), ProducerType.SINGLE, new BlockingWaitStrategy());
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
        if (null != session) { // TODO: 释放无用的资源
            session.release();
//            System.out.println("channelInactive" + INACTIVE.getAndIncrement());
        }
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
