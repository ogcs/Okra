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

package okra.demo.placement.mybatis;

import org.apache.ibatis.annotations.*;
import okra.demo.placement.bean.MemItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author TinyZ
 * @date 2017-01-11.
 */
@Component
public interface ItemMapper {

    @Select("SELECT * FROM tb_item WHERE uid=#{uid}")
    List<MemItem> selectByUid(long uid);

    @Update("UPDATE FROM tb_item SET amount=#{amount} WHERE uid=#{uid} AND itemId=#{itemId}")
    void update(@Param("uid")long uid, @Param("memItem") MemItem memItem);

    @Insert("INSERT INTO tb_item (uid,cfgItemId,amount) VALUES(#{uid}, #{memItem.cfgItemId}, #{memItem.amount})")
    @Options(useGeneratedKeys = true, keyProperty = "memItem.itemId")
    void insert(@Param("uid")long uid, @Param("memItem") MemItem memItem);

}
