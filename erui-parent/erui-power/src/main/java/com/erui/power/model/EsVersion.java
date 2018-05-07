package com.erui.power.model;

public class EsVersion {
    private Integer id;

    private Short updateVersion;

    private Short selectVersion;

    private String alias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(Short updateVersion) {
        this.updateVersion = updateVersion;
    }

    public Short getSelectVersion() {
        return selectVersion;
    }

    public void setSelectVersion(Short selectVersion) {
        this.selectVersion = selectVersion;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}