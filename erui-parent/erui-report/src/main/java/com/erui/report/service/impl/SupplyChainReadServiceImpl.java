package com.erui.report.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.data.string.StringUtils;
import com.erui.comm.util.encrypt.MD5;
import com.erui.report.dao.SupplyChainCategoryMapper;
import com.erui.report.dao.SupplyChainReadMapper;
import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyChainReadExample;
import com.erui.report.model.SupplyTrendVo;
import com.erui.report.service.SupplyChainReadService;
import com.erui.report.util.GetDataEnum;
import com.erui.report.util.SupplyChainCateVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SupplyChainReadServiceImpl extends BaseService<SupplyChainReadMapper> implements SupplyChainReadService {


    private static final String goodUrl = "http://api2.erui.com/v2/Report/getGoodsCount";//获取sku数据请求路径
    private static final String productUrl = "http://api2.erui.com/v2/Report/getProductCount";//获取spu数据请求路径
    private static final String supplierUrl = "http://api2.erui.com/v2/Report/getSupplierCount";//获取供应商数据请求路径
    private static final String cateUrl = "http://api2.erui.com/v2/Report/getCatProductCount";//获取供应链分类数据请求路径
    private  static final String key = "9b2a37b7b606c14d43db538487a148c7";
    private  static ObjectMapper om = new ObjectMapper();

    @Override
    public void supplyChainReadData(String startTime, String endTime) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut goodPutMethod = getPutMethod(goodUrl, startTime, endTime);
        HttpPut productPutMethod = getPutMethod(productUrl, startTime, endTime);
        HttpPut supplierPutMethod = getPutMethod(supplierUrl, startTime, endTime);
        HttpPut catePutMethod = getPutMethod(cateUrl, startTime, endTime);
        CloseableHttpResponse skuResult = client.execute(goodPutMethod);
        CloseableHttpResponse spuResult = client.execute(productPutMethod);
        //处理结果
        SupplyChainRead skuRead = this.handleResult(skuResult, GetDataEnum.SKU_DATA.getCode());
        SupplyChainRead spuRead = this.handleResult(spuResult, GetDataEnum.SPU_DATA.getCode());
        CloseableHttpResponse supplierResult = client.execute(supplierPutMethod);
        CloseableHttpResponse cateResult = client.execute(catePutMethod);
        SupplyChainRead supplierRead = this.handleResult(supplierResult, GetDataEnum.SUPPLIER_DATA.getCode());
        SupplyChainRead supplyChainRead = null;
        if (skuRead != null || spuRead != null || supplierRead != null) {
            supplyChainRead = mergeResult(skuRead, spuRead, supplierRead);
        }
        if (supplyChainRead != null) {
            supplyChainRead.setCreateAt(DateUtil.parseStringToDate(startTime,DateUtil.FULL_FORMAT_STR));
            this.writeMapper.insert(supplyChainRead);
        }
        //处理分类的数据
        JSONObject json = new JSONObject();
        String cateData = EntityUtils.toString(cateResult.getEntity());
        JSONObject cateJson = json.parseObject(cateData);
        int cateCode = (int) cateJson.get("code");
        if (cateCode == 1) {
            List<SupplyChainCategory> cateList = new ArrayList<>();
            String cates = cateJson.get("data").toString();
            List<HashMap> chainCateVoList = JSON.parseArray(cates, HashMap.class);
            if (chainCateVoList != null && chainCateVoList.size() > 0) {
                for (Map<String, Object> map : chainCateVoList) {
                    Object name = map.get("name");
                    int spu_count =0;
                    int sku_count =0;
                    int supplier_count =0;
                    if( map.get("spu_count")!=null){
                        spu_count= (int) map.get("spu_count");
                    }
                    if(map.get("sku_count")!=null) {
                         sku_count = (int) map.get("sku_count");
                    }
                    if(map.get("supplier_count")!=null) {
                         supplier_count = (int) map.get("supplier_count");
                    }
                    if(spu_count==0&&sku_count==0&&supplier_count==0){
                        continue;
                    }
                    SupplyChainCategory chainCate = new SupplyChainCategory();
                    chainCate.setCreateAt(DateUtil.parseStringToDate(startTime,DateUtil.FULL_FORMAT_STR));
                    if(name!=null){
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
              cateList.parallelStream().forEach(vo->catemapper.insert(vo));
            }
        }
    }


    /**
     * 获取PutMethod
     */
     HttpPut getPutMethod(String url, String startTime, String endTime) throws Exception {
        HttpPut method = new HttpPut(url);
       // method.getParams().setParameter("http.socket.timeout", 3000);
        //组装请求json
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> input = new HashMap<>();
        input.put("lang", "zh");
        input.put("created_at_start", startTime);
        input.put("created_at_end", endTime);
        String inputStr = om.writeValueAsString(input);
        System.out.println("=================" + inputStr);
        String sign = MD5.encode(key + inputStr);
        System.out.println(sign + "=====");
        jsonObject.put("sign", sign);
        jsonObject.put("input", inputStr);
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
        method.setEntity(entity);
        return method;
    }

    /**
     * 处理获取的数据结果
     */
    SupplyChainRead handleResult(CloseableHttpResponse response, int getDataCode) throws IOException {
        SupplyChainRead supplyChainRead = new SupplyChainRead();
        JSONObject json = new JSONObject();
        String rsultData = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = json.parseObject(rsultData);
        int resultCode = (int) jsonObject.get("code");
        if (resultCode == 1) {//成功
            int draft_count = 0;
            int brand_count = 0;
            int count = Integer.parseInt(jsonObject.get("count").toString()) ;
            int checking_count = Integer.parseInt(jsonObject.get("checking_count").toString()) ;
            int valid_count =  Integer.parseInt( jsonObject.get("valid_count").toString());
            int invalid_count = Integer.parseInt(  jsonObject.get("invalid_count").toString());
            if (getDataCode != GetDataEnum.SUPPLIER_DATA.getCode()) {
                draft_count = Integer.parseInt( jsonObject.get("draft_count").toString());
            } else {
                brand_count =  Integer.parseInt( jsonObject.get("brand_count").toString());
            }
            System.out.println(count + "====" + draft_count + "====" + checking_count
                    + "===" + valid_count + "==" + invalid_count);
            if (getDataCode == GetDataEnum.SKU_DATA.getCode()) {
                supplyChainRead.setSkuNum(count);//'开发SKU数'
                supplyChainRead.setAuditSkuNum(checking_count);//'审核中SKU数'
                supplyChainRead.setRejectSkuNum(invalid_count);//'已驳回SKU数'
                supplyChainRead.setPassSkuNum(valid_count);//'已通过SKU数'
                supplyChainRead.setTempoSkuNum(draft_count);//'暂存SKU数'
                return supplyChainRead;
            } else if (getDataCode == GetDataEnum.SPU_DATA.getCode()) {
                supplyChainRead.setSpuNum(count);//'开发SKU数'
                supplyChainRead.setAuditSpuNum(checking_count);//'审核中SPU数'
                supplyChainRead.setRejectSpuNum(invalid_count);//'已驳回SPU数'
                supplyChainRead.setPassSpuNum(valid_count);//'已通过SPU数'
                supplyChainRead.setTempoSpuNum(draft_count);//'暂存SPU数'
                return supplyChainRead;
            } else if (getDataCode == GetDataEnum.SUPPLIER_DATA.getCode()) {
                supplyChainRead.setSuppliNum(count);//'开发SKU数'
                supplyChainRead.setAuditSuppliNum(checking_count);//'审核中供应商数'
                supplyChainRead.setRejectSuppliNum(invalid_count);//'已驳回供应商数'
                supplyChainRead.setPassSuppliNum(valid_count);//'已通过供应商数'
                supplyChainRead.setBrandNum(brand_count);
                return supplyChainRead;
            }
        }
        return null;
    }

    /**
     * 合并结果
     */
    SupplyChainRead mergeResult(SupplyChainRead... supplyChainRead) {
        SupplyChainRead chainRead = new SupplyChainRead();
        if (supplyChainRead != null && supplyChainRead.length > 0) {
            List<SupplyChainRead> reads = Arrays.asList(supplyChainRead);
            for (SupplyChainRead read : reads) {
                if (read.getSkuNum() != null) {
                    chainRead.setSkuNum(read.getSkuNum());//'开发SKU数'
                    chainRead.setAuditSkuNum(read.getAuditSkuNum());//'审核中SKU数'
                    chainRead.setRejectSkuNum(read.getRejectSkuNum());//'已驳回SKU数'
                    chainRead.setPassSkuNum(read.getPassSkuNum());//'已通过SKU数'
                    chainRead.setTempoSkuNum(read.getTempoSkuNum());//'暂存SKU数'
                } else if (read.getSpuNum() != null) {
                    chainRead.setSpuNum(read.getSpuNum());//'开发SKU数'
                    chainRead.setAuditSpuNum(read.getAuditSpuNum());//'审核中SPU数'
                    chainRead.setRejectSpuNum(read.getRejectSpuNum());//'已驳回SPU数'
                    chainRead.setPassSpuNum(read.getPassSpuNum());//'已通过SPU数'
                    chainRead.setTempoSpuNum(read.getTempoSpuNum());//'暂存SPU数'
                } else if (read.getSuppliNum() != null) {
                    chainRead.setSuppliNum(read.getSuppliNum());//'开发SKU数'
                    chainRead.setAuditSuppliNum(read.getAuditSuppliNum());//'审核中供应商数'
                    chainRead.setRejectSuppliNum(read.getRejectSuppliNum());//'已驳回供应商数'
                    chainRead.setPassSuppliNum(read.getPassSuppliNum());//'已通过供应商数'
                    chainRead.setBrandNum(read.getBrandNum());//供应商品牌数量
                }
            }
        }
        return chainRead;
    }

    @Override
    public SupplyChainRead getSupplyChainReadDataByTime(Date startTime, Date endTime) {
        SupplyChainReadExample example = new SupplyChainReadExample();
        SupplyChainReadExample.Criteria criteria = example.createCriteria();
        if (startTime != null){
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if(endTime != null) {
            criteria.andCreateAtLessThan(endTime);
        }
        return readMapper.getSupplyChainReadDataByTime(example);
    }

    @Override
    public SupplyTrendVo supplyTrend(Date startTime, Date endTime) {
        SupplyChainReadExample example = new SupplyChainReadExample();
        SupplyChainReadExample.Criteria criteria = example.createCriteria();
        if (startTime != null){
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if(endTime != null) {
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
                String date2 = DateUtil.formatDate2String(list.get(i).getCreateAt(), "yyyy年MM月dd日");
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
                String datet2 = DateUtil.format("yyyy年MM月dd日", date);
                String datet3 = DateUtil.format("MM月dd日", date);
                if (dateMap.containsKey(datet2)) {
                    DateTime[i] = (datet3);
                    SPUFinishCount[i] = (dateMap.get(datet2).get("spu"));
                    SKUFinishCount[i] = (dateMap.get(datet2).get("sku"));
                    suppliyFinishCount[i] = (dateMap.get(datet2).get("suppliy"));
                } else {
                    DateTime[i] = (datet3);
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
