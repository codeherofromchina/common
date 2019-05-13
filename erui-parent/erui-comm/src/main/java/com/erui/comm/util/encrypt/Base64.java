package com.erui.comm.util.encrypt;

import java.io.UnsupportedEncodingException;

import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@SuppressWarnings("restriction")
public class Base64 {
	// 加密
	public static String encode(String str) {
		byte[] b = null;
		String s = null;
		Encoder encoder = java.util.Base64.getEncoder();
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = encoder.encodeToString(b);
		}
		return s;
	}

	// 解密
	public static String decode(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			Decoder decoder = java.util.Base64.getDecoder();
			try {
				b = decoder.decode(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
