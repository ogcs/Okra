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
package org.ogcs.netty.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.ogcs.netty.NettyBootstrap;

/**
 * TcpProtocolServer make easy to create tcp server(example : Socket, HTTP and others).
 * <p>
 * The {@link #start()} method use to tcp server bind port.
 * The {@link #stop()} method use to the server shutdown.
 * @since 1.0
 */
public abstract class TcpProtocolServer implements NettyBootstrap<ServerBootstrap> {

    private ServerBootstrap bootstrap;
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    protected int port;

    /**
     * Is netty transport native epoll.
     */
    protected static boolean isEpollAvailable = false;

    static {
        isEpollAvailable = Epoll.isAvailable();
    }

    @Override
    public ServerBootstrap createBootstrap() {
        bootstrap = new ServerBootstrap();
        if (isEpollAvailable) {
            this.parentGroup = new EpollEventLoopGroup();
            this.childGroup = new EpollEventLoopGroup();
            bootstrap.channel(EpollServerSocketChannel.class);
        } else {
            this.parentGroup = new NioEventLoopGroup();
            this.childGroup = new NioEventLoopGroup();
            bootstrap.channel(NioServerSocketChannel.class);
        }
        bootstrap.group(parentGroup(), childGroup());
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
            ChannelFuture future = sb.bind(port()).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            stop();// shutdown
        } finally {
            // add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    stop();
                }
            }));
        }
    }

    @Override
    public void stop() {
        if (parentGroup != null) {
            parentGroup.shutdownGracefully();
            parentGroup = null;
        }
        if (childGroup != null) {
            childGroup.shutdownGracefully();
            childGroup = null;
        }
        bootstrap = null;
        port = 0;
    }

    @Override
    public ServerBootstrap bootstrap() {
        return bootstrap;
    }

    public EventLoopGroup parentGroup() {
        return parentGroup;
    }

    public EventLoopGroup childGroup() {
        return childGroup;
    }

    public int port() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
