package com.erui.report.quartz;

import com.erui.comm.util.data.date.DateUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BuyerCountryInfo {
    private Connection conn;

    public BuyerCountryInfo(Connection conn) {
        this.conn = conn;
    }

    public void fullData() {
        fullBuyerBaseInfo();
        fullBuyerMembershipTime();
        fullBuyerAfter2019MembershipTime();
        fixSql();
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
                String areaBn = rs01.getString("area_bn");
                String areaName = rs01.getString("area_name");
                String countryBn = rs01.getString("country_bn");
                String countryName = rs01.getString("country_name");
                Long acquiringUserId = rs01.getLong("acquiring_user_id");
                String acquiringUserName = rs01.getString("acquiring_user_name");
                Date registerTime = rs01.getDate("register_time");
                Date applyTime = rs01.getDate("apply_time");
                pstm01.setString(1, buyerCode);
                ResultSet rs02 = pstm01.executeQuery();
                if (rs02.next()) { // 已经存在，更新，否则做新增操作
                    long id = rs02.getLong("id");

                    pstm02.setString(1, areaBn);
                    pstm02.setString(2, areaName);
                    pstm02.setString(3, countryBn);
                    pstm02.setString(4, countryName);
                    pstm02.setLong(5, acquiringUserId);
                    pstm02.setString(6, acquiringUserName);
                    pstm02.setDate(7, registerTime);
                    pstm02.setDate(8, applyTime);
                    pstm02.setLong(9, id);
                    pstm02.execute();
                } else {
                    pstm03.setString(1, areaBn);
                    pstm03.setString(2, areaName);
                    pstm03.setString(3, countryBn);
                    pstm03.setString(4, countryName);
                    pstm03.setLong(5, acquiringUserId);
                    pstm03.setString(6, acquiringUserName);
                    pstm03.setDate(7, registerTime);
                    pstm03.setDate(8, applyTime);
                    pstm03.setString(9, buyerNo);
                    pstm03.setString(10, buyerCode);
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void fullBuyerAfter2019MembershipTime() {
        try {
            Statement stmt = conn.createStatement();
            PreparedStatement pstm = conn.prepareStatement(MEMBERSHIP_TIME_AFTER_2019_QUERY_SQL);
            PreparedStatement pstm02 = conn.prepareStatement(UPDATE_MEMBER_TIME_AFTER_2019__SQL);
            ResultSet rs = stmt.executeQuery(USER_QUERY_ALL_SQL);
            while (rs.next()) {
                String buyerCode = rs.getString("buyer_code");
                Long id = rs.getLong("id");
                pstm.setString(1, buyerCode);
                ResultSet rs02 = pstm.executeQuery();
                if (rs02.next()) {
                    Date membershipTime = rs02.getDate("membership_time_2019after");
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 需求固定需要某些用户的首单时间固定
     */
    private void fixSql() {
        try {
            Map<String, java.util.Date> buyerFirstDate = new HashMap<>();
            buyerFirstDate.put("MYS/886333", DateUtil.parseStringToDate("2018-01-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM14006", DateUtil.parseStringToDate("2018-01-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA17083", DateUtil.parseStringToDate("2018-01-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IQ14007", DateUtil.parseStringToDate("2018-01-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW13005", DateUtil.parseStringToDate("2018-01-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW13009", DateUtil.parseStringToDate("2018-01-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC13", DateUtil.parseStringToDate("2018-01-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IT1493", DateUtil.parseStringToDate("2018-01-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KZ14152", DateUtil.parseStringToDate("2018-01-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140003", DateUtil.parseStringToDate("2018-01-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC1504226", DateUtil.parseStringToDate("2018-01-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IN140018", DateUtil.parseStringToDate("2018-01-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140045", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UZ1401", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140005", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140103", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140111", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC150617", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC160811", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC161028", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC180109", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC180110", DateUtil.parseStringToDate("2018-01-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140050", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140187", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170004", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170025", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CO1621", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL18001", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNE17030", DateUtil.parseStringToDate("2018-01-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11075", DateUtil.parseStringToDate("2018-01-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERWKL025", DateUtil.parseStringToDate("2018-01-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140046", DateUtil.parseStringToDate("2018-01-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NGC47", DateUtil.parseStringToDate("2018-01-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BEM1856", DateUtil.parseStringToDate("2018-01-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("PL17003", DateUtil.parseStringToDate("2018-01-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM17011", DateUtil.parseStringToDate("2018-01-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AR12008", DateUtil.parseStringToDate("2018-01-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KS12003", DateUtil.parseStringToDate("2018-01-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC150709", DateUtil.parseStringToDate("2018-01-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AE14819", DateUtil.parseStringToDate("2018-01-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("QA14801", DateUtil.parseStringToDate("2018-01-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM14001", DateUtil.parseStringToDate("2018-01-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140014", DateUtil.parseStringToDate("2018-01-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140178", DateUtil.parseStringToDate("2018-01-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170033", DateUtil.parseStringToDate("2018-01-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KVE332", DateUtil.parseStringToDate("2018-01-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CL1704005", DateUtil.parseStringToDate("2018-01-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UZ1402", DateUtil.parseStringToDate("2018-02-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018072701", DateUtil.parseStringToDate("2018-02-06", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1734", DateUtil.parseStringToDate("2018-02-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1735", DateUtil.parseStringToDate("2018-02-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1736", DateUtil.parseStringToDate("2018-02-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM16017", DateUtil.parseStringToDate("2018-02-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC20", DateUtil.parseStringToDate("2018-02-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("serbia20171208", DateUtil.parseStringToDate("2018-02-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW16002", DateUtil.parseStringToDate("2018-02-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM13004", DateUtil.parseStringToDate("2018-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140091", DateUtil.parseStringToDate("2018-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140055", DateUtil.parseStringToDate("2018-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SRB0004", DateUtil.parseStringToDate("2018-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RO14004", DateUtil.parseStringToDate("2018-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RO18003", DateUtil.parseStringToDate("2018-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EC2014021", DateUtil.parseStringToDate("2018-03-01", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA13005", DateUtil.parseStringToDate("2018-03-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11003", DateUtil.parseStringToDate("2018-03-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14002", DateUtil.parseStringToDate("2018-03-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IQ14004", DateUtil.parseStringToDate("2018-03-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG14013", DateUtil.parseStringToDate("2018-03-06", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MM1402", DateUtil.parseStringToDate("2018-03-06", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1721", DateUtil.parseStringToDate("2018-03-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1729", DateUtil.parseStringToDate("2018-03-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1737", DateUtil.parseStringToDate("2018-03-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1738", DateUtil.parseStringToDate("2018-03-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AE14641", DateUtil.parseStringToDate("2018-03-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AE14642", DateUtil.parseStringToDate("2018-03-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW13003", DateUtil.parseStringToDate("2018-03-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GM1401", DateUtil.parseStringToDate("2018-03-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KS12005", DateUtil.parseStringToDate("2018-03-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM13005", DateUtil.parseStringToDate("2018-03-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC24", DateUtil.parseStringToDate("2018-03-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SB001", DateUtil.parseStringToDate("2018-03-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140117", DateUtil.parseStringToDate("2018-03-19", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ECU20171005", DateUtil.parseStringToDate("2018-03-19", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BEM1897", DateUtil.parseStringToDate("2018-03-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14063", DateUtil.parseStringToDate("2018-03-21", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KS17003", DateUtil.parseStringToDate("2018-03-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170005", DateUtil.parseStringToDate("2018-03-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170011", DateUtil.parseStringToDate("2018-03-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK-VEI", DateUtil.parseStringToDate("2018-03-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11002", DateUtil.parseStringToDate("2018-03-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC09", DateUtil.parseStringToDate("2018-03-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11108", DateUtil.parseStringToDate("2018-03-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MX18007", DateUtil.parseStringToDate("2018-03-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IN140025", DateUtil.parseStringToDate("2018-03-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UZ1801", DateUtil.parseStringToDate("2018-03-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW15009", DateUtil.parseStringToDate("2018-03-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170027", DateUtil.parseStringToDate("2018-03-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RO15017", DateUtil.parseStringToDate("2018-03-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IN140020", DateUtil.parseStringToDate("2018-04-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AE14802", DateUtil.parseStringToDate("2018-04-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140041", DateUtil.parseStringToDate("2018-04-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU160012", DateUtil.parseStringToDate("2018-04-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CO16050", DateUtil.parseStringToDate("2018-04-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC18040605", DateUtil.parseStringToDate("2018-04-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM13025", DateUtil.parseStringToDate("2018-04-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA17003", DateUtil.parseStringToDate("2018-04-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW13015", DateUtil.parseStringToDate("2018-04-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC22", DateUtil.parseStringToDate("2018-04-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK-GM", DateUtil.parseStringToDate("2018-04-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG17033", DateUtil.parseStringToDate("2018-04-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140044", DateUtil.parseStringToDate("2018-04-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU170041", DateUtil.parseStringToDate("2018-04-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("LY14024", DateUtil.parseStringToDate("2018-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NGC160", DateUtil.parseStringToDate("2018-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CO17078", DateUtil.parseStringToDate("2018-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11120", DateUtil.parseStringToDate("2018-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140053", DateUtil.parseStringToDate("2018-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MX18008", DateUtil.parseStringToDate("2018-05-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018050401", DateUtil.parseStringToDate("2018-05-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA13003", DateUtil.parseStringToDate("2018-05-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU160024", DateUtil.parseStringToDate("2018-05-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU160025", DateUtil.parseStringToDate("2018-05-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14030", DateUtil.parseStringToDate("2018-05-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GHC05", DateUtil.parseStringToDate("2018-05-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NGC64", DateUtil.parseStringToDate("2018-05-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YRMD062", DateUtil.parseStringToDate("2018-05-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IN170012", DateUtil.parseStringToDate("2018-05-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NGC157", DateUtil.parseStringToDate("2018-05-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AR12007", DateUtil.parseStringToDate("2018-05-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW13002", DateUtil.parseStringToDate("2018-05-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CA1805", DateUtil.parseStringToDate("2018-05-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CA1806", DateUtil.parseStringToDate("2018-05-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1724", DateUtil.parseStringToDate("2018-05-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1727", DateUtil.parseStringToDate("2018-05-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1739", DateUtil.parseStringToDate("2018-05-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC18042701", DateUtil.parseStringToDate("2018-05-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KZ14112", DateUtil.parseStringToDate("2018-05-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOQDMK ", DateUtil.parseStringToDate("2018-05-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHYFD", DateUtil.parseStringToDate("2018-05-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAODYBT ", DateUtil.parseStringToDate("2018-05-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UA14001", DateUtil.parseStringToDate("2018-05-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CGC02", DateUtil.parseStringToDate("2018-05-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180010", DateUtil.parseStringToDate("2018-05-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180011", DateUtil.parseStringToDate("2018-05-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA15025", DateUtil.parseStringToDate("2018-06-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOPSK ", DateUtil.parseStringToDate("2018-06-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHDSY", DateUtil.parseStringToDate("2018-06-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC06", DateUtil.parseStringToDate("2018-06-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KG047", DateUtil.parseStringToDate("2018-06-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AZ1730", DateUtil.parseStringToDate("2018-06-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180012", DateUtil.parseStringToDate("2018-06-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG15020", DateUtil.parseStringToDate("2018-06-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERLS180002", DateUtil.parseStringToDate("2018-06-19", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG17003", DateUtil.parseStringToDate("2018-06-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NGC162", DateUtil.parseStringToDate("2018-06-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW16111", DateUtil.parseStringToDate("2018-06-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOYCBX", DateUtil.parseStringToDate("2018-06-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140147", DateUtil.parseStringToDate("2018-06-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CA1808", DateUtil.parseStringToDate("2018-06-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1723", DateUtil.parseStringToDate("2018-06-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BE18001", DateUtil.parseStringToDate("2018-06-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180016", DateUtil.parseStringToDate("2018-06-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("WKL034", DateUtil.parseStringToDate("2018-06-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOPGJX", DateUtil.parseStringToDate("2018-07-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("PL18007", DateUtil.parseStringToDate("2018-07-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU160035", DateUtil.parseStringToDate("2018-07-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180018", DateUtil.parseStringToDate("2018-07-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOSHSY", DateUtil.parseStringToDate("2018-07-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHSJT", DateUtil.parseStringToDate("2018-07-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IN170016", DateUtil.parseStringToDate("2018-07-05", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11027", DateUtil.parseStringToDate("2018-07-06", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOSJWLT", DateUtil.parseStringToDate("2018-07-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180019", DateUtil.parseStringToDate("2018-07-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NGC07", DateUtil.parseStringToDate("2018-07-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC18070901", DateUtil.parseStringToDate("2018-07-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KZX14094", DateUtil.parseStringToDate("2018-07-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ZZMG14001", DateUtil.parseStringToDate("2018-07-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14001", DateUtil.parseStringToDate("2018-07-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YRMD078", DateUtil.parseStringToDate("2018-07-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU160044", DateUtil.parseStringToDate("2018-07-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KE1402", DateUtil.parseStringToDate("2018-07-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MM1435", DateUtil.parseStringToDate("2018-07-21", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK-CA", DateUtil.parseStringToDate("2018-07-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW14006", DateUtil.parseStringToDate("2018-07-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC18051701", DateUtil.parseStringToDate("2018-07-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC18072601", DateUtil.parseStringToDate("2018-07-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018072601", DateUtil.parseStringToDate("2018-07-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AZ16018", DateUtil.parseStringToDate("2018-07-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180020", DateUtil.parseStringToDate("2018-07-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA16061", DateUtil.parseStringToDate("2018-07-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140028", DateUtil.parseStringToDate("2018-07-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KT11122", DateUtil.parseStringToDate("2018-07-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UA15020", DateUtil.parseStringToDate("2018-08-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UZ1404", DateUtil.parseStringToDate("2018-08-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("HU14002", DateUtil.parseStringToDate("2018-08-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180026", DateUtil.parseStringToDate("2018-08-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180038", DateUtil.parseStringToDate("2018-08-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOYDJS", DateUtil.parseStringToDate("2018-08-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180032", DateUtil.parseStringToDate("2018-08-21", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BEM1861", DateUtil.parseStringToDate("2018-08-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERWKL012", DateUtil.parseStringToDate("2018-08-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MG20180413", DateUtil.parseStringToDate("2018-08-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOFCGM", DateUtil.parseStringToDate("2018-08-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOFJKT", DateUtil.parseStringToDate("2018-08-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERWKL049", DateUtil.parseStringToDate("2018-08-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM17003", DateUtil.parseStringToDate("2018-09-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180024", DateUtil.parseStringToDate("2018-09-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOKSFM", DateUtil.parseStringToDate("2018-09-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOWHHG", DateUtil.parseStringToDate("2018-09-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GBC26", DateUtil.parseStringToDate("2018-09-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW16017", DateUtil.parseStringToDate("2018-09-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERTG003", DateUtil.parseStringToDate("2018-09-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KZX14145", DateUtil.parseStringToDate("2018-09-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOXXJT", DateUtil.parseStringToDate("2018-09-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KG070", DateUtil.parseStringToDate("2018-09-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ET1405", DateUtil.parseStringToDate("2018-09-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC18080301", DateUtil.parseStringToDate("2018-09-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAONBLC", DateUtil.parseStringToDate("2018-09-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOTAWY", DateUtil.parseStringToDate("2018-09-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOWFJD", DateUtil.parseStringToDate("2018-09-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AE201769", DateUtil.parseStringToDate("2018-09-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU160016", DateUtil.parseStringToDate("2018-09-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AE201785", DateUtil.parseStringToDate("2018-10-01", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA17060", DateUtil.parseStringToDate("2018-10-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK-CE", DateUtil.parseStringToDate("2018-10-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YHC1808301", DateUtil.parseStringToDate("2018-10-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CO14153", DateUtil.parseStringToDate("2018-10-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14012", DateUtil.parseStringToDate("2018-10-17", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA16058", DateUtil.parseStringToDate("2018-10-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140207", DateUtil.parseStringToDate("2018-10-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHMJT", DateUtil.parseStringToDate("2018-10-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHYHG", DateUtil.parseStringToDate("2018-10-13", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SR14001", DateUtil.parseStringToDate("2018-10-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140036", DateUtil.parseStringToDate("2018-10-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KZX14080", DateUtil.parseStringToDate("2018-10-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC1504222", DateUtil.parseStringToDate("2018-10-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018103101", DateUtil.parseStringToDate("2018-10-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MM1404", DateUtil.parseStringToDate("2018-11-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU180030", DateUtil.parseStringToDate("2018-11-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOCIMC", DateUtil.parseStringToDate("2018-11-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG17055", DateUtil.parseStringToDate("2018-11-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOWASM", DateUtil.parseStringToDate("2018-11-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("QA18005", DateUtil.parseStringToDate("2018-11-21", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOFEK", DateUtil.parseStringToDate("2018-11-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHDZDH", DateUtil.parseStringToDate("2018-11-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOQDTS", DateUtil.parseStringToDate("2018-11-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("10000186", DateUtil.parseStringToDate("2018-11-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM16014", DateUtil.parseStringToDate("2018-11-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHDJS", DateUtil.parseStringToDate("2018-11-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA18089", DateUtil.parseStringToDate("2018-11-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOSDXJ", DateUtil.parseStringToDate("2018-11-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018112002", DateUtil.parseStringToDate("2018-11-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018112004", DateUtil.parseStringToDate("2018-11-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018112003", DateUtil.parseStringToDate("2018-11-26", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAODYHY", DateUtil.parseStringToDate("2018-11-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NPSCO", DateUtil.parseStringToDate("2018-12-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU140137", DateUtil.parseStringToDate("2018-12-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAO181214", DateUtil.parseStringToDate("2018-12-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA14010", DateUtil.parseStringToDate("2018-12-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOJYSM", DateUtil.parseStringToDate("2018-12-21", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOWHSY", DateUtil.parseStringToDate("2018-12-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM15014", DateUtil.parseStringToDate("2018-12-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG14012", DateUtil.parseStringToDate("2018-12-31", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("Falcon", DateUtil.parseStringToDate("2018-12-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOSDRA", DateUtil.parseStringToDate("2018-12-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHMKJ", DateUtil.parseStringToDate("2019-01-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOJSZS", DateUtil.parseStringToDate("2019-01-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAO", DateUtil.parseStringToDate("2019-01-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOYSJ", DateUtil.parseStringToDate("2019-01-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM16018", DateUtil.parseStringToDate("2019-01-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("10022325", DateUtil.parseStringToDate("2019-01-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAO190115", DateUtil.parseStringToDate("2019-01-16", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("AGGREGO SERVICES DMCC", DateUtil.parseStringToDate("2019-01-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA19120", DateUtil.parseStringToDate("2019-01-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERELS181122", DateUtil.parseStringToDate("2019-01-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOWBSY", DateUtil.parseStringToDate("2019-01-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAO502", DateUtil.parseStringToDate("2019-01-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("LY14001", DateUtil.parseStringToDate("2019-02-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("天津海通达", DateUtil.parseStringToDate("2019-02-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG15030", DateUtil.parseStringToDate("2019-02-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU150943", DateUtil.parseStringToDate("2019-02-18", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("OM15024", DateUtil.parseStringToDate("2019-02-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA14016", DateUtil.parseStringToDate("2019-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("RU190001", DateUtil.parseStringToDate("2019-02-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KW17012", DateUtil.parseStringToDate("2019-02-01", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERELS180002", DateUtil.parseStringToDate("2019-03-07", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YRMD069", DateUtil.parseStringToDate("2019-03-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2019030801", DateUtil.parseStringToDate("2019-03-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("KR oilfield service DMCC", DateUtil.parseStringToDate("2019-03-14", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("Mexico Kerui ", DateUtil.parseStringToDate("2019-03-11", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("河南长兴建设集团有限公司", DateUtil.parseStringToDate("2019-03-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK-VK", DateUtil.parseStringToDate("2019-03-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK-MA_2", DateUtil.parseStringToDate("2019-03-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("IT1406", DateUtil.parseStringToDate("2019-03-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("EG17079", DateUtil.parseStringToDate("2019-03-23", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MMTG001", DateUtil.parseStringToDate("2019-03-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("UZ1902", DateUtil.parseStringToDate("2019-03-22", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("东营科力化工有限公司", DateUtil.parseStringToDate("2019-03-27", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GHC12", DateUtil.parseStringToDate("2019-03-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC180323", DateUtil.parseStringToDate("2019-03-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("Texan Stone", DateUtil.parseStringToDate("2019-03-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA19011", DateUtil.parseStringToDate("2019-04-02", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAO190111", DateUtil.parseStringToDate("2019-04-04", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAODYYC", DateUtil.parseStringToDate("2019-04-03", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHYHW", DateUtil.parseStringToDate("2019-04-01", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC140014", DateUtil.parseStringToDate("2019-04-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("ERELS20190326", DateUtil.parseStringToDate("2019-04-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("APT", DateUtil.parseStringToDate("2019-04-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("陕西腾飞石油机电新技术有限责任公司  ", DateUtil.parseStringToDate("2019-04-09", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOHYFY", DateUtil.parseStringToDate("2019-04-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("LYHG", DateUtil.parseStringToDate("2019-04-08", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("潜江市昌茂机械配件销售中心", DateUtil.parseStringToDate("2019-04-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("NAVOKO S.R.O.", DateUtil.parseStringToDate("2019-04-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("TK1401", DateUtil.parseStringToDate("2019-04-10", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CO20190415", DateUtil.parseStringToDate("2019-04-15", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("MX18012", DateUtil.parseStringToDate("2019-04-12", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("YNC2018110601", DateUtil.parseStringToDate("2019-04-20", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("USA00002", DateUtil.parseStringToDate("2019-04-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("USA00003", DateUtil.parseStringToDate("2019-04-25", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CHN00001", DateUtil.parseStringToDate("2019-04-19", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CHN00004", DateUtil.parseStringToDate("2019-04-24", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14001_ER", DateUtil.parseStringToDate("2019-04-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("BL14022", DateUtil.parseStringToDate("2019-04-29", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("SA18095", DateUtil.parseStringToDate("2019-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CAN00001", DateUtil.parseStringToDate("2019-04-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CAN00002", DateUtil.parseStringToDate("2019-04-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CAN00003", DateUtil.parseStringToDate("2019-04-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("JN1423", DateUtil.parseStringToDate("2019-04-30", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("CHN00003", DateUtil.parseStringToDate("2019-04-28", DateUtil.SHORT_FORMAT_STR));
            buyerFirstDate.put("GAOTGN-5-001-05-001", DateUtil.parseStringToDate("2019-04-28", DateUtil.SHORT_FORMAT_STR));

            java.util.Date date2019 = DateUtil.parseStringToDate("2019-01-01", DateUtil.SHORT_FORMAT_STR);

            String fixUpdate = "UPDATE erui_report.rep_buyer_country_info SET membership_time = ? WHERE buyer_code = ?";
            String fixUpdate2 = "UPDATE erui_report.rep_buyer_country_info SET membership_time_2019after = ? WHERE buyer_code = ?";

            PreparedStatement pstm = conn.prepareStatement(fixUpdate);
            PreparedStatement pstm2 = conn.prepareStatement(fixUpdate2);

            for (Map.Entry<String,java.util.Date> entry : buyerFirstDate.entrySet()) {
                String crmCode = entry.getKey();
                java.util.Date value = entry.getValue();
                // 更新首单时间
                pstm.setDate(1, new Date(value.getTime()));
                pstm.setString(2, crmCode);
                pstm.execute();
                // 更新2019年后的首单时间
                if (!value.before(value)) {
                    pstm2.setDate(1, new Date(value.getTime()));
                    pstm2.setString(2, crmCode);
                    pstm2.execute();
                }
            }
            pstm.close();
            pstm2.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static final String BASE_INFO_QUERY_SQL = "SELECT t1.buyer_no, t1.buyer_code, t4.bn AS area_bn, t4.name AS area_name, t2.bn AS country_bn , t2.name AS country_name, t1.created_by AS acquiring_user_id, emp.name AS acquiring_user_name , IFNULL(t1.checked_at, t1.created_at) AS register_time, bus.net_at AS apply_time FROM erui_buyer.buyer t1 LEFT JOIN erui_sys.user emp ON emp.id = t1.created_by LEFT JOIN erui_buyer.buyer_business bus ON bus.buyer_id = t1.id LEFT JOIN erui_dict.country t2 ON t1.country_bn = t2.bn AND t2.lang = 'zh' LEFT JOIN erui_operation.market_area_country t3 ON t2.bn = t3.country_bn LEFT JOIN erui_operation.market_area t4 ON t3.market_area_bn = t4.bn AND t4.lang = 'zh' WHERE (t1.deleted_flag = 'N' AND buyer_code IS NOT NULL AND t1.`status` IN ('arroved', 'pass') AND t2.bn IS NOT NULL)";
    private static final String USER_QUERY_SQL = "select id from erui_report.rep_buyer_country_info where buyer_code = ?";
    private static final String USER_QUERY_ALL_SQL = "select id,buyer_code from erui_report.rep_buyer_country_info";
    private static final String USER_UPDATE_SQL = "UPDATE erui_report.rep_buyer_country_info SET area_bn = ?, area_name = ?, country_bn = ?, country_name = ?, acquiring_user_id = ?, acquiring_user_name = ?, register_time = ?, apply_time = ? WHERE id = ?";
    private static final String USER_INSERT_SQL = "INSERT INTO erui_report.rep_buyer_country_info (area_bn, area_name, country_bn, country_name, acquiring_user_id , acquiring_user_name, register_time, apply_time, buyer_no, buyer_code) VALUES (?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
    private static final String MEMBERSHIP_TIME_QUERY_SQL = "SELECT MIN(t2.start_date) AS membership_time FROM erui_order.`order` t1, erui_order.project t2 WHERE (t1.order_category != 1 AND t1.order_category != 3 AND t1.id = t2.order_id AND t1.`status` > 2 AND t2.start_date IS NOT NULL AND t1.crm_code = ?)";
    private static final String UPDATE_MEMBER_TIME_SQL = "update erui_report.rep_buyer_country_info set membership_time = ? where id = ?";
    private static final String MEMBERSHIP_TIME_AFTER_2019_QUERY_SQL = "SELECT MIN(t2.start_date) AS membership_time_2019after FROM erui_order.`order` t1, erui_order.project t2 WHERE (t1.order_category != 1 AND t1.order_category != 3 AND t1.id = t2.order_id AND t1.`status` > 2 AND t2.start_date IS NOT NULL AND t2.start_date >= '2019-01-01' AND t1.crm_code = ?)";
    private static final String UPDATE_MEMBER_TIME_AFTER_2019__SQL = "update erui_report.rep_buyer_country_info set membership_time_2019after = ? where id = ?";
}
