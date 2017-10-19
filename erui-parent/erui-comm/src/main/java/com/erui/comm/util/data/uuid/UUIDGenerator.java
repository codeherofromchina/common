package com.erui.comm.util.data.uuid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.erui.comm.middle.redis.JedisPoolUtil;
import org.apache.commons.lang.RandomStringUtils;


public class UUIDGenerator {

	private static final SimpleDateFormat seqDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
	public static String getUUID() {
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.replaceAll("\\-","");  
        return temp;  
    } 
	
	/**
	 * @deprecated
	 * @param number
	 * @return
	 */
	public static String[] getUUID(int number) {  
        if (number < 1) {  
            return null;  
        }  
        String[] ss = new String[number];  
        for (int i = 0; i < number; i++) {  
            ss[i] = getUUID();  
        }  
        return ss;  
    }  
	
	public static String getOrderNum()
	{
		String seqDate = seqDateFormat.format(System.currentTimeMillis());
		String jid = autoGenericCode(JedisPoolUtil.getPcount(seqDate),3);
		String candidateSeq = new StringBuilder(17).append(seqDate).append(RandomStringUtils.randomNumeric(2)).append(jid).toString();
		return candidateSeq;
	}
	private static String autoGenericCode(long code, int num) {
        String result = "";
        result = String.format("%0" + num + "d", code);
        return result;
    }
	public static void main(String[] args) {
		for (String str : getUUID(13)) {
			
		}
		
	}
}
