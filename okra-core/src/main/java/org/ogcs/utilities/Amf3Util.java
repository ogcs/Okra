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

package org.ogcs.utilities;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * AMF3 protocol utility
 *
 * @author : TinyZ.
 * @version : 2016.05.17
 * @since 1.0
 */
public final class Amf3Util {

    private static final Logger LOG = LogManager.getLogger(Amf3Util.class);

    private Amf3Util() {
        // no-op
    }

    /**
     * Convert Java Object to AMF3 protocol's byte[]
     *
     * @param msg Java object
     * @return return byte array see: {@link ByteBuf}.
     * @throws IOException          amf3 output exception.
     * @throws NullPointerException where the msg is Null, throw the NullPointerException.
     */
    public static ByteBuf convertJavaObjToAmf3(Object msg) throws IOException {
        if (msg == null) {
            throw new NullPointerException("msg");
        }
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final Amf3Output op = new Amf3Output(SerializationContext.getSerializationContext());
        op.setOutputStream(bout);
        op.writeObject(msg);
        op.flush();
        op.close();
        return Unpooled.wrappedBuffer(bout.toByteArray());
    }

    /**
     * Convert AMF3 protocol's byte[] to Java Object.
     *
     * @param byteBuf byte array
     * @return Return java object
     * @throws Exception amf3 output exception.
     */
    public static Object convertAmf3ToObj(ByteBuf byteBuf) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteBuf.readBytes(byteBuf.readableBytes()).array());
        return convertAmf3ToObj(bis);
    }

    /**
     * Convert stream of AMF3 protocol's byte[] to Java Object.
     *
     * @throws Exception amf3 output exception.
     */
    public static Object convertAmf3ToObj(ByteArrayInputStream bis) throws Exception {
        Object obj;
        final Amf3Input amf3Input = new Amf3Input(SerializationContext.getSerializationContext());
        try {
            amf3Input.setInputStream(bis);
            obj = amf3Input.readObject();
            amf3Input.close();
        } catch (ClassNotFoundException e) {
            LOG.error("Error in AMF3Utils: {}.\n " + "Check if flash class has corresponding java class.", e);
            throw e;
        } catch (IOException e) {
            LOG.error("IO error in AMF3Utils: {}.", e);
            throw e;
        }
        return obj;
    }
}
