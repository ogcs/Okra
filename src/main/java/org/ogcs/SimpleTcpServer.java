package org.ogcs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author TinyZ on 2015/10/22.
 */
public abstract class SimpleTcpServer {

    protected final NioEventLoopGroup parent = new NioEventLoopGroup();
    protected final NioEventLoopGroup children = new NioEventLoopGroup();

    public void start() {
        Protocol protocol = protocol();
        if (protocol == null) {
            throw new NullPointerException("protocol");
        }
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(parent, children);
        bootstrap.childHandler(protocol.newChannelInitializer());

        //
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
//        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        // 非缓存
//        bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
//        bootstrap.childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
        try {
            bootstrap.bind(port()).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }));
    }

    public void stop() {
        parent.shutdownGracefully();
        children.shutdownGracefully();
    }

    protected abstract Protocol protocol();

    protected abstract int port();
}
