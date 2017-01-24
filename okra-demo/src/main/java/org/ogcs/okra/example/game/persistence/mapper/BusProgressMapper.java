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
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.ogcs.okra.example.game.persistence.domain.MemBusProgress;
import org.ogcs.okra.example.game.persistence.provider.BusProgressSqlProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("busProgressMapper")
public interface BusProgressMapper {

    @SelectProvider(type = BusProgressSqlProvider.class, method = "selectSql")
    List<MemBusProgress> select(long uid);

    @InsertProvider(type = BusProgressSqlProvider.class, method = "insertSql")
    void insert(MemBusProgress memBusProgress);

    @UpdateProvider(type = BusProgressSqlProvider.class, method = "updateSql")
    void update(MemBusProgress memBusProgress);

    @UpdateProvider(type = BusProgressSqlProvider.class, method = "deleteSql")
    void delete(@Param("uid") long uid, @Param("event") int busId);
}
