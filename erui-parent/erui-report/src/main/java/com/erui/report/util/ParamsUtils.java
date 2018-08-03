package com.erui.report.util;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 *   参数接收验证工具类
 * */
public class ParamsUtils {
    /**
     * 询订单分类 TOP N
     *
     * @param params         参数
     * @param dateFormat     时间 类型
     * @param otherParamsKey 其他参数验证
     * @return
     */
    public static Map<String, Object> verifyParam(Map<String, Object> params, String dateFormat, String... otherParamsKey) {
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = DateUtil.SHORT_SLASH_FORMAT_STR;
        }
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(String.valueOf(params.get("startTime")), dateFormat);
        Date endDate = DateUtil.parseString2DateNoException(String.valueOf(params.get("endTime")), dateFormat);
        if (startTime == null || endDate == null || startTime.after(endDate)) {
            return null;
        }

        if (!ArrayUtils.isEmpty(otherParamsKey)) {
            for (String param : otherParamsKey) {
                if (params.get(param) == null) {
                    return null;
                }
            }
        }
        if (dateFormat.equals(DateUtil.FULL_FORMAT_STR2)) {
            // 获取需要环比的开始时间
            long differenTime = endDate.getTime() - startTime.getTime();
            Date rateStartDate = DateUtil.getBeforTime(startTime, differenTime);
            params.put("chainStartTime", DateUtil.formatDateToString(rateStartDate, DateUtil.FULL_FORMAT_STR2));
            params.put("paramStatus", "current");
            return params;
        } else if (dateFormat.equals(DateUtil.SHORT_SLASH_FORMAT_STR)) {
            Date endTime = DateUtil.getOperationTime(endDate, 23, 59, 59);
            // 获取需要环比的开始时间
            int days = DateUtil.getDayBetween(startTime, endTime);
            Date chainStartTime = DateUtil.sometimeCalendar(startTime, days);
            params.put("endTime", DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2));
            params.put("chainStartTime", DateUtil.formatDateToString(chainStartTime, DateUtil.FULL_FORMAT_STR2));
            params.put("paramStatus", "current");
            return params;
        } else if (dateFormat.equals(DateUtil.FULL_FORMAT_STR)) { // 上周和当次的
            Date lastWeekDateTime = DateUtil.sometimeCalendar(startTime, 7);
            Date lastWeekDateTime02 = DateUtil.sometimeCalendar(startTime, 1, Calendar.SECOND);
            params.put("chainStartTime", DateUtil.formatDateToString(lastWeekDateTime, DateUtil.FULL_FORMAT_STR));
            params.put("chainEndTime", DateUtil.formatDateToString(lastWeekDateTime02, DateUtil.FULL_FORMAT_STR));
            return params;
        }
        return null;
    }

    public static Map<String, Object> getChainParams(Map<String, Object> params) {
        //查询环比数据
        if (String.valueOf(params.get("paramStatus")).equals("current")) {
            String startTime = String.valueOf(params.get("startTime"));
            String endTime = String.valueOf(params.get("endTime"));
            String chainStartTime = String.valueOf(params.get("chainStartTime"));
            params.put("startTime", chainStartTime);
            params.put("endTime", startTime);
            params.put("chainStartTime", endTime);
            params.put("paramStatus", "chain");
        }
        return params;
    }

    public static Map<String, Object> getCurrentParams(Map<String, Object> params) {

        if (String.valueOf(params.get("paramStatus")).equals("chain")) {
            String startTime = String.valueOf(params.get("startTime"));
            String endTime = String.valueOf(params.get("endTime"));
            String chainStartTime = String.valueOf(params.get("chainStartTime"));
            params.put("chainStartTime", startTime);
            params.put("startTime", endTime);
            params.put("endTime", chainStartTime);
            params.put("paramStatus", "current");
        }
        return params;
    }


}
