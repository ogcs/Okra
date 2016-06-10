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

package org.ogcs.utilities;

import org.junit.Test;

/**
 * 1. StringUtil.split()比JDK的String.split()快.
 * 2. String.split()使用“\\|”转移代替"|"效率快3倍.
 *
 * @author TinyZ on 2016/6/10.
 */
public class StringSplitTest {

    String str = "aaa||bbb|ccc|ddd";
    String str1 = "aaa,,bbb,ccc,ddd";
    String str2 = "aaa,bbb,ccc,ddd";

    private final static int count = 10000000;

    @Test
    public void testSplit() {
        for (int i = 0; i < count; i++) {
            String[] split = StringUtil.split(str, '|');
        }
    }

    @Test
    public void testSplitAllowEmpty() {
        for (int i = 0; i < count; i++) {
            String[] split = StringUtil.split(str, '|', 0, false);
        }
    }

    @Test
    public void testJdkSplit() {
        for (int i = 0; i < count; i++) {
            String[] split = str.split("\\|");
        }
    }
}