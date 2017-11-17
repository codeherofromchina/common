package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.model.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.SupplyChainMapper;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class SupplyChainServiceImpl extends BaseService<SupplyChainMapper> implements SupplyChainService {
    private final static Logger logger = LoggerFactory.getLogger(RequestCreditServiceImpl.class);

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:53 2017/11/14
     * @modified By
     */
    @Override
    public Date selectStart() {
        return this.readMapper.selectStart();
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:53 2017/11/14
     * @modified By
     */
    @Override
    public Date selectEnd() {
        return this.readMapper.selectEnd();
    }

    /**
     * @Author:SHIGS
     * @Description 查询 spu sku 供应商完成量
     * @Date:16:02 2017/10/21
     * @modified By
     */
    @Override
    public Map<String, Object> selectFinishByDate(Date startTime, Date endTime, String type) {
        int days = 0;
        SupplyChainExample supplyChainExample = new SupplyChainExample();
        supplyChainExample.setOrderByClause("create_at asc");
        List<Map> supplyMap = null;
        if (startTime != null && endTime != null) {
            days = DateUtil.getDayBetween(startTime, endTime);
            supplyChainExample.createCriteria().andCreateAtBetween(startTime, endTime);
            supplyMap = this.readMapper.selectFinishByDate(supplyChainExample);
        } else {
            supplyMap = this.readMapper.selectFinishByDate(supplyChainExample);
            startTime = (Date) supplyMap.get(0).get("create_at");
            endTime = (Date) supplyMap.get(supplyMap.size() - 1).get("create_at");
            days = DateUtil.getDayBetween(startTime, endTime);
        }
        List<Integer> spuList = new ArrayList<>();
        List<Integer> skuList = new ArrayList<>();
        List<Integer> supplierList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        Map<String, Map<String, Integer>> sqlDate = new HashMap<>();
        Map<String, Integer> lintData = null;
        for (Map map2 : supplyMap) {
            BigDecimal spu = new BigDecimal(map2.get("finish_spu_num").toString());
            BigDecimal sku = new BigDecimal(map2.get("finish_sku_num").toString());
            BigDecimal supplier = new BigDecimal(map2.get("finish_suppli_num").toString());
            Date date2 = (Date) map2.get("create_at");
            String dateString = DateUtil.format("MM月dd日", date2);
            if (sqlDate.containsKey(dateString)) {
                Map<String, Integer> map = sqlDate.get(dateString);
                Integer spu2 = map.get("spu");
                Integer sku2 = map.get("sku");
                Integer supplier2 = map.get("supplier");
                map.put("spu", spu.intValue() + spu2);
                map.put("sku", sku.intValue() + sku2);
                map.put("supplier", supplier.intValue() + supplier2);
            } else {
                lintData = new HashMap<>();
                lintData.put("spu", spu.intValue());
                lintData.put("sku", sku.intValue());
                lintData.put("supplier", supplier.intValue());
                sqlDate.put(dateString, lintData);
            }
        }
        for (int i = 0; i < days; i++) {
            Date datetime = DateUtil.sometimeCalendar(startTime, -i);
            String date = DateUtil.format("MM月dd日", datetime);
            if (sqlDate.containsKey(date)) {
                dateList.add(date);
                spuList.add(sqlDate.get(date).get("spu"));
                skuList.add(sqlDate.get(date).get("sku"));
                supplierList.add(sqlDate.get(date).get("supplier"));
            } else {
                dateList.add(date);
                spuList.add(0);
                skuList.add(0);
                supplierList.add(0);
            }
        }
        String[] s = {"SPU完成量", "SKU完成量", "供应商完成量"};
        Map<String, Object> data = new HashMap<>();
        if (type.equals("spu")) {
            data.put("legend", s[0]);
            data.put("xAxis", dateList);
            data.put("yAxis", spuList);

        } else if (type.equals("sku")) {
            data.put("legend", s[1]);
            data.put("xAxis", dateList);
            data.put("yAxis", skuList);
        } else {
            data.put("legend", s[2]);
            data.put("xAxis", dateList);
            data.put("yAxis", supplierList);
        }
        return data;
    }

    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse(
                new String[]{"planSkuNum", "finishSkuNum", "planSpuNum",
                        "finishSpuNum", "planSuppliNum", "finishSuppliNum"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        SupplyChain sc = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);

            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.SUPPLY_CHAIN, response, cellIndex)) {
                continue;
            }
            sc = new SupplyChain();

            try {
                sc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d hh:mm:ss",
                        DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "日期字段格式错误");
                continue;
            }
            sc.setOrganization(strArr[1]);
            sc.setCategory(strArr[2]);
            sc.setItemClass(strArr[3]);

            try {
                sc.setPlanSkuNum(new BigDecimal(strArr[4]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "计划SKU字段非数字");
                continue;
            }
            try {
                sc.setFinishSkuNum(new BigDecimal(strArr[5]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "SKU实际完成字段非数字");
                continue;
            }
            try {
                sc.setPlanSpuNum(new BigDecimal(strArr[6]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "计划SPU字段非数字");
                continue;
            }
            try {
                sc.setFinishSpuNum(new BigDecimal(strArr[7]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "SPU实际完成字段非数字");
                continue;
            }
            try {
                sc.setPlanSuppliNum(new BigDecimal(strArr[8]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "计划供应商数量非数字");
                continue;
            }
            try {
                sc.setFinishSuppliNum(new BigDecimal(strArr[9]).intValue());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, "供应商数量实际开发字段非数字");
                continue;
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(sc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.SUPPLY_CHAIN.getTable(), cellIndex, e.getMessage());
                continue;
            }
            if (NewDateUtil.inSaturdayWeek(sc.getCreateAt())) {
                response.sumData(sc);
            }
            response.incrSuccess();

        }
        response.setDone(true);

        return response;
    }

    @Override
    public List<SupplyChain> queryListByDate(Date startTime, Date endTime) {
        SupplyChainExample example = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = example.createCriteria();
        if (startTime != null ) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        return readMapper.selectByExample(example);
    }

    // 事业部-供应链明细列表
    @Override
    public List<SuppliyChainOrgVo> selectOrgSuppliyChain(Date startTime, Date endTime) {
        SupplyChainExample example = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = example.createCriteria();
        if (startTime != null ) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        List<SuppliyChainOrgVo> list = readMapper.selectOrgSuppliyChainByExample(example);
        return list;
    }

    @Override
    public List<SuppliyChainItemClassVo> selectItemCalssSuppliyChain(Date startTime, Date endTime) {
        SupplyChainExample supplyChainExample = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
        if (startTime != null ) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        return readMapper.selectItemCalssSuppliyChainByExample(supplyChainExample);
    }

    @Override
    public SuppliyChainItemClassVo selectSuppliyChainByItemClass(Date startTime, Date endTime, String itemClass) {
        SupplyChainExample supplyChainExample = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
        if (startTime != null ) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        if (StringUtils.isNotBlank(itemClass)) {
            criteria.andItemClassEqualTo(itemClass);
        }
        return readMapper.selectSuppliyChainByItemClassByExample(supplyChainExample);
    }

    @Override
    public List<SuppliyChainCateVo> selectCateSuppliyChain(Date startTime, Date endTime) {
        SupplyChainExample supplyChainExample = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
        if (startTime != null ) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        return readMapper.selectCateSuppliyChainByExample(supplyChainExample);
    }

    // 供应链趋势图
    @Override
    public SupplyTrendVo supplyTrend(Date startTime, Date endTime, String type) {
        int days = DateUtil.getDayBetween(startTime, endTime);
        SupplyChainExample example = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = example.createCriteria();
        if (startTime != null ) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        List<SupplyChain> list = readMapper.selectByExample(example);

        String[] DateTime = new String[days];
        Integer[] suppliyFinishCount = new Integer[days];
        Integer[] SPUFinishCount = new Integer[days];
        Integer[] SKUFinishCount = new Integer[days];

        if (list != null && list.size() > 0) {
            Map<String, Map<String, Integer>> dateMap = new HashMap<>();
            Map<String, Integer> datamap;
            for (int i = 0; i < list.size(); i++) {
                String date2 = DateUtil.formatDate2String(list.get(i).getCreateAt(), "MM月dd日");
                if (dateMap.containsKey(date2)) {
                    Map<String, Integer> map = dateMap.get(date2);
                    Integer sku = map.get("sku");
                    Integer spu = map.get("spu");
                    Integer suppliy = map.get("suppliy");
                    map.put("sku", sku + list.get(i).getFinishSkuNum());
                    map.put("spu", spu + list.get(i).getFinishSpuNum());
                    map.put("suppliy", suppliy + sku + list.get(i).getFinishSuppliNum());

                } else {
                    datamap = new HashMap<>();
                    datamap.put("sku", list.get(i).getFinishSkuNum());
                    datamap.put("spu", list.get(i).getFinishSpuNum());
                    datamap.put("suppliy", list.get(i).getFinishSuppliNum());
                    dateMap.put(date2, datamap);
                }

            }
            for (int i = 0; i < days; i++) {
                Date date = DateUtil.sometimeCalendar(startTime, -i);
                String datet2 = DateUtil.format("MM月dd日", date);
                if (dateMap.containsKey(datet2)) {
                    DateTime[i] = (datet2);
                    SPUFinishCount[i] = (dateMap.get(datet2).get("spu"));
                    SKUFinishCount[i] = (dateMap.get(datet2).get("sku"));
                    suppliyFinishCount[i] = (dateMap.get(datet2).get("suppliy"));
                } else {
                    DateTime[i] = (datet2);
                    SPUFinishCount[i] = (0);
                    SKUFinishCount[i] = (0);
                    suppliyFinishCount[i] = (0);
                }
            }

        } else {
            for (int i = 0; i < days; i++) {
                Date date = DateUtil.sometimeCalendar(startTime, -i);
                String datet2 = DateUtil.format("MM月dd日", date);
                DateTime[i] = datet2;
                suppliyFinishCount[i] = 0;
                SPUFinishCount[i] = 0;
                SKUFinishCount[i] = 0;
            }
        }
        SupplyTrendVo trend = new SupplyTrendVo(DateTime, suppliyFinishCount, SPUFinishCount, SKUFinishCount);

        return trend;
    }

    @Override
    public List<String> selectCategoryList() {
        return readMapper.selectCategoryList();
    }

    @Override
    public List<String> selectOrgList() {
        return readMapper.selectOrgList();
    }
}