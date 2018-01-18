package com.erui.report.util;

public enum OrgStatusEnum {
    ERUI(1, "易瑞"),
    WEFIC(2, "WEFIC"),
    YOUTIAN(3, "油田设备"),
    YOUZENG(4, "油增"),
    ZUANHUANG(5, "钻装"),
    KANGSAIDE(6, "康赛德"),
    DONGSHI(7, "东石"),
    YOUFU(8, "油服"),
    KANGBORUI(9, "康博瑞"),
    TIANRANQI(10, "天然气工程"),
    OTHER(11, "其它");
    private int code;
    private String message;

    OrgStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

}
