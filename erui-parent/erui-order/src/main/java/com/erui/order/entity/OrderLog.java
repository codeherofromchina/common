package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单-日志信息
 */
@Entity
@Table(name = "order_log")
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    /**
     * 订单日志类型 1：创建订单 2：商品入库 3：船期/航班 4：商品出库 5：报关放行时间 6：抵达时间 7：实际离港时间 8：交收完成
     */
    @Column(name="log_type")
    private Integer logType;

    // 操作说明
    private String operation;

    // 业务日期
    @Column(name = "business_date")
    private Date businessDate;

    @Column(name="create_id")
    @JsonIgnore
    private Integer createId;

    @Column(name="orders_goods_id")
    @JsonIgnore
    private Integer ordersGoodsId;

    @Column(name="create_name")
    @JsonIgnore
    private String createName;

    @Column(name="create_time")
    @JsonIgnore
    private Date createTime;

    @Column(name="order_account_id")
    @JsonIgnore
    private Integer orderAccountId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }


    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getOrdersGoodsId() {
        return ordersGoodsId;
    }

    public void setOrdersGoodsId(Integer ordersGoodsId) {
        this.ordersGoodsId = ordersGoodsId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderAccountId() {
        return orderAccountId;
    }

    public void setOrderAccountId(Integer orderAccountId) {
        this.orderAccountId = orderAccountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this).toString();
    }

    public static enum LogTypeEnum{
        CREATEORDER(1,"创建订单"),ADVANCE(2,"收到预付款"),GOODOUT(3,"商品出库"),DELIVERED(4,"已收货"),
        DELIVERYDONE(5,"全部交收完成"),OTHER(6,"其他");

        public int code;
        public String msg;
        LogTypeEnum(int code,String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}