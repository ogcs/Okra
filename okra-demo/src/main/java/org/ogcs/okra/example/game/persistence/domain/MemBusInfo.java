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

/**
 * @author TinyZ
 * @date 2016-09-26.
 */
public class MemBusInfo {

    private long uid;               //  隐藏字段 -   redisKey
    private int busId;              //  bus唯一ID    -     hashKey
    private int busTemplateId;      //  模板Id
    private String busTemplate;     //  动态模板    -   json字符串     -   结构类似于BusinessConfig中的静态模板和全局模板
    private long timeStart;         //  开始日期时间(时间戳)
    private int timeEnd;            //  结束日期时间(时间戳)
    private long expire;            //  过期时间(单位: 秒)
    private String weekdays;        //  生效星期 [周日(0), .... 周六(7)]
    private String beginTime;       //  每日生效开始时间    [ '24:00:00' ]
    private String overTime;        //  每日生效结束时间    [ '00:00:00' ]

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getBusTemplateId() {
        return busTemplateId;
    }

    public void setBusTemplateId(int busTemplateId) {
        this.busTemplateId = busTemplateId;
    }

    public String getBusTemplate() {
        return busTemplate;
    }

    public void setBusTemplate(String busTemplate) {
        this.busTemplate = busTemplate;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }
}
