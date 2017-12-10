package com.erui.order.entity;

import javax.persistence.*;

/**
 * Created by wangxiaodan on 2017/12/8.
 */
@Entity
@Table(name="order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="contract_no")
    private String contract_no;


    public Long getId() {
        return id;
    }

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }
}
