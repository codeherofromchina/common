package com.erui.out.web.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.StringUtils;

/**
 * 统一返回的结果集合
 */
//@JsonInclude(Include.NON_NULL)
public class Result<T> {
	private int code;
	private String msg;
	private T data;

	public Result() {
		this(ResultStatusEnum.SUCCESS);
	}
	
	public Result(ResultStatusEnum resultStatus) {
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
	}

	public Result(T data) {
		this.setStatus(ResultStatusEnum.SUCCESS);
		this.data = data;
	}

	public Result<T> setStatus(ResultStatusEnum resultStatus) {
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Result<T> setMsg(String msg) {
		if (StringUtils.isNotBlank(msg)) {
			this.msg = msg;
		}
		return this;
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String toJsonString(){
		return JSONObject.toJSONString(this);
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}


	public static void main(String[] args) {
		Result<Object> result = new Result<>(ResultStatusEnum.AREA_NOT_EXIST);

		System.out.println(result.toJsonString());
	}
}
