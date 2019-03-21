package com.erui.report.quartz;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BuyerOrgInfo {
	private Connection conn;

	public BuyerOrgInfo(Connection conn) {
		this.conn = conn;
	}

	public void fullData() {
		fullBuyerBaseInfo();
		fullBuyerMembershipTime();
	}

	private void fullBuyerBaseInfo() {
		try {
			Statement stmt = conn.createStatement();
			PreparedStatement pstm01 = conn.prepareStatement(USER_QUERY_SQL);
			PreparedStatement pstm02 = conn.prepareStatement(USER_UPDATE_SQL);
			PreparedStatement pstm03 = conn.prepareStatement(USER_INSERT_SQL);
			ResultSet rs01 = stmt.executeQuery(BASE_INFO_QUERY_SQL);
			while (rs01.next()) {
				String buyerNo = rs01.getString("buyer_no");
				String buyerCode = rs01.getString("buyer_code");
				Long orgId= rs01.getLong("org_id");
				String orgName = rs01.getString("org_name");
				Long acquiringUserId = rs01.getLong("acquiring_user_id");
				String acquiringUserName = rs01.getString("acquiring_user_name");
				Date registerTime = rs01.getDate("register_time");
				Date applyTime = rs01.getDate("apply_time");
				pstm01.setString(1, buyerCode);
				pstm01.setLong(2, orgId);
				ResultSet rs02 = pstm01.executeQuery();
				if (rs02.next()) { // 已经存在，更新，否则做新增操作
					long id = rs02.getLong("id");
					pstm02.setLong(1, acquiringUserId);
					pstm02.setString(2, acquiringUserName);
					pstm02.setDate(3, registerTime);
					pstm02.setDate(4, applyTime);
					pstm02.setLong(5, id);
					pstm02.execute();
				} else {
					pstm03.setLong(1, orgId);
					pstm03.setString(2, orgName);
					pstm03.setLong(3, acquiringUserId);
					pstm03.setString(4, acquiringUserName);
					pstm03.setDate(5, registerTime);
					pstm03.setDate(6, applyTime);
					pstm03.setString(7, buyerNo);
					pstm03.setString(8, buyerCode);
					pstm03.execute();
				}
				rs02.close();
			}
			rs01.close();
			pstm03.close();
			pstm02.close();
			pstm01.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fullBuyerMembershipTime() {
		try {
			Statement stmt = conn.createStatement();
			PreparedStatement pstm = conn.prepareStatement(MEMBERSHIP_TIME_QUERY_SQL);
			PreparedStatement pstm02 = conn.prepareStatement(UPDATE_MEMBER_TIME_SQL);
			ResultSet rs = stmt.executeQuery(USER_QUERY_ALL_SQL);
			while (rs.next()) {
				String buyerCode = rs.getString("buyer_code");
				Long id = rs.getLong("id");
				pstm.setString(1, buyerCode);
				ResultSet rs02 = pstm.executeQuery();
				if (rs02.next()) {
					Date membershipTime = rs02.getDate("membership_time");
					pstm02.setDate(1, membershipTime);
					pstm02.setLong(2, id);
					pstm02.execute();
				}
				rs02.close();
			}
			rs.close();
			pstm02.close();
			pstm.close();
			stmt.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static final String BASE_INFO_QUERY_SQL = "SELECT t1.buyer_no, t1.buyer_code, t4.id AS org_id, t4.name AS org_name, t1.created_by AS acquiring_user_id , emp.name AS acquiring_user_name, t1.checked_at AS register_time, bus.net_at AS apply_time FROM erui_buyer.buyer t1 LEFT JOIN erui_sys.employee emp ON emp.id = t1.created_by LEFT JOIN erui_buyer.buyer_business bus ON bus.buyer_id = t1.id, erui_buyer.buyer_agent t2, erui_sys.org_member t3, erui_sys.org t4 WHERE (t1.id = t2.buyer_id AND t2.agent_id = t3.employee_id AND t3.org_id = t4.id AND t1.deleted_flag = 'N' AND buyer_code IS NOT NULL AND t1.`status` IN ('arroved', 'pass') AND t2.deleted_flag = 'N' AND t4.`status` = 'NORMAL' AND t4.deleted_flag = 'N')";
	private static final String USER_QUERY_SQL = "select id from erui_report.rep_buyer_org_info where buyer_code = ? AND org_id = ? ";
	private static final String USER_QUERY_ALL_SQL = "select id,buyer_code from erui_report.rep_buyer_org_info";
	private static final String USER_UPDATE_SQL = "UPDATE erui_report.rep_buyer_org_info SET acquiring_user_id = ?, acquiring_user_name = ?, register_time = ?, apply_time = ? WHERE id = ?";
	private static final String USER_INSERT_SQL = "INSERT INTO erui_report.rep_buyer_org_info (org_id, org_name, acquiring_user_id , acquiring_user_name, register_time, apply_time, buyer_no, buyer_code) VALUES (?, ?, ? , ?, ?, ?, ?, ?)";
	private static final String MEMBERSHIP_TIME_QUERY_SQL = "SELECT MIN(t2.start_date) AS membership_time FROM erui_order.`order` t1, erui_order.project t2 WHERE (t1.id = t2.order_id AND t1.`status` > 2 AND t2.start_date IS NOT NULL AND t1.crm_code = ?)";
	private static final String UPDATE_MEMBER_TIME_SQL = "update erui_report.rep_buyer_org_info set membership_time = ? where id = ?";
}
