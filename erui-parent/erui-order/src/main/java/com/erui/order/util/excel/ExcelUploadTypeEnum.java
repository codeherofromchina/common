package com.erui.order.util.excel;

import com.erui.comm.util.data.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

public enum ExcelUploadTypeEnum {
    ORDER_MANAGE(1, "订单管理", new String[]{"订单类别", "海外销类型", "销售合同号", "框架协议号", "海外销售合同号", "PO号", "询单号", "订单签约日期", "合同交货日期", "市场经办人员工id", "获取人员工id", "签约主体公司",  "事业部id","执行分公司id", "所属地区", "分销部(获取人所在分类销售)", "国家", "CRM客户代码", "客户类型", "回款负责人员工id","商务技术经办人员工id","是否融资项目", "会员类别", "授信情况", "订单类型", "贸易术语", "运输方式","目的港", "目的国", "目的地", "合同总价", "币种","是否含税", "汇率", "合同总价(USD)", "收款方式", "质保金", "预收货款", "收款日期", "交货要求描述", "客户及项目背景描述", "项目开始日期", "项目名称", "执行单约定交付日期", "合同总价（USD）", "初步利润", "利润额", "执行单变更后日期", "有无项目经理", "下发部门", "项目状态","采购经办人","品控经办人","商务技术经办人","易瑞事业部项目经理", "项目备注","事业部名称","执行分公司"}),
    ORDER_CHECK(2, "订单管理数据校验", new String[]{"订单id", "订单类别", "海外销类型", "销售合同号", "框架协议号", "海外销售合同号", "PO号", "物流报价单号", "询单号", "订单签约日期", "合同交货日期", "市场经办人编号", "市场经办人", "获取人编号", "签约主体公司", "执行事业部id", "执行事业部", "执行分公司id", "执行分公司", "所属地区", "分销部id", "分销部", "国家", "CRM客户代码", "客户类型", "回款负责人", "商务技术经办人编号", "商务技术经办人", "是否融资项目", "会员类别", "授信情况", "订单类型", "贸易术语", "运输方式", "起运港", "起运国", "发运起始地", "目的港", "目的国", "目的地", "合同总价", "币种", "是否含税", "汇率", "合同总价(USD)", "收款方式", "质保金", "预收货款", "收款日期", "发货前收款", "发货前收款日期", "合同信息", "收款日期", "收款金额", "交货要求描述", "客户及项目背景描述"}),
    PROJECT_MANAGE(3, "项目管理", new String[]{"订单id", "销售合同号", "项目开始日期", "项目名称", "执行单约定交付日期", "合同总价（USD）", "初步利润率", "利润额", "执行单变更后日期", "要求采购到货日期", "有无项目经理", "下发部门id", "下发部门", "项目状态", "采购经办人编号", "采购经办人", "品控经办人编号", "品控经办人", "商务技术经办人编号", "商务技术经办人", "交付配送中心项目经理编号", "交付配送中心项目经理", "项目备注"}),
    GOODS_MANAGE(4, "商品导入", new String[]{"销售合同号", "序号", "SKU", "外文品名（必填）", "中文品名（必填）", "物料分类", "数量（必填）", "销售单价", "所属事业部", "单位（必填）", "品牌","型号"});


    private int type;
    private int column;
    private String table;
    private String[] title;

    ExcelUploadTypeEnum(int type, String table, String[] title) {
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
     *
     * @param title
     * @return
     */
    public boolean verifyTitleData(String[] title) {
        int len = this.title.length;
        if (title != null && title.length >= len) {
            for (int i = 0; i < len; i++) {
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

    /**
     * 简单验证单行数据
     *
     * @param data
     * @param excelType
     * @param response
     * @param lineNum   当前检测所在excel的行数
     * @return true:存在错误 false:数据正常
     */
    public static boolean verifyData(String[] data, ExcelUploadTypeEnum excelType, ImportDataResponse response, int lineNum) {
        if (StringUtil.isAllBlank(data)) {
            response.incrFail();
            response.pushFailItem(excelType.getTable(), lineNum, "整行数据为空");
            return true;
        }
        if (data.length < excelType.title.length - 1) {
            response.incrFail();
            response.pushFailItem(excelType.getTable(), lineNum, "行数据单元格数据不足");
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        ExcelUploadTypeEnum[] values = ExcelUploadTypeEnum.values();
        for (ExcelUploadTypeEnum v : values) {
            System.out.print(v.getType() + ":" + v.getTable() + ",");
        }
        String[] s = {"订单类别", "海外销类型", "销售合同号", "框架协议号", "海外销售合同号", "PO号", /*"物流报价单号",去掉*/
                "询单号", "订单签约日期", "合同交货日期", "市场经办人员工编号", "获取人员工编号", "签约主体公司", "事业部",
                "执行分公司", "所属地区", "分销部(获取人所在分类销售)", "国家", "CRM客户代码", "客户类型", "回款负责人员工编号", "商务技术经办人员工编号",/*新添加sw*/
                "是否融资项目", "会员类别", "授信情况", "订单类型", "贸易术语", "运输方式", "目的港", "目的国", "目的地", /*新添加3 m*/ "合同总价", "币种",/*新添加bz*/
                "是否含税", "汇率", "合同总价(USD)", "收款方式", "质保金", "预收货款", "收款日期", /*"发货前", "收款日期",去掉*/ "交货要求描述", "客户及项目背景描述",
                "项目开始日期", "项目名称", "执行单约定交付日期", "合同总价（USD）", "初步利润", "利润额", "执行单变更后日期", "有无项目经理", "下发部门", "项目状态",
                "采购经办人", "品控经办人", "商务技术经办人",/*新添加 c p s*/ "项目备注"};
        System.out.println("表列数：" + s.length);


    }
}
