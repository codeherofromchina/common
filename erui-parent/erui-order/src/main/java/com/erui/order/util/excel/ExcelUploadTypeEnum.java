package com.erui.order.util.excel;

import com.erui.comm.util.data.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

public enum ExcelUploadTypeEnum {
	ORDER_MANAGE(1,"订单管理",new String[]{"订单类别","海外销类型","销售合同号","框架协议号","海外销售合同号","PO号","物流报价单号","询单号","订单签约日期","合同交货日期","市场经办人员工编号","获取人员工编号","签约主体公司","执行分公司","所属地区","分销部(获取人所在分类销售)","国家","CRM客户代码","客户类型","回款负责人员工编号","是否融资项目","会员类别","授信情况","订单类型","授信情况","贸易术语","运输方式","合同总价","是否含税","汇率","合同总价(USD)","收款方式","质保金","预收货款","收款日期","发货前","收款日期","交货要求描述","客户及项目背景描述","项目开始日期","项目名称","执行单约定交付日期","合同总价（USD）","初步利润","利润额","执行单变更后日期","有无项目经理","下发部门","项目状态","项目备注"});
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
