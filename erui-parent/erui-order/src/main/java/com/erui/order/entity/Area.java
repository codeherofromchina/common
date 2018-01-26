package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 地区表
 */
@Entity
@Table(name="area")
public class Area {
   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private Integer id;

    /**
     * 地区名称，例如：北美
     */
    private String name;

    private String lang;
    @Id
    @GeneratedValue
    private String bn;

    private String status;
    @Column(name = "deleted_flag")
    private Character deletedFlag;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="area_bn",insertable=false,updatable=false)
    private Set<Company> companySet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Character getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Character deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
}