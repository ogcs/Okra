package org.ogcs;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author TinyZ on 2015/10/22.
 */
public abstract class SimpleSocketProtocol implements Protocol {

    @Override
    public ChannelInitializer<SocketChannel> newChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                initChannelHandler(cp);
                cp.addLast("disruptor", new OgcsServerHandler());
            }
        };
    }

    public abstract void initChannelHandler(ChannelPipeline cp);
}
