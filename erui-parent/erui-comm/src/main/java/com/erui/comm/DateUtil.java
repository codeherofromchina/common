package com.erui.comm;

import java.text.SimpleDateFormat;
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
}
