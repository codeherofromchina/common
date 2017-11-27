package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.model.HrCountExample;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.HrCountMapper;
import com.erui.report.model.HrCount;
import com.erui.report.service.HrCountService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

/**
 * Created by lirb on 2017/10/19.
 */

@Service
public class HrCountServiceImpl extends BaseService<HrCountMapper> implements HrCountService {
    private final static Logger logger = LoggerFactory.getLogger(HrCountServiceImpl.class);
    private DecimalFormat df = new DecimalFormat("0.0000");

    @Override
    public Date selectStart() {
        return readMapper.selectStart();
    }

    @Override
    public Date selectEnd() {
        return readMapper.selectEnd();
    }

    /**
     * @Author:SHIGS
     * @Description 总览战斗力
     * @Date:16:17 2017/10/25
     * @modified By
     */
    @Override
    public Map<String, Object> selectHrCount(Date startTime, Date endTime) {
        // 当前时期
        HrCountExample hrCountExample = new HrCountExample();
        HrCountExample.Criteria criteria = hrCountExample.createCriteria();
        // 即时数据
        HrCountExample hrCountExampleImediate = new HrCountExample();
        HrCountExample.Criteria criteriaImediate = hrCountExampleImediate.createCriteria();
        // 当前时段
        Map<String, Long> CurHrCountMap = null;
        if (startTime != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
            criteriaImediate.andCreateAtLessThan(endTime);
        }
        CurHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
        Map<String, Long> immediateMap = findImmediateNum(hrCountExampleImediate);
        if (CurHrCountMap == null) {
            CurHrCountMap = new HashMap<>();
            CurHrCountMap.put("turnRightCount", 0L);
            CurHrCountMap.put("dimissionCount", 0L);
            CurHrCountMap.put("groupTransferIn", 0L);
            CurHrCountMap.put("groupTransferOut", 0L);
        }

        Number planCount = immediateMap.get("plan_count");
        Number regularCount = immediateMap.get("regular_count");
        Number tryCount = immediateMap.get("try_count");
        Number turnRightCount = immediateMap.get("turn_right_count");
        Number dimissionCount = CurHrCountMap.get("dimissionCount");
        Number turnJobin = CurHrCountMap.get("groupTransferIn");
        Number turnJobout = CurHrCountMap.get("groupTransferOut");
        Map<String, Object> data = new HashMap<>();
        // 满编率
        double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
        // 转正率
        double turnRightRate = RateUtil.intChainRate(turnRightCount.intValue(), regularCount.intValue());
        data.put("staffFullRate", Double.parseDouble(df.format(staffFullRate)));
        //转正人员占比
        data.put("turnRightRate", Double.parseDouble(df.format(turnRightRate)));
        data.put("onDuty", regularCount);
        data.put("plan", planCount);
        data.put("turnJobin", turnJobin.intValue());
        data.put("turnJobout", turnJobout);
        data.put("try", tryCount);
        data.put("turnRight", turnRightCount);
        data.put("leave", dimissionCount);
        if (startTime != null && endTime != null && DateUtil.getDayBetween(startTime, endTime) > 0) {
            int days = DateUtil.getDayBetween(startTime, endTime);
            //环比开始
            Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
            // 环比时段
            if (chainEnd != null) {
                criteria.andCreateAtGreaterThanOrEqualTo(chainEnd);
            }
            if (startTime != null) {
                criteria.andCreateAtLessThan(startTime);
            }
            // 即时数据环比
            HrCountExample hrCountExampleImediate2 = new HrCountExample();
            HrCountExample.Criteria criteriaImediate2 = hrCountExampleImediate.createCriteria();
            criteriaImediate2.andCreateAtLessThanOrEqualTo(chainEnd);
            Map<String, Long> chainHrCountImmediateMap = findImmediateNum(hrCountExampleImediate2);
            // 环比人数
            Number chainRegularCount = chainHrCountImmediateMap.get("regular_count");
            Number chainTurnRightCount = chainHrCountImmediateMap.get("turn_right_count");
            // 环比增加人数
            int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
            int chainTurnAdd = chainTurnRightCount.intValue() - turnRightCount.intValue();
            // 环比
            double staffFullChainRate = RateUtil.doubleChainRate(chainFullAdd, chainRegularCount.intValue());
            double turnRightChainRate = RateUtil.doubleChainRate(chainTurnAdd, chainTurnRightCount.intValue());
            data.put("staffFullChainRate", Double.parseDouble(df.format(staffFullChainRate)));
            //转正人员占比
            data.put("turnRightChainRate", Double.parseDouble(df.format(turnRightChainRate)));
        }
        return data;
    }

