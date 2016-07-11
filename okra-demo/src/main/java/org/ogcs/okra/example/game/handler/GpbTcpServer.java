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

package org.ogcs.okra.example.game.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.ogcs.netty.handler.mq.LogicProcessor;
import org.ogcs.netty.impl.TcpProtocolServer;
import org.ogcs.okra.example.game.generated.Gpb.Request;

import java.util.concurrent.Executors;

@Sharable
public class GpbTcpServer extends TcpProtocolServer {

    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(2, false);

    private static final ChannelHandler GPB_DECODER_HANDLER = new ProtobufDecoder(Request.getDefaultInstance());

    private static final ChannelHandler GPB_ENCODER_HANDLER = new ProtobufEncoder();

    private ChannelHandler serverHandler;

    public GpbTcpServer(int port) {
        setPort(port);

        LogicProcessor processor = new LogicProcessor();
        Thread thread = Executors.defaultThreadFactory().newThread(processor);
        thread.setName("Logic-Processor");
        thread.start();

        serverHandler = new ServerHandler2(processor);
    }

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
                // handler
                cp.addLast("handler", serverHandler);
//                cp.addLast("handler", new ServerHandler());
            }
        };
    }
}
