package com.erui.power.model;

public class Resources {
    private Long id;

    private String name;

    private String url;

    private String tip;

    private Integer noneAuth;

    private Boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getNoneAuth() {
        return noneAuth;
    }

    public void setNoneAuth(Integer noneAuth) {
        this.noneAuth = noneAuth;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}