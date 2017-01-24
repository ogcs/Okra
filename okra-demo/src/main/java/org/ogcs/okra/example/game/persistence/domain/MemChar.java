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

package org.ogcs.okra.example.game.persistence.domain;

import java.io.Serializable;

/**
 * 角色信息
 */
public class MemChar implements Serializable {

    private static final long serialVersionUID = 316314981406576254L;
    private long rid;                   // ID
    private long uid;                   // 账户唯一ID
    private volatile String name;       // 角色名
    private volatile int figure;        // 形象
    private volatile int status;        //  状态[1.普通, 2:封禁登录，3:封禁聊天， 9:封禁全部]
    private volatile int identify;      //  标识[1:普通, 2:未成年, 3:游客， 9:福利账户]
    private volatile long silver;       // 银币
    private volatile int gold;          // 金币(充值)
    private volatile int totalGold;     // 累计充值

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdentify() {
        return identify;
    }

    public void setIdentify(int identify) {
        this.identify = identify;
    }

    public long getSilver() {
        return silver;
    }

    public void setSilver(long silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }
}
