package com.erui.boss.web.util;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.util.JSON;

public enum ResultStatusEnum {
	SUCCESS(0, "成功"), 
	FAIL(1, "失败"), 
	SERVER_ERROR(2, "服务器端异常"), 
	MISS_PARAM_ERROR(3, "缺少必要参数"), 
	PARAM_TYPE_ERROR(4,"参数类型错误"),
	REQUEST_METHOD_NOT_SUPPORT(5,"不支持的请求方法");

	private int code;
	private String msg;

	ResultStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", this.code);
		map.put("msg", this.msg);
		return JSON.serialize(map);
	}

}
