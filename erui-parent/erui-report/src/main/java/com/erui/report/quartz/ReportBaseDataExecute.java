package com.erui.report.quartz;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by wangxiaodan on 2019/3/18.
 */
public class ReportBaseDataExecute {
    private Connection conn;


    public void init() throws Exception {
        Properties properties = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jdbc-report.properties");
        properties.load(in);
        in.close();
        com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
        Properties info = new Properties();
        info.put("user", properties.getProperty("report.master.username"));
        info.put("password", properties.getProperty("report.master.password"));
        conn = driver.connect(properties.getProperty("report.master.url"), info);
    }


    public static void main(String[] args) {
        ReportBaseDataExecute quartz = new ReportBaseDataExecute();
        quartz.start();
    }

    public void start()  {
        new Thread(() -> {
            try {
                init();
                // 销售大区业绩统计数据定时完善
                fullAreaPerformance();
                // 订单业务数据业绩定时完善
                fullOrderList();
                // 会员地区信息表定时完善
                fullBuyerCountryInfo();
                // 会员事业部统计表定时完善
                fullBuyerOrgInfo();
                // 获取人基本信息表定时完善
                fullAcquiringUserBaseInfo();
                // 日报信息定时完善
                // 事业部日报信息定时完善
                fullDataForOrgDailyInfo();
                // 国家日报信息定时完善
                fullDataForCountryDailyInfo();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                destroy();
            }
        }).start();
    }

    /**
     *  订单统计-大区信息完善
     */
    public void fullAreaPerformance() {
        AreaPerformance areaPerformance = new AreaPerformance(conn);
        long sTimeMillis = System.currentTimeMillis();
        areaPerformance.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullAreaPerformance cost time : " + (eTimeMillis - sTimeMillis));
    }

    /**
     * 订单业务数据业绩定时完善
     */
    public void fullOrderList() {
        OrderList orderList = new OrderList(conn);
        long sTimeMillis = System.currentTimeMillis();
        orderList.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullOrderList cost time : " + (eTimeMillis - sTimeMillis));
    }

    /**
     * 会员地区信息表定时完善
     */
    public void fullBuyerCountryInfo() {
        BuyerCountryInfo buyerCountryInfo = new BuyerCountryInfo(conn);
        long sTimeMillis = System.currentTimeMillis();
        buyerCountryInfo.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullBuyerCountryInfo cost time : " + (eTimeMillis - sTimeMillis));
    }

    /**
     *  会员事业部统计表完善
     */
    public void fullBuyerOrgInfo() {
        BuyerOrgInfo buyerOrgInfo = new BuyerOrgInfo(conn);
        long sTimeMillis = System.currentTimeMillis();
        buyerOrgInfo.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullBuyerOrgInfo cost time : " + (eTimeMillis - sTimeMillis));
    }

    /**
     * 获取人基本信息表
     */
    public void fullAcquiringUserBaseInfo() {
        AcquiringUserBaseInfo acquiringUserBaseInfo = new AcquiringUserBaseInfo(conn);
        long sTimeMillis = System.currentTimeMillis();
        acquiringUserBaseInfo.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullAcquiringUserBaseInfo cost time : " + (eTimeMillis - sTimeMillis));
    }


    /**
     * 国家日报信息定时完善
     */
    public void fullDataForCountryDailyInfo() {
        CountryDailyInfo countryDailyInfo = new CountryDailyInfo(conn);
        long sTimeMillis = System.currentTimeMillis();
        countryDailyInfo.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullDataForCountryDailyInfo cost time : " + (eTimeMillis - sTimeMillis));
    }

    /**
     * 事业部日报信息定时完善
     */
    public void fullDataForOrgDailyInfo() {
        OrgDailyInfo orgDailyInfo = new OrgDailyInfo(conn);
        long sTimeMillis = System.currentTimeMillis();
        orgDailyInfo.fullData();
        long eTimeMillis = System.currentTimeMillis();
        System.out.println("fullDataForOrgDailyInfo cost time : " + (eTimeMillis - sTimeMillis));
    }



    public void destroy() {
        System.out.println("销毁");
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
