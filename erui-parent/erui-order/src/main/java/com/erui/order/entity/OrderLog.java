package com.erui.order.entity;

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
    private Order order;

    /**
     * 订单日志类型 1：创建订单 2：商品入库 3：船期/航班 4：商品出库 5：报关放行时间 6：抵达时间 7：实际离港时间 8：交收完成
     */
    @Column(name="log_type")
    private Integer logType;

    private String operation;

    @Column(name="create_id")
    private Integer createId;

    @Column(name="orders_goods_id")
    private Integer ordersGoodsId;

    @Column(name="create_name")
    private String createName;

    @Column(name="create_time")
    private Date createTime;

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
    public static enum LogTypeEnum{
        CREATEORDER(1,"创建订单"),GOODIN(2,"商品入库"),GOODOUT(3,"商品出库"),SHIPDATE(4,"船期/航班"),
        CLEARANCETIME(5,"报关放行时间"),SAILINGTIME(6,"实际离港时间"), ARRIVALTIME(7,"预计抵达时间"),DELIVERYDONE(8,"交收完成"),OTHER(8,"交收完成");

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