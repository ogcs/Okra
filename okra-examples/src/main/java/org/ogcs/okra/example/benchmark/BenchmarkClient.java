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

package org.ogcs.okra.example.benchmark;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.ogcs.netty.impl.TcpProtocolClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/19
 * @since 1.0
 */
public class BenchmarkClient extends TcpProtocolClient {

    public static final AtomicInteger COUNT = new AtomicInteger(0);

    public BenchmarkClient(String host, int port) {
        super(host, port);
    }

    // 复用可以复用的Handler，已减少Handler数量
    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(2, false);
    private static final ChannelHandler STRING_DECODER = new StringDecoder();
    private static final ChannelHandler STRING_ENCODER = new StringEncoder();

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                cp.addLast("prepender", FRAME_PREPENDER);
                // Any other useful handler
                cp.addLast("strDecoder", STRING_DECODER);
                cp.addLast("strEncoder", STRING_ENCODER);
                cp.addLast("handler", new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                        COUNT.getAndIncrement();
                        ChannelPromise voidPromise = ctx.voidPromise();
                        if (ctx.channel().isWritable()) {
                            ctx.writeAndFlush(msg, voidPromise);
                        } else {
                            ctx.channel().eventLoop().schedule(() -> {
                                ctx.writeAndFlush(msg, voidPromise);
                            }, 1L, TimeUnit.SECONDS);
                        }
                    }
                });
            }
        };
    }
}
