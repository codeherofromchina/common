package com.erui.power.model;

import java.util.Date;

public class FuncPerm {
    private Long id;

    private Long parentId;

    private String fn;

    private String fnEn;

    private String fnEs;

    private String fnRu;

    private String showName;

    private String showNameEn;

    private String showNameEs;

    private String showNameRu;

    private String fnGroup;

    private String url;

    private Integer sort;

    private String grantFlag;

    private String remarks;

    private String source;

    private Long createdBy;

    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getFnEn() {
        return fnEn;
    }

    public void setFnEn(String fnEn) {
        this.fnEn = fnEn;
    }

    public String getFnEs() {
        return fnEs;
    }

    public void setFnEs(String fnEs) {
        this.fnEs = fnEs;
    }

    public String getFnRu() {
        return fnRu;
    }

    public void setFnRu(String fnRu) {
        this.fnRu = fnRu;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowNameEn() {
        return showNameEn;
    }

    public void setShowNameEn(String showNameEn) {
        this.showNameEn = showNameEn;
    }

    public String getShowNameEs() {
        return showNameEs;
    }

    public void setShowNameEs(String showNameEs) {
        this.showNameEs = showNameEs;
    }

    public String getShowNameRu() {
        return showNameRu;
    }

    public void setShowNameRu(String showNameRu) {
        this.showNameRu = showNameRu;
    }

    public String getFnGroup() {
        return fnGroup;
    }

    public void setFnGroup(String fnGroup) {
        this.fnGroup = fnGroup;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getGrantFlag() {
        return grantFlag;
    }

    public void setGrantFlag(String grantFlag) {
        this.grantFlag = grantFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}