    /**
     * @Author:SHIGS
     * @Description 人力-总览
     * @Date:18:02 2017/10/24
     * @modified By
     */
    @Override
    public Map<String, Object> selectHrCountByPart(Date startTime, Date endTime) {
       /* int dayBetween = DateUtil.getDayBetween(startTime, endTime);
        Date bigStartTime = readerSession.getMapper(HrCountMapper.class).selectStart();
        Date bigEndTime = readerSession.getMapper(HrCountMapper.class).selectEnd();
        int bigDayBetween = DateUtil.getDayBetween(bigStartTime, bigEndTime);
        if (dayBetween > bigDayBetween / 2) {
        }*/
        // 当前时期
        HrCountExample hrCountExample = new HrCountExample();
        HrCountExample.Criteria criteria = hrCountExample.createCriteria();
        // 即时数据
        HrCountExample hrCountExampleImediate = new HrCountExample();
        HrCountExample.Criteria criteriaImediate = hrCountExampleImediate.createCriteria();
        // 当前时段
        Map<String, Long> CurHrCountMap = null;
        if (startTime != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
            criteriaImediate.andCreateAtLessThan(endTime);
        }
        // {"s1":"计划人数","s2":"在编人数","s3":"试用期人数","s4":"转正人数","s5":"中方人数",
        //"s6":"外籍人数","s7":"新进人数","s8":"离职人数","s9":"集团转进","s10":"集团转出",
        // "staffFullRate":"在编/计划--满编率","tryRate":"试用/在编--试用占比","addRate":"(新进/在编-离职/在编) -- 增长率",
        //"leaveRate":"(转刚出/在编-转岗进/在编)--转岗流失","foreignRate":"外籍/在编--外籍占比"}
        CurHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
        Map<String, Long> immediateMap = findImmediateNum(hrCountExampleImediate);
        if (CurHrCountMap == null) {
            CurHrCountMap = new HashMap<>();
            CurHrCountMap.put("turnRightCount", 0L);
            CurHrCountMap.put("newCount", 0L);
            CurHrCountMap.put("dimissionCount", 0L);
            CurHrCountMap.put("groupTransferIn", 0L);
            CurHrCountMap.put("groupTransferOut", 0L);
        }
        Number planCount = immediateMap.get("plan_count");
        Number regularCount = immediateMap.get("regular_count");
        Number tryCount = immediateMap.get("try_count");
        Number chinaCount = immediateMap.get("china_count");
        Number foreignCount = immediateMap.get("foreign_count");
        Number turnRightCount = immediateMap.get("turn_right_count");
        Number newCount = CurHrCountMap.get("newCount");
        Number dimissionCount = CurHrCountMap.get("dimissionCount");
        Number turnJobin = CurHrCountMap.get("groupTransferIn");
        Number turnJobout = CurHrCountMap.get("groupTransferOut");
        // 满编率
        double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
        // 试用占比
        double tryRate = RateUtil.intChainRate(tryCount.intValue(), regularCount.intValue());
        // 增长率
        double addRate = RateUtil.intChainRate(newCount.intValue(), regularCount.intValue())
                - RateUtil.intChainRate(dimissionCount.intValue(), regularCount.intValue());
        // 转岗流失率
        double leaveRate = RateUtil.intChainRate(turnJobout.intValue(), regularCount.intValue())
                - RateUtil.intChainRate(turnJobin.intValue(), regularCount.intValue());
        // 外籍占比
        double foreignRate = RateUtil.intChainRate(foreignCount.intValue(), regularCount.intValue());
        Map<String, Object> data = new HashMap<>();
        data.put("newCount", newCount);
        data.put("foreignCount", foreignCount);
        data.put("chinaCount", chinaCount);
        data.put("groupTransferIn", turnJobin.intValue());
        data.put("groupTransferOut", turnJobout);
        data.put("turnRightCount", turnRightCount);
        data.put("staffFullCount", regularCount);
        data.put("planCount", planCount);
        data.put("tryCount", tryCount);
        data.put("dimissionCount", dimissionCount);
        data.put("leaveRate", leaveRate);
        data.put("newAdd", addRate);
        data.put("foreignRate", foreignRate);
        data.put("staffFullRate", staffFullRate);
        data.put("tryRate", tryRate);
        if (startTime != null && endTime != null && DateUtil.getDayBetween(startTime, endTime) > 0) {
            HrCountExample hrCountExample02 = new HrCountExample();
            HrCountExample.Criteria criteria02 = hrCountExample02.createCriteria();
            int days = DateUtil.getDayBetween(startTime, endTime);
            //环比开始
            Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
            // 环比时段
            criteria02.andCreateAtGreaterThan(chainEnd).andCreateAtLessThan(startTime);
            // 即时数据环比
            HrCountExample hrCountExampleImediate2 = new HrCountExample();
            HrCountExample.Criteria criteriaImediate2 = hrCountExampleImediate.createCriteria();
            criteriaImediate2.andCreateAtLessThanOrEqualTo(chainEnd);
            Map<String, Long> chainHrCountMap = readMapper.selectHrCountByPart(hrCountExample02);
            Map<String, Long> chainHrCountImmediateMap = findImmediateNum(hrCountExampleImediate2);
            if (chainHrCountMap == null) {
                chainHrCountMap = new HashMap<>();
                chainHrCountMap.put("turnRightCount", 0L);
                chainHrCountMap.put("newCount", 0L);
                chainHrCountMap.put("groupTransferIn", 0L);
            }
            // 环比人数
            Number chainRegularCount = chainHrCountImmediateMap.get("regular_count");
            Number chainTurnRightCount = immediateMap.get("turn_right_count");
            Number chainNewCount = chainHrCountMap.get("newCount");
            Number chainTurnJobin = chainHrCountMap.get("groupTransferIn");
            Number chainForeignCount = chainHrCountImmediateMap.get("foreign_count");
            // 环比增加人数
            int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
            int chainTurnAdd = turnRightCount.intValue() - chainTurnRightCount.intValue();
            int chainAdd = newCount.intValue() - chainNewCount.intValue();
            int chainTurnJobinAdd = turnJobin.intValue() - chainTurnJobin.intValue();
            int chainForeignCountAdd = foreignCount.intValue() - chainForeignCount.intValue();
            // 环比
            double staffFullChainRate = RateUtil.intChainRate(chainFullAdd, chainRegularCount.intValue());
            double tryChainRate = RateUtil.intChainRate(chainTurnAdd, chainTurnRightCount.intValue());
            double chainAddRate = RateUtil.intChainRate(chainAdd, chainNewCount.intValue());
            double groupTransferChainRate = RateUtil.intChainRate(chainTurnJobinAdd, chainTurnJobin.intValue());
            double foreignChainRate = RateUtil.intChainRate(chainForeignCountAdd, chainForeignCount.intValue());
            data.put("foreignChainRate", Double.parseDouble(df.format(foreignChainRate)));
            data.put("groupTransferChainRate", Double.parseDouble(df.format(groupTransferChainRate)));
            data.put("newChainRate", Double.parseDouble(df.format(chainAddRate)));
            data.put("staffFullChainRate", Double.parseDouble(df.format(staffFullChainRate)));
            data.put("turnRightChainRate", Double.parseDouble(df.format(tryChainRate)));
        } else {
            // 没有环比的情况下置0
            data.put("foreignChainRate", 0);
            data.put("groupTransferChainRate", 0);
            data.put("newChainRate", 0);
            data.put("staffFullChainRate", 0);
            data.put("turnRightChainRate", 0);
        }
        return data;
    }

