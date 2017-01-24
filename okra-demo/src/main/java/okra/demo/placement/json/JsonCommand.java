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

import com.alibaba.fastjson.JSONArray;
import okra.demo.common.component.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;

import java.lang.reflect.Method;

/**
 * <pre>
 *     JSON Request:
 *     id   : request unique id. (int)
 *     api  : api name
 *     args : param array. 请求的参数为数组，数组中对象的顺序对应调用接口的各个参数。
 * </pre>
 *
 * @author TinyZ
 * @date 2017-01-19.
 * @see JsonRequest
 */
public class JsonCommand implements Command<Session, JsonRequest> {

    private static final Logger LOG = LogManager.getLogger(JsonCommand.class);
    private final String api;
    private final Component component;
    private final Method method;

    public JsonCommand(String api, Component component, Method method) {
        this.api = api;
        this.component = component;
        this.method = method;
    }

    @Override
    public void execute(Session session, JsonRequest request) throws Exception {
        Object[] params = createParams(session, request.getData());
        if (params == null) {
            return;
        }
        method.invoke(component, params);
    }

    //
    private Object[] createParams(Session session, String data) {
        Object[] args = new Object[]{session};
        try {
            JSONArray array = JSONArray.parseArray(data);
            args = new Object[array.size() + 1];
            args[0] = session;
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 1; i < parameterTypes.length; i++) {
                args[i] = array.getObject(i - 1, parameterTypes[i]);
            }
        } catch (Exception e) {
            LOG.error("Build param failed.", e);
        }
        return args;
    }
}
