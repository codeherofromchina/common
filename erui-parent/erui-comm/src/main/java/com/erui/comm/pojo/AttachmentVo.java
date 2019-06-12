package com.erui.comm.pojo;

import java.util.Date;

/**
 * @Auther 王晓丹
 * @Date 2019/6/12 下午5:26
 */
public class AttachmentVo {
    private Integer id;

    private String group;

    private String title;

    private String url;

    private Integer userId;

    private String userName;

    private String frontDate;

    private Boolean deleteFlag;

    private Date deleteTime;

    private Date createTime;

    private Integer type;

    private Integer relObjId;

    private String category;

    private String tenant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFrontDate() {
        return frontDate;
    }

    public void setFrontDate(String frontDate) {
        this.frontDate = frontDate;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRelObjId() {
        return relObjId;
    }

    public void setRelObjId(Integer relObjId) {
        this.relObjId = relObjId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
