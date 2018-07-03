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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.ForkJoinHandler;
import org.ogcs.netty.handler.mq.LogicProcessor;
import org.ogcs.netty.handler.mq.MessageQueueHandler;
import org.ogcs.netty.impl.TcpProtocolServer;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/19
 * @since 1.0
 */
public class BenchmarkServer extends TcpProtocolServer {

    public BenchmarkServer(int port) {
        setPort(port);
    }

    // 复用可以复用的Handler，已减少Handler数量
    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(2, false);
    private static final ChannelHandler STRING_DECODER = new StringDecoder();
    private static final ChannelHandler STRING_ENCODER = new StringEncoder();
    private static final ChannelHandler HANDLER = new BenchmarkHandler();
    private static final ChannelHandler FJ_HANDLER = new ForkJoinHandler();
    private static final ChannelHandler MQ_HANDLER = new MessageQueueHandler<String>(new LogicProcessor()) {
        @Override
        protected Executor newExecutor(Session session, String msg) {
            return new Executor() {
                @Override
                public void onExecute() {
                    if (null == msg) {
                        throw new NullPointerException("str");
                    }
                    session.writeAndFlush(msg);
                }

                @Override
                public void release() {

                }
            };
        }
    };

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
                    cp.addLast("handler", HANDLER);
                }
        };
    }
}
