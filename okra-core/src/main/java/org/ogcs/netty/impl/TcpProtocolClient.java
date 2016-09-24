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
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.ogcs.netty.NettyBootstrap;

import java.net.InetSocketAddress;

/**
 * TcpProtocolClient make easy to create tcp client.
 *
 * The {@link #start()} method use to tcp client connect to remote address.
 * The {@link #stop()} method use to the client disconnect.
 */
public abstract class TcpProtocolClient implements NettyBootstrap<Bootstrap> {

    protected static final NioEventLoopGroup DEFAULT_EVENT_LOOP_GROUP = new NioEventLoopGroup();

    protected String host;
    protected int port;
    private Bootstrap bootstrap;
    private EventLoopGroup childGroup;
    protected Channel client;

    public TcpProtocolClient() {
        this.childGroup = DEFAULT_EVENT_LOOP_GROUP;
    }

    public TcpProtocolClient(String host, int port) {
        this(host, port, DEFAULT_EVENT_LOOP_GROUP);
    }

    public TcpProtocolClient(String host, int port, EventLoopGroup eventLoopGroup) {
        this.host = host;
        this.port = port;
        this.childGroup = eventLoopGroup;
    }

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
        if (bootstrap == null) {
            createBootstrap();
        }
        try {
            ChannelFuture future = doConnect();
            future.await();
            client = future.channel();
            future.sync();
        } catch (Exception e) {
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

    public ChannelFuture doConnect() {
        return bootstrap.connect(new InetSocketAddress(host(), port()));
    }

    @Override
    public void stop() {
        if (childGroup != null)
            childGroup.shutdownGracefully();
    }

    @Override
    public Bootstrap bootstrap() {
        return bootstrap;
    }

    /**
     * Get child group
     *
     * @return child group
     */
    private EventLoopGroup childGroup() {
        if (null == childGroup) {
            childGroup = new NioEventLoopGroup();
        }
        return childGroup;
    }

    /**
     * Set client connect address
     *
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

    /**
     * Return the client netty channel
     *
     * @return Netty's {@link Channel}
     */
    public Channel client() {
        return client;
    }
}
