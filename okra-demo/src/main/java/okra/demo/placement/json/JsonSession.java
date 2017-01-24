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

package okra.demo.placement.json;

import io.netty.channel.ChannelHandlerContext;
import okra.demo.placement.component.ClientCallback;
import org.ogcs.app.DefaultSession;
import org.ogcs.app.ProxyCallback;

import java.lang.reflect.Proxy;

/**
 * @author TinyZ
 * @date 2017-01-24.
 */
public class JsonSession extends DefaultSession {

    private volatile ClientCallback callback;

    public JsonSession(ChannelHandlerContext ctx) {
        super(ctx);
    }

    @Override
    public ClientCallback callback() {
        if (callback == null) {
            this.callback = (ClientCallback) Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class[]{ClientCallback.class},
                    new JsonInvocationHandler(this)
            );
        }
        return this.callback;
    }
}
