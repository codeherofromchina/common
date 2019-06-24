package com.erui.report.quartz;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class BuyerCountryInfo {
    private Connection conn;

    public BuyerCountryInfo(Connection conn) {
        this.conn = conn;
    }

    public void fullData() {
        fullBuyerBaseInfo();
        fullBuyerMembershipTime();
        fullBuyerAfter2019MembershipTime();
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
			ResultSet rs = stmt.executeQuery(USER_QUERY_ALL_SQL1);
			while (rs.next()) {
				String buyerCode = rs.getString("buyer_code");
				Long id = rs.getLong("id");
				// 2019年05月19日 查询会员是否在固定给定的静态数据中
				if(staticMembershipTimeUsers.contains(buyerCode)){
					continue;
				}
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
			ResultSet rs = stmt.executeQuery(USER_QUERY_ALL_SQL2);
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

	private static final String BASE_INFO_QUERY_SQL = "SELECT t1.buyer_no, t1.buyer_code, t4.bn AS area_bn, t4.name AS area_name, t2.bn AS country_bn , t2.name AS country_name, t1.created_by AS acquiring_user_id, emp.name AS acquiring_user_name , IFNULL(t1.checked_at, t1.created_at) AS register_time, bus.net_at AS apply_time FROM erui_buyer.buyer t1 LEFT JOIN erui_sys.user emp ON emp.id = t1.created_by LEFT JOIN erui_buyer.buyer_business bus ON bus.buyer_id = t1.id LEFT JOIN erui_dict.country t2 ON t1.country_bn = t2.bn AND t2.lang = 'zh' LEFT JOIN erui_operation.market_area_country t3 ON t2.bn = t3.country_bn LEFT JOIN erui_operation.market_area t4 ON t3.market_area_bn = t4.bn AND t4.lang = 'zh' WHERE (t1.deleted_flag = 'N' AND buyer_code IS NOT NULL AND t1.`status` IN ('arroved', 'pass') AND t2.bn IS NOT NULL)";
	private static final String USER_QUERY_SQL = "select id from erui_report.rep_buyer_country_info where buyer_code = ?";
	private static final String USER_QUERY_ALL_SQL = "select id,buyer_code from erui_report.rep_buyer_country_info";
	private static final String USER_QUERY_ALL_SQL1 = "select id,buyer_code from erui_report.rep_buyer_country_info where membership_time is null";
	private static final String USER_QUERY_ALL_SQL2 = "select id,buyer_code from erui_report.rep_buyer_country_info where membership_time_2019after is null";
	private static final String USER_UPDATE_SQL = "UPDATE erui_report.rep_buyer_country_info SET area_bn = ?, area_name = ?, country_bn = ?, country_name = ?, acquiring_user_id = ?, acquiring_user_name = ?, register_time = ?, apply_time = ? WHERE id = ?";
	private static final String USER_INSERT_SQL = "INSERT INTO erui_report.rep_buyer_country_info (area_bn, area_name, country_bn, country_name, acquiring_user_id , acquiring_user_name, register_time, apply_time, buyer_no, buyer_code) VALUES (?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
	private static final String MEMBERSHIP_TIME_QUERY_SQL = "SELECT MIN(t2.start_date) AS membership_time FROM erui_order.`order` t1, erui_order.project t2 WHERE (t1.order_category != 1 AND t1.order_category != 3 AND t1.id = t2.order_id AND t1.`status` in (3,4) AND t1.contract_no LIKE 'YR%' AND t2.project_status <> 'CANCEL' AND t1.delete_flag = 0 AND t2.start_date IS NOT NULL AND t1.crm_code = ?)";
	private static final String UPDATE_MEMBER_TIME_SQL = "update erui_report.rep_buyer_country_info set membership_time = ? where id = ?";
	private static final String MEMBERSHIP_TIME_AFTER_2019_QUERY_SQL = "SELECT MIN(t2.start_date) AS membership_time_2019after FROM erui_order.`order` t1, erui_order.project t2 WHERE (t1.order_category != 1 AND t1.order_category != 3 AND t1.id = t2.order_id AND t1.`status` in (3,4) AND t1.contract_no LIKE 'YR%' AND t2.project_status <> 'CANCEL' AND t1.delete_flag = 0 AND t2.start_date IS NOT NULL AND t2.start_date >= '2019-01-01' AND t1.crm_code = ?)";
	private static final String UPDATE_MEMBER_TIME_AFTER_2019__SQL = "update erui_report.rep_buyer_country_info set membership_time_2019after = ? where id = ?";

	protected static Set<String> staticMembershipTimeUsers = new HashSet<>();
	static {

		staticMembershipTimeUsers.add("MYS/886333");
		staticMembershipTimeUsers.add("OM14006");
		staticMembershipTimeUsers.add("SA17083");
		staticMembershipTimeUsers.add("IQ14007");
		staticMembershipTimeUsers.add("KW13005");
		staticMembershipTimeUsers.add("KW13009");
		staticMembershipTimeUsers.add("GBC13");
		staticMembershipTimeUsers.add("IT1493");
		staticMembershipTimeUsers.add("KZ14152");
		staticMembershipTimeUsers.add("YNC140003");
		staticMembershipTimeUsers.add("YNC1504226");
		staticMembershipTimeUsers.add("IN140018");
		staticMembershipTimeUsers.add("RU140045");
		staticMembershipTimeUsers.add("UZ1401");
		staticMembershipTimeUsers.add("YNC140005");
		staticMembershipTimeUsers.add("YNC140103");
		staticMembershipTimeUsers.add("YNC140111");
		staticMembershipTimeUsers.add("YNC150617");
		staticMembershipTimeUsers.add("YNC160811");
		staticMembershipTimeUsers.add("YNC161028");
		staticMembershipTimeUsers.add("YNC180109");
		staticMembershipTimeUsers.add("YNC180110");
		staticMembershipTimeUsers.add("RU140050");
		staticMembershipTimeUsers.add("RU140187");
		staticMembershipTimeUsers.add("RU170004");
		staticMembershipTimeUsers.add("RU170025");
		staticMembershipTimeUsers.add("CO1621");
		staticMembershipTimeUsers.add("BL18001");
		staticMembershipTimeUsers.add("YNE17030");
		staticMembershipTimeUsers.add("KT11075");
		staticMembershipTimeUsers.add("ERWKL025");
		staticMembershipTimeUsers.add("RU140046");
		staticMembershipTimeUsers.add("NGC47");
		staticMembershipTimeUsers.add("BEM1856");
		staticMembershipTimeUsers.add("PL17003");
		staticMembershipTimeUsers.add("OM17011");
		staticMembershipTimeUsers.add("AR12008");
		staticMembershipTimeUsers.add("KS12003");
		staticMembershipTimeUsers.add("YNC150709");
		staticMembershipTimeUsers.add("AE14819");
		staticMembershipTimeUsers.add("QA14801");
		staticMembershipTimeUsers.add("OM14001");
		staticMembershipTimeUsers.add("RU140014");
		staticMembershipTimeUsers.add("RU140178");
		staticMembershipTimeUsers.add("RU170033");
		staticMembershipTimeUsers.add("KVE332");
		staticMembershipTimeUsers.add("CL1704005");
		staticMembershipTimeUsers.add("UZ1402");
		staticMembershipTimeUsers.add("YNC2018072701");
		staticMembershipTimeUsers.add("JN1734");
		staticMembershipTimeUsers.add("JN1735");
		staticMembershipTimeUsers.add("JN1736");
		staticMembershipTimeUsers.add("OM16017");
		staticMembershipTimeUsers.add("GBC20");
		staticMembershipTimeUsers.add("serbia20171208");
		staticMembershipTimeUsers.add("KW16002");
		staticMembershipTimeUsers.add("OM13004");
		staticMembershipTimeUsers.add("RU140091");
		staticMembershipTimeUsers.add("RU140055");
		staticMembershipTimeUsers.add("SRB0004");
		staticMembershipTimeUsers.add("RO14004");
		staticMembershipTimeUsers.add("RO18003");
		staticMembershipTimeUsers.add("EC2014021");
		staticMembershipTimeUsers.add("SA13005");
		staticMembershipTimeUsers.add("KT11003");
		staticMembershipTimeUsers.add("BL14002");
		staticMembershipTimeUsers.add("IQ14004");
		staticMembershipTimeUsers.add("EG14013");
		staticMembershipTimeUsers.add("MM1402");
		staticMembershipTimeUsers.add("JN1721");
		staticMembershipTimeUsers.add("JN1729");
		staticMembershipTimeUsers.add("JN1737");
		staticMembershipTimeUsers.add("JN1738");
		staticMembershipTimeUsers.add("AE14641");
		staticMembershipTimeUsers.add("AE14642");
		staticMembershipTimeUsers.add("KW13003");
		staticMembershipTimeUsers.add("GM1401");
		staticMembershipTimeUsers.add("KS12005");
		staticMembershipTimeUsers.add("OM13005");
		staticMembershipTimeUsers.add("GBC24");
		staticMembershipTimeUsers.add("SB001");
		staticMembershipTimeUsers.add("RU140117");
		staticMembershipTimeUsers.add("ECU20171005");
		staticMembershipTimeUsers.add("BEM1897");
		staticMembershipTimeUsers.add("BL14063");
		staticMembershipTimeUsers.add("KS17003");
		staticMembershipTimeUsers.add("RU170005");
		staticMembershipTimeUsers.add("RU170011");
		staticMembershipTimeUsers.add("TK-VEI");
		staticMembershipTimeUsers.add("KT11002");
		staticMembershipTimeUsers.add("GBC09");
		staticMembershipTimeUsers.add("KT11108");
		staticMembershipTimeUsers.add("MX18007");
		staticMembershipTimeUsers.add("IN140025");
		staticMembershipTimeUsers.add("UZ1801");
		staticMembershipTimeUsers.add("KW15009");
		staticMembershipTimeUsers.add("RU170027");
		staticMembershipTimeUsers.add("RO15017");
		staticMembershipTimeUsers.add("IN140020");
		staticMembershipTimeUsers.add("AE14802");
		staticMembershipTimeUsers.add("RU140041");
		staticMembershipTimeUsers.add("RU160012");
		staticMembershipTimeUsers.add("CO16050");
		staticMembershipTimeUsers.add("YNC18040605");
		staticMembershipTimeUsers.add("OM13025");
		staticMembershipTimeUsers.add("SA17003");
		staticMembershipTimeUsers.add("KW13015");
		staticMembershipTimeUsers.add("GBC22");
		staticMembershipTimeUsers.add("TK-GM");
		staticMembershipTimeUsers.add("EG17033");
		staticMembershipTimeUsers.add("RU140044");
		staticMembershipTimeUsers.add("RU170041");
		staticMembershipTimeUsers.add("LY14024");
		staticMembershipTimeUsers.add("NGC160");
		staticMembershipTimeUsers.add("CO17078");
		staticMembershipTimeUsers.add("KT11120");
		staticMembershipTimeUsers.add("YNC140053");
		staticMembershipTimeUsers.add("MX18008");
		staticMembershipTimeUsers.add("YNC2018050401");
		staticMembershipTimeUsers.add("SA13003");
		staticMembershipTimeUsers.add("RU160024");
		staticMembershipTimeUsers.add("RU160025");
		staticMembershipTimeUsers.add("BL14030");
		staticMembershipTimeUsers.add("GHC05");
		staticMembershipTimeUsers.add("NGC64");
		staticMembershipTimeUsers.add("YRMD062");
		staticMembershipTimeUsers.add("IN170012");
		staticMembershipTimeUsers.add("NGC157");
		staticMembershipTimeUsers.add("AR12007");
		staticMembershipTimeUsers.add("KW13002");
		staticMembershipTimeUsers.add("CA1805");
		staticMembershipTimeUsers.add("CA1806");
		staticMembershipTimeUsers.add("JN1724");
		staticMembershipTimeUsers.add("JN1727");
		staticMembershipTimeUsers.add("JN1739");
		staticMembershipTimeUsers.add("YNC18042701");
		staticMembershipTimeUsers.add("KZ14112");
		staticMembershipTimeUsers.add("GAOQDMK ");
		staticMembershipTimeUsers.add("GAOHYFD");
		staticMembershipTimeUsers.add("GAODYBT ");
		staticMembershipTimeUsers.add("UA14001");
		staticMembershipTimeUsers.add("CGC02");
		staticMembershipTimeUsers.add("RU180010");
		staticMembershipTimeUsers.add("RU180011");
		staticMembershipTimeUsers.add("SA15025");
		staticMembershipTimeUsers.add("GAOPSK ");
		staticMembershipTimeUsers.add("  GAOHDSY");
		staticMembershipTimeUsers.add("GBC06");
		staticMembershipTimeUsers.add("KG047");
		staticMembershipTimeUsers.add("AZ1730");
		staticMembershipTimeUsers.add("RU180012");
		staticMembershipTimeUsers.add("EG15020");
		staticMembershipTimeUsers.add("ERLS180002");
		staticMembershipTimeUsers.add("EG17003");
		staticMembershipTimeUsers.add("NGC162");
		staticMembershipTimeUsers.add("KW16111");
		staticMembershipTimeUsers.add("GAOYCBX");
		staticMembershipTimeUsers.add("RU140147");
		staticMembershipTimeUsers.add("CA1808");
		staticMembershipTimeUsers.add("JN1723");
		staticMembershipTimeUsers.add("BE18001");
		staticMembershipTimeUsers.add("RU180016");
		staticMembershipTimeUsers.add("WKL034");
		staticMembershipTimeUsers.add("GAOPGJX");
		staticMembershipTimeUsers.add("PL18007");
		staticMembershipTimeUsers.add("RU160035");
		staticMembershipTimeUsers.add("RU180018");
		staticMembershipTimeUsers.add("GAOSHSY");
		staticMembershipTimeUsers.add("GAOHSJT");
		staticMembershipTimeUsers.add("IN170016");
		staticMembershipTimeUsers.add("KT11027");
		staticMembershipTimeUsers.add("GAOSJWLT");
		staticMembershipTimeUsers.add("RU180019");
		staticMembershipTimeUsers.add("NGC07");
		staticMembershipTimeUsers.add("YNC18070901");
		staticMembershipTimeUsers.add("KZX14094");
		staticMembershipTimeUsers.add("ZZMG14001");
		staticMembershipTimeUsers.add("BL14001");
		staticMembershipTimeUsers.add("YRMD078");
		staticMembershipTimeUsers.add("RU160044");
		staticMembershipTimeUsers.add("KE1402");
		staticMembershipTimeUsers.add("MM1435");
		staticMembershipTimeUsers.add("TK-CA");
		staticMembershipTimeUsers.add("KW14006");
		staticMembershipTimeUsers.add("YNC18051701");
		staticMembershipTimeUsers.add("YNC18072601");
		staticMembershipTimeUsers.add("YNC2018072601");
		staticMembershipTimeUsers.add("AZ16018");
		staticMembershipTimeUsers.add("RU180020");
		staticMembershipTimeUsers.add("SA16061");
		staticMembershipTimeUsers.add("YNC140028");
		staticMembershipTimeUsers.add("KT11122");
		staticMembershipTimeUsers.add("UA15020");
		staticMembershipTimeUsers.add("UZ1404");
		staticMembershipTimeUsers.add("HU14002");
		staticMembershipTimeUsers.add("RU180026");
		staticMembershipTimeUsers.add("RU180038");
		staticMembershipTimeUsers.add("GAOYDJS");
		staticMembershipTimeUsers.add("RU180032");
		staticMembershipTimeUsers.add("BEM1861");
		staticMembershipTimeUsers.add("ERWKL012");
		staticMembershipTimeUsers.add("MG20180413");
		staticMembershipTimeUsers.add("GAOFCGM");
		staticMembershipTimeUsers.add("GAOFJKT");
		staticMembershipTimeUsers.add("ERWKL049");
		staticMembershipTimeUsers.add("OM17003");
		staticMembershipTimeUsers.add("RU180024");
		staticMembershipTimeUsers.add("GAOKSFM");
		staticMembershipTimeUsers.add("GAOWHHG");
		staticMembershipTimeUsers.add("GBC26");
		staticMembershipTimeUsers.add("KW16017");
		staticMembershipTimeUsers.add("ERTG003");
		staticMembershipTimeUsers.add("KZX14145");
		staticMembershipTimeUsers.add("GAOXXJT");
		staticMembershipTimeUsers.add("KG070");
		staticMembershipTimeUsers.add("ET1405");
		staticMembershipTimeUsers.add("YNC18080301");
		staticMembershipTimeUsers.add("GAONBLC");
		staticMembershipTimeUsers.add("GAOTAWY");
		staticMembershipTimeUsers.add("GAOWFJD");
		staticMembershipTimeUsers.add("AE201769");
		staticMembershipTimeUsers.add("RU160016");
		staticMembershipTimeUsers.add("AE201785");
		staticMembershipTimeUsers.add("SA17060");
		staticMembershipTimeUsers.add("TK-CE");
		staticMembershipTimeUsers.add("YHC1808301");
		staticMembershipTimeUsers.add("CO14153");
		staticMembershipTimeUsers.add("BL14012");
		staticMembershipTimeUsers.add("SA16058");
		staticMembershipTimeUsers.add("RU140207");
		staticMembershipTimeUsers.add("GAOHMJT");
		staticMembershipTimeUsers.add("GAOHYHG");
		staticMembershipTimeUsers.add("SR14001");
		staticMembershipTimeUsers.add("RU140036");
		staticMembershipTimeUsers.add("KZX14080");
		staticMembershipTimeUsers.add("YNC1504222");
		staticMembershipTimeUsers.add("YNC2018103101");
		staticMembershipTimeUsers.add("MM1404");
		staticMembershipTimeUsers.add("RU180030");
		staticMembershipTimeUsers.add("GAOCIMC");
		staticMembershipTimeUsers.add("EG17055");
		staticMembershipTimeUsers.add("GAOWASM");
		staticMembershipTimeUsers.add("QA18005");
		staticMembershipTimeUsers.add("GAOFEK");
		staticMembershipTimeUsers.add("GAOHDZDH");
		staticMembershipTimeUsers.add("GAOQDTS");
		staticMembershipTimeUsers.add("10000186");
		staticMembershipTimeUsers.add("OM16014");
		staticMembershipTimeUsers.add("GAOHDJS");
		staticMembershipTimeUsers.add("SA18089");
		staticMembershipTimeUsers.add("GAOSDXJ");
		staticMembershipTimeUsers.add("YNC2018112002");
		staticMembershipTimeUsers.add("YNC2018112004");
		staticMembershipTimeUsers.add("YNC2018112003");
		staticMembershipTimeUsers.add("GAODYHY");
		staticMembershipTimeUsers.add("NPSCO");
		staticMembershipTimeUsers.add("RU140137");
		staticMembershipTimeUsers.add("GAO181214");
		staticMembershipTimeUsers.add("SA14010");
		staticMembershipTimeUsers.add("GAOJYSM");
		staticMembershipTimeUsers.add("GAOWHSY");
		staticMembershipTimeUsers.add("OM15014");
		staticMembershipTimeUsers.add("EG14012");
		staticMembershipTimeUsers.add("Falcon");
		staticMembershipTimeUsers.add("GAOSDRA");
	}
}
