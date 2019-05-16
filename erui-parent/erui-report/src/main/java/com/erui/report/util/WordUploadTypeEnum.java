package com.erui.report.util;

public enum WordUploadTypeEnum {
    PROCUREMENT_CONTRACT_STANDARD_TEMP(1, "标准合同模板", new String[]{"序号" });

    private int type;
    private int column;
    private String table;

    WordUploadTypeEnum(int type, String table, String[] title) {
        this.type = type;
        this.table = table;
        this.column = title.length;
    }

    public int getType() {
        return type;
    }

    public String getTable() {
        return table;
    }

    public int getColumn() {
        return column;
    }

    public static WordUploadTypeEnum getByType(Integer type) {
        if (type != null) {
            int typeInt = type.intValue();
            WordUploadTypeEnum[] values = WordUploadTypeEnum.values();
            for (WordUploadTypeEnum eu : values) {
                if (eu.getType() == typeInt) {
                    return eu;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        WordUploadTypeEnum[] values = WordUploadTypeEnum.values();
        for (WordUploadTypeEnum v : values) {
            System.out.print(v.getType() + ":" + v.getTable() + ",");
        }

    }

}
