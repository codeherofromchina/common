package com.erui.report.quartz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountryDailyInfo {
	private Connection conn;

	public CountryDailyInfo(Connection conn) {
		this.conn = conn;
	}

	public void fullData() {
		fullRegisterBuyer();
		fullBuyerNum();
		fullInquiryNum();
		fullQuoteInfo();
		fullOrderInfo();
	}

	/**
	 * 填充注册用户数量信息
	 */
	public void fullRegisterBuyer() {
		fullFieldData(REGISTER_BUYER_QUERY_SQL, REGISTER_BUYER_UPDATE_SQL, REGISTER_BUYER_INSERT_SQL, "register_buyer");
	}

	/**
	 * 填充会员数量信息
	 */
	public void fullBuyerNum() {
		fullFieldData(BUYER_NUM_QUERY_SQL, BUYER_NUM_UPDATE_SQL, BUYER_NUM_INSERT_SQL, "buyer_num");
	}

	/**
	 * 填充会员数量信息
	 */
	public void fullInquiryNum() {
		fullFieldData(INQUIRY_NUM_QUERY_SQL, INQUIRY_NUM_UPDATE_SQL, INQUIRY_NUM_INSERT_SQL, "inquiry_num");
	}

	/**
	 * 填充询价数量和金额信息
	 */
	public void fullQuoteInfo() {
		fullFieldData(QUOTE_INFO_QUERY_SQL, QUOTE_INFO_UPDATE_SQL, QUOTE_INFO_INSERT_SQL, "quote_num", "quote_amount");
	}

	/**
	 * 填充订单数量和金额信息
	 */
	public void fullOrderInfo() {
		fullFieldData(ORDER_INFO_QUERY_SQL, ORDER_INFO_UPDATE_SQL, ORDER_INFO_INSERT_SQL, "order_num", "order_amount");
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
			String areaName = null;
			String areaBn = null;
			String countryName = null;
			String countryBn = null;
			while (rs.next()) {
				dayDate = rs.getString("day_date");
				areaName = rs.getString("area_name");
				areaBn = rs.getString("area_bn");
				countryName = rs.getString("country_name");
				countryBn = rs.getString("country_bn");

				Object[] objArr = new Object[field.length];
				for (int i = 0; i < objArr.length; i++) {
					objArr[i] = rs.getObject(field[i]);
				}

				pstm1.setString(1, dayDate);
				pstm1.setString(2, areaName);
				pstm1.setString(3, areaBn);
				pstm1.setString(4, countryName);
				pstm1.setString(5, countryBn);

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
					pstm3.setString(2, areaName);
					pstm3.setString(3, areaBn);
					pstm3.setString(4, countryName);
					pstm3.setString(5, countryBn);
					for (int n = 0, x = 6; n < objArr.length; n++, x++) {
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

	private static final String COMMON_QUERY_SQL = "SELECT id FROM erui_report.rep_country_daily_info WHERE (day_date = ? AND area_name = ? AND area_bn = ? AND country_name = ? AND country_bn = ?)";
	// 注册用户数量信息sql
	private static final String REGISTER_BUYER_QUERY_SQL = "SELECT DATE_FORMAT(t1.created_at, '%Y-%m-%d') AS day_date, t3.name AS area_name, t3.bn AS area_bn , t4.name AS country_name, t4.bn AS country_bn, COUNT(t1.id) AS register_buyer FROM erui_buyer.buyer t1, erui_operation.market_area_country t2, erui_operation.market_area t3, erui_dict.country t4 WHERE (t1.country_bn = t2.country_bn AND t2.market_area_bn = t3.bn AND t3.lang = 'zh' AND t2.country_bn = t4.bn AND t4.lang = 'zh' AND t4.deleted_flag = 'N' AND t1.source IN (1, 2) AND t1.deleted_flag = 'N') GROUP BY t4.name, t4.bn, t3.name, t3.bn, DATE_FORMAT(t1.created_at, '%Y-%m-%d') ORDER BY 1 ASC";
	private static final String REGISTER_BUYER_UPDATE_SQL = "UPDATE erui_report.rep_country_daily_info SET register_buyer = ? WHERE id = ?";
	private static final String REGISTER_BUYER_INSERT_SQL = "INSERT INTO erui_report.rep_country_daily_info (day_date, area_name, area_bn, country_name, country_bn , register_buyer) VALUES (?, ?, ?, ?, ? , ?)";
	// 会员数量信息sql
	private static final String BUYER_NUM_QUERY_SQL = "SELECT DATE_FORMAT(t5.dt, '%Y-%m-%d') AS day_date, t3.name AS area_name, t3.bn AS area_bn , t4.name AS country_name, t4.bn AS country_bn, COUNT(t1.id) AS buyer_num FROM erui_buyer.buyer t1, erui_operation.market_area_country t2, erui_operation.market_area t3, erui_dict.country t4, ( SELECT crm_code, MIN(p.start_date) AS dt FROM erui_order.`order` o, erui_order.`project` p WHERE (o.order_category != 1 AND o.order_category != 3 AND o.id = p.order_id AND o.`status` in (3,4) AND o.delete_flag = 0 AND o.contract_no like 'YR%' AND p.start_date IS NOT NULL) GROUP BY crm_code ) t5 WHERE (t1.buyer_code = t5.crm_code AND t1.country_bn = t2.country_bn AND t2.market_area_bn = t3.bn AND t3.lang = 'zh' AND t2.country_bn = t4.bn AND t4.lang = 'zh' AND t1.`status` <> 'REJECTED' AND t1.deleted_flag = 'N' AND t4.deleted_flag = 'N' AND t1.created_at < t5.dt) GROUP BY t4.name, t4.bn, t3.name, t3.bn, DATE_FORMAT(t5.dt, '%Y-%m-%d') ORDER BY 1 ASC";
	private static final String BUYER_NUM_UPDATE_SQL = "UPDATE erui_report.rep_country_daily_info SET buyer_num = ? WHERE id = ?";
	private static final String BUYER_NUM_INSERT_SQL = "INSERT INTO erui_report.rep_country_daily_info (day_date, area_name, area_bn, country_name, country_bn, buyer_num) VALUES (?, ?, ?, ?, ? , ?)";
	// 询单数量信息sql
	private static final String INQUIRY_NUM_QUERY_SQL = "SELECT DATE_FORMAT(t2.log_create_at, '%Y-%m-%d') AS day_date, t5.name AS area_name, t5.bn AS area_bn , t3.name AS country_name, t3.bn AS country_bn, COUNT(t1.id) AS inquiry_num FROM `erui_rfq`.`inquiry` t1, ( SELECT inquiry_id AS log_inquiry_id, MIN(out_at) AS log_create_at FROM erui_rfq.inquiry_check_log WHERE out_node = 'BIZ_DISPATCHING' AND out_at IS NOT NULL GROUP BY inquiry_id ) t2, `erui_dict`.`country` t3, erui_operation.market_area_country t4, erui_operation.market_area t5 WHERE (t1.id = t2.log_inquiry_id AND t1.`deleted_flag` = 'N' AND t1.`status` <> 'DRAFT' AND t1.`country_bn` = `t3`.`bn` AND `t3`.`lang` = 'zh' AND t3.deleted_flag = 'N' AND t3.bn = t4.country_bn AND t4.market_area_bn = t5.bn AND t5.lang = 'zh') GROUP BY t5.name, t5.bn, t3.name, t3.bn, DATE_FORMAT(t2.log_create_at, '%Y-%m-%d') ORDER BY 1 ASC";
	private static final String INQUIRY_NUM_UPDATE_SQL = "UPDATE erui_report.rep_country_daily_info SET inquiry_num = ? WHERE id = ?";
	private static final String INQUIRY_NUM_INSERT_SQL = "INSERT INTO erui_report.rep_country_daily_info (day_date, area_name, area_bn, country_name, country_bn , inquiry_num) VALUES (?, ?, ?, ?, ? , ?)";
	// 报价信息sql
	private static final String QUOTE_INFO_QUERY_SQL = "SELECT DATE_FORMAT(t3.log_create_at, '%Y-%m-%d') AS day_date, t6.name AS area_name, t6.bn AS area_bn , t4.name AS country_name, t4.bn AS country_bn, COUNT(t2.id) AS quote_num , SUM(IFNULL(t2.total_quote_price, 0)) AS quote_amount FROM `erui_rfq`.`inquiry` t1, `erui_rfq`.`quote` t2, ( SELECT inquiry_id AS log_inquiry_id, MAX(out_at) AS log_create_at FROM erui_rfq.inquiry_check_log WHERE out_node = 'MARKET_CONFIRMING' GROUP BY inquiry_id ) t3, `erui_dict`.`country` t4, erui_operation.market_area_country t5, erui_operation.market_area t6 WHERE (t1.id = t2.inquiry_id AND t2.deleted_flag = 'N' AND t1.id = t3.log_inquiry_id AND t1.`deleted_flag` = 'N' AND t1.`country_bn` = `t4`.`bn` AND `t4`.`lang` = 'zh' AND t4.deleted_flag = 'N' AND t2.`status` IN ('MARKET_CONFIRMING', 'QUOTE_SENT', 'INQUIRY_CLOSED') AND t4.bn = t5.country_bn AND t5.market_area_bn = t6.bn AND t6.lang = 'zh') GROUP BY t6.name, t6.bn, t4.name, t4.bn, DATE_FORMAT(t3.log_create_at, '%Y-%m-%d') ORDER BY 1 DESC";
	private static final String QUOTE_INFO_UPDATE_SQL = "UPDATE erui_report.rep_country_daily_info SET quote_num = ?, quote_amount = ? WHERE id = ?";
	private static final String QUOTE_INFO_INSERT_SQL = "INSERT INTO erui_report.rep_country_daily_info (day_date, area_name, area_bn, country_name, country_bn , quote_num , quote_amount) VALUES (?, ?, ?, ?, ? , ? , ?)";
	// 订单信息sql
	private static final String ORDER_INFO_QUERY_SQL = "SELECT DATE_FORMAT(t2.start_date, '%Y-%m-%d') AS day_date, t3.name AS area_name, t3.bn AS area_bn , t5.name AS country_name, t5.bn AS country_bn, COUNT(t1.id) AS order_num , SUM(IFNULL(t2.total_price_usd, 0)) AS order_amount FROM erui_order.`order` t1, erui_order.project t2, erui_operation.market_area t3, erui_operation.market_area_country t4, erui_dict.country t5 WHERE (t1.order_category != 1 AND t1.order_category != 3 AND t1.id = t2.order_id AND t1.`status` in (3,4) AND t1.delete_flag = 0 AND t1.contract_no like 'YR%' AND t1.country = t5.bn AND t5.bn = t4.country_bn AND t4.market_area_bn = t3.bn AND t5.lang = 'zh' AND t5.deleted_flag = 'N' AND t3.lang = 'zh' AND t2.start_date IS NOT NULL) GROUP BY t3.name, t3.bn, t5.name, t5.bn, DATE_FORMAT(t2.start_date, '%Y-%m-%d') ORDER BY 1 ASC";
	private static final String ORDER_INFO_UPDATE_SQL = "UPDATE erui_report.rep_country_daily_info SET order_num = ?, order_amount = ? WHERE id = ?";
	private static final String ORDER_INFO_INSERT_SQL = "INSERT INTO erui_report.rep_country_daily_info (day_date, area_name, area_bn, country_name, country_bn , order_num , order_amount) VALUES (?, ?, ?, ?, ? , ? , ?)";

}
