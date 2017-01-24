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

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.springframework.stereotype.Component;

@Component("exampleMapper")
public interface ExampleMapper {

    @Update("CREATE TABLE IF NOT EXISTS `tb_product` (\n" +
            "  `pdt_id` varchar(50) NOT NULL DEFAULT '' COMMENT '产品货号',\n" +
            "  `pdt_name` varchar(100) NOT NULL DEFAULT '' COMMENT '产品名称',\n" +
            "  `pdt_color` varchar(20) NOT NULL DEFAULT '0' COMMENT '产品颜色',\n" +
            "  `pdt_counts` varchar(100) NOT NULL DEFAULT '' COMMENT '产品库存',\n" +
            "  `pdt_cost` double(20,2) NOT NULL DEFAULT '0.00' COMMENT '成本价',\n" +
            "  `pdt_price` float(11,2) NOT NULL DEFAULT '0.00' COMMENT '标价',\n" +
            "  `pdt_total` double(20,2) NOT NULL DEFAULT '0.00' COMMENT '库存总数',\n" +
            "  `pdt_comment` text COMMENT '备注',\n" +
            "  `total_rmb` double(20,2) NOT NULL DEFAULT '0.00' COMMENT '库存总价值',\n" +
            "  `datetime` datetime NOT NULL COMMENT '记录时间',\n" +
            "  `timeLastOp` datetime NOT NULL COMMENT '最后一次操作时间',\n" +
            "  `flag` tinyint(3) NOT NULL DEFAULT '0' COMMENT '[0]有效数据  [1]废弃的',\n" +
            "  PRIMARY KEY (`pdt_id`),\n" +
            "  UNIQUE KEY `pdt_id` (`pdt_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;")
    void createTable();

    @Select("select * from tb_role where account=#{account}")
    MemAccount select(String account);
}