    /**
     * @Author:SHIGS
     * @Description 查询部门
     * @Date:18:02 2017/10/24
     * @modified By
     */
    @Override
    public Map selectBigDepart() {
        List<Map> orgMap = readMapper.selectBigDepart();
        List<String> departList = new ArrayList<>();
        for (Map map : orgMap) {
            String area = map.get("big_depart").toString();
            departList.add(area);
        }
        Map<String, List<String>> mapData = new HashMap<>();
        mapData.put("depart", departList);
        return mapData;
    }

    /**
     * @Author:SHIGS
     * @Description 数据对比
     * @Date:11:53 2017/10/30
     * @modified By
     */
    @Override
    public Map<String, Object> selectHrCountByDepart(Date startTime, Date endTime, String depart) {
        HrCountExample hrCountExample = new HrCountExample();
        HrCountExample.Criteria criteria = hrCountExample.createCriteria();
        Map<String, Long> curHrCountMap = null;
        // 即时数据
        Date lastDate = selectLeastDate();
        HrCountExample hrCountExampleImediate = new HrCountExample();
        HrCountExample.Criteria criteriaImediate = hrCountExampleImediate.createCriteria();
        Map<String, Long> immediateMap = null;
        if (startTime != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
            criteriaImediate.andCreateAtEqualTo(lastDate);
        }
        if ("".equals(depart) || depart == null) {
            curHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
            immediateMap = findImmediateNum(hrCountExampleImediate);
            if (curHrCountMap == null) {
                curHrCountMap = new HashMap<>();
                curHrCountMap.put("newCount", 0L);
                curHrCountMap.put("dimissionCount", 0L);
                curHrCountMap.put("groupTransferIn", 0L);
                curHrCountMap.put("groupTransferOut", 0L);

            }
        } else {
            criteria.andBigDepartEqualTo(depart);
            criteriaImediate.andBigDepartEqualTo(depart);
            curHrCountMap = readMapper.selectHrCountByPart(hrCountExample);
            immediateMap = findImmediateNum(hrCountExampleImediate);
            if (curHrCountMap == null) {
                curHrCountMap = new HashMap<>();
                curHrCountMap.put("newCount", 0L);
                curHrCountMap.put("dimissionCount", 0L);
                curHrCountMap.put("groupTransferIn", 0L);
                curHrCountMap.put("groupTransferOut", 0L);
            }
        }
        //即时时段
        Number planCount = immediateMap.get("plan_count");
        Number regularCount = immediateMap.get("regular_count");
        Number tryCount = immediateMap.get("try_count");
        Number chinaCount = immediateMap.get("china_count");
        Number foreignCount = immediateMap.get("foreign_count");
        Number turnRightCount = immediateMap.get("turn_right_count");
        // 当前时段
        Number newCount = curHrCountMap.get("newCount");
        Number dimissionCount = curHrCountMap.get("dimissionCount");
        Number turnJobin = curHrCountMap.get("groupTransferIn");
        Number turnJobout = curHrCountMap.get("groupTransferOut");
        // 满编率
        double staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
        // 试用占比
        double tryRate = RateUtil.intChainRate(tryCount.intValue(), regularCount.intValue());
        // 增长率
        double addRate = RateUtil.intChainRate(newCount.intValue(), regularCount.intValue())
                - RateUtil.intChainRate(dimissionCount.intValue(), regularCount.intValue());
        // 转岗流失率
        double leaveRate = RateUtil.intChainRate(turnJobout.intValue(), regularCount.intValue())
                - RateUtil.intChainRate(turnJobin.intValue(), regularCount.intValue());
        // 外籍占比
        double foreignRate = RateUtil.intChainRate(foreignCount.intValue(), regularCount.intValue());
        List<Integer> seriesList01 = new ArrayList<>();
        List<Integer> seriesList02 = new ArrayList<>();
        seriesList01.add(planCount.intValue());
        seriesList02.add(regularCount.intValue());
        seriesList01.add(turnRightCount.intValue());
        seriesList02.add(tryCount.intValue());
        seriesList01.add(newCount.intValue());
        seriesList02.add(dimissionCount.intValue());
        seriesList01.add(turnJobin.intValue());
        seriesList02.add(turnJobout.intValue());
        seriesList01.add(chinaCount.intValue());
        seriesList02.add(foreignCount.intValue());
        String[] xArray = {"计划编制/到岗编制", "转正/试用", "新进/离职", "转岗(进)/转岗(出)", "中方/外籍"};
        Map<String, Object> data = new HashMap<>();
        data.put("xAxis", xArray);
        data.put("seriesData01", seriesList01);
        data.put("seriesData02", seriesList02);
        data.put("staffFullRate", staffFullRate);
        data.put("tryRate", tryRate);
        data.put("addRate", addRate);
        data.put("leaveRate", leaveRate);
        data.put("foreignRate", foreignRate);
        return data;
    }

