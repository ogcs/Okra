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

package org.ogcs.okra.example.game.bean;

import java.util.Map;

/**
 * @author TinyZ
 * @date 2016-09-25.
 */
public class CfgBusTemplate {

    private int tplId;
    private Integer[] events;
    private Map<Integer, Integer> targets;
    private boolean isDaily;
    private boolean isPersistent;

    public int getTplId() {
        return tplId;
    }

    public void setTplId(int tplId) {
        this.tplId = tplId;
    }

    public Integer[] getEvents() {
        return events;
    }

    public void setEvents(Integer[] events) {
        this.events = events;
    }

    public Map<Integer, Integer> getTargets() {
        return targets;
    }

    public void setTargets(Map<Integer, Integer> targets) {
        this.targets = targets;
    }

    public boolean isDaily() {
        return isDaily;
    }

    public void setDaily(boolean daily) {
        isDaily = daily;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }
}
