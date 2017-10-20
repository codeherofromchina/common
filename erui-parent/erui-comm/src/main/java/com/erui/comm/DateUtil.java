package com.erui.comm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
	
	
	public static String format(String format,Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return null;
	}

	//向后退时间
	public static Date recedeTime(int day){
	    if(day>=0){
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DAY_OF_MONTH,-day);
            Date time = cal.getTime();
            if(time!=null){
                return time;
            }
        }
        return null;
	}
}
