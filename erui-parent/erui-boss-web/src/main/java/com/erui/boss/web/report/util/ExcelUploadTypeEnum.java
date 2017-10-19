package com.erui.boss.web.report.util;

import org.apache.commons.lang3.StringUtils;

public enum ExcelUploadTypeEnum {
	CATEGORY_QUALITY(1, "品控",new String[]{}), 
	CREDIT_EXTENSION(2, "授信数据",new String[]{}), 
	STORAGE_ORGANICOUNT(3, "仓储物流-事业部",new String[]{}), 
	HR_COUNT(4,"人力资源",new String[]{}), 
	INQUIRY_COUNT(5, "客户中心-询单",new String[]{}), 
	MARKETER_COUNT(6, "市场人员",new String[]{}), 
	MEMBER(7, "会员",new String[]{}), 
	ORDER_COUNT(8,"客户中心-订单",new String[]{}), 
	ORDER_ENTRY_COUNT(9, "仓储物流-订单入库",new String[]{}), 
	ORDER_OUTBOUND_COUNT(10,"仓储物流-订单出库",new String[]{}), 
	PROCUREMENT_COUNT(11,"采购",new String[]{}), 
	REQUEST_CREDIT(12, "应收账款",new String[]{}), 
	SUPPLY_CHAIN(13, "供应链",new String[]{});

	private Integer type;
	private String desc;
	private String[] title;

	ExcelUploadTypeEnum(Integer type, String desc,String[] title) {
		this.type = type;
		this.desc = desc;
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
	
	/**
	 * 验证excel数据的头信息是否正确
	 * @param title
	 * @return
	 */
	public boolean verifyTitleData(String[] title) {
		int len = this.title.length;
		if (title != null && title.length >= len) {
			for (int i=0;i<len;i++) {
				if (!StringUtils.equals(this.title[i], title[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static ExcelUploadTypeEnum getByType(Integer type) {
		if (type != null) {
			int typeInt = type.intValue();
			ExcelUploadTypeEnum[] values = ExcelUploadTypeEnum.values();
			for (ExcelUploadTypeEnum eu : values) {
				if (eu.getType() == typeInt) {
					return eu;
				}
			}
		}
		return null;
	}

}
