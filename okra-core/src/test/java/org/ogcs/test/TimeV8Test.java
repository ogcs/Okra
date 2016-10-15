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

package org.ogcs.test;

import org.junit.Assert;
import org.junit.Test;
import org.ogcs.utilities.DateUtil;
import org.ogcs.utilities.TimeV8Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;

import static org.ogcs.utilities.TimeV8Util.*;

public class TimeV8Test {

    @Test
    public void testDate() {
        Assert.assertEquals(
                TimeV8Util.date(),
                DateUtil.formatDate(System.currentTimeMillis())
        );
        String var1 = TimeV8Util.date(System.currentTimeMillis());
        System.out.println("DATE : " + var1);
        Assert.assertEquals(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                var1
        );
    }

    @Test
    public void testDateTime() {
        long n = System.currentTimeMillis();
        LocalDateTime now = LocalDateTime.ofEpochSecond(n / 1000L, (int) (n % 1000), ZoneOffset.of("+08:00"));
        String var1 = TimeV8Util.dateTime(n);
        System.out.println("DATETIME : " + var1);
        Assert.assertEquals(
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                var1
        );
    }

    @Test
    public void testDayInterval() {
        int day1 = dayInterval(parseDate("2016-09-24"), parseDate("2016-09-24"));
        Assert.assertEquals(day1, 0);
        int day2 = dayInterval(parseDate("2016-09-23"), parseDate("2016-09-24"));
        Assert.assertEquals(day2, 1);
        int day3 = dayInterval(parseDate("2016-09-25"), parseDate("2016-09-24"));
        Assert.assertEquals(day3, -1);
        LinkedList<String> dayList = getDayList(parseDate("2016-09-19"), parseDate("2016-09-24"));
        Assert.assertEquals(dayList, Arrays.asList(new String[]{
                "2016-09-19",
                "2016-09-20",
                "2016-09-21",
                "2016-09-22",
                "2016-09-23",
                "2016-09-24",
        }));
    }

    @Test
    public void testParseDateTime() {
        String time = "2016-09-24 00:00:00";
        long l1 = DateUtil.parseTime(time);
        long l2 = TimeV8Util.parseDateTime(time);
        Assert.assertEquals(l1, l2);
    }
}
