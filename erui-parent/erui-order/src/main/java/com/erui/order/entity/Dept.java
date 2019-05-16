package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

/**
 * 分销部
 */
@Entity
@Table(name = "dept")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 分公司
     */
 /*   @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;*/
    //英文名称
    @Column(name = "en_name")
    private String enName;
    /**
     * 分销部名称
     */
    private String name;

    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

   /* public void setId(Integer id) {
        this.id = id;
    }*/

/*    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }*/

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}