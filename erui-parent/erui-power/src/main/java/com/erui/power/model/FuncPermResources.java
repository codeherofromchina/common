package com.erui.power.model;

public class FuncPermResources {
    private Long id;

    private Long funcPermId;

    private String resourcesId;

    private Boolean auth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuncPermId() {
        return funcPermId;
    }

    public void setFuncPermId(Long funcPermId) {
        this.funcPermId = funcPermId;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }
}