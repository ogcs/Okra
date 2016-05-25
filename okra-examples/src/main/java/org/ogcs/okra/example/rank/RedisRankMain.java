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

package org.ogcs.okra.example.rank;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * Redis实现排行榜的示例
 * @author TinyZ on 2016/5/25.
 */
public class RedisRankMain {

    public static void main(String[] args) {
        String rankKey = "nRank";

        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        try (Jedis jedis = pool.getResource()) {
            // Test data
            jedis.zadd(rankKey, 100, "uid-00100");
            jedis.zadd(rankKey, 101, "uid-00101");
            jedis.zadd(rankKey, 102, "uid-00102");
            jedis.zadd(rankKey, 103, "uid-00103");
            jedis.zadd(rankKey, 104, "uid-00104");
            jedis.zadd(rankKey, 105, "uid-00105");
            jedis.zadd(rankKey, 106, "uid-00106");
            jedis.zadd(rankKey, 107, "uid-00107");
            jedis.zadd(rankKey, 108, "uid-00108");
            jedis.zadd(rankKey, 109, "uid-00109");
            jedis.zadd(rankKey, 50, "uid-00050");

            // 重新赋值
            jedis.zadd(rankKey, 200, "uid-00001");
            // 增加值
//            jedis.zincrby(rankKey, 10, "uid-00001");

            // =Redis基本用法=
            //  1.从小到大排序
            //  返回key的列表
            Set<String> zrange = jedis.zrange(rankKey, 0, -1);
            //  返回key和score的元组
            Set<Tuple> tuples = jedis.zrangeWithScores(rankKey, 0, -1);
            //  score在(-inf, +inf)范围内的排序， 返回key的列表
            Set<String> strings = jedis.zrangeByScore(rankKey, "-inf", "+inf");
            //  score在(-inf, +inf)范围内的排序， 返回key和score的元组
            Set<Tuple> tuples1 = jedis.zrangeByScoreWithScores(rankKey, "-inf", "+inf");
            //  2.从大到小的排序
            Set<Tuple> tuples2 = jedis.zrevrangeWithScores(rankKey, 0, -1);

            // 从大到小排序 根据score获取前5名
            Set<Tuple> rankTop5 = jedis.zrevrangeWithScores(rankKey, 0, 4);
            // 从大到小排序, score范围[+inf, 80).
            Set<Tuple> tuples4 = jedis.zrevrangeByScoreWithScores(rankKey, "+inf", "(80");
            // 从大到小排序, score范围[+inf, 80).  设置偏移和获取数量  可以用于实现排行榜分页显示
            Set<Tuple> tuples3 = jedis.zrevrangeByScoreWithScores(rankKey, "+inf", "(80", 2, 3);

            System.out.println();
        }
        // close pool
        pool.destroy();

    }
}
