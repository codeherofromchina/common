package com.erui.boss.web.util;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.util.JSON;

public enum ResultStatusEnum {
    SUCCESS(200, "成功"),
    FAIL(1, "失败"),
    SERVER_ERROR(2, "服务器端异常"),
    MISS_PARAM_ERROR(3, "缺少必要参数"),
    PARAM_TYPE_ERROR(4, "参数类型错误"),
    REQUEST_METHOD_NOT_SUPPORT(5, "不支持的请求方法"),
    MEDIA_TYPE_NOT_SUPPORT(6, "不支持的MediaType"),
    DATA_NULL(7, "数据为空"),
    PARAM_ERROR(8, "参数错误"),
    //-----------excel----------
    EXCEL_TYPE_NOT_SUPPORT(10, "不支持的Excel模板文件类型"),
    EXCEL_CONTENTYPE_ERROR(11, "错误的Excel文件类型"),
    EXCEL_SAVE_FAIL(12, "Excel文件保存失败"),
    EXCEL_CONTENT_EMPTY(13, "Excel数据内容为空"),
    EXCEL_HEAD_VERIFY_FAIL(14, "Excel头信息验证失败"),
    EXCEL_OPERATOR_FAIL(15, "Excel文件操作失败"),
    EXCEL_FILE_NOT_EXIST(16, "Excel文件不存"),

    //-----------daqu----------------
    AREA_NOT_EXIST(20, "不存在的大区"),
    ////// ----
    PAGE_ERROR(21, "页码必须不能小于1"),
    DUPLICATE_ERROR(22,"值不唯一");
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
