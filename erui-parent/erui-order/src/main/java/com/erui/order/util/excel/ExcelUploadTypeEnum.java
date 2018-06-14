package com.erui.order.util.excel;

import com.erui.comm.util.data.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

public enum ExcelUploadTypeEnum {
	ORDER_COUNT(8,"客户中心-订单",new String[]{"序号","生产通知单号","执行单号","PO号","事业部","执行分公司","所属地区部","客户代码","客户分类","品名中文","品名外文","规格","油气or非油气","平台产品分类","产品分类","数量","单位","订货号","订单号","项目金额（美元）","初步利润率","回款方式","回款时间","回款金额（美元）","授信情况","项目开始","执行单约定交付时间","变更后日期","要求采购到货时间","市场经办人","商务技术部经办人","采购经办人","供应商","采购合同号","前期报价（人民币）","采购金额（人民币）","采购金额（美元）","采购签合同日期","采购合同规定交货期","采购给供应商付款方式","给工厂付款时间","采购到货日期","报检时间","检验完成时间","入库时间","下发订舱时间","市场要求时间","发运经办人","包装完成时间","货物离开仓库日期","箱单通知时间","船期或航班","到达日","物流发票号","前期物流报价（美元）","物流发运金额（美元）","实际完成时间","贸易方式","采购延期时间（天）","物流延期时间（天）","项目状态","原因类型","原因种类","真实原因","项目进展","备注"});
	private int type;
	private int column;
	private String table;
	private String[] title;

	ExcelUploadTypeEnum(int type, String table,String[] title) {
		this.type = type;
		this.table = table;
		this.title = title;
		this.column = title.length;
	}

	public int getType() {
		return type;
	}

	public String getTable() {
		return table;
	}
	
	public int getColumn() {
		return column;
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
					System.out.println(this.title[i] + "   " + title[i]);
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
	
	public static void main(String[] args) {
		ExcelUploadTypeEnum[] values = ExcelUploadTypeEnum.values();
		for (ExcelUploadTypeEnum v:values) {
			System.out.print(v.getType() + ":" + v.getTable() +",");
		}
		
	}
	
	/**
	 * 简单验证单行数据
	 * @param data
	 * @param excelType
	 * @param response
	 * @param lineNum	当前检测所在excel的行数
	 * @return	true:存在错误  false:数据正常
	 */
	public static boolean verifyData(String[] data,ExcelUploadTypeEnum excelType,ImportDataResponse response,int lineNum) {
		if (StringUtil.isAllBlank(data)){
			response.incrFail();
			response.pushFailItem(excelType.getTable(), lineNum, "整行数据为空");
			return true;
		}
		if (data.length < excelType.column) {
			response.incrFail();
			response.pushFailItem(excelType.getTable(), lineNum, "行数据单元格数据不足");
			return true;
		}

		return false;
	}

}
