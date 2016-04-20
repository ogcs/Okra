package org.ogcs.okra.example.game.persistence.provider;

import org.apache.ibatis.jdbc.SQL;
import org.ogcs.okra.example.game.persistence.domain.MemRole;

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

    public String insertSql(final MemRole memRole) {
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

    public String deleteSql() {
        return new SQL() {{
            DELETE_FROM(TABLE_NAME);
            WHERE("uid=#{uid}");
        }}.toString();
    }
}
