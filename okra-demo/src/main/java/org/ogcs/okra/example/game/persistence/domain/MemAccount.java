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
 * 账户信息.
 * (登录和游戏角色分离，有助于缓解开服时瞬时的登录压力)
 */
public class MemAccount implements Serializable {

    private static final long serialVersionUID = 316314981406576254L;
    private long uid;                //  游戏的玩家唯一ID
    private String account;          //  游戏登录帐号
    private String openId;           //  接入的平台帐号或ID
    private long timeCreateAccount;  //  创建账号时间(单位:毫秒)
    private String ip;               //  创建IP
    private long lastLoginTime;      //  最后一次登录时间(单位:毫秒)
    private long lastLogoutTime;     //  最后一次登出时间(单位:毫秒)
    private String lastLoginIP;      //  最后一次登录ip

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getTimeCreateAccount() {
        return timeCreateAccount;
    }

    public void setTimeCreateAccount(long timeCreateAccount) {
        this.timeCreateAccount = timeCreateAccount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(long lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }
}
