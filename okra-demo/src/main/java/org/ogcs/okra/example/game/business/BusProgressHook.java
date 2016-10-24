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
import org.ogcs.okra.example.game.persistence.domain.MemBusProgress;
import org.ogcs.okra.example.game.persistence.mapper.BusProgressMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-10-24.
 */
public class BusProgressHook {

    private BusProgressMapper busProgressMapper = AppContext.getBean("busListenerMapper", BusProgressMapper.class);

    private long uid;
    private Map<Long/* key */, Long/* value */> map = new HashMap<>();


    public BusProgressHook(long uid) {
        this.uid = uid;
    }

    public void initialize() {
        List<MemBusProgress> list = busProgressMapper.select(uid);
        for (MemBusProgress memBusProgress : list) {
            map.put(memBusProgress.getTargetKey(), memBusProgress.getTargetValue());
        }
    }


}
