package com.erui.order.requestVo;

/**
 * Created by wangxiaodan on 2017/12/16.
 */
public class InspectReportVo {
    private Integer id;

    private Integer checkUserId; // 质检员ID


    private int page = 0;

    private int pageSize = 20;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
