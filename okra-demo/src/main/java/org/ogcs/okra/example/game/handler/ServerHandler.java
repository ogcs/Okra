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

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterHandler;
import org.ogcs.okra.example.game.generated.Gpb;

import java.io.IOException;

public class ServerHandler extends DisruptorAdapterHandler<Gpb.Request> {

    private static final Logger LOG = LogManager.getLogger(ServerHandler.class);

    @Override
    protected Executor newExecutor(Session session, Gpb.Request msg) {
        return new GpbExecutor(session, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            LOG.info("远程主机强迫关闭了一个现有的连接 : " + ctx.channel().remoteAddress().toString() + " => " + ctx.channel().localAddress().toString());
        } else if (cause.getCause() instanceof InvalidProtocolBufferException) {
            LOG.info("Invalid Google Protocol Buffer Message : " + cause.getMessage());
            super.exceptionCaught(ctx, cause);
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
