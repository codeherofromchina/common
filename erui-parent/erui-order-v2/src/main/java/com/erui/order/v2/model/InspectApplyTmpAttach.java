package com.erui.order.v2.model;

public class InspectApplyTmpAttach {
    private Integer id;

    private Integer inspectApplyId;

    private Integer attachId;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectApplyId() {
        return inspectApplyId;
    }

    public void setInspectApplyId(Integer inspectApplyId) {
        this.inspectApplyId = inspectApplyId;
    }

    public Integer getAttachId() {
        return attachId;
    }

    public void setAttachId(Integer attachId) {
        this.attachId = attachId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}