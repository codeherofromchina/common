package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 分公司实体信息
 */
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Area area;

    /**
     * 分公司名称
     */
    private String name;
    //所属地区
    @Column(name="area_bn",insertable=false,updatable=false)
    private String areaBn;
    //英文名称
    @Column(name = "en_name")
    private String enName;
    @Column(name = "create_time")
    private Date createTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Set<Dept> deptSet = new HashSet<>();

    public String getEnName() {
        return enName;
    }
    public void setEnName(String enName) {
        this.enName = enName;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaBn() {
        return areaBn;
    }

    public void setAreaBn(String areaBn) {
        this.areaBn = areaBn;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Dept> getDeptSet() {
        return deptSet;
    }

    public void setDeptSet(Set<Dept> deptSet) {
        this.deptSet = deptSet;
    }
}