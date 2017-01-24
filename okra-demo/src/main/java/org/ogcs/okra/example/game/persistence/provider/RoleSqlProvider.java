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
import org.ogcs.okra.example.game.persistence.domain.MemAccount;

import java.util.List;

public class RoleSqlProvider {

    private static final String TABLE_NAME = "tb_role";

    public String selectSql() {
        return new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("account=#{account}");
        }}.toString();
    }

    public String selectByUidSql() {
        return new SQL() {{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String insertSql(final MemAccount memAccount) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("uid", "#{uid}");
            VALUES("name", "#{name}");
            VALUES("figure", "#{figure}");
            VALUES("account", "#{account}");
            VALUES("psw", "#{psw}");
            VALUES("timeCreate", "#{timeCreate}");
            VALUES("timeLastLogin", "#{timeLastLogin}");
            VALUES("ipCreate", "#{ipCreate}");
            VALUES("ipLastLogin", "#{ipLastLogin}");
        }}.toString();
    }

    public String updateSql() {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("uid=#{uid}");
            SET("name=#{name}");
            SET("figure=#{figure}");
            SET("account=#{account}");
            SET("psw=#{psw}");
            SET("timeLastLogin=#{timeLastLogin}");
            SET("ipLastLogin=#{ipLastLogin}");
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String updateByFieldsSql(final MemAccount memAccount, final List<String> fileds) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            for (String filed : fileds) {
                SET(filed + "=#{" + filed + "}");
            }
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String deleteSql() {
        return new SQL() {{
            DELETE_FROM(TABLE_NAME);
            WHERE("uid=#{uid}");
        }}.toString();
    }
}
