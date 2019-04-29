package com.erui.order.requestVo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GS on 2017/12/14 0014.
 * 门户订单列表返回  为兼容商城老版本
 */
public class ResponseOutOrder {
    private Integer id;
    private String order_no;
    private String po_no;
    private Integer pay_status;
    private Integer status;
    private String trade_terms_bn;
    private String currency_bn;
    private String to_port;
    private String to_country;
    //订单日期
    private Date created_at;
    //交货日期
    private String delivery_at;
    //订单金额
    private BigDecimal amount;
    private Integer type;
    //支付状态显示
    private String pay_status_text;
    //状态显示
    private String show_status_text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPo_no() {
        return po_no;
    }

    public void setPo_no(String po_no) {
        this.po_no = po_no;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTrade_terms_bn() {
        return trade_terms_bn;
    }

    public void setTrade_terms_bn(String trade_terms_bn) {
        this.trade_terms_bn = trade_terms_bn;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDelivery_at() {
        return delivery_at;
    }

    public void setDelivery_at(String delivery_at) {
        this.delivery_at = delivery_at;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCurrency_bn() {
        return currency_bn;
    }

    public void setCurrency_bn(String currency_bn) {
        this.currency_bn = currency_bn;
    }

    public String getTo_port() {
        return to_port;
    }

    public void setTo_port(String to_port) {
        this.to_port = to_port;
    }

    public String getTo_country() {
        return to_country;
    }

    public void setTo_country(String to_country) {
        this.to_country = to_country;
    }

    public String getPay_status_text() {
        return pay_status_text;
    }

    public void setPay_status_text(String pay_status_text) {
        this.pay_status_text = pay_status_text;
    }

    public String getShow_status_text() {
        return show_status_text;
    }

    public void setShow_status_text(String show_status_text) {
        this.show_status_text = show_status_text;
    }
}
