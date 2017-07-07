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

package org.ogcs.okra.example.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2015/11/20
 */
public class HttpRequestExecutor implements Executor {

    private static final Logger LOG = LogManager.getLogger(HttpRequestExecutor.class);

    protected Session session;
    protected FullHttpRequest request;

    public HttpRequestExecutor(Session session, FullHttpRequest request) {
        this.session = session;
        this.request = request;
    }

    @Override
    public void onExecute() {
        if (null == request) {
            throw new NullPointerException("request");
        }
        try {
            QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
            switch (decoder.path()) {
                case "/test":
                    response(session.channel(), "{state:0}");
                    return;
                case "/favicon.ico":
                    break;
            }
            simple(session.channel(), FORBIDDEN);
        } catch (Exception e) {
            session.channel().close();
            LOG.info("HTTP Api throw exception : ", e);
        }
    }

    private static void simple(Channel channel, HttpResponseStatus status) {
        ChannelFuture channelFuture = channel.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status));
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    private static void response(Channel channel, String msg) {
        HttpResponse response;
        if (msg != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        }
        ChannelFuture channelFuture = channel.writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void release() {
        this.session = null;
        this.request = null;
    }
}
