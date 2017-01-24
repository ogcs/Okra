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

package okra.demo.common.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ogcs.okra.example.game.persistence.domain.MemChar;
import org.springframework.stereotype.Component;

/**
 * 角色
 * @author TinyZ
 * @date 2017-01-11.
 */
@Component
public interface CharMapper {

    @Select("SELECT * FROM tb_char WHERE uid=#{uid}")
    MemChar select(long uid);

    @Update("UPDATE FROM tb_char" +
            " SET cfgMasterId=#{target.cfgMasterId}, hp=#{target.hp}, timeLastRwd=#{target.timeLastRwd}" +
            " WHERE uid=#{uid}")
    void update(@Param("uid") long uid, @Param("char") MemChar target);

    @Insert("INSERT INTO tb_char (uid,cfgMasterId,hp,timeLastRwd)" +
            " VALUES(#{uid}, #{target.cfgMasterId}, #{target.hp}, #{target.timeLastRwd})")
    void insert(@Param("uid") long uid, @Param("char") MemChar target);
}
