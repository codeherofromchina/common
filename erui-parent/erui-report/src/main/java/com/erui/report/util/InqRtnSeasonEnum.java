package com.erui.report.util;

public enum InqRtnSeasonEnum {
    PROJECT_CLEAR("projectClear","项目澄清"),
    NOT_ORG("notOrg","非事业部业务范围"),
    NOT_SUPPLY("notSupply","无供应渠道"),
    SYSTEM_PROBLEMS("systemProblems","系统问题"),
    OTHER("other","其他");
    private String en;
    private String ch;

    InqRtnSeasonEnum(String en, String ch) {
        this.en = en;
        this.ch = ch;
    }

    public String getEn() {
        return en;
    }

    public String getCh() {
        return ch;
    }
}
