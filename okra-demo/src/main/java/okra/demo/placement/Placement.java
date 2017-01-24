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

package okra.demo.placement;

import okra.demo.placement.component.ItemComponent;
import okra.demo.placement.component.PkComponent;
import okra.demo.placement.component.RoleComponent;
import okra.demo.placement.manager.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ
 * @date 2017-01-22.
 */
@Service
public class Placement {

    @Autowired
    PlacementServer server;

    public void initialize() {
        //  初始化服务
        server.initialize();
        //  启动服务器
        server.start();
    }






}
