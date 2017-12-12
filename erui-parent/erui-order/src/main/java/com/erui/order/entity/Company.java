package com.erui.order.entity;

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

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name="area_id")
    private Area area;

    /**
     * 分公司名称
     */
    private String name;

    @Column(name="create_time")
    private Date createTime;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Dept> deptSet = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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