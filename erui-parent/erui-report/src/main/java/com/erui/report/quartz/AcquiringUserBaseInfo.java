package com.erui.report.quartz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AcquiringUserBaseInfo {
	private Connection conn;

	public AcquiringUserBaseInfo(Connection conn) {
		this.conn = conn;
	}


	public void fullData() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(TRUNCATE_SQL);
			stmt.execute(INSERT_SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static final String INSERT_SQL = "INSERT INTO erui_report.rep_acquiring_user_base_info (acquiring_user_id, acquiring_user_no, acquiring_user_name, area_bn, area_name , country_bn, country_name) SELECT t1.id AS acquiring_user_id, t1.user_no AS acquiring_user_no, t1.`name` AS acquiring_user_name, GROUP_CONCAT(DISTINCT area.bn) AS area_bn , GROUP_CONCAT(DISTINCT area.`name`) AS area_name, GROUP_CONCAT(DISTINCT c.bn) AS country_bn , GROUP_CONCAT(DISTINCT c.`name`) AS country_name FROM erui_sys.user t1 LEFT JOIN erui_sys.area_member am ON am.employee_id = t1.id LEFT JOIN erui_operation.market_area area ON (area.bn = am.market_area_bn AND area.lang = 'zh' AND area.deleted_flag = 'N') LEFT JOIN erui_sys.country_member cm ON cm.employee_id = t1.id LEFT JOIN erui_dict.country c ON (c.bn = cm.country_bn AND c.lang = 'zh' AND c.deleted_flag = 'N'), erui_sys.role t2, erui_sys.role_user t3 WHERE (t1.id = t3.user_id AND t2.id = t3.role_id AND t2.`name` = '客户 ( 经办 ) 负责人' AND t1.deleted_flag = 'N') GROUP BY t1.id, t1.user_no, t1.`name` ORDER BY t1.id ASC";
	private static final String TRUNCATE_SQL = "TRUNCATE TABLE erui_report.rep_acquiring_user_base_info";
}
