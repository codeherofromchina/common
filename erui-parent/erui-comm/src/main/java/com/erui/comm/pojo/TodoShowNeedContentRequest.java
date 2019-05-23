package com.erui.comm.pojo;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/5/16 上午10:13
 */
public class TodoShowNeedContentRequest {
    private String key;
    private List<String> bizKeys;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getBizKeys() {
        return bizKeys;
    }

    public void setBizKeys(List<String> bizKeys) {
        this.bizKeys = bizKeys;
    }
}
