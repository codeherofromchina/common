package com.erui.report.util;

/**
 * 客户中心数量汇总VO
 * @author wangxiaodan
 *
 */
public class NumSummaryVO {
	private Integer total;
	private Integer oil;
	private Integer nonoil;
	public Integer getTotal() {
		if (total == null) {
			return 0;
		}
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getOil() {
		if (oil == null) {
			return 0;
		}
		return oil;
	}
	public void setOil(Integer oil) {
		this.oil = oil;
	}
	public Integer getNonoil() {
		if (nonoil == null) {
			return 0;
		}
		return nonoil;
	}
	public void setNonoil(Integer nonoil) {
		this.nonoil = nonoil;
	}
	
	
	
}
