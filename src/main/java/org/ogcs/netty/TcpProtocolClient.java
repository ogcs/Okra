package org.ogcs.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author TinyZ on 2015/6/4.
 */
public abstract class TcpProtocolClient implements NettyBootstrap<Bootstrap> {

    private String host;
    private int port;
    private Bootstrap bootstrap;
    private NioEventLoopGroup childGroup;

    @Override
    public Bootstrap createBootstrap() {
        bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(childGroup());
        bootstrap.handler(newChannelInitializer());
        return bootstrap;
    }

    protected abstract ChannelHandler newChannelInitializer();

    @Override
    public void start() {
        if (bootstrap == null){
            createBootstrap();
        }
        bootstrap.connect(host(), port());
        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }));
    }

    @Override
    public void stop() {
        if (childGroup != null)
            childGroup.shutdownGracefully();
    }

    @Override
    public Bootstrap bootstrap() {
        return null;
    }

    /**
     * Get child group
     * @return child group
     */
    private EventLoopGroup childGroup() {
        childGroup = new NioEventLoopGroup();
        return childGroup;
    }

    /**
     * Set client connect address
     * @param host The connect host
     * @param port The connect port
     */
    public void setConnectAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String host() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int port() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
