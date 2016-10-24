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

package org.ogcs.okra.example.game.persistence.provider;

import org.apache.ibatis.jdbc.SQL;
import org.ogcs.okra.example.game.persistence.domain.MemBusInfo;
import org.ogcs.utilities.StringUtil;

import java.util.List;

public class BusInfoSqlProvider {

    private static final String TABLE_NAME = "tb_bus_info";

    public String selectSql(final long uid) {
        return new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String insertSql(final MemBusInfo memBusInfo) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("uid", "#{uid}");
            VALUES("busId", "#{busId}");
            VALUES("busTemplateId", "#{busTemplateId}");
            VALUES("busTemplate", "#{busTemplate}");
            VALUES("timeStart", "#{timeStart}");
            VALUES("timeEnd", "#{timeEnd}");
            VALUES("expire", "#{expire}");
            VALUES("weekdays", "#{weekdays}");
            VALUES("beginTime", "#{beginTime}");
            VALUES("overTime", "#{overTime}");
        }}.toString();
    }

    public String updateSql(final MemBusInfo memBusInfo) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("busTemplateId=#{busTemplateId}");
            SET("busTemplate=#{busTemplate}");
            SET("timeStart=#{timeStart}");
            SET("timeEnd=#{timeEnd}");
            SET("expire=#{expire}");
            SET("weekdays=#{weekdays}");
            SET("beginTime=#{beginTime}");
            SET("overTime=#{overTime}");
            WHERE("uid=#{uid} AND busId=#{busId}");
        }}.toString();
    }

    public String updateByFieldsSql(final MemBusInfo memBusInfo, final List<String> fileds) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            for (String filed : fileds) {
                SET(filed + "=#{" + filed + "}");
            }
            WHERE("uid=#{uid} AND busId=#{busId}");
        }}.toString();
    }

    public String deleteByListSql(final long uid, final List<Integer> list) {
        return new SQL() {{
            DELETE_FROM(TABLE_NAME);
            String implode = StringUtil.implode(',', list);
            WHERE("uid=#{uid} AND busId IN (" + implode + ")");
        }}.toString();
    }

    public String deleteSql() {
        return new SQL() {{
            DELETE_FROM(TABLE_NAME);
            WHERE("uid=#{uid} AND busId=#{busId}");
        }}.toString();
    }
}
