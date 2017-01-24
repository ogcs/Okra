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

package okra.demo.placement.manager;

import okra.demo.common.annotation.PublicApi;
import okra.demo.common.component.Component;
import okra.demo.placement.json.JsonCommand;
import org.ogcs.app.Command;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的基于JSON的服务管理.
 *
 * @author TinyZ
 * @date 2017-01-17.
 */
@Service
public class ServiceManager {

    private final Map<String, Component> components = new ConcurrentHashMap<>();
    private final Map<String, Command> commands = new HashMap<>();


    public void registerService(Component component) {
        if (component == null) throw new NullPointerException("component");
        if (components.containsKey(component.id()))
            throw new IllegalStateException("Component " + component.id() + " has bean registered.");
        components.put(component.id(), component);
        for (Method method : component.getClass().getDeclaredMethods()) {
            PublicApi anoApi = method.getAnnotation(PublicApi.class);
            if (anoApi != null) {
                String api = method.getName();
                JsonCommand command = new JsonCommand(api, component, method);
                commands.put(api, command);
            }
        }
    }

    public Command getCommand(String api) {
        return commands.get(api);
    }


}
