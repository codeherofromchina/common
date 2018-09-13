package com.erui.comm;


import com.alibaba.fastjson.JSON;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewDateUtil {
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";


    public static String format(String format, Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

            return localDateTime.format(DateTimeFormatter.ofPattern(format));
        }
        return null;
    }


    //向后退时间111
    public static Date recedeTime(int day) {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.minusDays(day);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(dateTime.atZone(zone).toInstant());
    }


    /**
     * 给定日期是否在上周内（定义一周是从周六开始到下周五结束）
     *
     * @param date
     * @return
     */
    public static boolean inSaturdayWeek(Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate paramDate = instant.atZone(zoneId).toLocalDate();

            LocalDate localDate = LocalDate.now();
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (dayOfWeek.compareTo(DayOfWeek.FRIDAY) > 0) { // 大于周五，算本周五就可以
                localDate = localDate.minusDays(dayOfWeek.getValue() - DayOfWeek.FRIDAY.getValue());
            } else { // 计算上周五
                localDate = localDate.minusDays(dayOfWeek.getValue() + (DayOfWeek.SUNDAY.getValue() - DayOfWeek.FRIDAY.getValue()));
            }

            if (localDate.isBefore(paramDate)) { // 大于周五，不在上周内
                return false;
            }
            localDate = localDate.minusDays(6); // 获取相对于周五的上周六

            if (localDate.isAfter(paramDate)) { // 小于上周六，不在上周内
                return false;
            }
            return true;
        }
        return false;
    }
    /**
     * 给定日期是否在上周五之前（定义一周是从周六开始到下周五结束）
     *
     * @param date
     * @return
     */
    public static boolean lessFridayWeek(Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate paramDate = instant.atZone(zoneId).toLocalDate();

            LocalDate localDate = LocalDate.now();
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (dayOfWeek.compareTo(DayOfWeek.FRIDAY) > 0) { // 大于周五，算本周五就可以
                localDate = localDate.minusDays(dayOfWeek.getValue() - DayOfWeek.FRIDAY.getValue());
            } else { // 计算上周五
                localDate = localDate.minusDays(dayOfWeek.getValue() + (DayOfWeek.SUNDAY.getValue() - DayOfWeek.FRIDAY.getValue()));
            }
            if (localDate.isBefore(paramDate)) { // 大于周五，不在上周内
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 获取给定日期的上周日期的连续字符串
     * @param date
     * @return 此处不通用，返回的为 yyyy/MM/dd--yyyy/MM/dd的格式不能变
     */
    public static String getBeforeSaturdayWeekStr(Date date) {
        Date[] week = getBeforeSaturdayWeek(date);
        String formatStr = "yyyy/MM/dd";
        String format = format(formatStr, week[0]);
        return String.format("%s--%s",format(formatStr, week[0]),format(formatStr, week[1]));
    }

    /**
     * 获取给定日期的上周日期 （定义一周是从周六开始到下周五结束）
     * @param date 当前给定日期，如果空则为当前日期
     * @return (上周开始日期，上周结束日期) -- 返回日期只包含年月日数据
     */
    public static Date[] getBeforeSaturdayWeek(Date date) {
        Date[] result = new Date[2];

        LocalDate paramDate = null;
        ZoneId zoneId = ZoneId.systemDefault();
        if (date == null) {
            paramDate = LocalDate.now();
        } else {
            Instant instant = date.toInstant();
            paramDate = instant.atZone(zoneId).toLocalDate();
        }

        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek.compareTo(DayOfWeek.FRIDAY) > 0) { // 大于周五，算本周五就可以
            localDate = localDate.minusDays(dayOfWeek.getValue() - DayOfWeek.FRIDAY.getValue());
        } else { // 计算上周五
            localDate = localDate.minusDays(dayOfWeek.getValue() + (DayOfWeek.SUNDAY.getValue() - DayOfWeek.FRIDAY.getValue()));
        }

        result[1] = Date.from(localDate.atStartOfDay(zoneId).toInstant());
        localDate = localDate.minusDays(6); // 获取相对于周五的上周六
        result[0] = Date.from(localDate.atStartOfDay(zoneId).toInstant());

        return result;
    }

    /**
     * 给定日期是否在下个自然月内
     *
     * @param date
     * @return
     */
    public static boolean inNextMonth(Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate paramDate = instant.atZone(zoneId).toLocalDate();

            LocalDate localDate = LocalDate.now();
            localDate = localDate.plusMonths(1); // 获取下个月
            // 获得这个月的第一天
            localDate = localDate.minusDays(localDate.getDayOfMonth() - 1);
            if (localDate.isAfter(paramDate)) {
                return false;
            }
            // 获取这个月的最后一天
            localDate = localDate.plusDays(localDate.lengthOfMonth() - 1);
            if (localDate.isBefore(paramDate)) {
                return false;
            }
            return true;
        }


        return false;
    }

    /**
     * 获取环比(上环比或者下环比)日期
     * 例如：给定 2017-12-03 、2017-12-05  -> 2017-12-07
     * 例如：给定 2017-12-05 、2017-12-03  -> 2017-12-01
     * 例如：给定 2017-12-01 、2017-12-02  -> 2017-12-03
     * 例如：给定 2017-12-02 、2017-12-01  -> 2017-11-31
     * 例如：给定 2017-12-01 、2017-12-01  -> 2017-12-01
     * 例如：给定 2017-12-02 、null  -> null
     * 例如：给定 null 、2017-12-01  -> null
     * 例如：给定 null 、null  -> null
     *
     * @return date
     */
    public static Date getBeforeRateDate(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime startDateTime = startDate.toInstant().atZone(zoneId).toLocalDateTime();
            LocalDateTime endDateTime = endDate.toInstant().atZone(zoneId).toLocalDateTime();

            Duration duration = Duration.between(startDateTime, endDateTime);
            long days = duration.toDays();
            LocalDateTime result = endDateTime.plusDays(days);
            return Date.from(result.atZone(zoneId).toInstant());
        }

        return null;
    }

    /**
     * 获取给定日期直接的时间差 - 默认天
     * @param startDate
     * @param endDate
     * @param unit
     * @return
     */
    public static long getDuration(Date startDate, Date endDate , TimeUnit unit) {
        if (startDate != null && endDate != null) {
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime startDateTime = startDate.toInstant().atZone(zoneId).toLocalDateTime();
            LocalDateTime endDateTime = endDate.toInstant().atZone(zoneId).toLocalDateTime();

            Duration duration = Duration.between(startDateTime, endDateTime);
            if (unit == TimeUnit.MINUTES) {
                return duration.toMinutes();
            } else if (unit == TimeUnit.HOURS) {
                return duration.toHours();
            }
            return duration.toDays();

        }

        return 0;
    }

    /**
     * 在现有的时间上增加n天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(Date date, int days) {
        if (date != null) {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime();
            dateTime = dateTime.plusDays(days);
            return Date.from(dateTime.atZone(zoneId).toInstant());
        }
        return null;
    }



    /**
     * 只获取给定日期的年月日日期
     * @param date
     * @return
     */
    public static Date getDate(Date date) {
        if (date == null) {
            return null;
        }

        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    /**
     * 获取所有跨度的年字符串列表
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> allSpanYearList(Date startTime, Date endTime) {
        List<String> result = new ArrayList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        if (endCalendar.compareTo(startCalendar) >= 0) {
            int startYear = startCalendar.get(Calendar.YEAR);
            result.add(String.valueOf(startYear));

            startCalendar.add(Calendar.YEAR,1);
            while(endCalendar.compareTo(startCalendar) > 0) {
                int year = startCalendar.get(Calendar.YEAR);
                result.add(String.valueOf(year));
                startCalendar.add(Calendar.YEAR,1);
            }
            int endYear = endCalendar.get(Calendar.YEAR);
            if (!result.get(result.size()-1).equals(String.valueOf(endYear))) {
                result.add(String.valueOf(endYear));
            }
        }
        return result;
    }


    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2030-01-01 12:23:33");
        List<String> strings = allSpanYearList(date, date2);
        System.out.println(JSON.toJSONString(strings));
    }
}
