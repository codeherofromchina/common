package com.erui.order.requestVo;

import com.erui.order.entity.ComplexOrder;
import com.erui.order.entity.Order;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GS on 2017/12/14 0014.
 * 门户订单列表模糊查询条件
 */
public class OutOrderDetail {
    private Integer id;
    //订单编号
    private String order_no;
    private String po_no;
    private String execute_no;
    //合同日期
    private Date contract_date;
    private Integer buyer_id;
    private String address;
    //订单状态
    private Integer status;
    private Integer show_status;
    //付款状态
    private Integer pay_status;
    //订单总金额
    private BigDecimal amount;
    private String trade_terms_bn;
    private String currency_bn;
    private String trans_mode_bn;
    private String from_country_bn;
    private String to_country_bn;
    private String from_port_bn;
    private String to_port_bn;
    private String quality;
    private String comment_flag;
    private String remark;
    //创建时间
    private Date created_at;
    private String from_country;
    private String to_country;
    private String from_port;
    private String to_port;
    private String trans_mode;
    private String show_status_text;
    private String pay_status_text;
    //合同交货日期 (修改为文本格式)
    private String delivery_at;
    private String delivery_left;
    private String show_name;
    private String user_name;
    private String symbol;
    private String others;
    private String po;
    private String order_contact;

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

    public String getExecute_no() {
        return execute_no;
    }

    public void setExecute_no(String execute_no) {
        this.execute_no = execute_no;
    }

    public Date getContract_date() {
        return contract_date;
    }

    public void setContract_date(Date contract_date) {
        this.contract_date = contract_date;
    }

    public Integer getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Integer buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShow_status() {
        return show_status;
    }

    public void setShow_status(Integer show_status) {
        this.show_status = show_status;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrade_terms_bn() {
        return trade_terms_bn;
    }

    public void setTrade_terms_bn(String trade_terms_bn) {
        this.trade_terms_bn = trade_terms_bn;
    }

    public String getCurrency_bn() {
        return currency_bn;
    }

    public void setCurrency_bn(String currency_bn) {
        this.currency_bn = currency_bn;
    }

    public String getTrans_mode_bn() {
        return trans_mode_bn;
    }

    public void setTrans_mode_bn(String trans_mode_bn) {
        this.trans_mode_bn = trans_mode_bn;
    }

    public String getFrom_country_bn() {
        return from_country_bn;
    }

    public void setFrom_country_bn(String from_country_bn) {
        this.from_country_bn = from_country_bn;
    }

    public String getTo_country_bn() {
        return to_country_bn;
    }

    public void setTo_country_bn(String to_country_bn) {
        this.to_country_bn = to_country_bn;
    }

    public String getFrom_port_bn() {
        return from_port_bn;
    }

    public void setFrom_port_bn(String from_port_bn) {
        this.from_port_bn = from_port_bn;
    }

    public String getTo_port_bn() {
        return to_port_bn;
    }

    public void setTo_port_bn(String to_port_bn) {
        this.to_port_bn = to_port_bn;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getComment_flag() {
        return comment_flag;
    }

    public void setComment_flag(String comment_flag) {
        this.comment_flag = comment_flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getFrom_country() {
        return from_country;
    }

    public void setFrom_country(String from_country) {
        this.from_country = from_country;
    }

    public String getTo_country() {
        return to_country;
    }

    public void setTo_country(String to_country) {
        this.to_country = to_country;
    }

    public String getFrom_port() {
        return from_port;
    }

    public void setFrom_port(String from_port) {
        this.from_port = from_port;
    }

    public String getTo_port() {
        return to_port;
    }

    public void setTo_port(String to_port) {
        this.to_port = to_port;
    }

    public String getTrans_mode() {
        return trans_mode;
    }

    public void setTrans_mode(String trans_mode) {
        this.trans_mode = trans_mode;
    }

    public String getShow_status_text() {
        return show_status_text;
    }

    public void setShow_status_text(String show_status_text) {
        this.show_status_text = show_status_text;
    }

    public String getPay_status_text() {
        return pay_status_text;
    }

    public void setPay_status_text(String pay_status_text) {
        this.pay_status_text = pay_status_text;
    }

    public String getDelivery_at() {
        return delivery_at;
    }

    public void setDelivery_at(String delivery_at) {
        this.delivery_at = delivery_at;
    }

    public String getDelivery_left() {
        return delivery_left;
    }

    public void setDelivery_left(String delivery_left) {
        this.delivery_left = delivery_left;
    }

    public String getShow_name() {
        return show_name;
    }

    public void setShow_name(String show_name) {
        this.show_name = show_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getOrder_contact() {
        return order_contact;
    }

    public void setOrder_contact(String order_contact) {
        this.order_contact = order_contact;
    }
    public void copyInfo(Order order){
        this.setId(order.getId());
        this.setOrder_no(order.getContractNo());
        this.setTo_port(order.getPoNo());
        this.setExecute_no(null);
        this.setContract_date(order.getSigningDate());
        this.setBuyer_id(order.getCreateUserId());
        this.setAddress(null);
        this.setStatus(order.getStatus());
        this.setPay_status(order.getPayStatus());
        this.setShow_status_text(ComplexOrder.fromStatusCode(order.getStatus()));
        this.setPay_status_text(ComplexOrder.fromPayCode(order.getPayStatus()));
        this.setAmount(order.getTotalPrice());
        this.setTrade_terms_bn(order.getTradeTerms());
        this.setCurrency_bn(order.getCurrencyBn());
        this.setTrans_mode(order.getTradeTerms());
        this.setFrom_country(order.getFromCountry());
        this.setTo_country(order.getToCountry());
        this.setFrom_port(order.getFromPort());
        this.setTo_port(order.getToPort());
        this.setFrom_country_bn(null);
        this.setTo_country_bn(null);
        this.setFrom_port_bn(null);
        this.setTo_port_bn(null);
        this.setQuality(null);
        this.setComment_flag(null);
        this.setRemark(order.getDeliveryRequires());
        this.setCreated_at(order.getCreateTime());
        this.setTrans_mode(order.getTransportType());
        this.setDelivery_at(order.getDeliveryDate());
        this.setShow_name(null);
        this.setUser_name(null);
        this.setSymbol(null);
        this.setOthers(null);
        this.setOrder_contact(order.getContractNo());
    }
}
