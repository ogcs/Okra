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

package org.ogcs.okra.example.game.business;

/**
 * @author TinyZ
 * @date 2016-09-27.
 */
public class BusLogic {

    /**
     * 计算派发事件Key
     * @param type 类型  [1: key  2:max]    [-128, 127]   保留字段
     * @param var8  byte值   [-128, 127]
     * @param var16 short值  [-32768, 32767]
     * @param var32 int值    [0x80000000, 0x7fffffff]
     * @return 返回计算出来的事件key
     */
    public static long eventKey(byte type, byte var8, short var16, int var32) {
        long key = (type & 0xffL) << 56;
        key |= (var8 & 0xffL) << 48;
        key |= (var16 & 0xffffL) << 32;
        key |= (var32 & 0xffffffffL);
        return key;
    }

    /**
     * 获取Key的类型
     * @param key 派发事件Key
     * @return 返回Key的类型
     */
    public static long eventKeyType(long key) {
        return (key >> 56) & 0xffL;
    }



    public static void main(String[] args) {
        long id = eventKey((byte)1, Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE);
        int var32 = (int) (id & 0xffffffffL);
        int var16 = (short) ((id >> 32) & 0xffffL);
        int var8 = (byte) ((id >> 48) & 0xffL);
        long type = eventKeyType(id);

        System.out.println();
    }

}