    /**
     * @Author:SHIGS
     * @Description 部门明细
     * @Date:11:31 2017/10/25
     * @modified By
     */
    @Override
    public List<Map> selectDepartmentCount(Date startTime, Date endTime) {
        HrCountExample example = new HrCountExample();
        HrCountExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        // 查询累计数据
        List<Map> bigList = readMapper.selectDepartmentCountByExample(example);
        // 查询即时数据
        Date lastDate = selectLeastDate();
        HrCountExample hrCountExampleImediate = new HrCountExample();
        HrCountExample.Criteria criteriaImediate = hrCountExampleImediate.createCriteria();
        criteriaImediate.andCreateAtEqualTo(lastDate);
        List<Map> bigImmediteList = findImmediateDepartmentNum(hrCountExampleImediate);
        Map<String, Map<String, Object>> bigImmediteMap = bigImmediteList.parallelStream().collect(Collectors.toMap(vo -> (String) StringUtils.defaultIfBlank((String) vo.get("department"), ""), vo -> vo));
        Map<String, Object> bigerPart = new HashMap<>();
        bigList.stream().forEach(vo -> {
            String deparment = (String) vo.get("department");
            if (StringUtils.isBlank(deparment)) {
                deparment = "";
            }
            String bigDeparment = (String) vo.get("big_depart");
            Map<String, Object> immeMap = bigImmediteMap.get(deparment);

            Object bigObj = bigerPart.get(bigDeparment);
            Map<String, Object> bigMap = null;
            if (bigObj == null) {
                bigMap = new HashMap<>();
            } else {
                bigMap = (Map<String, Object>) bigObj;
            }
            bigerPart.put(bigDeparment, bigMap);

            int regularCount = ((Number) immeMap.get("regular_count")).intValue();
            int planCount = ((Number) immeMap.get("plan_count")).intValue();
            int tryCount = ((Number) immeMap.get("try_count")).intValue();
            int foreignCount = ((Number) immeMap.get("foreign_count")).intValue();

            int newCount = ((Number) vo.get("newCount")).intValue();
            int dimissionCount = ((Number) vo.get("dimissionCount")).intValue();
            int groupTransferIn = ((Number) vo.get("groupTransferIn")).intValue();
            int groupTransferOut = ((Number) vo.get("groupTransferOut")).intValue();
            if (StringUtils.isNotBlank(deparment)) {
                Map<String, Object> children = new HashMap<>();
                children.put("departmentName", deparment);
                children.put("staffFullRate", RateUtil.intChainRate(regularCount, planCount));
                children.put("tryRate", RateUtil.intChainRate(tryCount, regularCount));
                children.put("addRate", RateUtil.intChainRate(newCount, regularCount) - RateUtil.intChainRate(dimissionCount, regularCount));
                children.put("foreignRate", RateUtil.intChainRate(foreignCount, regularCount));
                children.put("leaveRate", RateUtil.intChainRate(groupTransferOut, regularCount) - RateUtil.intChainRate(groupTransferIn, regularCount));
                Object childrens = bigMap.get("children");
                List<Object> list = null;
                if (childrens == null) {
                    list = new ArrayList<>();
                    bigMap.put("children", list);
                } else {
                    list = (List) childrens;
                }
                list.add(children);
            }


            Object count = bigMap.get("count");
            Map<String, Integer> bigPartNum = null;
            if (count == null) {
                bigPartNum = new HashMap<>();
                bigPartNum.put("regularCount", regularCount);
                bigPartNum.put("planCount", planCount);
                bigPartNum.put("tryCount", tryCount);
                bigPartNum.put("foreignCount", foreignCount);
                bigPartNum.put("newCount", newCount);
                bigPartNum.put("dimissionCount", dimissionCount);
                bigPartNum.put("groupTransferIn", groupTransferIn);
                bigPartNum.put("groupTransferOut", groupTransferOut);
            } else {
                bigPartNum = (Map<String, Integer>) count;
                bigPartNum.put("regularCount", regularCount + bigPartNum.get("regularCount"));
                bigPartNum.put("planCount", planCount + bigPartNum.get("planCount"));
                bigPartNum.put("tryCount", tryCount + bigPartNum.get("tryCount"));
                bigPartNum.put("foreignCount", foreignCount + bigPartNum.get("foreignCount"));
                bigPartNum.put("newCount", newCount + bigPartNum.get("newCount"));
                bigPartNum.put("dimissionCount", dimissionCount + bigPartNum.get("dimissionCount"));
                bigPartNum.put("groupTransferIn", groupTransferIn + bigPartNum.get("groupTransferIn"));
                bigPartNum.put("groupTransferOut", groupTransferOut + bigPartNum.get("groupTransferOut"));
            }
            bigMap.put("count", bigPartNum);
        });

        List<Map> result = new ArrayList<>();
        // 循环大部门
        for (Map.Entry<String, Object> entry : bigerPart.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> bigMap = (Map<String, Object>) entry.getValue();
            Map<String, Integer> count = (Map<String, Integer>) bigMap.get("count");
            bigMap.put("staffFullRate", RateUtil.intChainRate(count.get("regularCount"), count.get("planCount")));
            bigMap.put("tryRate", RateUtil.intChainRate(count.get("tryCount"), count.get("regularCount")));
            bigMap.put("addRate", RateUtil.intChainRate(count.get("newCount"), count.get("regularCount")) - RateUtil.intChainRate(count.get("dimissionCount"), count.get("regularCount")));
            bigMap.put("foreignRate", RateUtil.intChainRate(count.get("foreignCount"), count.get("regularCount")));
            bigMap.put("leaveRate", RateUtil.intChainRate(count.get("groupTransferOut"), count.get("regularCount")) - RateUtil.intChainRate(count.get("groupTransferIn"), count.get("regularCount")));
            bigMap.put("departName", key);
            result.add(bigMap);
        }
        return result;
//
//        List<Map> bigList = readMapper.selectBigDepartCountByExample(example);
//        List<Map> bigImmediteList = findImmediateBigDepartNum(hrCountExampleImediate);
//        List<Map> departList = readMapper.selectDepartmentCountByExample(example);
//        List<Map> departImmediteList = findImmediateDepartmentNum(hrCountExampleImediate);
//        Map<String, Map<String, Object>> departMap2 = new HashMap<>();
//        List<Map> result = new ArrayList<>();
//        double staffFullRate = 0.0;
//        double tryRate = 0.0;
//        double addRate = 0.0;
//        double leaveRate = 0.0;
//        double foreignRate = 0.0;
//        for (Map<String, Object> mapBig : bigList) {
//            if ("".equals(mapBig.get("big_depart")) || mapBig.get("big_depart") == null) {
//                continue;
//            }
//            Number newCount = (Number) mapBig.get("newCount");
//            Number dimissionCount = (Number) mapBig.get("dimissionCount");
//            Number turnJobin = (Number) mapBig.get("groupTransferIn");
//            Number turnJobout = (Number) mapBig.get("groupTransferOut");
//            for (Map<String, Object> immediateMap : bigImmediteList) {
//                if ("".equals(immediateMap.get("big_depart")) || immediateMap.get("big_depart") == null) {
//                    continue;
//                }
//                Number planCount = (Number) immediateMap.get("plan_count");
//                Number regularCount = (Number) immediateMap.get("regular_count");
//                Number tryCount = (Number) immediateMap.get("try_count");
//                Number foreignCount = (Number) immediateMap.get("foreign_count");
//                staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
//                // 试用占比
//                tryRate = RateUtil.intChainRate(tryCount.intValue(), regularCount.intValue());
//                // 增长率
//                addRate = RateUtil.intChainRate(newCount.intValue(), regularCount.intValue())
//                        - RateUtil.intChainRate(dimissionCount.intValue(), regularCount.intValue());
//                // 转岗流失率
//                leaveRate = RateUtil.intChainRate(turnJobout.intValue(), regularCount.intValue())
//                        - RateUtil.intChainRate(turnJobin.intValue(), regularCount.intValue());
//                // 外籍占比
//                foreignRate = RateUtil.intChainRate(foreignCount.intValue(), regularCount.intValue());
//            }
//            Map<String, Object> departMap = new HashMap<>();
//            departMap.put("staffFullRate", staffFullRate);
//            departMap.put("tryRate", tryRate);
//            departMap.put("addRate", addRate);
//            departMap.put("leaveRate", leaveRate);
//            departMap.put("foreignRate", foreignRate);
//            departMap.put("departName", mapBig.get("big_depart"));
//            departMap2.put(mapBig.get("big_depart").toString(), departMap);
//            result.add(departMap);
//            // 满编率
//        }
//        for (Map mapDepart : departList) {
//            Number newCount = (Number) mapDepart.get("newCount");
//            Number dimissionCount = (Number) mapDepart.get("dimissionCount");
//            Number turnJobin = (Number) mapDepart.get("groupTransferIn");
//            Number turnJobout = (Number) mapDepart.get("groupTransferOut");
//            if ("".equals(mapDepart.get("department")) || mapDepart.get("department") == null) {
//                continue;
//            }
//            Map<String, Object> bigDepartment = departMap2.get(mapDepart.get("big_depart").toString());
//            for (Map<String, Object> immediateMap2 : departImmediteList) {
//                if ("".equals(immediateMap2.get("department")) || immediateMap2.get("department") == null) {
//                    continue;
//                }
//                Number planCount = (Number) immediateMap2.get("plan_count");
//                Number regularCount = (Number) immediateMap2.get("regular_count");
//                Number tryCount = (Number) immediateMap2.get("try_count");
//                Number foreignCount = (Number) immediateMap2.get("foreign_count");
//                // 满编率
//                staffFullRate = RateUtil.intChainRate(regularCount.intValue(), planCount.intValue());
//                // 试用占比
//                tryRate = RateUtil.intChainRate(tryCount.intValue(), regularCount.intValue());
//                // 增长率
//                addRate = RateUtil.intChainRate(newCount.intValue(), regularCount.intValue())
//                        - RateUtil.intChainRate(dimissionCount.intValue(), regularCount.intValue());
//                // 转岗流失率
//                leaveRate = RateUtil.intChainRate(turnJobout.intValue(), regularCount.intValue())
//                        - RateUtil.intChainRate(turnJobin.intValue(), regularCount.intValue());
//                // 外籍占比
//                foreignRate = RateUtil.intChainRate(foreignCount.intValue(), regularCount.intValue());
//            }
//            Map<String, Object> departMap = new HashMap<>();
//            departMap.put("staffFullRate", staffFullRate);
//            departMap.put("tryRate", tryRate);
//            departMap.put("addRate", addRate);
//            departMap.put("leaveRate", leaveRate);
//            departMap.put("foreignRate", foreignRate);
//            departMap.put("departmentName", mapDepart.get("department").toString());
//            Object obj = bigDepartment.get("children");
//            if (obj == null) {
//                obj = new ArrayList<Map<String, Double>>();
//                bigDepartment.put("children", obj);
//            }
//            ((List) obj).add(departMap);
//        }
//        return result;
    }
    @Override
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
        ImportDataResponse response = new ImportDataResponse(
                new String[]{"planCount", "regularCount", "tryCount", "turnRightCount",
                        "chinaCount", "foreignCount",
                        "newCount", "groupTransferIn",
                        "groupTransferOut", "dimissionCount"});
        response.setOtherMsg(NewDateUtil.getBeforeSaturdayWeekStr(null));
        int size = datas.size();
        HrCount hc = null;
        String preBigDept = null;
        if (!testOnly) {
            writeMapper.truncateTable();
        }
        for (int index = 0; index < size; index++) {
            int cellIndex = index + 2; // 数据从第二行开始
            String[] strArr = datas.get(index);
            if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.HR_COUNT, response, cellIndex)) {
                continue;
            }
            hc = new HrCount();
            if (strArr[0] != null) {
                try {
                    hc.setCreateAt(
                            DateUtil.parseString2Date(strArr[0], DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "日期字段格式错误");
                    continue;
                }
            }

            if (strArr[1] != null) {
                preBigDept = strArr[1];
            }
            hc.setBigDepart(preBigDept);
            hc.setDepartment(strArr[2]);

            if (strArr[3] != null) {
                try {
                    hc.setPlanCount(new BigDecimal(strArr[3]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "计划人数不是数字");
                    continue;
                }
            }

            if (strArr[4] != null) {
                try {
                    hc.setRegularCount(new BigDecimal(strArr[4]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "在编人数不是数字");
                    continue;
                }
            }

            if (strArr[5] != null) {
                try {
                    hc.setTryCount(new BigDecimal(strArr[5]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "试用期人数不是数字");
                    continue;
                }
            }

            if (strArr[6] != null) {
                try {
                    hc.setTurnRightCount(new BigDecimal(strArr[6]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "转正人数不是数字");
                    continue;
                }
            }

            if (strArr[7] != null) {
                try {
                    hc.setChinaCount(new BigDecimal(strArr[7]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "中方字段不是数字");
                    continue;
                }
            }
            if (strArr[8] != null) {
                try {
                    hc.setForeignCount(new BigDecimal(strArr[8]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "外籍字段不是数字");
                    continue;
                }
            }
            if (strArr[9] != null) {
                try {
                    hc.setNewCount(new BigDecimal(strArr[9]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "新进字段不是数字");
                    continue;
                }
            }
            if (strArr[10] != null) {
                try {
                    hc.setGroupTransferIn(new BigDecimal(strArr[10]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "集团转岗（进）字段不是数字");
                    continue;
                }
            }
            if (strArr[11] != null) {
                try {
                    hc.setGroupTransferOut(new BigDecimal(strArr[11]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "集团转岗（出）字段不是数字");
                    continue;
                }
            }
            if (strArr[12] != null) {
                try {
                    hc.setDimissionCount(new BigDecimal(strArr[12]).intValue());
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                    response.incrFail();
                    response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, "离职字段不是数字");
                    continue;
                }
            }

            try {
                if (!testOnly) {
                    writeMapper.insertSelective(hc);
                }
            } catch (Exception e) {
                response.incrFail();
                response.pushFailItem(ExcelUploadTypeEnum.HR_COUNT.getTable(), cellIndex, e.getMessage());
                continue;
            }
            // 在上周范围内则统计
            if (NewDateUtil.inSaturdayWeek(hc.getCreateAt())) {
                response.sumData(hc);
            }
            response.incrSuccess();

        }
        response.setDone(true);

        return response;
    }


    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(dayOfWeek);
        System.out.println(dayOfWeek.getValue());
        int value = dayOfWeek.getValue();
        if (value <= 5) {
            localDate = localDate.minusDays(value + 2);
        } else {
            localDate = localDate.minusDays(value - 5);
        }
        System.out.println(localDate);

        localDate = localDate.minusDays(7);
        System.out.println(localDate);


    }

    /**
     * @param example
     * @return {"plan_count":"计划编制","regular_count":"到岗编制","try_count":"试用期人数","china_count":"中方","foreign_count":"外籍"}
     * @Author:SHIGS
     * @Description
     * @Date:22:14 2017/11/23
     * @modified By
     */
    private Map<String, Long> findImmediateNum(HrCountExample example) {
        Map<String, Long> map = readMapper.findImmediateNum(example);
        if (map == null) {
            map = new HashMap<>();
            map.put("plan_count", 0L);
            map.put("regular_count", 0L);
            map.put("try_count", 0L);
            map.put("china_count", 0L);
            map.put("foreign_count", 0L);
            map.put("turn_right_count", 0L);
        }
        return map;
    }
    private List<Map> findImmediateDepartmentNum(HrCountExample example) {
        List<Map> map = readMapper.findImmediateDepartmentNum(example);
        return map;
    }
    private Date selectLeastDate() {
        return readMapper.selectEnd();
    }
}
