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

package org.ogcs.okra.example.game.impl.arpg;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.ogcs.netty.impl.TcpProtocolServer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author TinyZ on 2016/6/9.
 */
public class ArpgLogicServer extends TcpProtocolServer {

    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);
    private static final ChannelHandler STRING_DECODER = new StringDecoder();
    private static final ChannelHandler STRING_ENCODER = new StringEncoder();

    public ArpgLogicServer(int port) {
        setPort(port);
    }

    public static final Set<ChannelHandlerContext> sets = Collections.synchronizedSet(new HashSet<>());

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                cp.addLast("prepender", FRAME_PREPENDER);
                cp.addLast("decoder", STRING_DECODER);
                cp.addLast("encoder", STRING_ENCODER);
                cp.addLast("handler", new SimpleChannelInboundHandler<String>() {

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                        sets.add(ctx);
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        super.channelInactive(ctx);
                        sets.remove(ctx);
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//                        JSONObject jsonObject = JSON.parseObject(msg);
                        System.out.println(msg);

                        for (ChannelHandlerContext set : sets) {
                            set.writeAndFlush(msg);
                        }
                    }
                });
            }
        };
    }
}
