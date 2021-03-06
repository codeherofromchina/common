package com.erui.report.quartz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderList {
    private Connection conn;

    public OrderList(Connection conn) {
        this.conn = conn;
    }

    public void fullData() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            // 并发解决结束
            stmt.execute(DELETE_SQL);
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
    private static final String INSERT_SQL = "INSERT INTO erui_report.rep_order_list (project_start_date, contract_no, order_type, project_name, exec_co_name , org_id, org_name, area_name, area_bn, country_bn, country_name, crm_code, money, profit_percent, profit, acquiring_user, business_name, already_gathering_money) SELECT t2.start_date AS project_start_date, t1.contract_no AS contract_no , CASE t1.order_category WHEN 1 THEN '预投' WHEN 2 THEN '售后' WHEN 3 THEN '试用' WHEN 4 THEN '现货（出库）' WHEN 5 THEN '订单' WHEN 6 THEN '国内订单' ELSE '' END AS order_type, t2.project_name AS project_name, t1.exec_co_name AS exec_co_name, t1.business_unit_id AS org_id, org.name AS org_name , IFNULL(IFNULL(area.`name`, area2.`name`), t1.region) AS area_name , IFNULL(area.bn, area2.bn) AS area_bn, cou.bn AS country_bn , cou.`name` AS country_name, t1.crm_code, IFNULL(t2.total_price_usd, 0) AS money , t2.profit_percent AS profit_percent, t2.profit AS profit, emp.`name` AS acquiring_user, t1.business_name AS business_name, ROUND(IFNULL(t1.already_gathering_money, 0) * IFNULL(t1.exchange_rate, 0),2) AS already_gathering_money FROM erui_order.`order` t1 LEFT JOIN erui_sys.group org ON org.id = t1.business_unit_id LEFT JOIN erui_operation.market_area area ON area.bn = t1.region AND area.lang = 'zh' LEFT JOIN erui_operation.market_area area2 ON area2.`name` = t1.region AND area2.lang = 'zh' LEFT JOIN erui_sys.user emp ON emp.id = t1.acquire_id LEFT JOIN erui_dict.country cou ON (cou.bn = t1.country AND cou.lang = 'zh' AND cou.deleted_flag = 'N'), erui_order.project t2 WHERE (t2.order_id = t1.id AND t1.`status` in (3,4) AND t1.order_category != 1 and t1.order_category != 3 AND t1.contract_no LIKE 'YR%' AND t2.project_status <> 'CANCEL' AND t1.delete_flag = 0  AND t2.start_date IS NOT NULL) ORDER BY 1 ASC";
    private static final String DELETE_SQL = "DELETE FROM erui_report.rep_order_list";

}
