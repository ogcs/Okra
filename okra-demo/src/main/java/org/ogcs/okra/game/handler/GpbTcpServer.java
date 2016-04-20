package org.ogcs.okra.game.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.ogcs.netty.impl.TcpProtocolServer;
import org.ogcs.okra.game.generated.Gpb.Request;

public class GpbTcpServer extends TcpProtocolServer {

    public GpbTcpServer(int port) {
        setPort(port);
    }

    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);

    private static final ChannelHandler GPB_DECODER_HANDLER = new ProtobufDecoder(Request.getDefaultInstance());

    private static final ChannelHandler GPB_ENCODER_HANDLER = new ProtobufEncoder();

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                cp.addLast("prepender", FRAME_PREPENDER);
                cp.addLast("decoder", GPB_DECODER_HANDLER);
                cp.addLast("encoder", GPB_ENCODER_HANDLER);
                cp.addLast("handler", new ServerHandler());
            }
        };
    }
}
