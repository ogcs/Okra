package org.ogcs.okra.game.persistence.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.ogcs.okra.game.persistence.domain.MemRole;
import org.ogcs.okra.game.persistence.provider.RoleSqlProvider;

public interface RoleMapper {

    @InsertProvider(type = RoleSqlProvider.class, method = "insertSql")
    void insert(MemRole memRole);

    @UpdateProvider(type = RoleSqlProvider.class, method = "deleteSql")
    void delete(long uid);

    @UpdateProvider(type = RoleSqlProvider.class, method = "updateSql")
    MemRole update(MemRole memRole);

    @SelectProvider(type = RoleSqlProvider.class, method = "selectSql")
    MemRole select(String account);

    @SelectProvider(type = RoleSqlProvider.class, method = "selectByUidSql")
    MemRole selectByUid(long uid);
}
