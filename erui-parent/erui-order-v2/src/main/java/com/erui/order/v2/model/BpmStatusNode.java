package com.erui.order.v2.model;

/**
 * 流程--流程状态节点
 */
public class BpmStatusNode {
    //  主键
    private Long id;
    // '业务类型'
    private String category;
    // '业务子类型'
    private String subCategory;
    // '流程节点ID'
    private String actId;
    // '流程节点名称'
    private String actName;
    // '流程节点排序值'
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

