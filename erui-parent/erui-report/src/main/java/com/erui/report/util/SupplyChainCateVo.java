package com.erui.report.util;

public class SupplyChainCateVo {
    private String  id;
    private String cat_no;
    private String lang;
    private String name;
    private String status;
    private String sort_order;
    private int spu;
    private int sku;
    private int supplier_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_no() {
        return cat_no;
    }

    public void setCat_no(String cat_no) {
        this.cat_no = cat_no;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public int getSpu() {
        return spu;
    }

    public void setSpu(int spu) {
        this.spu = spu;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getSupplier_count() {
        return supplier_count;
    }

    public void setSupplier_count(int supplier_count) {
        this.supplier_count = supplier_count;
    }
}
