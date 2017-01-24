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

package org.ogcs.okra.example.game.persistence.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.persistence.provider.RoleSqlProvider;
import org.springframework.stereotype.Component;

@Component("roleMapper")
public interface RoleMapper {

    @InsertProvider(type = RoleSqlProvider.class, method = "insertSql")
    void insert(MemAccount memAccount);

    @UpdateProvider(type = RoleSqlProvider.class, method = "deleteSql")
    void delete(long uid);

    @UpdateProvider(type = RoleSqlProvider.class, method = "updateSql")
    MemAccount update(MemAccount memAccount);

    @SelectProvider(type = RoleSqlProvider.class, method = "selectSql")
    MemAccount select(String account);

    @SelectProvider(type = RoleSqlProvider.class, method = "selectByUidSql")
    MemAccount selectByUid(long uid);
}
