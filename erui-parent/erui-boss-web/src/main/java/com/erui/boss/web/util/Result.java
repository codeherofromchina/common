package com.erui.boss.web.util;

/**
 * 统一返回的结果集合
 */
public class Result<T> {
	private int code;
	private String msg;
	private T data;

	public Result(ResultStatusEnum resultStatus) {
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
	}

	public Result(T data) {
		this.setStatus(ResultStatusEnum.SUCCESS);
		this.data = data;
	}

	public void setStatus(ResultStatusEnum resultStatus) {
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
