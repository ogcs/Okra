package org.ogcs.okra.example.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.ogcs.netty.impl.TcpProtocolServer;

/**
 *
 */
public class TcpServer extends TcpProtocolServer {

    public TcpServer(int port) {
        setPort(port);
    }

    // 复用可以复用的Handler，已减少Handler数量
    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                cp.addLast("prepender", FRAME_PREPENDER);
                // Any other useful handler
                cp.addLast("handler", new ExampleSocketHandler());
            }
        };
    }

    @Override
    public void stop() {
        super.stop();
    }
}
