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
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.ogcs.netty.NettyBootstrap;

/**
 * TcpProtocolServer make easy to create tcp server(example : Socket, HTTP and others).
 *
 * The {@link #start()} method use to tcp server bind port.
 * The {@link #stop()} method use to the server shutdown.
 */
public abstract class TcpProtocolServer implements NettyBootstrap<ServerBootstrap> {

    private ServerBootstrap bootstrap;
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    protected int port;

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
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        if (null == parentGroup)
            parentGroup = new NioEventLoopGroup();
        return parentGroup;
    }

    public EventLoopGroup childGroup() {
        if (null == childGroup) {
            childGroup = new NioEventLoopGroup();
        }
        return childGroup;
    }

    public int port() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
