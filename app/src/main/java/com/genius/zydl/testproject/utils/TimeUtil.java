package com.genius.zydl.testproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Genius on 2017/7/4/0004.
 */

public class TimeUtil {
    /**
     * 返回当前日期
     *
     * @return 2017-07-04
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(System.currentTimeMillis());
        return date;
    }

    /**
     * 返回当前短时间
     *
     * @return HH:mm
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(System.currentTimeMillis());
        return time;
    }

    /**
     * 获取当前长日期
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentLongDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(System.currentTimeMillis());
        return date;
    }

    public static String getCurrentLongDate(long data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(data);
        return date;
    }

    /**
     * 获取当前短日期
     *
     * @return MM-dd
     */
    public static String getCurrentShortDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        String date = simpleDateFormat.format(System.currentTimeMillis());
        return date;
    }

    /**
     * 获取当前日期
     *
     * @return 2017年7月4日
     */
    public static String getCurrentDateInString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR); // 获取当前年份
        int month = calendar.get(Calendar.MONTH) + 1;// 获取当前月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 一年内的第几个周
     *
     * @return 27
     */
    public static int getWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * 星期几
     *
     * @return 星期二
     */
    public static String getDayOfWeek() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[day_of_week];
    }

    /**
     * 两个时间进行比较
     *
     * @param time1 标准值
     * @param time2 时间段的中间值，上下浮动30分钟
     * @return
     */
    public static boolean compareTime(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        long pointTime = 0;
        long currentTime = 0;
        try {
            pointTime = format.parse(time1).getTime();
            currentTime = format.parse(time2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((currentTime - 30 * 60 * 1000) <= pointTime && pointTime <= (currentTime + 30 * 60 * 1000)) {
            return true;
        } else {
            return false;
        }
    }
}
