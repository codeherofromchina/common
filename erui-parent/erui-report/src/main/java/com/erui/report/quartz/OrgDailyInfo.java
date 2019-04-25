package com.erui.report.quartz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrgDailyInfo {
	private Connection conn;

	public OrgDailyInfo(Connection conn) {
		this.conn = conn;
	}

	public void fullData() {
		fullQuoteAmount();
		fullOrderInfo();
		fullSupplierPass();
		fullSkuNum();
		fullSpuNum();
	}

	/**
	 * 填充报价金额信息
	 */
	public void fullQuoteAmount() {
		fullFieldData(QUOTE_AMOUNT_QUERY_SQL, QUOTE_AMOUNT_UPDATE_SQL, QUOTE_AMOUNT_INSERT_SQL, "quote_amount", "quote_num");
	}

	/**
	 * 填充订单数量和金额信息
	 */
	public void fullOrderInfo() {
		fullFieldData(ORDER_INFO_QUERY_SQL, ORDER_INFO_UPDATE_SQL, ORDER_INFO_INSERT_SQL, "order_num", "order_amount");
	}

	public void fullSupplierPass() {
		fullFieldData(SUPPLIER_PASS_QUERY_SQL, SUPPLIER_PASS_UPDATE_SQL, SUPPLIER_PASS_INSERT_SQL, "supplier_pass");
	}

	/**
	 * SKU数量信息
	 */
	public void fullSkuNum() {
		fullFieldData(SKU_QUERY_SQL, SKU_UPDATE_SQL, SKU_INSERT_SQL, "sku_num");
	}

	/**
	 * SPU数量信息
	 */
	public void fullSpuNum() {
		fullFieldData(SPU_QUERY_SQL, SPU_UPDATE_SQL, SPU_INSERT_SQL, "spu_num");
	}

	/**
	 * 填充帮助方法
	 */
	private void fullFieldData(String querySql, String updateSql, String insertSql, String... field) {
		Statement stmt = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			stmt = conn.createStatement();
			pstm1 = conn.prepareStatement(COMMON_QUERY_SQL);
			pstm2 = conn.prepareStatement(updateSql);
			pstm3 = conn.prepareStatement(insertSql);
			rs = stmt.executeQuery(querySql);
			String dayDate = null;
			String orgName = null;
			while (rs.next()) {
				dayDate = rs.getString("day_date");
				orgName = rs.getString("org_name");

				Object[] objArr = new Object[field.length];
				for (int i = 0; i < objArr.length; i++) {
					objArr[i] = rs.getObject(field[i]);
				}

				pstm1.setString(1, dayDate);
				pstm1.setString(2, orgName);

				rs2 = pstm1.executeQuery();
				if (rs2.next()) {
					// 修改
					Long id = rs2.getLong("id");
					int x = 1;
					for (int n = 0; n < objArr.length; n++, x++) {
						pstm2.setObject(x, objArr[n]);
					}
					pstm2.setLong(x, id);
					pstm2.execute();
				} else {
					// 插入操作
					pstm3.setString(1, dayDate);
					pstm3.setString(2, orgName);
					for (int n = 0, x = 3; n < objArr.length; n++, x++) {
						pstm3.setObject(x, objArr[n]);
					}
					pstm3.execute();
				}
				close(rs2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(rs);
			close(stmt, pstm1, pstm2, pstm3);
		}
	}

	public void close(Statement... stmt) {
		for (Statement s : stmt) {
			if (s != null) {
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				s = null;
			}
		}
	}

	public void close(ResultSet... rs) {
		for (ResultSet r : rs) {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				r = null;
			}
		}
	}

	private static final String COMMON_QUERY_SQL = "SELECT id FROM erui_report.rep_org_daily_info WHERE (day_date = ? AND org_name = ? )";
	// 报价金额和报价数量sql
	private static final String QUOTE_AMOUNT_QUERY_SQL = "SELECT DATE_FORMAT(t5.dt, '%Y-%m-%d') AS day_date, t5.org_name , SUM(IFNULL(t5.total_quote_price, 0)) AS quote_amount, COUNT(DISTINCT quote_id) AS quote_num FROM ( SELECT t4.dt , CASE  WHEN t3.name = '易瑞-钻完井设备事业部' THEN '易瑞-钻完井设备事业部' WHEN t3.name = '易瑞-采油工程事业部' THEN '易瑞-采油工程事业部' WHEN t3.name = '易瑞-工业品事业部' THEN '易瑞-工业品事业部' WHEN t3.name = '国内销售部' THEN '易瑞-国内销售部' ELSE '其他' END AS org_name, t2.total_quote_price, t2.id as quote_id FROM erui_rfq.inquiry t1, erui_rfq.quote t2, erui_sys.org t3, ( SELECT inquiry_id, MAX(out_at) AS dt FROM erui_rfq.inquiry_check_log WHERE out_node = 'MARKET_CONFIRMING' GROUP BY inquiry_id ) t4 WHERE (t1.id = t2.inquiry_id AND t3.id = t1.org_id AND t4.inquiry_id = t1.id AND t2.`status` IN ('MARKET_CONFIRMING', 'QUOTE_SENT', 'INQUIRY_CLOSED')) ) t5 GROUP BY t5.org_name, DATE_FORMAT(t5.dt, '%Y-%m-%d') ORDER BY 1";
	private static final String QUOTE_AMOUNT_UPDATE_SQL = "UPDATE erui_report.rep_org_daily_info SET quote_amount = ?, quote_num = ? WHERE id = ?";
	private static final String QUOTE_AMOUNT_INSERT_SQL = "INSERT INTO erui_report.rep_org_daily_info (day_date, org_name, quote_amount, quote_num) VALUES (?, ?, ?, ?)";

	// 订单数量和订单金额信息sql
	private static final String ORDER_INFO_QUERY_SQL = "SELECT DATE_FORMAT(t4.start_date, '%Y-%m-%d') AS day_date, t4.name AS org_name , COUNT(t4.oid) AS order_num , SUM(IFNULL(t4.total_price, 0)) AS order_amount FROM ( SELECT CASE  WHEN t2.name = '易瑞-钻完井设备事业部' THEN '易瑞-钻完井设备事业部' WHEN t2.name = '易瑞-采油工程事业部' THEN '易瑞-采油工程事业部' WHEN t2.name = '易瑞-工业品事业部' THEN '易瑞-工业品事业部' WHEN t2.name = '国内销售部' THEN '易瑞-国内销售部' ELSE '其他' END AS name, t1.id AS oid, t1.total_price_usd AS total_price, t3.start_date FROM erui_order.order t1, erui_sys.org t2, erui_order.project t3 WHERE (t1.id = t3.order_id AND t1.order_category != 1 AND t1.order_category != 3 AND t1.business_unit_id = t2.id AND t3.start_date IS NOT NULL AND t1.status > 2 AND t1.delete_flag = 0) ) t4 GROUP BY t4.name, DATE_FORMAT(t4.start_date, '%Y-%m-%d') ORDER BY 1 ASC";
	private static final String ORDER_INFO_UPDATE_SQL = "UPDATE erui_report.rep_org_daily_info SET order_num = ?,order_amount = ? WHERE id = ?";
	private static final String ORDER_INFO_INSERT_SQL = "INSERT INTO erui_report.rep_org_daily_info (day_date, org_name, order_num, order_amount) VALUES (?, ?, ?, ?)";

	// 合格供应商数量信息sql
	private static final String SUPPLIER_PASS_QUERY_SQL = "SELECT DATE_FORMAT(t4.checked_at, '%Y-%m-%d') AS day_date, t4.name AS org_name , COUNT(1) AS supplier_pass FROM ( SELECT t1.id AS sid , CASE  WHEN t2.name = '易瑞-钻完井设备事业部' THEN '易瑞-钻完井设备事业部' WHEN t2.name = '易瑞-采油工程事业部' THEN '易瑞-采油工程事业部' WHEN t2.name = '易瑞-工业品事业部' THEN '易瑞-工业品事业部' WHEN t2.name = '国内销售部' THEN '易瑞-国内销售部' ELSE '其他' END AS name, t1.checked_at FROM erui_supplier.supplier t1, erui_sys.org t2 WHERE (t1.org_id = t2.id AND t2.deleted_flag = 'N' AND t1.status = 'APPROVED' AND t1.deleted_flag = 'N' AND t1.checked_at IS NOT NULL) ) t4 GROUP BY t4.name, DATE_FORMAT(t4.checked_at, '%Y-%m-%d') ORDER BY 1";
	private static final String SUPPLIER_PASS_UPDATE_SQL = "UPDATE erui_report.rep_org_daily_info SET supplier_pass = ? WHERE id = ?";
	private static final String SUPPLIER_PASS_INSERT_SQL = "INSERT INTO erui_report.rep_org_daily_info (day_date, org_name, supplier_pass) VALUES (?, ?, ?)";

	// SPU信息sql
	private static final String SPU_QUERY_SQL = "SELECT DATE_FORMAT(t.created_at, '%Y-%m-%d') AS day_date, t.name AS org_name , COUNT(DISTINCT t.spu) AS spu_num FROM ( SELECT p.bizline_id, p.spu, scp.created_at , CASE  WHEN b.name = '易瑞-钻完井设备事业部' THEN '易瑞-钻完井设备事业部' WHEN b.name = '易瑞-采油工程事业部' THEN '易瑞-采油工程事业部' WHEN b.name = '易瑞-工业品事业部' THEN '易瑞-工业品事业部' WHEN b.name = '国内销售部' THEN '易瑞-国内销售部' ELSE '其他' END AS name FROM erui_goods.product p, erui_goods.show_cat_product scp, erui_operation.bizline b WHERE (scp.spu = p.spu AND scp.lang = p.lang AND scp.onshelf_flag = 'Y' AND p.deleted_flag = 'N' AND p.lang = 'en' AND p.bizline_id = b.id) ) t GROUP BY t.name, DATE_FORMAT(t.created_at, '%Y-%m-%d') ORDER BY 1";
	private static final String SPU_UPDATE_SQL = "UPDATE erui_report.rep_org_daily_info SET spu_num = ? WHERE id = ?";
	private static final String SPU_INSERT_SQL = "INSERT INTO erui_report.rep_org_daily_info (day_date, org_name, spu_num) VALUES (?, ?, ?)";
	// SKU信息sql
	private static final String SKU_QUERY_SQL = "SELECT DATE_FORMAT(t4.created_at, '%Y-%m-%d') AS day_date, t4.name AS org_name , COUNT(DISTINCT t4.sku) AS sku_num FROM ( SELECT g.sku, biz.created_at , CASE  WHEN biz.name = '易瑞-钻完井设备事业部' THEN '易瑞-钻完井设备事业部' WHEN biz.name = '易瑞-采油工程事业部' THEN '易瑞-采油工程事业部' WHEN biz.name = '易瑞-工业品事业部' THEN '易瑞-工业品事业部' WHEN biz.name = '国内销售部' THEN '易瑞-国内销售部' ELSE '其他' END AS name FROM erui_goods.goods g, erui_goods.product p, erui_goods.show_cat_goods scg, erui_operation.bizline biz WHERE (p.spu = g.spu AND p.lang = g.lang AND scg.sku = g.sku AND scg.lang = g.lang AND scg.onshelf_flag = 'Y' AND p.bizline_id = biz.id AND g.deleted_flag = 'N' AND p.deleted_flag = 'N' AND g.lang = 'en') ) t4 GROUP BY t4.name, DATE_FORMAT(t4.created_at, '%Y-%m-%d') ORDER BY 1";
	private static final String SKU_UPDATE_SQL = "UPDATE erui_report.rep_org_daily_info SET sku_num = ? WHERE id = ?";
	private static final String SKU_INSERT_SQL = "INSERT INTO erui_report.rep_org_daily_info (day_date, org_name, sku_num) VALUES (?, ?, ?)";

}
