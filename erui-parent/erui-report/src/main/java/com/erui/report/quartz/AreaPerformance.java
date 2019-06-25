package com.erui.report.quartz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 完善销售大区业绩表中的数据
 */
public class AreaPerformance {
    private Connection conn;

    public AreaPerformance(Connection conn) {
        this.conn = conn;
    }

    public void fullData() {
        full2019OrderData();
    }

    public void full2019OrderData() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(DELETE_2019_SQL);
            stmt.execute(INSERT_2019_SQL);
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

    private static final String INSERT_2019_SQL = "INSERT INTO erui_report.rep_area_performance (`year`, area_bn, area_name, money, count) SELECT 2019, t3.bn AS area_bn, t3.`name` AS area_name , SUM(IFNULL(t1.total_price_usd, 0)) AS totalAmount , COUNT(t1.id) AS totalNum FROM erui_order.`order` t1, erui_order.`project` t2, erui_operation.market_area t3 WHERE (t1.order_category != 1 AND t1.order_category != 3 AND t1.`status` in (3,4) AND t1.delete_flag = 0  AND t1.contract_no LIKE 'YR%' AND t2.project_status <> 'CANCEL' AND t1.id = t2.order_id AND t3.lang = 'zh' AND t1.region = t3.bn AND t2.start_date >= '2019-01-01') GROUP BY t3.bn, t3.name";

    private static final String DELETE_2019_SQL = "DELETE FROM erui_report.rep_area_performance WHERE `year` = 2019 ";
}
