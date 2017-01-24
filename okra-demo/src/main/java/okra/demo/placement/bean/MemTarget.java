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

package okra.demo.placement.bean;

/**
 * 玩家攻击的目标
 */
public class MemTarget {

    private int cfgMasterId;  //  目标配置表ID
    private int hp;           //  血量
    private long timeLastRwd;  //  上一次结算奖励时间

    public int getCfgMasterId() {
        return cfgMasterId;
    }

    public void setCfgMasterId(int cfgMasterId) {
        this.cfgMasterId = cfgMasterId;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public long getTimeLastRwd() {
        return timeLastRwd;
    }

    public void setTimeLastRwd(long timeLastRwd) {
        this.timeLastRwd = timeLastRwd;
    }
}
