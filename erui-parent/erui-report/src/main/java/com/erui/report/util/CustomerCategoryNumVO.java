package com.erui.report.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 客户中心询单订单分类数量数据
 * @author wangxiaodan
 *
 */
public class CustomerCategoryNumVO {
	private String category;
	private Integer orderNum;
	private Integer inquiryNum;
	private Integer totalNum;
	private Double chainrate; // 环比
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getInquiryNum() {
		return inquiryNum;
	}
	public void setInquiryNum(Integer inquiryNum) {
		this.inquiryNum = inquiryNum;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Double getChainrate() {
		return chainrate;
	}
	public void setChainrate(Double chainrate) {
		this.chainrate = chainrate;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
