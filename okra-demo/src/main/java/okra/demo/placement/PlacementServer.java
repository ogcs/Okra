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

package okra.demo.placement;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import okra.demo.placement.component.ItemComponent;
import okra.demo.placement.component.PkComponent;
import okra.demo.placement.component.RoleComponent;
import okra.demo.placement.json.JsonRequest;
import okra.demo.placement.manager.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterBy41xHandler;
import org.ogcs.netty.impl.TcpProtocolServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * <zh-cn>
 * 放置类服务器.
 * 1. 管理注册游戏逻辑API.
 * 2. 管理Netty的Handler.
 * </zh-cn>
 *
 * @author TinyZ
 * @date 2017-01-13.
 */
@Service
public final class PlacementServer extends TcpProtocolServer {

    private static final Logger LOG = LogManager.getLogger(PlacementServer.class);
    private static final ChannelHandler STRING_DECODER = new StringDecoder(Charset.forName("UTF-8"));
    private static final ChannelHandler STRING_ENCODER = new StringEncoder(Charset.forName("UTF-8"));

    @Autowired
    ServiceManager serviceManager;

    public void initialize() {
        setPort(9005);
        initService();
    }

    private void initService() {
        serviceManager.registerService(new RoleComponent());
        serviceManager.registerService(new ItemComponent());
        serviceManager.registerService(new PkComponent());
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
                cp.addLast("prepender", new LengthFieldPrepender(2, false));
                // Any other useful handler
                cp.addLast("strDecoder", STRING_DECODER);
                cp.addLast("strEncoder", STRING_ENCODER);
                cp.addLast("handler", new DisruptorAdapterBy41xHandler<JsonRequest>() {
                    protected Executor newExecutor(Session session, JsonRequest msg) {
                        return new Executor() {
                            @Override
                            @SuppressWarnings("unchecked")
                            public void onExecute() {
                                try {
                                    Command command = serviceManager.getCommand(msg.getApi());
                                    if (command == null) {
                                        LOG.warn("Command is not exist. API : ", msg.getApi());
                                        return;
                                    }
                                    command.execute(session, msg);
                                } catch (Exception e) {
                                    LOG.error("Error occured by execute command.", e);
                                }
                            }

                            @Override
                            public void release() {

                            }
                        };
                    }
                });
            }
        };
    }
}
