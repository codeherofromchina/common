package com.erui.report.util;

public enum AnalyzeTypeEnum {

    INQUIRY_COUNT(0,"询单数量"),
    INQUIRY_AMOUNT(1,"询单金额"),
    QUOTE_COUNT(2,"报价数量");

    private int code;
    private String typeName;

    AnalyzeTypeEnum(int code ,String typeName){
        this.code=code;
        this.typeName=typeName;
    }

    public int getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }
}
