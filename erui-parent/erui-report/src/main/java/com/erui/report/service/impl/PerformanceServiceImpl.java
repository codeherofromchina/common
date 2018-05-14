package com.erui.report.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.PerformanceAssignMapper;
import com.erui.report.dao.PerformanceCountMapper;
import com.erui.report.model.PerformanceAssign;
import com.erui.report.model.PerformanceCount;
import com.erui.report.service.PerformanceService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.InquiryAreaVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PerformanceServiceImpl extends BaseService<PerformanceCountMapper> implements PerformanceService {

    private final static Logger logger = LoggerFactory.getLogger(PerformanceServiceImpl.class);

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {

        ImportDataResponse response = new ImportDataResponse();
        Map<String, BigDecimal> sumMap = new HashMap<>();//和数据
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        PerformanceCount pc = null;
        for (int index = 0; index < datas.size(); index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.SALES_PERFORMANCE, response, cellIndex)) {
                continue;
            }
            pc = new PerformanceCount();

            if (strArr[0] != null) {
                try {
                    pc.setSerialNumber(new BigDecimal(strArr[0]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "序号非数字");
                    continue;
                }
            }

            pc.setExecuteNum(strArr[1]);
            pc.setOverseasSalesNum(strArr[2]);
            pc.setSignCompany(strArr[3]);
            pc.setNotEruiReason(strArr[4]);
            pc.setIsThroughAgent(strArr[5]);
            pc.setAgentCode(strArr[6]);
            pc.setOrganization(strArr[7]);
            pc.setExecuteCompany(strArr[8]);
            pc.setArea(strArr[9]);
            pc.setBuyerCode(strArr[10]);
            pc.setGoodsCh(strArr[11]);
            pc.setCategory(strArr[12]);

            if (strArr[13] != null) {
                try {
                    pc.setGoodsCount(new BigDecimal(strArr[13]).intValue());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "商品数量非数字");
                    continue;
                }
            }

            pc.setUnit(strArr[14]);

            if (strArr[15] != null) {
                try {
                    pc.setProjectAmount(new BigDecimal(strArr[15]));
                    if (strArr[8] != null) { // 统计各个国家的总业绩
                        if (sumMap.containsKey(strArr[8])) {
                            sumMap.put(strArr[8], sumMap.get(strArr[8]).add(new BigDecimal(strArr[15])));
                        } else {
                            sumMap.put(strArr[8], new BigDecimal(strArr[15]));
                        }
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "项目金额(美元)非数字");
                    continue;
                }
            }

            if (strArr[16] != null) {
                try {
                    pc.setInitialProfitMargin(new BigDecimal(strArr[16]));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.PROCUREMENT_COUNT.getTable(), cellIndex, "初步利润率非数字");
                    continue;
                }
            }

            pc.setReceivedMode(strArr[17]);

            if (strArr[18] != null) {
                try {
                    Date date = DateUtil.parseString2Date(strArr[18], DateUtil.SHORT_FORMAT_STR, "yyyy/M/d", "yyyy/M/d hh:mm:ss", DateUtil.FULL_FORMAT_STR);
                    //判断 日期是否是之前的日期
                    Date endTime = readMapper.selectEndTime();
                    if (endTime != null) {
                        if (date.getTime() < endTime.getTime()) {
                            response.setTotal(datas.size());
                            response.setFail(datas.size());
                            response.setSuccess(0);
                            response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), cellIndex, "数据库已经有比该日期更近的数据");
                            response.setDone(false);
                            return response;
                        }
                    }
                    pc.setStartTime(date);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.ORDER_COUNT.getTable(), cellIndex, "开始时间格式错误");
                    continue;
                }
            }

            pc.setObtainMainCompany(strArr[19]);
            pc.setObtainer(strArr[20]);
            pc.setMarketer(strArr[21]);
            pc.setBusinesser(strArr[22]);
            pc.setRemark(strArr[23]);

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(pc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SALES_PERFORMANCE.getTable(), cellIndex, e.getMessage());
                continue;
            }


            response.incrSuccess();
        }
        response.setSumMap(sumMap);
        response.setDone(true);
        return response;
    }

    @Override
    public List<String> selectDateList() {
        return readMapper.selectDateList();
    }

    @Override
    public List<InquiryAreaVO> selectAllAreaAndCountryList() {

        List<InquiryAreaVO> result = new ArrayList<>();
        //查询所有大区和国家
        List<Map<String, String>> areaAndCountryList = readMapper.selectAllAreaAndCountryList();
        InquiryCountServiceImpl inqService = new InquiryCountServiceImpl();
        inqService.coverAreaAndCountryData(result, areaAndCountryList, "country");
        return result;
    }

    @Override
    public List<Map<String, Object>> selectIncrBuyerDetail(Map<String, String> params) {

        List<Map<String, Object>> data = readMapper.selectIncrBuyerByCondition(params);
        Map<String, Map<String, Object>> resultMap = new HashMap<>(); //返回结果集
        //如果 area 为空 country 为空
        if (StringUtils.isEmpty(params.get("area"))) {
            data.stream().forEach(m -> {
                String area = m.get("area").toString();
                if (resultMap.containsKey(area)) {
                    Map<String, Object> rMap = resultMap.get(area);
                    List<String> buyerCodeList = (List<String>) rMap.get("buyerCodeList");
                    buyerCodeList.add(m.get("buyerCode").toString());
                    rMap.put("incrBuyerCount", buyerCodeList.size());
                } else {
                    Map<String, Object> rm = new HashMap<>();
                    rm.put("area", m.get("area").toString());
                    rm.put("country", "全部");
                    rm.put("month", params.get("month"));
                    List<String> buyerList = new ArrayList<>();
                    buyerList.add(m.get("buyerCode").toString());
                    rm.put("incrBuyerCount", buyerList.size());
                    rm.put("buyerCodeList", buyerList);
                    resultMap.put(area, rm);
                }
            });
        } else {
            data.stream().forEach(m -> {
                String country = m.get("country").toString();
                if (resultMap.containsKey(country)) {
                    Map<String, Object> rMap = resultMap.get(country);
                    List<String> buyerCodeList = (List<String>) rMap.get("buyerCodeList");
                    buyerCodeList.add(m.get("buyerCode").toString());
                    rMap.put("incrBuyerCount", buyerCodeList.size());
                } else {
                    Map<String, Object> rm = new HashMap<>();
                    rm.put("area", params.get("area"));
                    rm.put("country", m.get("country").toString());
                    rm.put("month", params.get("month"));
                    List<String> buyerList = new ArrayList<>();
                    buyerList.add(m.get("buyerCode").toString());
                    rm.put("incrBuyerCount", buyerList.size());
                    rm.put("buyerCodeList", buyerList);
                    resultMap.put(country, rm);
                }
            });
        }
        return new ArrayList<>(resultMap.values());
    }

    @Override
    public List<Map<String, Object>> obtainerPerformance(Map<String, String> params) {

        //如果没有分配信息 查询源数据
        List<Map<String, Object>> data = readMapper.selectObtainerPerformance(params);
        Map<String, Map<String, Object>> resultMap = new HashMap<>(); //返回结果集
        //如果 area 为空 country 为空
        if (StringUtils.isEmpty(params.get("area")) && StringUtils.isEmpty(params.get("country"))) {
            data.stream().forEach(m -> {
                String area = m.get("area").toString();
                if (resultMap.containsKey(area)) {
                    Map<String, Object> rMap = resultMap.get(area);
                    double totalPerformance = Double.parseDouble(rMap.get("totalPerformance").toString()) +
                            Double.parseDouble(m.get("totalPerformance").toString());
                    double eruiPerformance = Double.parseDouble(rMap.get("eruiPerformance").toString()) +
                            Double.parseDouble(m.get("eruiPerformance").toString());
                    double otherPerformance = Double.parseDouble(rMap.get("otherPerformance").toString()) +
                            Double.parseDouble(m.get("otherPerformance").toString());
                    rMap.put("totalPerformance", RateUtil.doubleChainRateTwo(totalPerformance, 1));
                    rMap.put("eruiPerformance", RateUtil.doubleChainRateTwo(eruiPerformance, 1));
                    rMap.put("otherPerformance", RateUtil.doubleChainRateTwo(otherPerformance, 1));
                } else {

                    m.put("country", "全部");
                    m.put("employee", "全部");
                    m.put("month", params.get("month"));
                    m.put("job", "--");
                    m.put("totalPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("totalPerformance").toString()),
                            1));
                    m.put("eruiPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("eruiPerformance").toString()),
                            1));
                    m.put("otherPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("otherPerformance").toString()),
                            1));
                    resultMap.put(area, m);
                }
            });
            return new ArrayList<>(resultMap.values());
        } else if (StringUtils.isNotEmpty(params.get("area")) && StringUtils.isEmpty(params.get("country"))) {
            data.stream().forEach(m -> {
                String country = m.get("country").toString();
                if (resultMap.containsKey(country)) {
                    Map<String, Object> rMap = resultMap.get(country);
                    double totalPerformance = Double.parseDouble(rMap.get("totalPerformance").toString()) +
                            Double.parseDouble(m.get("totalPerformance").toString());
                    double eruiPerformance = Double.parseDouble(rMap.get("eruiPerformance").toString()) +
                            Double.parseDouble(m.get("eruiPerformance").toString());
                    double otherPerformance = Double.parseDouble(rMap.get("otherPerformance").toString()) +
                            Double.parseDouble(m.get("otherPerformance").toString());
                    rMap.put("totalPerformance", RateUtil.doubleChainRateTwo(totalPerformance, 1));
                    rMap.put("eruiPerformance", RateUtil.doubleChainRateTwo(eruiPerformance, 1));
                    rMap.put("otherPerformance", RateUtil.doubleChainRateTwo(otherPerformance, 1));
                } else {
                    m.put("employee", "全部");
                    m.put("month", params.get("month"));
                    m.put("job", "--");
                    m.put("totalPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("totalPerformance").toString()),
                            1));
                    m.put("eruiPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("eruiPerformance").toString()),
                            1));
                    m.put("otherPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("otherPerformance").toString()),
                            1));
                    resultMap.put(country, m);
                }
            });

            data.stream().forEach(m -> {
                m.put("totalPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("totalPerformance").toString()),
                        1));
                m.put("eruiPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("eruiPerformance").toString()),
                        1));
                m.put("otherPerformance", RateUtil.doubleChainRateTwo(Double.parseDouble(m.get("otherPerformance").toString()),
                        1));
            });
            return new ArrayList<>(resultMap.values());
        }

        //如果 区域和国家都不为空  ，查询该国家业绩是否被分配
        PerformanceAssignMapper assignMapper = readerSession.getMapper(PerformanceAssignMapper.class);
        List<PerformanceAssign> dataList = assignMapper.selectCountryAssignDetailByTime(params);
        if (CollectionUtils.isNotEmpty(dataList)) {
            if (dataList.get(0).getAssignStatus() > 0 && dataList.get(0).getAssignRate() != null) {
                List<Map<String, Object>> ll = new ArrayList<>();
                for (PerformanceAssign p : dataList) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("area", p.getTwoLevelDepartment());
                    m.put("country", p.getThreeLevelDepartment());
                    m.put("employee", p.getNameCh());
                    m.put("job", p.getStation());
                    m.put("totalPerformance", p.getSalesmanPerformance());
                    m.put("eruiPerformance", p.getSalesmanPerformance());
                    m.put("otherPerformance", 0d);
                    m.put("month", params.get("month"));
                    ll.add(m);
                }
                return ll;
            }
        }
        //如果 区域和国家都不为空并且没有被分配
        data.stream().forEach(m -> {
            m.put("month", params.get("month"));
        });
        return data;
    }

    @Override
    public XSSFWorkbook exportIncrBuyer(List<Map<String, Object>> dataList) {
        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("新增会员统计表");
        sheet.setDefaultColumnWidth(10);
        //产生标题行
        XSSFRow row = sheet.createRow(0);
        String[] headers = new String[]{"地区", "国家", "月份", "当月新增会员数", "会员客户代码"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        //添加数据
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> m = dataList.get(i);
                XSSFRow row1 = sheet.createRow(i + 1);
                XSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(m.get("area").toString());
                XSSFCell cell1 = row1.createCell(1);
                cell1.setCellValue(m.get("country").toString());
                XSSFCell cell2 = row1.createCell(2);
                cell2.setCellValue(m.get("month").toString());
                XSSFCell cell3 = row1.createCell(3);
                cell3.setCellValue(m.get("incrBuyerCount").toString());
                XSSFCell cell4 = row1.createCell(4);
                List<String> buyerCodeList = (List<String>) m.get("buyerCodeList");
                if (CollectionUtils.isNotEmpty(buyerCodeList)) {
                    StringBuffer sb = new StringBuffer();
                    for (String buyer : buyerCodeList) {
                        sb.append(buyer + ",");
                    }
                    cell4.setCellValue(sb.substring(0, sb.length() - 1));
                }
            }

        }

        return wb;
    }

    @Override
    public XSSFWorkbook exportSalesPerformance(List<Map<String, Object>> dataList) {
        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("销售业绩统计表");
        sheet.setDefaultColumnWidth(10);
        sheet.setColumnWidth(7, 8192);
        sheet.setColumnWidth(6, 7000);
        sheet.setColumnWidth(5, 4521);
        //产生标题行
        XSSFRow row = sheet.createRow(0);
        String[] headers = new String[]{"地区", "国家", "人名", "岗位", "月份", "总业绩（万美元）",
                "易瑞签约业绩（万美元）", "其他事业部签约业绩（万美元）"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        //导入销售业绩数据
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> m = dataList.get(i);
                XSSFRow row1 = sheet.createRow(i + 1);
                XSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(m.get("area").toString());
                XSSFCell cell1 = row1.createCell(1);
                cell1.setCellValue(m.get("country").toString());
                XSSFCell cell2 = row1.createCell(2);
                cell2.setCellValue(m.get("employee").toString());
                XSSFCell cell3 = row1.createCell(3);
                cell3.setCellValue(m.get("job").toString());
                XSSFCell cell4 = row1.createCell(4);
                cell4.setCellValue(Integer.parseInt(m.get("month").toString()));
                XSSFCell cell5 = row1.createCell(5);
                cell5.setCellValue(Double.parseDouble(m.get("totalPerformance").toString()));
                XSSFCell cell6 = row1.createCell(6);
                cell6.setCellValue(Double.parseDouble(m.get("eruiPerformance").toString()));
                XSSFCell cell7 = row1.createCell(7);
                cell7.setCellValue(Double.parseDouble(m.get("otherPerformance").toString()));
            }
        }

        return wb;
    }

    @Override
    public List<String> selectCountryByUserId(Integer userId) {
        return readMapper.selectCountryByUserId(userId);
    }

    @Override
    public List<PerformanceAssign> countryAssignDetailByTime(Map<String, String> params) {
        //查询是否有此国家的分配信息
        PerformanceAssignMapper assignMapper = readerSession.getMapper(PerformanceAssignMapper.class);
        List<PerformanceAssign> data1 = assignMapper.selectCountryAssignDetailByTime(params);
        if (CollectionUtils.isNotEmpty(data1)) {
            if(data1.get(0).getAssignStatus()!=3){//如果不是已驳回状态 驳回理由置为空
                data1.stream().forEach(p->{
                    p.setRejectReason(null);
                });
            }
            return data1;
        }
        List<PerformanceAssign> salesmanList = readMapper.selectSalesmanByCountry(params.get("country"));
        //查询指定国家和月份的总业绩
        double totalPerformance = readMapper.selectTotalPerformanceByCountryAndTime(params);
        double total = RateUtil.doubleChainRateTwo(totalPerformance, 10000);//变成万美元
        salesmanList.stream().forEach(p -> {
            p.setCountryPerformance(new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP));
            p.setAssignStatus(0);
            p.setStartTime(DateUtil.parseString2DateNoException(params.get("startTime"), DateUtil.FULL_FORMAT_STR));
        });
        if(totalPerformance>0) {
            insertPerformanceAssign(salesmanList);
        }
        return salesmanList;
    }

    @Override
    public void insertPerformanceAssign(List<PerformanceAssign> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            PerformanceAssignMapper assignWriterMapper = writerSession.getMapper(PerformanceAssignMapper.class);
            for (PerformanceAssign p : dataList) {
                assignWriterMapper.insertSelective(p);
            }
        }
    }

    @Override
    public void updatePerformanceAssign(List<PerformanceAssign> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            PerformanceAssignMapper assignWriterMapper = writerSession.getMapper(PerformanceAssignMapper.class);
            dataList.stream().forEach(p -> {
                assignWriterMapper.updatePerformanceAssign(p);
            });
        }
    }

    @Override
    public List<PerformanceAssign> selectAuditingPerformanceByCountry(Map<String, String> params) {
        PerformanceAssignMapper assignMapper = readerSession.getMapper(PerformanceAssignMapper.class);
        return assignMapper.selectAuditingPerformanceByCountry(params);
    }

    @Override
    public void auditPerformance(Map<String, String> params) {
        PerformanceAssignMapper assignWriterMapper = writerSession.getMapper(PerformanceAssignMapper.class);
        assignWriterMapper.auditPerformance(params);
    }


}
