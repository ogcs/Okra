package org.ogcs.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public abstract class TcpProtocolServer implements NettyBootstrap<ServerBootstrap> {

    private ServerBootstrap bootstrap;
    private NioEventLoopGroup parentGroup;
    private NioEventLoopGroup childGroup;
    private int port;

    @Override
    public ServerBootstrap createBootstrap() {
        bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup(), childGroup());
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(newChannelInitializer());

        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
//        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        return bootstrap;
    }

    protected abstract ChannelHandler newChannelInitializer();

    @Override
    public void start() {
        ServerBootstrap sb = bootstrap() != null ? bootstrap() : createBootstrap();
        try {
            sb.bind(port()).sync();
            // add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    stop();
                }
            }));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void stop() {
        if (parentGroup != null)
            parentGroup.shutdownGracefully();
        if (childGroup != null)
            childGroup.shutdownGracefully();
    }

    @Override
    public ServerBootstrap bootstrap() {
        return bootstrap;
    }

    public EventLoopGroup parentGroup() {
        parentGroup = new NioEventLoopGroup();
        return parentGroup;
    }

    public EventLoopGroup childGroup() {
        childGroup = new NioEventLoopGroup();
        return childGroup;
    }

    public int port() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
