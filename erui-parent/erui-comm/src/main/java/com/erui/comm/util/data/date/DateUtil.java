package com.erui.comm.util.data.date;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
    public static final String FULL_FORMAT_STR2 = "yyyy/MM/dd HH:mm:ss";
    public static final String FULL_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_START_TIME = "yyyy-MM-dd 00:00:00";
    public static final String DAY_END_TIME = "yyyy-MM-dd 23:59:59";
    public static final String SHORT_FORMAT_STR = "yyyy-MM-dd";
    public static final String SHORT_FORMAT_DOT_STR = "yyyy.MM.dd";
    public static final String SHORT_SLASH_FORMAT_STR = "yyyy/MM/dd";
    public static final String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * <summary>
     * 判断年份是否是闰年，true为闰年，false为平年
     * </summary>
     *
     * @return
     * @author Xiongyan
     * @Date 2015年8月6日
     */
    public static boolean isLeapYear(Serializable year) {
        Integer _year = Integer.parseInt(year.toString());
        if ((_year % 400 == 0) || (_year % 4 == 0) && (_year % 100 != 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <summary>
     * 格式化日期到字符串。
     * </summary>
     *
     * @param date ：指定要格式转换的日期<br>
     *             format ：指定要转换的日期格式，如果指定为null，默认格式为：yyyy-MM-dd HH:mm:ss
     * @return
     * @author Xiongyan
     * @Date 2015年8月6日
     */
    public static String formatDateToString(Date date, String format) {
        if (StringUtils.isEmpty(format))
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        else
            return new SimpleDateFormat(format).format(date);
    }

    /**
     * <summary>
     * 解析字符串到日期。
     * </summary>
     *
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String dateStr, String format) throws ParseException {
        if (dateStr != null && !"".equals(dateStr)) {
            if (StringUtils.isEmpty(format))
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            else
                return new SimpleDateFormat(format).parse(dateStr);
        }
        return null;
    }

    /**
     * <summary>
     * 解析字符串到日期。
     * </summary>
     *
     * @param dateStr
     * @param formats
     * @return
     * @throws ParseException
     */
    public static Date parseString2Date(String dateStr, String... formats) throws ParseException {
        if (formats != null && formats.length > 0) {
            for (String format : formats) {
                try {
                    return parseStringToDate(dateStr, format);
                } catch (ParseException ex) {
                }
            }
        } else {
            return parseStringToDate(dateStr, null);
        }
        throw new ParseException(org.apache.commons.lang3.StringUtils.join(formats, " ") + "中没有符合的转换格式", 1);
    }


    public static Date parseString2DateNoException(String dateStr, String... formats) {
        try {
            if (formats != null && formats.length > 0) {
                for (String format : formats) {
                    try {
                        return parseStringToDate(dateStr, format);
                    } catch (ParseException ex) {
                    }
                }
            } else {
                return parseStringToDate(dateStr, null);
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 获取日期当天开始时间   如:(2015-09-10 00:00:00)
     *
     * @param date ：指定要格式转换的日期<br>
     *             format ：指定要转换的日期格式，如果指定为null，默认格式为：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws
     * @author peihuhu
     * @date 2015年9月10日下午4:10:43
     */
    public static String getStartTime(Date date, String format) {
        date = getOperationTime(date, 0, 0, 0);
        String strDate = formatDateToString(date, format);
        return strDate;
    }

    /**
     * 获取日期当天结束时间   如:(2015-09-10 23:59:59)
     *
     * @param date ：指定要格式转换的日期<br>
     *             format ：指定要转换的日期格式，如果指定为null，默认格式为：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws
     * @author peihuhu
     * @date 2015年9月10日下午3:59:47
     */
    public static String getEndTime(Date date, String format) {
        date = getOperationTime(date, 23, 59, 59);
        String strDate = formatDateToString(date, format);
        return strDate;
    }

    /**
     * 获取时间前多少毫秒的时间
     *
     * @param startDate ：当前日期
     * @param msec：毫秒数
     * @return
     * @throws
     * @author lirb
     * @date 2018年01月02日下午3:59:47
     */
    public static Date getBeforTime(Date startDate, long msec) {
        if (startDate != null) {
            long time = startDate.getTime();
            long beforeTime = time - msec;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beforeTime);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 获取操作后的日期, 对小时、分、秒操作
     *
     * @param date : 指定要操作的日期<br>
     *             hour	: 小时
     *             minute	: 分
     *             second	: 秒
     * @return
     * @throws
     * @author peihuhu
     * @date 2015年9月10日下午4:03:04
     */
    public static Date getOperationTime(Date date, int hour, int minute, int second) {
        if (date != null && !"".equals(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
            date = calendar.getTime();
            return date;
        }
        return null;
    }

    /**
     * 获取传入日期的周几
     *
     * @param date 传入日期
     * @param week 传入日期的周数(1-7)
     * @return
     */
    public static Date getWeek(Date date, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayWeek == 0) {
            dayWeek = 7;
        }
        calendar.add(Calendar.DATE, -dayWeek + week);
        date = getOperationTime(calendar.getTime(), 23, 59, 59);
        return date;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     * @Author:lirb
     */
    public static String getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:25 2017/11/15
     * @modified By
     */
    public static Date getBeforeWeek(Date date, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayWeek == 0) {
            dayWeek = 7;
        }
        calendar.add(Calendar.DATE, -dayWeek + week);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        date = getOperationTime(calendar.getTime(), 0, 0, 0);
        return date;
    }

    /**
     * @Author:lirb
     * @Description 获取本周六时间
     * @Date:16:25 2017/12/21
     * @modified By
     */
    public static Date getWeekSix(int week) {
        Calendar cal = Calendar.getInstance();
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int n = cal.get(Calendar.DAY_OF_WEEK);
        if (n == 1) {
            n = 7;
        } else {
            n = n - 1;
        }
        cal.set(Calendar.DAY_OF_MONTH, date + week - n);
        Date time = cal.getTime();
        return parseString2DateNoException(getStartTime(time, FULL_FORMAT_STR), FULL_FORMAT_STR);
    }

    /**
     * 得到本月的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            date = getOperationTime(calendar.getTime(), 0, 0, 0);
            return date;
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description 得到下月的第一天
     * @Date:13:50 2017/11/15
     * @modified By
     */
    public static Date getNextMonthFirstDay(Date date) {
        if (date != null && !"".equals(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONDAY, 1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            date = getOperationTime(calendar.getTime(), 0, 0, 0);
            return date;
        }
        return null;
    }

    /**
     * 得到本月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            date = getOperationTime(calendar.getTime(), 23, 59, 59);
            return date;
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description 得到下月的最后一天
     * @Date:15:51 2017/11/15
     * @modified By
     */
    public static Date getNextMonthLastDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            date = getOperationTime(calendar.getTime(), 23, 59, 59);
            return date;
        }
        return null;
    }

    /**
     * 获取传入日期的所属年份的第一天
     *
     * @param date
     * @return
     */
    public static Date getYearFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取传入日期的所属年份的最一天
     *
     * @param date
     * @return
     */
    public static Date getYearLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getMaximum(Calendar.DAY_OF_YEAR) - 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * @Author:SHIGS
     * @Description 支持跨年月两个日期之间的天数
     * @Date:16:57 2017/11/13
     * @modified By
     */
    public static int getDayBetween(Date d1, Date d2) {
        return (int) NewDateUtil.getDuration(d1, d2, TimeUnit.DAYS) + 1;

        /**
         Calendar calendar1 = Calendar.getInstance();
         Calendar calendar2 = Calendar.getInstance();
         calendar1.setTime(d1);
         calendar2.setTime(d2);
         int days = 0;
         while (calendar1.before(calendar2)) {
         days++;
         calendar1.add(Calendar.DAY_OF_YEAR, 1);
         }
         return days;
         **/

    }

    /**
     * 计算两个日期之间总天数
     *
     * @param d1 开始时间
     * @param d2 结束日期
     * @return
     * @throws
     * @author peihuhu
     * @date 2015年9月26日下午5:48:03
     */
    public static int getDaysBetween(Date d1, Date d2) {
        return getDayBetween(d1, d2) + 1;
    }

    public static Date str2Date(String strdate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
            LOGGER.info("转换异常[data:{},err:{}]", strdate, e);
        } catch (Exception ex) {
            LOGGER.info("转换异常[data:{},err:{}]", strdate, ex);
        }
        return null;
    }

    /**
     * @param d
     * @param day
     * @return
     * @author lirb
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return getOperationTime(now.getTime(), 23, 59, 59);
    }

    /**
     * 获取日期是当年第多少周
     *
     * @param datetime
     * @return
     */
    public static int getWeekNumber(Date datetime) {
        if (datetime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(datetime);
            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            return weekNumber;
        }
        return 0;
    }


    /**
     * 对当前时间做加减计算
     *
     * @param type 时间类型  时、 分 、 秒
     * @param time 加减的数值
     * @return Date
     * @throws
     * @author xiongyan
     * @date 2015年9月26日下午5:48:03
     */
    public static Date timeCalendar(int type, int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(type, time);
        return calendar.getTime();
    }

    /**
     * <div style="color:green;font-weight: bolder;">
     * <h3>时间比较</h3>
     * &emsp;&emsp;描述：比较当前时间与传递的时间参数的大小。若当前时间小于，返回false，否则返回true。
     * </div>
     *
     * @param
     * @return
     * @throws
     * @author xiongyan
     * @date 2015年12月2日下午1:29:58
     */
    public static boolean comparedTime(Date date) {
        Date currentDate = new Date();
        if (currentDate.getTime() > date.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <div style="color:green;font-weight: bolder;">
     * <h3></h3>
     * &emsp;&emsp;描述：将毫秒转换为秒
     * </div>
     *
     * @param
     * @return
     * @throws
     * @date 2016年4月6日下午2:47:37
     */
    public static Double ms2second(Serializable time) {
        return Double.parseDouble(String.valueOf(time)) / 1000;
    }

    /**
     * <div style="color:green;font-weight: bolder;">
     * <h3></h3>
     * &emsp;&emsp;描述：将时期按照指定的格式转换为字符串形式。
     * </div>
     *
     * @param
     * @return
     * @throws
     * @author xiongyan
     * @date 2016年3月18日下午8:22:23
     */
    public static String formatDate2String(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 计算现在的前day天开始时间
     *
     * @return
     */
    public static Date recedeTime(int day) {

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, -day);
        Date time = cal.getTime();
        if (time != null) {
            time = getOperationTime(time, 0, 0, 0);
        }
        return time;
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:29 2017/11/6
     * @modified By
     */
    public static String format(String format, Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:33 2017/11/14
     * @modified By
     */
    public static Date minTime(Date... dates) {
        if (dates != null && dates.length > 0) {
            Date result = dates[0];
            for (int i = 0; i < dates.length; i++) {
                if (result == null) {
                    result = dates[i];
                }
                if (dates[i] == null) {
                    continue;
                }
                if (result.after(dates[i])) {
                    result = dates[i];
                }
            }
            return result;
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:46 2017/11/14
     * @modified By
     */
    public static Date maxTime(Date... dates) {
        if (dates != null && dates.length > 0) {
            Date result = dates[0];
            for (int i = 0; i < dates.length; i++) {
                if (result == null) {
                    result = dates[i];
                }
                if (dates[i] == null) {
                    continue;
                }
                if (result.before(dates[i])) {
                    result = dates[i];
                }
            }
            return result;
        }
        return null;
    }

    /**
     * @Author:SHIGS
     * @Description 求某一天的前多少天
     * @Date:14:09 2017/11/13
     * @modified By
     */
    public static Date sometimeCalendar(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - days);
        return calendar.getTime();
    }


    /**
     * @param timeUnit Calendar的常量数值，例如Calendar.SECOND
     * @Description 求某日期的前多少时间
     * @Date:14:09 2017/11/13
     * @modified By
     */
    public static Date sometimeCalendar(Date date, int num, int timeUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int time = calendar.get(timeUnit);
        calendar.set(timeUnit, time - num);
        return calendar.getTime();
    }


    /**
     * 获取给定日期的年，如果日期为空，则返回-1
     * @param date
     * @return
     */
    public static int getDateYear(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            return year;
        }
        return -1;
    }

    /**
     * 获取给定日期的上一年日期
     * @param date
     * @return
     */
    public static Date getPreYearDate(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, -1);
            Date time = cal.getTime();
            return time;
        }
        return null;
    }


    /**
     * 解析 "yyyyy年MM月"成 "yyyy-MM-dd" 格式的字符串
     *
     * @param chDateStr
     * @return
     */
    public static Date parseChDateStrToEnDateStr(String chDateStr) throws ParseException {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(chDateStr)) {
            Date chDate = new SimpleDateFormat("yyyy年MM月").parse(chDateStr);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(chDate);
            Date startTime = format.parse(date);
            return startTime;
        }
        return null;
    }

    /**
     * 获取月份
     *
     * @param datetime
     * @return
     */
    public static int getMonth(Date datetime) {
        if (datetime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            int month = cal.get(Calendar.MONTH);
            return month + 1;
        }
        return 0;
    }

    /**
     * 获取年份
     *
     * @param datetime
     * @return
     */
    public static int getYearNumber(Date datetime) {
        if (datetime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            int year = cal.get(Calendar.YEAR);
            return year;
        }
        return 0;
    }

    /**
     * 获取某年第多少周
     *
     * @param datetime
     * @return 2018年第23周
     */
    public static String getYearAndWeekNumber(Date datetime) {
        if (datetime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);
            int year = cal.get(Calendar.YEAR);
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            return year + "年第" + week + "周";
        }
        return null;
    }

    /**
     * 计算在给定范围的日期内总共占有多少天
     *
     * @param startDate
     * @param endDate
     * @param rangeStartDate
     * @param rangeEndDate
     * @return
     */
    public static int inRangeDateDayNum(Date startDate, Date endDate, Date rangeStartDate, Date rangeEndDate) {
        if (startDate.compareTo(endDate) > 0 || startDate.compareTo(rangeEndDate) > 0 || endDate.compareTo(rangeStartDate) < 0) {
            return 0;
        }
        int result = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (startDate.compareTo(endDate) <= 0) {
            if (startDate.compareTo(rangeEndDate) > 0) { // 超过范围则直接退出
                break;
            }
            if (startDate.compareTo(rangeStartDate) >= 0) { // 在范围内则增加天数
                result++;
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
            startDate = cal.getTime();
        }


        return result;
    }

    /**
     * 计算两个日期之间的时间差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int diffDayNum(Date startDate, Date endDate) {
        int result = 0;
        if (startDate.compareTo(endDate) <= 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            do {
                result++;
                cal.add(Calendar.DAY_OF_YEAR, 1);
            } while (cal.getTime().compareTo(endDate) <= 0);
        }
        return result;
    }


    public static void main(String[] args) throws ParseException {
        Date startDate = parseStringToDate("2018-01-01 00:00:00", FULL_FORMAT_STR);
        Date endDate = parseStringToDate("2018-01-08 00:00:00", FULL_FORMAT_STR);
        Date rangeStartDate = parseStringToDate("2018-01-02 00:00:00", FULL_FORMAT_STR);
        Date rangeEndDate = parseStringToDate("2018-01-03 23:59:59", FULL_FORMAT_STR);
        int i = inRangeDateDayNum(startDate, endDate, rangeStartDate, rangeEndDate);
        System.out.println(i);


        Date d = new Date();
        String format = format(DAY_END_TIME, d);
        System.out.println(format);

    }
}