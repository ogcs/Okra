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

import org.ogcs.app.AppContext;
import org.ogcs.okra.example.game.persistence.domain.MemBusListener;
import org.ogcs.okra.example.game.persistence.mapper.BusListenerMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-10-24.
 */
public class BusListenerHook {

    private BusListenerMapper busListenerMapper = AppContext.getBean("busListenerMapper", BusListenerMapper.class);

    private long uid;

    private Map<Integer/*event*/, Map<Integer/*busId*/, Integer/* 0 */>> map = new HashMap<>();

    public BusListenerHook(long uid) {
        this.uid = uid;
    }

    protected void initialize() {
        List<MemBusListener> select = busListenerMapper.select(this.uid);
        for (MemBusListener memBusListener : select) {
            Map<Integer, Integer> tmpMap = map.get(memBusListener.getEvent());
            if (tmpMap == null) {
                tmpMap = new HashMap<>();
                map.put(memBusListener.getEvent(), tmpMap);
            }
            tmpMap.put(memBusListener.getBusId(), 0);
        }
    }

    protected void readFromDB() {

    }

    protected void clear() {
        // TODO Auto-generated method stub

    }


}
