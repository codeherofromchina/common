package com.erui.report.util;

public enum AreaEnum {
    BEIMEI(0,"北美地区"),
    FANE(1,"泛俄地区"),
    FEIZHOU(2,"非洲地区"),
    NANMEI(3,"南美地区"),
    OUZHOU(4,"欧洲地区"),
    YATAI(5,"亚太地区"),
    ZHONGDONG(6,"中东地区");

    private  int code;
    private String message;

     AreaEnum(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
