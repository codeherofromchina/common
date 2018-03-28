package com.erui.order.requestVo;

import com.erui.order.entity.Goods;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Null;
import java.math.BigDecimal;

/**
 * Created by wangxiaodan on 2017/12/13.
 *
 */
public class OutGoods {
    private Integer id;//商品id
    private String order_no;//销售合同号
    private String sku;//sku编码
    private String en;
    private String name;//商品英文名称
    private String model;//型号
    private String spec_attrs;//商品属性
    private String price;
    private Integer buy_number; // 合同商品数量
    private Integer min_pack_naked_qty;
    private String nude_cargo_unit;
    private String min_pack_unit;//单位
    private Integer thumb; // null
    private Integer buyer_id; // buyer_id
    private String remark; // 备注
    private String symbol;
    private String delivery_at;
    private String buyer;
    private String clientDesc;//商品描述
    private String from_country;
    private String trans_mode;
    private String supplier;
    private String show_status_text;
    private String pay_status_text;
    private String subtotal;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec_attrs() {
        return spec_attrs;
    }

    public void setSpec_attrs(String spec_attrs) {
        this.spec_attrs = spec_attrs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getBuy_number() {
        return buy_number;
    }

    public void setBuy_number(Integer buy_number) {
        this.buy_number = buy_number;
    }

    public Integer getMin_pack_naked_qty() {
        return min_pack_naked_qty;
    }

    public void setMin_pack_naked_qty(Integer min_pack_naked_qty) {
        this.min_pack_naked_qty = min_pack_naked_qty;
    }

    public String getNude_cargo_unit() {
        return nude_cargo_unit;
    }

    public void setNude_cargo_unit(String nude_cargo_unit) {
        this.nude_cargo_unit = nude_cargo_unit;
    }

    public String getMin_pack_unit() {
        return min_pack_unit;
    }

    public void setMin_pack_unit(String min_pack_unit) {
        this.min_pack_unit = min_pack_unit;
    }

    public Integer getThumb() {
        return thumb;
    }

    public void setThumb(Integer thumb) {
        this.thumb = thumb;
    }

    public Integer getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Integer buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDelivery_at() {
        return delivery_at;
    }

    public void setDelivery_at(String delivery_at) {
        this.delivery_at = delivery_at;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public String getFrom_country() {
        return from_country;
    }

    public void setFrom_country(String from_country) {
        this.from_country = from_country;
    }

    public String getTrans_mode() {
        return trans_mode;
    }

    public void setTrans_mode(String trans_mode) {
        this.trans_mode = trans_mode;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }


    public void copyInfo(Goods goods){
        this.setId(goods.getId());
        this.setSku(goods.getSku());
        this.setEn(null);
        this.setName(goods.getNameEn());
        this.setModel(goods.getModel());
        this.setSpec_attrs(null);
        this.setPrice(null);
        this.setBuy_number(goods.getContractGoodsNum());
        this.setMin_pack_naked_qty(null);
        this.setNude_cargo_unit(null);
        this.setMin_pack_unit(goods.getUnit());
        this.setTrans_mode(null);
        this.setRemark(null);
        this.setSymbol(null);
        this.setDelivery_at(null);
        this.setClientDesc(null);
        this.setFrom_country(null);
        this.setTrans_mode(null);
        this.setSupplier(null);
        this.setShow_status_text(null);
        this.setPay_status_text(null);
        this.setSubtotal(null);

    }
}
