package com.erui.report.util;

import java.math.BigDecimal;

/**
 * 客户中心数量和金额汇总VO
 * 
 * @author wangxiaodan
 *
 */
public class CustomerNumSummaryVO {
	private Integer total;
	private Integer oil;
	private Integer nonoil;
	private BigDecimal amount;
	private BigDecimal oilAmount;
	private BigDecimal noNoilAmount;

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

	public BigDecimal getAmount() {
		if (amount == null) {
			return BigDecimal.ZERO;
		}
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getOilAmount() {
		if (oilAmount == null) {
			return BigDecimal.ZERO;
		}
		return oilAmount;
	}

	public void setOilAmount(BigDecimal oilAmount) {
		this.oilAmount = oilAmount;
	}

	public BigDecimal getNoNoilAmount() {
		if (noNoilAmount == null) {
			return BigDecimal.ZERO;
		}
		return noNoilAmount;
	}

	public void setNoNoilAmount(BigDecimal noNoilAmount) {
		this.noNoilAmount = noNoilAmount;
	}

}
