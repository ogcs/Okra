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

import com.alibaba.fastjson.JSON;
import org.ogcs.app.Session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author TinyZ
 * @date 2017-01-24.
 */
public class JsonInvocationHandler implements InvocationHandler {

    private Session session;

    public JsonInvocationHandler(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JsonRequest request = new JsonRequest();
        request.setApi(method.getName());
        request.setData(JSON.toJSONString(args));
        session.writeAndFlush(request);
        return null;
    }
}
