package com.erui.report.util;

import org.apache.commons.lang3.StringUtils;

import com.erui.comm.util.data.string.StringUtil;

public enum ExcelUploadTypeEnum {
	CATEGORY_QUALITY(1, "品控",new String[]{"时间","报检总数（件）","产品入厂首检合格数（件）","产品入厂首检合格率","产品出厂总数（件）","出厂检验合格数（件）","产品出厂检验合格率","外派监造总数（件）","监造产品出厂合格数（件）","产品外派监造合格率"}), 
	CREDIT_EXTENSION(2, "授信数据",new String[]{"时间","区域","国家","信报代码","客户名称","批复信用额度（USD）","限额生效日期","限额失效日期","已用额度（USD）","可用额度（USD）"}), 
	STORAGE_ORGANI_COUNT(3, "仓储物流-事业部",new String[]{"时间","事业部","托盘数量","产品数量","备注"}), 
	HR_COUNT(4,"人力资源",new String[]{"日期","大部门","小部门","计划人数","在编人数","试用期人数","转正人数","中方","外籍","新进","集团转岗（进）","集团转岗（出）","离职"}), 
	INQUIRY_COUNT(5, "客户中心-询单",new String[]{"序号","报价单号","询价单位","所属地区部","事业部","客户名称或代码","客户及项目背景描述","品名中文","品名外文","规格","图号","数量","单位","油气or非油气","平台产品分类","产品分类","是否科瑞设备用配件","是否投标","转入日期","需用日期","澄清完成日期","报出日期","报价用时","市场负责人","商务技术部报价人","事业部负责人","产品品牌","报价单位","供应商报价人","报价人联系方式","厂家单价（元）","厂家总价（元）","利润率","报价单价（元）","报价总价（元）","报价总金额（美金）","单重(kg)","总重(kg)","包装体积(mm)","包装方式","交货期（天）","有效期（天）","贸易术语","最新进度及解决方案","报价后状态","退回次数","备注","报价超48小时原因类型","报价超48小时分析","成单或失单","失单原因类型","失单原因分析"}),
	MARKETER_COUNT(6, "市场人员",new String[]{"时间","区域","国家","市场人员","询单数量","报价数量","成单数量","成单金额","询单金额","新增会员"}), 
	MEMBER(7, "运营数据",new String[]{"日期","普通会员数量","普通会员二次购买数量","高级会员数量","高级会员二次购买数量","黄金会员数量","黄金会员二次购买数量"}), 
	ORDER_COUNT(8,"客户中心-订单",new String[]{"序号","生产通知单号","执行单号","PO号","事业部","执行分公司","所属地区部","客户及项目背景描述","客户分类","品名中文","品名外文","规格","油气or非油气","平台产品分类","产品分类","数量","单位","订货号","订单号","项目金额（美元）","初步利润率","回款方式","回款时间","回款金额（美元）","授信情况","项目开始","执行单约定交付时间","变更后日期","要求采购到货时间","市场经办人","商务技术部经办人","采购经办人","供应商","采购合同号","前期报价（人民币）","采购金额（人民币）","采购金额（美元）","采购签合同日期","采购合同规定交货期","采购给供应商付款方式","给工厂付款时间","采购到货日期","报检时间","检验完成时间","入库时间","下发订舱时间","市场要求时间","发运经办人","包装完成时间","货物离开仓库日期","箱单通知时间","船期或航班","到达日","物流发票号","前期物流报价（美元）","物流发运金额（美元）","实际完成时间","贸易方式","采购延期时间（天）","物流延期时间（天）","项目状态","原因类型","原因种类","真实原因","项目进展","备注"}),
	ORDER_ENTRY_COUNT(9, "仓储物流-订单入库",new String[]{"时间","入库单号","项目执行号","采购通知单号","数量","金额","入库时间","采购员","供应商名称","备注"}), 
	ORDER_OUTBOUND_COUNT(10,"仓储物流-订单出库",new String[]{"时间","出库单号","采购通知单号","目的国","项目执行号","包装件数","出库时间","金额","备注"}), 
	PROCUREMENT_COUNT(11,"采购",new String[]{"采购下达时间","采购计划单号","项目执行单号","金额","品类","区域","国家","油气非油气"}), 
	REQUEST_CREDIT(12, "应收账款",new String[]{"序号","订单编号","销售主体公司","营销大区","销售国家","隶属事业部","客户代码","出口产品名称","贸易方式","创建日期","订单金额","结算货币","应收账款余额","已预警","回款责任人","下次回款日期"}), 
	SUPPLY_CHAIN(13, "供应链",new String[]{"日期","事业部","品类部","一级物料分类","计划SKU","SKU实际完成","计划SPU","SPU实际完成","计划供应商数量","供应商数量实际开发"}),
	REQUEST_RECEIVE(14, "应收账款-回款金额",new String[]{"序号","订单编号","销售主体公司","营销大区","销售国家","隶属事业部","客户代码","出口产品名称","贸易方式","创建日期","订单金额","结算货币","应收账款余额","回款责任人","下次回款日期","回款日期","回款金额","其他扣费金额"});

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
