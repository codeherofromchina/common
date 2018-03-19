package com.erui.order.entity;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 复合的订单表（包含老订单和新订单信息）
 */
@Entity
@Table(name = "complex_order")
public class ComplexOrder {
    @Id
    private Integer id;
    // 新老订单标识  1：新订单  2：老订单
    @Column(name = "type")
    private Integer type;
    //订单状态 1:待确认 2:未执行 3:执行中 4：完成 -1：未知
    @Column(name = "`status`")
    private Integer status;
    // 收款状态 1:未付款 2:部分付款 3:收款完成
    @Column(name = "pay_status")
    private Integer payStatus;
    // 订单创建时间
    @Column(name = "create_time")
    private Date createTime;
    // 框架协议号
    @Column(name = "contract_no")
    private String contractNo;
    // PO号
    @Column(name = "po_no")
    private String poNo;
    // 订单签约日期
    @Column(name = "signing_date")
    private String signingDate;
    // 合同交货日期
    @Column(name = "delivery_date")
    private Date deliveryDate;
    // 合同总价
    @Column(name = "total_price")
    private String totalPrice;
    // 货币类型
    @Column(name = "currency_bn")
    private String currencyBn;
    // 市场经办人ID
    @Column(name = "agent_id")
    private String agentId;
    // 市场经办人名称
    @Column(name = "agent_name")
    private String agentName;
    // 贸易术语
    @Column(name = "trade_terms")
    private String tradeTerms;
    // 运输方式
    @Column(name = "transport_type")
    private String transportType;
    // 起运国
    @Column(name = "from_country")
    private String fromCountry;
    ///起运港
    @Column(name = "from_port")
    private String fromPort;
    // 目的国
    @Column(name = "to_country")
    private String toCountry;
    // 目的港
    @Column(name = "to_port")
    private String toPort;
    // 目的地
    @Column(name = "to_place")
    private String toPlace;
    @Column(name = "buyer_id")
    private Integer buyerId;
    @Transient
    private String pay_status_text;
    @Transient
    private String show_status_text;


    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(String signingDate) {
        this.signingDate = signingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrencyBn() {
        return currencyBn;
    }

    public void setCurrencyBn(String currencyBn) {
        this.currencyBn = currencyBn;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getPay_status_text() {
        return pay_status_text;
    }

    public void setPay_status_text(String pay_status_text) {
        this.pay_status_text = pay_status_text;
    }

    public String getShow_status_text(int code) {
        return show_status_text;
    }

    public void setShow_status_text(String show_status_text) {
        this.show_status_text = show_status_text;
    }

    public static enum StatusEnum {
        ONE(-1, "草稿") ,INIT(1, "待确认"), UNEXECUTED(2, "未执行"), EXECUTING(3, "执行中"), DONE(4, "完成");

        public int code;
        public String msg;

        StatusEnum(int code, String msg) {
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

    public static enum PayStatusEnum {
        ONE(-1, "草稿"),INIT(1, "待确认"), UNEXECUTED(2, "未执行"), EXECUTING(3, "执行中"), DONE(4, "完成");

        public int code;
        public String msg;

        PayStatusEnum(int code, String msg) {
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

    public static StatusEnum fromStatusCode(Integer code) {
        if (code != null) {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.getCode() == code) {
                    return statusEnum;
                }
            }
        }
        return null;
    }
    public static PayStatusEnum fromPayCode(Integer code) {
        if (code != null) {
            for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
                if (payStatusEnum.getCode() == code) {
                    return payStatusEnum;

                }
            }
        }
        return null;
    }
}