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

/**
 * @author TinyZ
 * @date 2016-09-29.
 */
public class BusInfoBean {

    private long uid;               // 隐藏字段 -   redisKey
    private long busId;              // bus唯一ID    -     hashKey
    private long tplId;      //  模板Id
    private long timeStart;          //  开始日期时间(时间戳)
    private long timeEnd;            //  结束日期时间(时间戳)
    private long expire = -1;        //  过期时间(单位: 秒)
    private int[] weekdays;           //  生效星期 [周日(0), .... 周六(7)]
    private String[] period;        //  生效时间


    private short beginTime;          //  每日生效开始时间    [ '24:00:00' ]
    private short overTime;           //  每日生效结束时间    [ '00:00:00' ]

    private CfgBusTemplate template;        //  动态模板    -   json字符串     -   结构类似于BusinessConfig中的静态模板和全局模板


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getBusId() {
        return busId;
    }

    public void setBusId(long busId) {
        this.busId = busId;
    }

    public long getTplId() {
        return tplId;
    }

    public void setTplId(long tplId) {
        this.tplId = tplId;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public int[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(int[] weekdays) {
        this.weekdays = weekdays;
    }

    public String[] getPeriod() {
        return period;
    }

    public void setPeriod(String[] period) {
        this.period = period;
    }

    public short getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(short beginTime) {
        this.beginTime = beginTime;
    }

    public short getOverTime() {
        return overTime;
    }

    public void setOverTime(short overTime) {
        this.overTime = overTime;
    }

    public CfgBusTemplate getTemplate() {
        return template;
    }

    public void setTemplate(CfgBusTemplate template) {
        this.template = template;
    }
}
