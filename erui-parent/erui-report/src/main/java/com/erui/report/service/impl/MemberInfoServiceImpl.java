package com.erui.report.service.impl;

import com.erui.comm.util.excel.BuildExcel;
import com.erui.comm.util.excel.BuildExcelImpl;
import com.erui.comm.util.excel.ExcelCustomStyle;
import com.erui.report.dao.MemberInfoStatisticsMapper;
import com.erui.report.dao.SalesmanNumsMapper;
import com.erui.report.service.MemberInfoService;
import com.erui.report.service.SalesmanNumsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoStatisticsMapper memberInfoStatisticsMapper;
    @Autowired
    private SalesmanNumsService salesmanNumsService;
    @Autowired
    private SalesmanNumsMapper salesmanNumsMapper;

    /**
     * 按照地区统计会员等级
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> membershipGradeByArea(Map<String, Object> params) {
        List<Map<String, Object>> gradeList = memberInfoStatisticsMapper.membershipGradeByArea(params);
        Map<String, List<Object>> result = _handleMemberGradeData(gradeList);
        return result;
    }

    @Override
    public HSSFWorkbook exportMembershipGradeByArea(Map<String, Object> params) {
        Map<String, List<Object>> map = membershipGradeByArea(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("normalGradeList");
        row01.add(0, "普通会员数量");
        List<Object> row02 = map.get("topGradeList");
        row02.add(0, "高级会员数量");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());
        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员等级-按地区统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员等级-按地区统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    @Override
    public Map<String, List<Object>> membershipGradeByCountry(Map<String, Object> params) {
        List<Map<String, Object>> gradeList = memberInfoStatisticsMapper.membershipGradeByCountry(params);
        Map<String, List<Object>> result = _handleMemberGradeData(gradeList);
        return result;
    }

    @Override
    public HSSFWorkbook exportMembershipGradeByCountry(Map<String, Object> params) {
        Map<String, List<Object>> map = membershipGradeByCountry(params);
        List<Object> headerList = map.get("nameList");
        List<Object> row01 = map.get("normalGradeList");
        List<Object> row02 = map.get("topGradeList");
//        int headerListSize = headerList.size();
//        int row01Size = row01.size();
//        int row02Size = row02.size();
//        if (params.get("sort") != null && "1".equals(String.valueOf(params.get("sort")))) {
//            headerList = headerList.subList(headerListSize > 10 ? headerListSize - 10 : 0, headerListSize);
//            row01 = row01.subList(row01Size > 10 ? row01Size - 10 : 0, row01Size);
//            row02 = row02.subList(row02Size > 10 ? row02Size - 10 : 0, row02Size);
//        } else {
//            headerList = headerList.subList(0, headerListSize >= 10 ? 10 : headerListSize);
//            row01 = row01.subList(0, row01Size >= 10 ? 10 : row01Size);
//            row02 = row02.subList(0, row02Size >= 10 ? 10 : row02Size);
//        }
        headerList.add(0, "");
        row01.add(0, "普通会员数量");
        row02.add(0, "高级会员数量");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员等级-按国家统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员等级-按国家统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

//    @Override
//    public Map<String, List<Object>> visitStatisticsByOrg(Map<String, Object> params) {
//        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByOrg(params);
//        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
//        return result;
//    }
    
    @Override
    public Map<String, List<Object>> visitStatisticsByOrg(Map<String, Object> params) {
        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByOrg(params);
        // 获取各个事业部的平均销售人员
        List<Map<String, Object>> avgManNumInMonthByOrgList = salesmanNumsMapper.avgManNumInMonthByOrg(_changeDateToFirstDay(params));
        Map<String, List<Object>> result = _handleNewVisitStatisticsData(visitStatisticsData,avgManNumInMonthByOrgList);
        return result;
    }

    @Override
    public HSSFWorkbook exportVisitStatisticsByOrg(Map<String, Object> params) {
        Map<String, List<Object>> map = visitStatisticsByOrg(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("numList");
        row01.add(0, "拜访数");
        List<Object> row02 = map.get("avgList");
        row02.add(0, "人均拜访数");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "客户拜访统计-按事业部统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "客户拜访统计-按事业部统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

//    @Override
//    public Map<String, List<Object>> visitStatisticsByArea(Map<String, Object> params) {
//        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByArea(params);
//        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
//        return result;
//    }
    
    @Override
    public Map<String, List<Object>> visitStatisticsByArea(Map<String, Object> params) {
        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByArea(params);
        // 获取各个地区的平均销售人员
        List<Map<String, Object>> avgManNumInMonthByAreaList = salesmanNumsMapper.avgManNumInMonthByArea(_changeDateToFirstDay(params));
        Map<String, List<Object>> result = _handleNewVisitStatisticsData(visitStatisticsData,avgManNumInMonthByAreaList);
        return result;
    }

    @Override
    public HSSFWorkbook exportVisitStatisticsByArea(Map<String, Object> params) {
        Map<String, List<Object>> map = visitStatisticsByArea(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("numList");
        row01.add(0, "拜访数");
        List<Object> row02 = map.get("avgList");
        row02.add(0, "人均拜访数");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "客户拜访统计-按地区统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "客户拜访统计-按地区统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

//    @Override
//    public Map<String, List<Object>> visitStatisticsByCountry(Map<String, Object> params) {
//        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByCountry(params);
//        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
//        return result;
//    }
    
    @Override
    public Map<String, List<Object>> visitStatisticsByCountry(Map<String, Object> params) {
        List<Map<String, Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByCountry(params);
        // 获取各个国家的平均销售人员
        List<Map<String, Object>> avgManNumInMonthByCountryList = salesmanNumsMapper.avgManNumInMonthByCountry(_changeDateToFirstDay(params));
        Map<String, List<Object>> result = _handleNewVisitStatisticsData(visitStatisticsData,avgManNumInMonthByCountryList);
        return result;
    }

    @Override
    public HSSFWorkbook exportVisitStatisticsByCountry(Map<String, Object> params) {
        Map<String, List<Object>> map = visitStatisticsByCountry(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("numList");
        row01.add(0, "拜访数");
        List<Object> row02 = map.get("avgList");
        row02.add(0, "人均拜访数");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "客户拜访统计-按国家统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "客户拜访统计-按国家统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    @Override
    @Deprecated
    public Map<String, List<Object>> membershipByArea(Map<String, Object> params) {
        List<Map<String, Object>> membershipNumList = memberInfoStatisticsMapper.membershipByArea(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(membershipNumList);
        return result;
    }


    /**
     * 按地区统计新增会员
     * 统计新增会员的成单金额
     *
     * @param params {"startTime":"2017-12-01","endTime":"2019-01-02","sort":2}
     *               sort:1 正序  2 倒序
     * @return
     */
    @Override
    public Map<String, List<Object>> statisticsAddMembershipByArea(Map<String, Object> params) {
        // 按地区查找所有新增会员
        List<Map<String, Object>> newMenbersList = memberInfoStatisticsMapper.findAllNewMembershipByArea(params);

        Map<String, List<Object>> result = new HashMap<>();
        List<Object> names = new ArrayList<>(); // 地区名称
        List<Object> nums = new ArrayList<>(); // 新增客户数量
        List<Object> amounts = new ArrayList<>(); // 新增客户成单金额

        newMenbersList.stream().forEach(vo -> {
            Long totalNum = (Long) vo.get("totalNum");
            BigDecimal totalPrice = (BigDecimal) vo.get("totalPrice");
            String regionName = (String) vo.get("region_name");

            names.add(regionName);
            nums.add(totalNum);
            amounts.add(totalPrice.setScale(2, BigDecimal.ROUND_DOWN));
        });

        result.put("names", names);
        result.put("num", nums);
        result.put("amount", amounts);

        return result;
    }


    @Override
    public HSSFWorkbook exportMembershipByArea(Map<String, Object> params) {
        Map<String, List<Object>> map = statisticsAddMembershipByArea(params);
        List<Object> headerList = map.get("names");
        if (headerList == null || headerList.size() == 0) {
            return null;
        }
        headerList.add(0, "");

        List<Object> row01 = map.get("num");
        row01.add(0, "会员数量（个）");
        List<Object> row02 = map.get("amount");
        row02.add(0, "成单金额（万美元）");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员统计-按地区统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员统计-按地区统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    @Override
    @Deprecated
    public Map<String, List<Object>> membershipByCountry(Map<String, Object> params) {
        List<Map<String, Object>> membershipNumList = memberInfoStatisticsMapper.membershipByCountry(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(membershipNumList);
        return result;
    }


    @Override
    public Map<String, List<Object>> statisticsAddMembershipByCountry(Map<String, Object> params) {
        // 按地区查找所有新增会员
        List<Map<String, Object>> newMenbersList = memberInfoStatisticsMapper.findAllNewMembershipByCountry(params);

        Map<String, List<Object>> result = new HashMap<>();

        List<Object> names = new ArrayList<>(); // 国家名称
        List<Object> nums = new ArrayList<>(); // 新增客户数量
        List<Object> amounts = new ArrayList<>(); // 新增客户成单金额

        newMenbersList.stream().forEach(vo -> {
            Long totalNum = (Long) vo.get("totalNum");
            BigDecimal totalPrice = (BigDecimal) vo.get("totalPrice");
            String countryName = (String) vo.get("country_name");

            names.add(countryName);
            nums.add(totalNum);
            amounts.add(totalPrice.setScale(2, BigDecimal.ROUND_DOWN));
        });

        result.put("names", names);
        result.put("num", nums);
        result.put("amount", amounts);

        return result;
    }


    @Override
    public HSSFWorkbook exportMembershipByCountry(Map<String, Object> params) {
        Map<String, List<Object>> map = statisticsAddMembershipByCountry(params);
        List<Object> headerList = map.get("names");
        List<Object> row01 = map.get("num");
        List<Object> row02 = map.get("amount");
        if (headerList == null || headerList.size() == 0) {
            return null;
        }

//        int headerListSize = headerList.size();
//        int row01Size = row01.size();
//        int row02Size = row02.size();
//        if (params.get("sort") != null && "1".equals(String.valueOf(params.get("sort")))) {
//            headerList = headerList.subList(headerListSize > 10 ? headerListSize - 10 : 0, headerListSize);
//            row01 = row01.subList(row01Size > 10 ? row01Size - 10 : 0, row01Size);
//            row02 = row02.subList(row02Size > 10 ? row02Size - 10 : 0, row02Size);
//        } else {
//            headerList = headerList.subList(0, headerListSize >= 10 ? 10 : headerListSize);
//            row01 = row01.subList(0, row01Size >= 10 ? 10 : row01Size);
//            row02 = row02.subList(0, row02Size >= 10 ? 10 : row02Size);
//        }

        headerList.add(0, "");
        row01.add(0, "会员数量（个）");
        row02.add(0, "成单金额（万美元）");


        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员统计-按国家统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员统计-按国家统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }


    @Override
    public List<Map<String, Object>> singleCustomer(Map<String, Object> params) {
        List<Map<String, Object>> singleCustomerData = memberInfoStatisticsMapper.singleCustomer(params);
        List<Map<String, Object>> resultData = new ArrayList<>();
        int totalInquiryNum = 0;
        int totalOrderNum = 0;
        BigDecimal oneHundred = new BigDecimal(100);
        int eruiTotalOrderNum = 0; // 这里需要把orgId为1、2、3的总数加到4上来，sql控制了1、2、3在4的前面
        int eruiTotalInquiryNum = 0; // 这里需要把orgId为1、2、3的总数加到4上来，sql控制了1、2、3在4的前面
        for (Map<String, Object> map : singleCustomerData) {
            String name = (String) map.get("name");
            if (StringUtils.isBlank(name)) {
                map.put("name", "其他");
            }
            Integer orgId = (Integer) map.get("orgId");


            BigDecimal inquiryNum = (BigDecimal) map.get("inquiryNum");
            totalInquiryNum += inquiryNum.intValue();
            map.put("inquiryNum", inquiryNum.intValue()); // 询单数量转换成整数
            BigDecimal orderNum = (BigDecimal) map.get("orderNum");
            totalOrderNum += orderNum.intValue();
            map.put("orderNum", orderNum.intValue()); // 订单数量转换成整数
            BigDecimal bigDecimal = (BigDecimal) map.get("rate");
            bigDecimal = bigDecimal.multiply(oneHundred).setScale(2, BigDecimal.ROUND_DOWN);
            map.put("rate", bigDecimal);
            resultData.add(map);
            if (orgId != null && orgId == 1 || orgId == 4) {
                eruiTotalOrderNum += orderNum.intValue();
                eruiTotalInquiryNum += inquiryNum.intValue();
                if (orgId == 4) {
                    map.put("inquiryNum", eruiTotalInquiryNum);
                    map.put("orderNum", eruiTotalOrderNum);
                }
            }

        }
        Map<String, Object> total = new HashMap<>();
        total.put("name", "合计");
        total.put("inquiryNum", totalInquiryNum);
        total.put("orderNum", totalOrderNum);
        BigDecimal totalBigDecimal;
        if (totalInquiryNum == 0) {
            totalBigDecimal = BigDecimal.ZERO;
        } else {
            totalBigDecimal = new BigDecimal((double) totalOrderNum / totalInquiryNum * 100).setScale(2, BigDecimal.ROUND_DOWN);
        }
        total.put("rate", totalBigDecimal);
        resultData.add(total);
        return resultData;
    }

    @Override
    public HSSFWorkbook exportSingleCustomer(Map<String, Object> params) {
        List<Map<String, Object>> listData = singleCustomer(params);
        String[] header = {"事业部", "报价数量（个）", "成单数量（个）", "成单率（%）"};
        String[] keys = {"name", "inquiryNum", "orderNum", "rate"};

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(listData, header, keys,
                "会员数据统计-成单客户");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, listData.size());
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        if (params.get("startTime") != null) {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员数据统计-成单客户（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        } else {
            ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员数据统计-成单客户");
        }
        return workbook;
    }

    @Override
    public Map<String, List<Object>> signingBodyByArea(Map<String, Object> params) {
        List<Map<String, Object>> signingBodyList = memberInfoStatisticsMapper.signingBodyByArea(params);
        Map<String, List<Object>> result = _handleSigningBodyData(signingBodyList);
        return result;
    }

    @Override
    public HSSFWorkbook exportSigningBodyByArea(Map<String, Object> params) {
        Map<String, List<Object>> map = signingBodyByArea(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("eruiNumList");
        row01.add(0, "易瑞签约主体（个）");
        List<Object> row02 = map.get("otherNumList");
        row02.add(0, "其他签约主体（个）");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员签约主体-按地区统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员签约主体-按地区统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    @Override
    public Map<String, List<Object>> signingBodyByCountry(Map<String, Object> params) {
        List<Map<String, Object>> signingBodyList = memberInfoStatisticsMapper.signingBodyByCountry(params);
        Map<String, List<Object>> result = _handleSigningBodyData(signingBodyList);
        return result;
    }

    @Override
    public HSSFWorkbook exportSigningBodyByCountry(Map<String, Object> params) {
        Map<String, List<Object>> map = signingBodyByCountry(params);
        List<Object> headerList = map.get("nameList");
        int headerListSize = headerList.size();
//        headerList = headerList.subList(0, headerListSize >= 10 ? 10 : headerListSize);
        headerList.add(0, "");

        List<Object> row01 = map.get("eruiNumList");
//        int row01Size = row01.size();
//        row01 = row01.subList(0, row01Size >= 10 ? 10 : row01Size);
        row01.add(0, "易瑞签约主体（个）");
        List<Object> row02 = map.get("otherNumList");
//        int row02Size = row02.size();
//        row02 = row02.subList(0, row02Size >= 10 ? 10 : row02Size);
        row02.add(0, "其他签约主体（个）");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员签约主体-按国家统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员签约主体-按国家统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    @Override
    public Map<String, List<Object>> signingBodyByOrg(Map<String, Object> params) {
        List<Map<String, Object>> signingBodyList = memberInfoStatisticsMapper.signingBodyByOrg(params);
        Map<String, List<Object>> result = _handleSigningBodyData(signingBodyList);
        return result;
    }

    @Override
    public HSSFWorkbook exportSigningBodyByOrg(Map<String, Object> params) {
        Map<String, List<Object>> map = signingBodyByOrg(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("eruiNumList");
        row01.add(0, "易瑞签约主体（个）");
        List<Object> row02 = map.get("otherNumList");
        row02.add(0, "其他签约主体（个）");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());
        rowList.add(row02.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "会员签约主体-按事业部统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 2);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "会员签约主体-按事业部统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

//    @Override
//    public Map<String, List<Object>> efficiencyByArea(Map<String, Object> params) {
//        // 获取数据
//        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByArea(params);
//        Map<String, Map<String, Object>> totalNumMap = salesmanNumsService.manTotalNumByArea(params);
//        boolean ascFlag = "1".equals(String.valueOf(params.get("sort")));
//        // 处理数据
//        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, totalNumMap, ascFlag);
//        return resultMap;
//    }
    
    @Override
    public Map<String, List<Object>> efficiencyByArea(Map<String, Object> params) {
        // 获取各个区域的总销售额数据
        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByArea(params);
        // 获取各个区域的平均销售人员
        List<Map<String, Object>> avgManNumInMonthByAreaList = salesmanNumsMapper.avgManNumInMonthByArea(_changeDateToFirstDay(params));
        boolean ascFlag = "1".equals(String.valueOf(params.get("sort")));
        // 处理数据
        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, avgManNumInMonthByAreaList, ascFlag);
        return resultMap;
    }

    @Override
    public HSSFWorkbook exportEfficiencyByArea(Map<String, Object> params) {
        Map<String, List<Object>> map = efficiencyByArea(params);
        List<Object> headerList = map.get("nameList");
        headerList.add(0, "");

        List<Object> row01 = map.get("dataList");
        row01.add(0, "万美元");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "人均效能统计-地区统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, 1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "人均效能统计-地区统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

//    @Override
//    public Map<String, List<Object>> efficiencyByCountry(Map<String, Object> params) {
//        // 获取数据
//        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByCountry(params);
//        Map<String, Map<String, Object>> totalNumMap = salesmanNumsService.manTotalNumByCountry(params);
//        boolean ascFlag = "1".equals(String.valueOf(params.get("sort")));
//        // 处理数据
//        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, totalNumMap, ascFlag);
//        return resultMap;
//    }
    
    @Override
    public Map<String, List<Object>> efficiencyByCountry(Map<String, Object> params) {
        // 获取各个国家的总销售额数据
        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByCountry(params);
        // 获取各个国家的平均销售人员
        List<Map<String, Object>> avgManNumInMonthByAreaList = salesmanNumsMapper.avgManNumInMonthByCountry(_changeDateToFirstDay(params));
        boolean ascFlag = "1".equals(String.valueOf(params.get("sort")));
        // 处理数据
        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, avgManNumInMonthByAreaList, ascFlag);
        return resultMap;
    }

    @Override
    public HSSFWorkbook exportEfficiencyByCountry(Map<String, Object> params) {
        Map<String, List<Object>> map = efficiencyByCountry(params);
        List<Object> headerList = map.get("nameList");
        List<Object> row01 = map.get("dataList");

        int headerListSize = headerList.size();
        int row01Size = row01.size();
        // 导出excel中要全部数据
//        if (params.get("sort") != null && "1".equals(String.valueOf(params.get("sort")))) {
//            headerList = headerList.subList(headerListSize > 10 ? headerListSize - 10 : 0, headerListSize);
//            row01 = row01.subList(row01Size > 10 ? row01Size - 10 : 0, row01Size);
//        } else {
//            headerList = headerList.subList(0, headerListSize >= 10 ? 10 : headerListSize);
//            row01 = row01.subList(0, row01Size >= 10 ? 10 : row01Size);
//        }


        headerList.add(0, "");
        row01.add(0, "万美元");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "人均效能统计-国家统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "人均效能统计-国家统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    @Override
    public Map<String, List<Object>> efficiencyByOrg(Map<String, Object> params) {
        // 获取各个事业部的总销售额数据
        List<Map<String, Object>> orderTotalPriceList = memberInfoStatisticsMapper.orderTotalPriceByOrg(params);
        // 获取各个事业部的平均销售人员
        List<Map<String, Object>> avgManNumInMonthByAreaList = salesmanNumsMapper.avgManNumInMonthByOrg(_changeDateToFirstDay(params));
        boolean ascFlag = "1".equals(String.valueOf(params.get("sort")));
        // 处理数据
        Map<String, List<Object>> resultMap = _handleEfficiencyData(orderTotalPriceList, avgManNumInMonthByAreaList, ascFlag);
        return resultMap;
    }

    @Override
    public HSSFWorkbook exportEfficiencyByOrg(Map<String, Object> params) {
        Map<String, List<Object>> map = efficiencyByOrg(params);
        List<Object> headerList = map.get("nameList");
        List<Object> row01 = map.get("dataList");

        headerList.add(0, "");
        row01.add(0, "万美元");

        // 填充数据
        List<Object[]> rowList = new ArrayList<>();
        rowList.add(row01.toArray());

        // 生成excel并返回
        BuildExcel buildExcel = new BuildExcelImpl();
        HSSFWorkbook workbook = buildExcel.buildExcel(rowList, headerList.toArray(new String[headerList.size()]), null,
                "人均效能统计-事业部统计");
        // 设置样式
        ExcelCustomStyle.setHeadStyle(workbook, 0, 0);
        ExcelCustomStyle.setContextStyle(workbook, 0, 1, -1);
        // 如果要加入标题
        ExcelCustomStyle.insertRow(workbook, 0, 0, 1);
        ExcelCustomStyle.insertTitle(workbook, 0, 0, 0, "人均效能统计-事业部统计（" + params.get("startTime") + "-" + params.get("endTime") + "）");
        return workbook;
    }

    
    /**
     * 处理会员等级数据
     *
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleMemberGradeData(List<Map<String, Object>> gradeList) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> normalGradeList = new ArrayList<>();
        List<Object> topGradeList = new ArrayList<>();
        for (Map<String, Object> map : gradeList) {
            Object name = map.get("name");
            BigDecimal normalGrade = (BigDecimal) map.get("normalGrade");
            BigDecimal topGrade = (BigDecimal) map.get("topGrade");
            nameList.add(name);
            normalGradeList.add(normalGrade.intValue());
            topGradeList.add(topGrade.intValue());
        }
        result.put("nameList", nameList);
        result.put("normalGradeList", normalGradeList);
        result.put("topGradeList", topGradeList);
        return result;
    }

    /**
     * 优化，处理客户拜访统计数据
     * 人均客户拜访次数的公式修改为：周期内拜访总次数/周期内该维度的 平均人数
     * 人数平均值为时间维度跨度对应的人数除以时间相关的月份个数（例如选择2.28-3.15，取二月人数与三月人数的平均值）
     * 
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleNewVisitStatisticsData(List<Map<String, Object>> gradeList,List<Map<String, Object>> avgManNumInMonthList) {
        
    	// 遍历平均销售人数集合，将名称放到名称列表中，并将集合转换成 名称为key，元素为value的map
        Map<String, Map<String, Object>> avgManNumInMonthByAreaMap = avgManNumInMonthList.stream().collect(Collectors.toMap(vo -> {
            String name = (String) vo.get("name");
            return name;
        }, vo -> vo));
    	
    	Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> numList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (Map<String, Object> map : gradeList) {
            // 获取名称
        	Object name = map.get("name");
            // 获取该名称下的拜访总数
        	BigDecimal num = (BigDecimal) map.get("num");
            // 获取该名称下的平均销售人员数量
            Map<String, Object> avgManNumMap = avgManNumInMonthByAreaMap.get(name);
            // 名称 放到输出列表中
            nameList.add(name);
            // 总数放到输出列表中
            numList.add(num);
            
            // 如果该名称下的有平均销售人员数量，则计算人均客户拜访次数的=周期内拜访总次数/周期内该维度的 平均人数；
            if (avgManNumMap != null) {
            	// 获取平均销售人数
            	BigDecimal avgNum = (BigDecimal) avgManNumMap.get("avgNum");
                // 计算人均客户拜访次数的=周期内拜访总次数/周期内该维度的 平均人数；
            	avgList.add(num.divide(avgNum,2, BigDecimal.ROUND_DOWN));
           
            // 该名称下没有平均人数，则人均客户拜访次数 为0
            } else {
                avgList.add(BigDecimal.ZERO);
            }
        }
        result.put("nameList", nameList);
        result.put("numList", numList);
        result.put("avgList", avgList);
        return result;
    }

    /**
     * 处理会员等级数据
     *
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleVisitStatisticsData(List<Map<String, Object>> gradeList) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> numList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (Map<String, Object> map : gradeList) {
            Object name = map.get("name");
            Long num = (Long) map.get("num");
            BigDecimal avg = (BigDecimal) map.get("avgP");
            nameList.add(name);
            numList.add(num.longValue());
            if (avg != null) {
                avgList.add(avg.setScale(2, BigDecimal.ROUND_DOWN));
            } else {
                avgList.add(BigDecimal.ZERO);
            }
        }
        result.put("nameList", nameList);
        result.put("numList", numList);
        result.put("avgList", avgList);
        return result;
    }

    /**
     * 处理签约主体数据
     *
     * @param signingBodyList
     * @return
     */
    private Map<String, List<Object>> _handleSigningBodyData(List<Map<String, Object>> signingBodyList) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> eruiNumList = new ArrayList<>();
        List<Object> otherNumList = new ArrayList<>();
        for (Map<String, Object> map : signingBodyList) {
            Object name = map.get("name");
            Integer eruiNum = ((BigDecimal) map.get("eruiNum")).intValue();
            Integer otherNum = ((BigDecimal) map.get("otherNum")).intValue();
            nameList.add(name == null ? "其他" : name);
            eruiNumList.add(eruiNum);
            otherNumList.add(otherNum);
        }
        result.put("nameList", nameList);
        result.put("eruiNumList", eruiNumList);
        result.put("otherNumList", otherNumList);
        return result;
    }

//    /**
//     * 处理人均效能统计数据
//     *
//     * @param orderTotalPriceList
//     * @param totalNumMap
//     * @param ascFlag             true:升序  false:降序
//     * @return
//     */
//    private Map<String, List<Object>> _handleEfficiencyData(List<Map<String, Object>> orderTotalPriceList, Map<String, Map<String, Object>> totalNumMap, boolean ascFlag) {
//        Set<String> areaNameList = new HashSet<>();
//        areaNameList.addAll(totalNumMap.keySet());
//
//        Map<String, Map<String, Object>> orderTotalPriceMap = orderTotalPriceList.stream().collect(Collectors.toMap(vo -> {
//            String name = (String) vo.get("name");
//            areaNameList.add(name);
//            return name;
//        }, vo -> vo));
//
//        int totalNum = 0;
//        BigDecimal totalData = BigDecimal.ZERO;
//        BigDecimal tenThousand = new BigDecimal(10000); // 单位是美元，通过除以10000转换为万美元
//        List<Object[]> list = new ArrayList<>();
//        for (String areaName : areaNameList) {
//            Map<String, Object> numMap = totalNumMap.get(areaName);
//            Map<String, Object> priceMap = orderTotalPriceMap.get(areaName);
//            int n = 0;
//            Object[] objArr = new Object[2];
//            objArr[0] = areaName;
//            if (numMap != null && priceMap != null) {
//                Integer numDayNum = (Integer) numMap.get("dayNum");
//                BigDecimal salesManNum = (BigDecimal) numMap.get("salesManNum");
//                Long priceDayNum = (Long) priceMap.get("dayNum");
//                BigDecimal totalPriceUsd = (BigDecimal) priceMap.get("totalPriceUsd");
//                if (salesManNum != null && salesManNum.compareTo(BigDecimal.ZERO) > 0) {
//                    BigDecimal perTotalPrice = totalPriceUsd.divide(new BigDecimal(priceDayNum), 2, BigDecimal.ROUND_DOWN);
//                    BigDecimal perNum = salesManNum.divide(new BigDecimal(numDayNum), 2, BigDecimal.ROUND_DOWN);
//                    BigDecimal nengXiao = perTotalPrice.divide(perNum, 0, BigDecimal.ROUND_DOWN).divide(tenThousand, 2, BigDecimal.ROUND_DOWN);
//                    objArr[1] = nengXiao;
//                    if (!nengXiao.equals(BigDecimal.ZERO)) {
//                        // 求平均能效的中介值，0的不算其中
//                        totalNum++;
//                        totalData = totalData.add(nengXiao);
//                    }
//                } else {
//                    objArr[1] = BigDecimal.ZERO; // 销售人员为0，能效为0%
//                }
//            } else {
//                objArr[1] = BigDecimal.ZERO; // 如果计划销售人员数量为空或完成金额为空，则效能为0%
//            }
//            list.add(objArr);
//        }
//
//        list.sort(new Comparator<Object[]>() {
//            @Override
//            public int compare(Object[] o1, Object[] o2) {
//                BigDecimal bd01 = (BigDecimal) o1[1];
//                BigDecimal bd02 = (BigDecimal) o2[1];
//                if (ascFlag) {
//                    return bd01.compareTo(bd02);
//                } else {
//                    return -bd01.compareTo(bd02);
//                }
//            }
//        });
//
//        List<Object> nameList = new ArrayList<>();
//        List<Object> dataList = new ArrayList<>();
//        list.stream().forEach(vo -> {
//            nameList.add((String) vo[0]);
//            dataList.add((BigDecimal) vo[1]);
//        });
////        nameList.add("平均");
////        dataList.add(totalNum == 0 ? BigDecimal.ZERO : totalData.divide(new BigDecimal(totalNum), 2, BigDecimal.ROUND_DOWN));
//        Map<String, List<Object>> result = new HashMap<>();
//        result.put("nameList", nameList);
//        result.put("dataList", dataList);
//
//        return result;
//    }

    /**
     * 处理人均效能统计数据
     *
     * @param orderTotalPriceList 总销售额集合
     * @param avgManNumInMonthByAreaList 平均销售人数集合
     * @param ascFlag             true:升序  false:降序
     * @return
     */
    private Map<String, List<Object>> _handleEfficiencyData(List<Map<String, Object>> orderTotalPriceList, List<Map<String, Object>> avgManNumInMonthByAreaList, boolean ascFlag) {
        // 名称集合
    	Set<String> nameList = new HashSet<>();

    	// 遍历总销售额集合，将名称放到名称列表中，并将集合转换成 名称为key，元素为value的map
        Map<String, Map<String, Object>> orderTotalPriceMap = orderTotalPriceList.stream().collect(Collectors.toMap(vo -> {
            String name = (String) vo.get("name");
            nameList.add(name);
            return name;
        }, vo -> vo));
        
        // 遍历平均销售人数集合，将名称放到名称列表中，并将集合转换成 名称为key，元素为value的map
        Map<String, Map<String, Object>> avgManNumInMonthByAreaMap = avgManNumInMonthByAreaList.stream().collect(Collectors.toMap(vo -> {
            String name = (String) vo.get("name");
            nameList.add(name);
            return name;
        }, vo -> vo));

        // 单位是美元，通过除以10000转换为万美元
        BigDecimal tenThousand = new BigDecimal(10000); 
        // 名称 效能 结果集合
        List<Object[]> nameAndEfficacyList = new ArrayList<>();
        // 遍历名称集合，计算效能，并封装到结合集合中
        for (String name : nameList) {
            // 获取该名称下的销售总额
        	Map<String, Object> priceMap = orderTotalPriceMap.get(name);
        	// 获取该名称下的平均销售人员数量
            Map<String, Object> avgManNumMap = avgManNumInMonthByAreaMap.get(name);
            
            int n = 0;
            // 名称 效能 数组，作为结果集的元素
            Object[] objArr = new Object[2];
            // 数组第一个元素 为 名称
            objArr[0] = name;
            // 计算数组第二个元素  效能
            // 如果有销售总额和平均销售人数，则进行计算效能，否则效能为0
            if (priceMap != null && avgManNumMap != null) {
                // 获取平均销售人数
            	BigDecimal avgNum = (BigDecimal) avgManNumMap.get("avgNum");
                // 获取销售总额
            	BigDecimal totalPriceUsd = (BigDecimal) priceMap.get("totalPriceUsd");
                // 如果平均销售人数大于零，则进行计算效能，否则效能为0
            	if (avgNum != null && avgNum.compareTo(BigDecimal.ZERO) > 0) {
                    // 能效（万美元） = 销售总额（万美元）/平均销售人数
            		BigDecimal efficacy = totalPriceUsd.divide(tenThousand).divide(avgNum,2, BigDecimal.ROUND_DOWN);
                    objArr[1] = efficacy;
                } else {
                	// 销售人员为0，能效为0 
                    objArr[1] = BigDecimal.ZERO; 
                }
            } else {
            	// 如果 销售总额为空或平均销售人数为空，则效能为0 
                objArr[1] = BigDecimal.ZERO; 
            }
            nameAndEfficacyList.add(objArr);
        }

        // 名称 效能 结果集合 按效能排序
        nameAndEfficacyList.sort(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                BigDecimal bd01 = (BigDecimal) o1[1];
                BigDecimal bd02 = (BigDecimal) o2[1];
                if (ascFlag) {
                    return bd01.compareTo(bd02);
                } else {
                    return -bd01.compareTo(bd02);
                }
            }
        });
        // 排序后的名称集合
        List<Object> nameSortedList = new ArrayList<>();
        // 排序后的效能集合
        List<Object> efficacySortedList = new ArrayList<>();
        nameAndEfficacyList.stream().forEach(vo -> {
        	nameSortedList.add((String) vo[0]);
        	efficacySortedList.add((BigDecimal) vo[1]);
        });
        Map<String, List<Object>> result = new HashMap<>();
        result.put("nameList", nameSortedList);
        result.put("dataList", efficacySortedList);

        return result;
    }
    
    /**
     * 将开始时间 和 结束时间 的日期格式转换成yyyy-MM-01
     * @param params
     * @return
     */
    private Map<String, Object>  _changeDateToFirstDay(Map<String, Object> params) {
    	Map<String, Object> res  = new HashMap<String, Object>();
    	// 获取日期，并处理日期格式
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        
        // 转换
        String[] startSplit = startTime.split("-");
        startTime = startSplit[0]+"-"+startSplit[1]+"-01";
        String[] endSplit = endTime.split("-");
        endTime = endSplit[0]+"-"+endSplit[1]+"-01";
        
        // 封装
        res.put("startTime", startTime);
        res.put("endTime", endTime);
        
        return res;
    }
}
