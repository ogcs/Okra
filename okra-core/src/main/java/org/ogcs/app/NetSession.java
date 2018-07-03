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
package org.ogcs.app;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Default session implement.
 * @since 2.0
 */
public class NetSession implements Session {

    private static final Logger LOG = LogManager.getLogger(NetSession.class);
     * Netty channel.
     */
    private volatile Channel channel;
    private volatile Connector connector;
    private final boolean reliable; //  是否可靠的发送
    /**
     * When the channel's isWritable is false, retry to transport after 1 sec.
     */
    private final boolean retry;    //  是否重发
    /**
     * The max retry write count.
     */
    private final int maxRetryCount;//  重发尝试次数

    public NetSession(Channel channel) {
        this(channel, false, false, 5);
    }

    public NetSession(Channel channel, boolean reliable, boolean retry, int maxRetryCount) {
        this.channel = channel;
        this.reliable = reliable;
        this.retry = retry;
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public Channel channel() {
        return channel;
    }

    @Override
    public boolean isActive() {
        return channel != null && channel.isActive();
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public void transport(Object data, ChannelFutureListener listener) {
        transport(data, listener, 0);
    }

    /**
     * @param data       The transport message data.
     * @param listener   the callback listener.
     * @param retryCount retry transport data count, default is zero.
     */
    public void transport(Object data, ChannelFutureListener listener, final int retryCount) {
        if (null == channel) {
            LOG.debug("ChannelHandlerContext is null.");
            return;
        }
        if (channel == null || !channel.isActive()) {
            LOG.debug("Channel is null or inactive. Channel id:{}", channel.id().toString());
            return;
        }
        if (reliable || channel.isWritable()) {
            if (listener == null) {
                channel.writeAndFlush(data, channel.voidPromise());
            } else {
                channel.writeAndFlush(data).addListener(listener);
            }
        } else {
            if (retryCount > 0) {
                //  超过最大重发次数 - 消息被丢弃
                if (this.retry && retryCount < this.maxRetryCount) {

                } else {
                    return;
                }
            }
            channel.eventLoop().schedule(() -> {
                transport(data, listener, retryCount + 1);
            }, 1L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void writeAndFlush(Object msg) {
        writeAndFlush(msg, null);
    }

    @Override
    public void writeAndFlush(Object message, ChannelFutureListener listener) {
        if (null == channel || !channel.isActive()) {
            return;
        }
        if (channel.isWritable()) {
            if (listener == null) {
                channel.writeAndFlush(message, channel.voidPromise());
            } else {
                channel.writeAndFlush(message).addListener(listener);
            }
        } else {
            channel.eventLoop().schedule(() -> {
                writeAndFlush(message, listener);
            }, 1L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void active() {
        if (null != connector)
            connector.sessionActive();
    }

    @Override
    public void inactive() {
        if (null != connector) {
            connector.sessionInactive();
            connector = null;
        }
    }

    @Override
    public void offline() {
        this.close();
    }

    @Override
    public void close() {
        if (channel != null) {
            inactive();
            if (channel.isActive())
                channel.close();
            channel = null;
        }
    }
}
