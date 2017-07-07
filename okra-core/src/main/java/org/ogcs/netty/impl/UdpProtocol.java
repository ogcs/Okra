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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.ogcs.netty.NettyBootstrap;

/**
 * Bootstrap (UDP) must use {@link Bootstrap} .
 * <p>The {@link Bootstrap#bind()} methods are useful in combination with connectionless transports such as datagram (UDP).
 * For regular TCP connections, please use the provided {@link Bootstrap#connect()} methods.</p>
 *
 * @author TinyZ.
 * @since 1.0
 */
public abstract class UdpProtocol implements NettyBootstrap<Bootstrap> {

    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap;
    private int port;
    private Channel udpChannel;

    protected UdpProtocol() {
        this(0);
    }

    protected UdpProtocol(int port) {
        this.port = port;
    }

    @Override
    public Bootstrap createBootstrap() {
        bootstrap = new Bootstrap();
        bootstrap.channel(NioDatagramChannel.class);
        bootstrap.group(eventLoopGroup);
        bootstrap.handler(newChannelInitializer());
        return bootstrap;
    }

    @Override
    public void start() {
        Bootstrap bootstrap = bootstrap() == null ? createBootstrap() : bootstrap();
        try {
            udpChannel = bootstrap.bind(port()).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }));
    }

    protected abstract ChannelHandler newChannelInitializer();

    @Override
    public void stop() {
        eventLoopGroup.shutdownGracefully();
    }

    @Override
    public Bootstrap bootstrap() {
        return bootstrap;
    }

    public int port() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get UDP protobuf channel bound port
     *
     * @return The channel listen special port
     */
    public Channel udpChannel() {
        return this.udpChannel;
    }
}
