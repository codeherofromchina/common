package com.erui.report.util;

/**
 * 询单状态
 * Created by lirb on 2017/11/21.
 */
public enum QuotedStatusEnum {
    STATUS_QUOTED_NO(0,"未报价"),
    STATUS_QUOTED_ING(1,"报价中"),
    STATUS_QUOTED_ED(2,"已报价"),
    STATUS_QUOTED_FINISHED(3,"已完成"),
    STATUS_QUOTED_CANCEL(4,"询单取消");
    private int code;
    private String quotedStatus;

    QuotedStatusEnum(Integer code, String quotedStatus){
        this.code=code;
        this.quotedStatus=quotedStatus;
    }

    public int getCode() {
        return code;
    }

    public String getQuotedStatus() {
        return quotedStatus;
    }

    @Override
    public String toString() {
        return "QuotedStatusEnum{" +
                "code=" + code +
                ", quotedStatus='" + quotedStatus + '\'' +
                '}';
    }
}
