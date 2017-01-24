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
import org.ogcs.okra.example.game.persistence.mapper.BusProgressMapper;
import org.springframework.beans.factory.annotation.Autowired;

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

            } else if (bean.getTimeStart() < now) {
                //

            }
        }


    }

    @Autowired
    BusProgressMapper busProgressMapper;

//    public void updateProgress(DefaultRole role, int busId, CfgBusTemplate busTemplate, Map<Long, Long> data) {
////        busTemplate.getTplId()
////        busProgressMapper.select()
//        Map<Integer, Integer> targets = busTemplate.getTargets();
//
//        Map<Long, Long> beforeProcess = role.busProgressMap.get(busId);
//        Map<Long, Long> afterProgress = new HashMap<>();
//
//        if (targets != null
//                && targets.size() > data.size()) {
//            for (Map.Entry<Long, Long> entry : data.entrySet()) {
//                long event = entry.getKey();
//                long val = entry.getValue();
//                if (targets.containsKey(event)) {
//                    int keyType = BusLogic.eventKeyType(targets.get(event));
//                    if (beforeProcess.containsKey(event)) {
//                        long tempVal = beforeProcess.get(event);
//                        switch (keyType) {
//                            case 2:
//                                if (val > tempVal)
//                                    afterProgress.put(event, val);
//                                break;
//                            default:
//                                afterProgress.put(event, tempVal + val);
//                                break;
//                        }
//                    } else {
//                        afterProgress.put(event, val);
//                    }
//                }
//            }
//        } else {
//            for (Map.Entry<Integer, Integer> entry : targets.entrySet()) {
//                int event = entry.getKey();
//                if (data.containsKey(event)) {
//                    Integer val = data.get(event);
//                    int keyType = BusLogic.eventKeyType(targets.get(event));
//                    if (beforeProcess.containsKey(event)) {
//                        Integer tempVal = beforeProcess.get(event);
//                        switch (keyType) {
//                            case 2:
//                                if (val > tempVal)
//                                    afterProgress.put(event, val);
//                                break;
//                            default:
//                                afterProgress.put(event, tempVal + val);
//                                break;
//                        }
//                    } else {
//                        afterProgress.put(event, val);
//                    }
//                }
//            }
//        }
//
//
//    }


}
