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

package org.ogcs.okra.example.game.business;

import org.ogcs.okra.example.game.bean.CfgBusTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-09-29.
 */
public class BusManager {

    private Map<Long/*busId*/, BusInfoBean> infos = new HashMap<>();


    private Map<Integer/* event */, List<Long>/* busId */> listeners = new HashMap<>();


    public void onEvent(long uid, int event) {
        List<Long> list = listeners.get(event);
        if (list == null || list.isEmpty()) {
            return;
        }
        for (Long regBusId : list) {
            BusInfoBean bean = infos.get(regBusId);
            if (bean == null) {
                list.remove(regBusId);
                if (list.isEmpty()) {
                    listeners.remove(event);
                }
                return;
            }
            CfgBusTemplate template = bean.getTemplate();
            if (template == null) {
                template = new CfgBusTemplate(); // TODO: 获取静态模板
            }

            long now = System.currentTimeMillis();
            if (bean.getTimeEnd() >= 0 && bean.getTimeEnd() < now) {

            } else if (bean.getTimeStart() < now){
                //

            }
        }


    }



}
