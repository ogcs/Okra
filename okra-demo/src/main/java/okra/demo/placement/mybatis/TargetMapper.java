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

import okra.demo.placement.bean.MemTarget;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author TinyZ
 * @date 2017-01-11.
 */
@Component
public interface TargetMapper {

    @Select("SELECT * FROM tb_target WHERE uid=#{uid}")
    MemTarget select(long uid);

    @Update("UPDATE FROM tb_target" +
            " SET cfgMasterId=#{target.cfgMasterId}, hp=#{target.hp}, timeLastRwd=#{target.timeLastRwd}" +
            " WHERE uid=#{uid}")
    void update(@Param("uid") long uid, @Param("target") MemTarget target);

    @Insert("INSERT INTO tb_target (uid,cfgMasterId,hp,timeLastRwd)" +
            " VALUES(#{uid}, #{target.cfgMasterId}, #{target.hp}, #{target.timeLastRwd})")
    void insert(@Param("uid") long uid, @Param("target") MemTarget target);
}
