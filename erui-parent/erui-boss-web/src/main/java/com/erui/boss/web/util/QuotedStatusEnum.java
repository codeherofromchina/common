package com.erui.boss.web.util;

/**
 * Created by lirb on 2017/11/21.
 */
public enum QuotedStatusEnum {
    STATUS_QUOTED_NO(0,"未报价"),
    STATUS_QUOTED_ING(1,"报价中"),
    STATUS_QUOTED_ED(2,"已报价"),
    STATUS_QUOTED_CANCEL(3,"询单取消"),
    STATUS_QUOTED_NORMAL(4,"询单正常");
    private int code;
    private String quotedStatus;

    QuotedStatusEnum(Integer code,String quotedStatus){
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
