package com.erui.report.util;

public enum GetDataEnum {
    SKU_DATA(0,"获取sku数据"),
    SPU_DATA(1,"获取spu数据"),
    SUPPLIER_DATA(2,"获取供应商数据");
    private int code;
    private String message; 

    GetDataEnum(int code,String message) {
        this.code = code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage(){
        return  message;
    }
    @Override
    public String toString() {
        return "GetDataEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
