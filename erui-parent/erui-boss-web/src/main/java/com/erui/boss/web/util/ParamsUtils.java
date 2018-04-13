package com.erui.boss.web.util;

import com.erui.comm.util.data.date.DateUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Date;
import java.util.Map;

/*
 *   参数接收验证工具类
 * */
public class ParamsUtils {

    public static Map<String,String> verifyParam(Map<String,String> params,String dateFormat,String ...otherParam){
        if(dateFormat.isEmpty()){
            dateFormat= DateUtil.SHORT_SLASH_FORMAT_STR;
        }
        // 获取参数并转换成时间格式
        Date startTime = DateUtil.parseString2DateNoException(params.get("startTime"), dateFormat);
        Date endDate = DateUtil.parseString2DateNoException(params.get("endTime"), dateFormat);
        if (startTime == null || endDate == null || startTime.after(endDate)) {
            return null;
        }

        if(!ArrayUtils.isEmpty(otherParam)){
            for (String param:otherParam) {
                if(params.get(param).isEmpty()){
                    return null;
                }
            }
        }
        if(dateFormat.equals(DateUtil.FULL_FORMAT_STR2)){
            // 获取需要环比的开始时间
            long differenTime = endDate.getTime() - startTime.getTime();
            Date rateStartDate = DateUtil.getBeforTime(startTime, differenTime);
            params.put("chainStartTime",DateUtil.formatDateToString(rateStartDate,DateUtil.FULL_FORMAT_STR2));
            return params;
        }else if(dateFormat.equals(DateUtil.SHORT_SLASH_FORMAT_STR)){
            Date endTime = DateUtil.getOperationTime(endDate, 23, 59, 59);
            // 获取需要环比的开始时间
            int days = DateUtil.getDayBetween(startTime, endTime);
            Date chainStartTime = DateUtil.sometimeCalendar(startTime, days);
            params.put("endTime",DateUtil.formatDateToString(endTime,DateUtil.FULL_FORMAT_STR2));
            params.put("chainStartTime",DateUtil.formatDateToString(chainStartTime,DateUtil.FULL_FORMAT_STR2));
            return params;
        }

        return  null;
    }

}
