package com.erui.power.model;

/**
 * API资源实体类
 */
public class Resources {
    private Long id;


    private String name;

    /**
     * 唯一
     */
    private String url;

    private String tip;

    private int noneAuth = 0;

    /**
     * 是否可用 false：不可用  true：可用
     */
    private boolean enable = true;

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

    public int getNoneAuth() {
        return noneAuth;
    }

    public void setNoneAuth(int noneAuth) {
        this.noneAuth = noneAuth;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 可以调用的资源需要的权限枚举类
     */
    public static enum NoneAuthEnum {
        PERMISSIONS(0, "需要权限系统分配才能调用"), LOGIN(1, "登录后可以调用"), ANONYMOUS(2, "可随意调用");

        private int code;
        private String desc;

        private NoneAuthEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}