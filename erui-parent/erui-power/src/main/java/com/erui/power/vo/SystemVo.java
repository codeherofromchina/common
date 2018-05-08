package com.erui.power.vo;

/**
 * 系统处理的参数实例
 */
public class SystemVo {
    /**
     * 页大小,pageSize=0 时查询所有信息
     */
    private Integer pageSize;

    /**
     * 页码，从0开始，pageNum<=0 会查询第一页
     */
    private Integer pageNum;

    /**
     * 系统编码
     */
    private String bn;

    /**
     * 系统名称
     */
    private String name;

    /**
     * 系统是否启用 true:启用  false:已停用
     */
    private Boolean enable;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
