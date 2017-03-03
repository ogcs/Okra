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

import java.util.concurrent.TimeUnit;

/**
 * Default session implement.
 */
public class NetSession implements Session {

    private volatile Channel channel;
    private volatile Connector connector;

    public NetSession(Channel channel) {
        this.channel = channel;
    }

    @Override
    public Channel channel() {
        return channel;
    }

    @Override
    public boolean isOnline() {
        return channel != null && channel.isActive();
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
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
    public void offline() {
        if (channel != null) {
            channel.close();
        }
    }

    @Override
    public void release() {
        channel = null;
        if (null != connector) {
            connector.disconnect();
            connector.setSession(null);
            connector = null;
        }
    }

    @Override
    public void close() {
        if (channel != null && channel.isActive()) {
            channel.close();
            channel = null;
        }
    }
}
