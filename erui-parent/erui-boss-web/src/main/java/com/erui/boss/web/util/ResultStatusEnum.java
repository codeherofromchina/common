package com.erui.boss.web.util;

import java.util.HashMap;
import java.util.Map;


public enum ResultStatusEnum {
    SUCCESS(200, "成功", "Successful"),
    FAIL(1, "失败", "Failed"),
    SERVER_ERROR(2, "服务器端异常", "Server exception"),
    MISS_PARAM_ERROR(3, "缺少必要参数", "Lack of necessary parameters"),
    VALUE_NULL(31, "驳回原因不能为空", "Reason is not Null"),
    PARAM_TYPE_ERROR(4, "参数类型错误", "Error in parameter type"),
    REQUEST_METHOD_NOT_SUPPORT(5, "不支持的请求方法", "Request method unsupported"),
    MEDIA_TYPE_NOT_SUPPORT(6, "不支持的MediaType", "Media type unsupported"),
    DATA_NULL(7, "数据为空", "Lack of data"),
    PARAM_ERROR(8, "参数错误", "Parameter error"),
    GET_USERINFO_ERROR(9, "获取用户信息失败", "get data error"),
    NULL_DATA(-1, "数据为空", "Lack of data"),
    //-----------excel----------
    EXCEL_TYPE_NOT_SUPPORT(10, "不支持的Excel模板文件类型", "Excel template file type unsupported"),
    EXCEL_CONTENTYPE_ERROR(11, "错误的Excel文件类型", "Excel file type incorrect"),
    EXCEL_SAVE_FAIL(12, "Excel文件保存失败", "Excel file save failed"),
    EXCEL_CONTENT_EMPTY(13, "Excel数据内容为空", "Lack of Excel data"),
    EXCEL_HEAD_VERIFY_FAIL(14, "Excel头信息验证失败", "Excel information verification failed"),
    EXCEL_OPERATOR_FAIL(15, "Excel文件操作失败", "Excel file operation failed"),
    EXCEL_FILE_NOT_EXIST(16, "Excel文件不存在", "Excel file does not exist"),
    EXCEL_DOWNLOAD_FALL(17, "文件下载失败", "File download failed"),
    EXCEL_DATA_REPEAT(18, "Execel存在比数据库更早的数据", "data is not the latest"),
    //-----------大区----------------
    AREA_NOT_EXIST(20, "不存在的大区", "This area does not exist"),
    ////// ----
    PAGE_ERROR(21, "页码必须不能小于1", "Page number must exceed 1"),
    DUPLICATE_ERROR(22, "值不唯一", "Not unique"),
    COMPANY_NOT_EXIST(23, "公司不存在", "The company does not exist"),
    LACK_OF_AUTHORITY(24, "权限不足", "Lack of authority"),
    SQLDATA_ERROR(25, "数据错误,请检查数据是否规范", "Data error, please check if the data is normal"),
    DATA_REPEAT_ERROR(29, "数据重复错误", "Data duplication error"),
    ORDER_AUDIT_NOT_DONE_ERROR(26, "订单审核状态未完成", "Order audit status is not completed"),
    PROJECT_NOT_EXIST(27, "项目不存在", "project not exist"),
    NOT_NOW_AUDITOR(28, "非审核人，不能操作", "Non auditor can not operate."),
    NOT_NOW_USER(29, "非市场经办人不能操作", "Non agent can not operate."),
    DELIVER_CONSIGN_NOT_EXIST(30, "出口发货通知单不存在", "delivery notice not exist");

    private int code;
    private String msg;
    private String enMsg;

    ResultStatusEnum(Integer code, String msg, String enMsg) {
        this.code = code;
        this.msg = msg;
        this.enMsg = enMsg;
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

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        map.put("enMsg", this.enMsg);
        //return JSON.serialize(map);
        return "";
    }

}
