package org.ogcs.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author zzh 2013-8-19
 *         时间日期相关应用
 */
public class DateUtil {

    /**
     * 缺省的日期样式"yyyy-MM-dd"
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 缺省的日期时间样式"yyyy-MM-dd HH:mm:ss"
     */
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    private static SimpleDateFormat timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);

    /**
     * 格式转换(日期 + 时间)
     *
     * @param time 时间戳
     * @return 返回"yyyy-MM-dd HH:mm:ss"格式日期时间字符串
     */
    public static String formatTime(long time) {
        return timeFormat.format(new Date(time));
    }

    /**
     * 格式转换(日期)
     *
     * @param time 时间戳
     * @return 返回"yyyy-MM-dd"格式日期时间字符串
     */
    public static String formatDate(long time) {
        return dateFormat.format(new Date(time));
    }

    /**
     * 格式转换()
     *
     * @param time    时间戳
     * @param pattern 格式日期样式, 例如"yyyy-MM-dd"或"yyyy-MM-dd HH:mm:ss"
     * @return 返回相应样式的日期时间字符串
     */
    public static String formatTime(long time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }

    /**
     * 格式转换(日期)
     *
     * @param str 日期字符串, 例如“2015-01-25”
     * @return 时间戳
     */
    public static long parseTime(String str) {
        long time = System.currentTimeMillis();
        try {
            Date date = dateFormat.parse(str);
            time = date.getTime();
        } catch (ParseException localParseException) {
            // TODO: handle exception
        }
        return time;
    }

    /**
     * 格式转换(时间)
     *
     * @param str     时间字符串
     * @param pattern 格式
     * @return 时间戳
     */
    public static long parseTime(String str, String pattern) {
        long time = System.currentTimeMillis();
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date date = format.parse(str);
            time = date.getTime();
        } catch (ParseException localParseException) {
            // TODO: handle exception
        }
        return time;
    }

    /**
     * 获取0点时间
     *
     * @param time 时间戳
     * @return 返回时间戳当日0点的时间戳
     */
    public static long getZeroTime(long time) {
        String str = dateFormat.format(new Date(time));

        long zeroTime = time;
        try {
            Date date = dateFormat.parse(str);
            zeroTime = date.getTime();
        } catch (ParseException localParseException) {
            // TODO: handle exception
        }
        return zeroTime;
    }

    /**
     * 计算时间间隔(天数)
     *
     * @param start 起始时间
     * @param end   终止时间 , 当时间大于起始时间 返回正数，否则返回负数
     * @return 间隔天数
     */
    public static int getDayInterval(long start, long end) {
        long startZeroTime = getZeroTime(start);
        long endZeroTime = getZeroTime(end);
        return (int) ((endZeroTime - startZeroTime) / 86400000L);
    }
//------------------------------------------------------------

    /**
     * 获取一段时间内的凌晨点
     *
     * @param time1 开始日期时间戳
     * @param time2 结束日期时间戳
     * @return 一段时间内0点的时间戳列表
     */
    public static LinkedList<Long> getDayListByTime(long time1, long time2) {
        LinkedList<Long> dayList = new LinkedList<Long>();
        try {
            long zeroTime1 = getZeroTime(time1);
            long zeroTime2 = getZeroTime(time2);
            // 小的时间放前面
            long dayTime = 86400000;
            int day = (int) ((zeroTime2 - zeroTime1) / dayTime);
            for (int i = 0; i <= day; i++) {
                dayList.addLast(zeroTime1 + i * dayTime);
            }
        } catch (Exception ignored) {
        }
        return dayList;
    }

    /**
     * 获取当前日期的值
     *
     * @return 当前日期的值, 例如“2015-03-31”
     */
    public static String getDayPath() {
        long now = System.currentTimeMillis();
        return dateFormat.format(new Date(now));
    }
}