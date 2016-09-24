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

package org.ogcs.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.utilities.Amf3Util;

import java.io.IOException;
import java.util.List;

/**
 * Encode Java Object to Amf3 {@link ByteBuf}.
 * @author : TinyZ.
 * @since 1.0
 */
@Sharable
public class ObjectToAmf3Encoder extends MessageToMessageEncoder<Object> {

    private static final Logger LOG = LogManager.getLogger(Amf3ToObjectDecoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        if (msg == null) {
            LOG.info("msg is null.");
            return;
        }
        try {
            ByteBuf byteBuf = Amf3Util.convertJavaObjToAmf3(msg);
            out.add(byteBuf);
        } catch (IOException e) {
            LOG.error("Convert Java Object To AMF3 Error: {}.", e);
        }
    }
}
