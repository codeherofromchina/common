package com.erui.comm.util.encrypt;

import java.security.MessageDigest;
/**
 * MD5加密类
 * @author lixiang
 */
public class MD5 {
	/**
	 * 私有方法 字节数组转成字符串
	 * @author lx
	 * @param	b		要转化的字节数组	
	 * @return 	String	转化后的字符串
	 */
	private static String byteToHexString(byte b) {
		final String hexDigits = "0123456789abcdef";
		int n = b < 0 ? 256 + b : b;
		int d1 = n / 16;
		int d2 = n % 16;
		return "" + hexDigits.charAt(d1) + hexDigits.charAt(d2);
	}
	
	/**
	* 私有方法 转换字节数组为16进制字串
	* @return 16进制字串
	*/	
	private static String byteArrayToHexString(byte[] b) {
		
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	* MD5加密
	* @param 	origin 	要加密的字符串
	* @return 	String	加密后的字符串
	*/	
	public static String encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			
		}catch (Exception ex) {
			System.out.println("MD5加密出现错误");
		}
		return resultString;
	}
	
	public static void main(String[] args) {
		System.out.println(encode("2469BBC2929DCC1967CE6FB252A10BEE").toUpperCase());
	}
}