package com.erui.comm;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    //向后退时间
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


    public static void main(String[] args) throws ParseException {
        LocalTime localTime = LocalTime.of(12, 24, 32);
        LocalTime localTime2 = LocalTime.of(11, 22, 0);

        Duration dration = Duration.between(localTime2, localTime);
        System.out.println(dration.toMinutes());

    }
}
