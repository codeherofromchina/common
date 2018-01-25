package com.erui.report.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.encrypt.MD5;
import com.erui.report.dao.SupplyChainCategoryMapper;
import com.erui.report.dao.SupplyChainReadMapper;
import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyChainReadExample;
import com.erui.report.model.SupplyTrendVo;
import com.erui.report.service.SupplyChainReadService;
import com.erui.report.util.GetDataEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SupplyChainReadServiceImpl extends BaseService<SupplyChainReadMapper> implements SupplyChainReadService {



    @Override
    public void supplyChainReadData(String startTime,  SupplyChainRead supplyChainRead, List<HashMap> chainCateVoList) throws Exception {

        Date date = DateUtil.parseStringToDate(startTime, DateUtil.FULL_FORMAT_STR);
        SupplyChainReadExample example = new SupplyChainReadExample();
        SupplyChainReadExample.Criteria criteria = example.createCriteria();
        criteria.andCreateAtEqualTo(date);
        List<SupplyChainRead> reads = readMapper.selectByExample(example);
        if (supplyChainRead != null) {
                supplyChainRead.setCreateAt(date);
                if (reads == null || reads.size() < 1) {
//                MongoUtil.addStaticLog("supplyChainReadData", "");
                    this.writeMapper.insert(supplyChainRead);
                }
        }
        if (reads == null || reads.size() < 1) {
            //处理分类的数据
                List<SupplyChainCategory> cateList = new ArrayList<>();
                if (chainCateVoList != null && chainCateVoList.size() > 0) {
                    for (Map<String, Object> map : chainCateVoList) {
                        Object name = map.get("name");
                        int spu_count = 0;
                        int sku_count = 0;
                        int supplier_count = 0;
                        if (map.get("spu_count") != null) {
                            spu_count = (int) map.get("spu_count");
                        }
                        if (map.get("sku_count") != null) {
                            sku_count = (int) map.get("sku_count");
                        }
                        if (map.get("supplier_count") != null) {
                            supplier_count = (int) map.get("supplier_count");
                        }
                        if (spu_count == 0 && sku_count == 0 && supplier_count == 0) {
                            continue;
                        }
                        SupplyChainCategory chainCate = new SupplyChainCategory();
                        chainCate.setCreateAt(DateUtil.parseStringToDate(startTime, DateUtil.FULL_FORMAT_STR));
                        if (name != null) {
                            chainCate.setItemClass(name.toString());
                        }
                        chainCate.setSkuNum(sku_count);
                        chainCate.setSpuNum(spu_count);
                        chainCate.setSuppliNum(supplier_count);
                        cateList.add(chainCate);
                    }
                }
                SupplyChainCategoryMapper catemapper = this.writerSession.getMapper(SupplyChainCategoryMapper.class);
                if (cateList != null && cateList.size() > 0) {
                    cateList.parallelStream().forEach(vo -> catemapper.insert(vo));
                }
            }

    }

    @Override
    public SupplyChainRead getSupplyChainReadDataByTime(Date startTime, Date endTime) {
        SupplyChainReadExample example = new SupplyChainReadExample();
        SupplyChainReadExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        return readMapper.getSupplyChainReadDataByTime(example);
    }

    @Override
    public SupplyTrendVo supplyTrend(Date startTime, Date endTime) {
        SupplyChainReadExample example = new SupplyChainReadExample();
        SupplyChainReadExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        List<SupplyChainRead> list = readMapper.selectByExample(example);
        //解析数据
        int days = DateUtil.getDayBetween(startTime, endTime);
        String[] DateTime = new String[days];//封装日期列表
        Integer[] suppliyFinishCount = new Integer[days];//封装供应商数据列表
        Integer[] SPUFinishCount = new Integer[days];//封装spu数据列表
        Integer[] SKUFinishCount = new Integer[days];//封装sku数据列表
        if (list != null && list.size() > 0) {
            Map<String, Map<String, Integer>> dateMap = new HashMap<>();
            Map<String, Integer> datamap;
            for (int i = 0; i < list.size(); i++) {
                String date2 = DateUtil.formatDate2String(list.get(i).getCreateAt(), "yyyy-MM-dd");
                if (dateMap.containsKey(date2)) {
                    Map<String, Integer> map = dateMap.get(date2);
                    Integer sku = map.get("sku");
                    Integer spu = map.get("spu");
                    Integer suppliy = map.get("suppliy");
                    map.put("sku", sku + list.get(i).getSkuNum());
                    map.put("spu", spu + list.get(i).getSpuNum());
                    map.put("suppliy", suppliy + list.get(i).getSuppliNum());

                } else {
                    datamap = new HashMap<>();
                    datamap.put("sku", list.get(i).getSkuNum());
                    datamap.put("spu", list.get(i).getSpuNum());
                    datamap.put("suppliy", list.get(i).getSuppliNum());
                    dateMap.put(date2, datamap);
                }

            }
            for (int i = 0; i < days; i++) {
                Date date = DateUtil.sometimeCalendar(startTime, -i);
                String datet2 = DateUtil.format("yyyy-MM-dd", date);
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
                String datet2 = DateUtil.format("yyyy-MM-dd", date);
                DateTime[i] = datet2;
                suppliyFinishCount[i] = 0;
                SPUFinishCount[i] = 0;
                SKUFinishCount[i] = 0;
            }
        }
        SupplyTrendVo trend = new SupplyTrendVo(DateTime, suppliyFinishCount, SPUFinishCount, SKUFinishCount);

        return trend;
    }

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
}
