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

package org.ogcs.okra.example;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.ogcs.netty.impl.TcpProtocolClient;
import org.ogcs.okra.example.game.generated.Gpb;
import org.ogcs.okra.example.game.generated.Gpb.Request;
import org.ogcs.okra.example.game.generated.Gpb.Response;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TinyZ
 * @date 2016-07-11.
 */
public class Client {

    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(2, false);

    private static final ChannelHandler GPB_DECODER_HANDLER = new ProtobufDecoder(Gpb.Response.getDefaultInstance());

    private static final ChannelHandler GPB_ENCODER_HANDLER = new ProtobufEncoder();

    public static void main(String[] args) {

        TcpProtocolClient client = new TcpProtocolClient("127.0.0.1", 9008) {
            @Override
            protected ChannelHandler newChannelInitializer() {
                return new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline cp = ch.pipeline();
                        cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                        cp.addLast("prepender", FRAME_PREPENDER);
                        cp.addLast("decoder", GPB_DECODER_HANDLER);
                        cp.addLast("encoder", GPB_ENCODER_HANDLER);
                        // handler
                        cp.addLast("handler", new SimpleChannelInboundHandler<Response>() {

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {

                                System.out.println("msg:" + msg.getId() + ", ");

                            }
                        });
//                cp.addLast("handler", new ServerHandler());
                    }
                };
            }
        };
        client.start();

        AtomicInteger ID = new AtomicInteger(0);
        Channel client1 = client.client();
        client1.writeAndFlush(Request.newBuilder()
                .setId(ID.incrementAndGet())
                .setApi(1)
                .build());
    }
}
