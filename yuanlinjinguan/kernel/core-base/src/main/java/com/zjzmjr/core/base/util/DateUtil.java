package com.zjzmjr.core.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: DateUtil.java, v 0.1 2016-10-14 下午3:24:01 Administrator Exp $
 */
public final class DateUtil {

    public static final String YY = "20";

    public static final String YYYMMDDHHMMMSS = "yyyy-MM-dd HH:mm:ss";
    
    public static final String YYMMDDHHMMMSS = "yy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_dd = "yyyy-MM-dd";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMMDDHHMM = "yyyy_MM_dd HH:mm";

    public static final String YYMMDD = "yyMMdd";

    public static final String YY_MM_DD = "yy-MM-dd";

    public static final String HHMMSS = "HH:mm:ss";

    public static final String YYYYMMDDEEEHHMMSS = "yyyy-MM-dd EEE HH:mm:ss";

    public static final String YYYY = "yyyy";
    
    public static final String YYYYMM = "yyyyMM";
    
    public static final String CN_YYYYMMDD = "yyyy年MM月dd日";

    private DateUtil() {

    }

    public static String getCurrentDateStr() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return format(currDate);
    }

    public static Date parse(String datetime, String pattern) {

        if (StringUtils.isEmpty(datetime) || StringUtils.isEmpty(pattern)) {
            return null;
        }

        SimpleDateFormat dateFromat = new SimpleDateFormat();
        dateFromat.applyPattern(pattern);

        try {
            return dateFromat.parse(datetime);
        } catch (ParseException e) {
            return null;
        }

    }

    public static Date parseUS(String datetime) {
        return parseUS(datetime, YYYYMMDDEEEHHMMSS);
    }

    public static Date parseUS(String datetime, String pattern) {
        if (StringUtils.isEmpty(datetime) || StringUtils.isEmpty(pattern)) {
            return null;
        }

        SimpleDateFormat dateFromat = new SimpleDateFormat(pattern, Locale.US);

        try {
            return dateFromat.parse(datetime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getCurrentDateStr(String strFormat) {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();

        return format(currDate, strFormat);
    }

    public static String format(Date datetime, String pattern) {
        if (datetime == null || StringUtils.isEmpty(pattern))
            return null;

        try {
            SimpleDateFormat dateFromat = new SimpleDateFormat();
            dateFromat.applyPattern(pattern);
            return dateFromat.format(datetime);
        } catch (Exception e) {
            return null;
        }

    }

    public static String format(Date dateTime) {
        return format(dateTime, YYYY_MM_dd);
    }

    public static Date addMinuts(Date date, int minuts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minuts);

        return calendar.getTime();
    }

    /**
     * 计算二个时间相差的秒钟
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static long getDistanceSecond(Date d1, Date d2) {
        DateFormat df = new SimpleDateFormat(YYYMMDDHHMMMSS);
        try {
            long t1 = df.parse(df.format(d1)).getTime();
            long t2 = df.parse(df.format(d2)).getTime();

            return (t2 - t1) / 1000;

        } catch (ParseException e) {

            return 0;
        }

    }

    public static Date addDay(Date date, int days) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, days);

        return startDT.getTime();
    }

    /**
     * 时间增加N秒
     * 
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSecond(Date date, int seconds) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.SECOND, seconds);

        return startDT.getTime();
    }

    /**
     * 
     * @param date
     * @param seconds
     * @return
     */
    public static Date subSecond(Date date, int seconds) {
        seconds = 0 - seconds;
        return addSecond(date, seconds);
    }

    /**
     * 奖期顺延计算两个时间相差的天数
     * 
     * @param start
     *            开始时间
     * @param end
     *            结束时间
     * @return 天数
     */
    public static int issueDateParse(String start, String end) {
        if (StringUtils.isBlank(start) && StringUtils.isBlank(end)) {
            return 0;
        }
        Calendar caStart = Calendar.getInstance();
        caStart.setTime(parse(start, YYYYMMDD));
        int yearStart = caStart.get(Calendar.DAY_OF_YEAR);

        Calendar caEnd = Calendar.getInstance();
        caEnd.setTime(parse(end, YYYYMMDD));
        int yearEnd = caEnd.get(Calendar.DAY_OF_YEAR);

        int num = yearEnd - yearStart + 1;
        return num;
    }

    /**
     * 获取两个日期的月份差
     * 
     * @param maxDate
     *            大日期
     * @param minDate
     *            小日期
     * @return
     */
    public static int getMonthDiff(Date maxDate, Date minDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(maxDate);
        c2.setTime(minDate);
        if (c1.getTimeInMillis() < c2.getTimeInMillis())
            return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        int yearInterval = year1 - year2;
        if (month1 < month2 || month1 == month2 && day1 < day2)
            yearInterval--;
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2)
            monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }
}
