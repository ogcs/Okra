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
import org.ogcs.okra.example.game.persistence.domain.MemBusInfo;
import org.ogcs.okra.example.game.persistence.domain.MemBusListener;
import org.ogcs.okra.example.game.persistence.provider.BusInfoSqlProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("busInfoMapper")
public interface BusInfoMapper {

    @SelectProvider(type = BusInfoSqlProvider.class, method = "selectSql")
    List<MemBusInfo> select(long uid);

    @InsertProvider(type = BusInfoSqlProvider.class, method = "insertSql")
    void insert(MemBusInfo memBusInfo);

    @SelectProvider(type = BusInfoSqlProvider.class, method = "selectByEventSql")
    MemBusListener selectByEvent(@Param("uid") long uid, @Param("event") int event);

    @UpdateProvider(type = BusInfoSqlProvider.class, method = "updateSql")
    void update(MemBusInfo memBusInfo);

    @UpdateProvider(type = BusInfoSqlProvider.class, method = "deleteSql")
    void delete(@Param("uid") long uid, @Param("busId") int busId);

    @UpdateProvider(type = BusInfoSqlProvider.class, method = "deleteByListSql")
    void deleteBatch(@Param("uid") long uid, @Param("list") List<Integer> list);

}
