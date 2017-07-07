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
package org.ogcs.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * Data Transport Protocol.
 * Every protocol must be implement the {@link #newChannelInitializer()} method.
 * this method use to initialize the {@link Channel}'s handlers.
 * <pre>
 *      <lang desc="zh-cn">
 *      数据传输协议接口. 实现{@link #newChannelInitializer()}接口用于初始化{@link Channel}的handler.
 *      </lang>
 * </pre>
 *
 * @author TinyZ
 * @version 2015.10.22
 * @since 1.0
 */
public interface Protocol<T extends Channel> {

    /**
     * @return New Channel Initializer.
     */
    ChannelInitializer<T> newChannelInitializer();
}
