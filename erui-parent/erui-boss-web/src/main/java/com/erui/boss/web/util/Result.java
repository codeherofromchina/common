package com.erui.boss.web.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 统一返回的结果集合
 */
//@JsonInclude(Include.NON_NULL)
public class Result<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(Result.class);
    private int code;
    private String msg;
    private String enMsg;
    private T data;

    public Result() {
        this(ResultStatusEnum.SUCCESS);
    }

    public Result(ResultStatusEnum resultStatus) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMsg();
        this.enMsg = resultStatus.getMsg();
    }

    public Result(T data) {
        this.setStatus(ResultStatusEnum.SUCCESS);
        this.data = data;
    }

    public Result<T> setStatus(ResultStatusEnum resultStatus) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMsg();
        this.enMsg = resultStatus.getEnMsg();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public Result<T> setEnMsg(String enMsg) {
        if (StringUtils.isNotBlank(enMsg)) {
            this.enMsg = enMsg;
        }
        return this;
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


    public void printResult(OutputStream out) {
        String s = JSONObject.toJSONString(this);
        try {
            out.write(s.getBytes("UTF-8"));
        } catch (IOException e) {
            LOGGER.error("输出异常[data:{},err:{}]", this, e);
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", enMsg='" + enMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
