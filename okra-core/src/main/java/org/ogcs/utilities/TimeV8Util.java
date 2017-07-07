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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * 日期时间工具
 * <p>基于JDK 1.8</p>
 *
 * @author TinyZ
 * @since 2.0
 */
public final class TimeV8Util {

    /**
     * 缺省时区:  +08:00 东八区
     */
    private static String defaultZoneOffset = "+08:00";
    private static final String DATE_TIME_STR = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_STR = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_STR);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_STR);

    static {
        defaultZoneOffset = System.getProperty("time.timezone", defaultZoneOffset);
    }

    private TimeV8Util() {
        //  no-op
    }

    // DATE

    public static String date() {
        return date(LocalDate.now());
    }

    public static String date(long time) {
        return date(time, ZoneOffset.of(defaultZoneOffset), DATE_FORMATTER);
    }

    public static String date(long time, String zone) {
        return date(time, ZoneOffset.of(zone));
    }

    public static String date(long time, String zone, String pattern) {
        return date(time, ZoneOffset.of(zone), DateTimeFormatter.ofPattern(pattern));
    }

    public static String date(long time, ZoneOffset offset) {
        return date(time, offset, DATE_FORMATTER);
    }

    public static String date(long time, ZoneOffset offset, DateTimeFormatter formatter) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), offset).toLocalDate().format(formatter);
    }

    public static String date(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String date(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static long parseDate(String date) {
        return parseDate(date, DATE_STR, defaultZoneOffset);
    }

    public static long parseDate(String date, String pattern, String zone) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern))
                .atStartOfDay()
                .toEpochSecond(ZoneOffset.of(zone)) * 1000L;
    }

    /**
     * 计算日期间隔(天数)
     *
     * @param date0 起始日期
     * @param date1 终止日期
     * @return 间隔天数, 当时间大于起始时间 返回正数，否则返回负数
     */
    public static int dayInterval(String date0, String date1) {
        ZoneOffset offset = ZoneOffset.of(defaultZoneOffset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_STR);
        return (int) Duration.between(
                LocalDate.parse(date0, formatter).atStartOfDay().toInstant(offset),
                LocalDate.parse(date1, formatter).atStartOfDay().toInstant(offset)
        ).toDays();
    }

    /**
     * 计算日期间隔(天数)
     *
     * @param time0 起始时间戳(微秒)
     * @param time1 终止时间戳(微秒)
     * @return 间隔天数, 当时间大于起始时间 返回正数，否则返回负数
     */
    public static int dayInterval(long time0, long time1) {
        ZoneOffset offset = ZoneOffset.of(defaultZoneOffset);
        return (int) (Duration.between(
                LocalDateTime
                        .ofEpochSecond(time0 / 1000L, 0, offset)
                        .withHour(0).withMinute(0).withSecond(0),
                LocalDateTime
                        .ofEpochSecond(time1 / 1000L, 0, offset)
                        .withHour(0).withMinute(0).withSecond(0)
        ).toDays());
    }

    public static LinkedList<Long> getTimeList(long time0, long time1) {
        return getTimeList(time0, time1, defaultZoneOffset);
    }

    public static LinkedList<Long> getTimeList(long time0, long time1, String zone) {
        ZoneOffset offset = ZoneOffset.of(zone);
        LocalDateTime var0 = LocalDateTime.ofEpochSecond(time0 / 1000L, 0, offset)
                .withHour(0).withMinute(0).withSecond(0);
        LocalDateTime var1 = LocalDateTime.ofEpochSecond(time1 / 1000L, 0, offset)
                .withHour(0).withMinute(0).withSecond(0);
        LinkedList<Long> dayList = new LinkedList<>();
        if (var1.isBefore(var0)) {
            return dayList;
        }
        LocalDateTime tmp = var0;
        while (tmp.isBefore(var1) || tmp.isEqual(var1)) {
            dayList.add(tmp.toLocalDate().atStartOfDay().toEpochSecond(offset) * 1000L);
            tmp = tmp.plusDays(1);
        }
        return dayList;
    }

    public static LinkedList<String> getDayList(long time0, long time1) {
        return getDayList(time0, time1, defaultZoneOffset);
    }

    public static LinkedList<String> getDayList(long time0, long time1, String zone) {
        ZoneOffset offset = ZoneOffset.of(zone);
        LocalDateTime var0 = LocalDateTime.ofEpochSecond(time0 / 1000L, 0, offset)
                .withHour(0).withMinute(0).withSecond(0);
        LocalDateTime var1 = LocalDateTime.ofEpochSecond(time1 / 1000L, 0, offset)
                .withHour(0).withMinute(0).withSecond(0);
        LinkedList<String> dayList = new LinkedList<>();
        if (var1.isBefore(var0)) {
            return dayList;
        }
        LocalDateTime tmp = var0;
        while (tmp.isBefore(var1) || tmp.isEqual(var1)) {
            dayList.add(date(tmp.toLocalDate()));
            tmp = tmp.plusDays(1);
        }
        return dayList;
    }

    // DATE TIME

    public static String dateTime() {
        return dateTime(LocalDateTime.now());
    }

    public static String dateTime(long time) {
        return dateTime(time, ZoneOffset.of(defaultZoneOffset), DATE_TIME_FORMATTER);
    }

    public static String dateTime(long time, String zone) {
        return dateTime(time, ZoneOffset.of(zone), DATE_TIME_FORMATTER);
    }

    public static String dateTime(long time, String zone, String pattern) {
        return dateTime(time, ZoneOffset.of(zone), DateTimeFormatter.ofPattern(pattern));
    }

    public static String dateTime(long time, ZoneOffset offset) {
        return dateTime(time, offset, DATE_TIME_FORMATTER);
    }

    public static String dateTime(long time, ZoneOffset offset, DateTimeFormatter formatter) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), offset).format(formatter);
    }

    public static String dateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String dateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static long parseDateTime(String dateTime) {
        return parseDateTime(dateTime, DATE_TIME_STR, defaultZoneOffset);
    }

    public static long parseDateTime(String dateTime, String pattern) {
        return parseDateTime(dateTime, pattern, defaultZoneOffset);
    }

    public static long parseDateTime(String dateTime, String pattern, String zone) {
        return LocalDateTime.
                parse(dateTime, DateTimeFormatter.ofPattern(pattern)).
                toEpochSecond(ZoneOffset.of(zone)) * 1000L;
    }
}
