package com.erui.report.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.encrypt.MD5;
import com.erui.report.dao.SupplyChainCategoryMapper;
import com.erui.report.dao.SupplyChainReadMapper;
import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyChainReadExample;
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


    private static final String goodUrl = "http://api.eruidev.com/v2/Report/getGoodsCount";//获取sku数据请求路径
    private static final String productUrl = "http://api.eruidev.com/v2/Report/getProductCount";//获取spu数据请求路径
    private static final String supplierUrl = "http://api.eruidev.com/v2/Report/getSupplierCount";//获取供应商数据请求路径
    private static final String cateUrl = "http://api.eruidev.com/v2/Report/getCatProductCount";//获取供应链分类数据请求路径
    public static final String key = "9b2a37b7b606c14d43db538487a148c7";
    private static ObjectMapper om = new ObjectMapper();


    @Override
    public void getSupplyChainReadData(String startTime, String endTime) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut goodPutMethod = getPutMethod(goodUrl, startTime, endTime);
        HttpPut productPutMethod = getPutMethod(productUrl, startTime, endTime);
        HttpPut supplierPutMethod = getPutMethod(supplierUrl, startTime, endTime);
        HttpPut catePutMethod = getPutMethod(cateUrl, startTime, endTime);
        CloseableHttpResponse skuResult = client.execute(goodPutMethod);
        CloseableHttpResponse spuResult = client.execute(productPutMethod);
        CloseableHttpResponse supplierResult = client.execute(supplierPutMethod);
        CloseableHttpResponse cateResult = client.execute(catePutMethod);
        //处理结果
        SupplyChainRead skuRead = this.handleResult(skuResult, GetDataEnum.SKU_DATA.getCode());
        SupplyChainRead spuRead = this.handleResult(spuResult, GetDataEnum.SKU_DATA.getCode());
        SupplyChainRead supplierRead = this.handleResult(supplierResult, GetDataEnum.SKU_DATA.getCode());
        SupplyChainRead supplyChainRead = null;
        if (skuRead != null || spuRead != null || supplierRead != null) {
            supplyChainRead = mergeResult(skuRead, spuRead, supplierRead);
        }
        if (supplyChainRead != null) {
            this.writeMapper.insert(supplyChainRead);
        }
        //处理分类的数据
        SupplyChainCategory chainCategory = new SupplyChainCategory();
        JSONObject json = new JSONObject();
        String cateData = EntityUtils.toString(cateResult.getEntity());
        JSONObject cateJson = json.parseObject(cateData);
        int cateCode = (int) cateJson.get("code");
        List<SupplyChainCategory> cateList = null;
        if (cateCode == 1) {
            List<SupplyChainCateVo> chainCateVoList = (List) cateJson.get("data");
            if (chainCateVoList != null && chainCateVoList.size() > 0) {
                for (SupplyChainCateVo cateVo : chainCateVoList) {
                    SupplyChainCategory chainCate = new SupplyChainCategory();
                    chainCate.setCreateAt(new Date());
                    chainCate.setItemClass(cateVo.getName());
                    chainCate.setSkuNum(cateVo.getSku());
                    chainCate.setSpuNum(cateVo.getSpu());
                    chainCate.setSuppliNum(cateVo.getSupplier_count());
                }
            }
            SupplyChainCategoryMapper catemapper = this.writerSession.getMapper(SupplyChainCategoryMapper.class);
            if (cateList != null && cateList.size() > 0) {
                for (SupplyChainCategory cate : cateList) {
                    catemapper.insert(cate);
                }
            }
        }
    }


    /**
     * 获取PutMethod
     */
    private HttpPut getPutMethod(String url, String startTime, String endTime) throws Exception {
        HttpPut method = new HttpPut(url);
        method.getParams().setParameter("http.socket.timeout", 3000);
        //组装请求json
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> input = new HashMap<>();
        input.put("lang", "zh");
        input.put("creat_at_start", startTime);
        input.put("creat_at_end", endTime);
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
            int count = (int) jsonObject.get("count");
            int checking_count = (int) jsonObject.get("checking_count");
            int valid_count = (int) jsonObject.get("valid_count");
            int invalid_count = (int) jsonObject.get("invalid_count");
            if (getDataCode != GetDataEnum.SUPPLIER_DATA.getCode()) {
                draft_count = (int) jsonObject.get("draft_count");
            } else {
                brand_count = (int) jsonObject.get("brand_count");
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
        if(startTime!=null&&endTime!=null){
            criteria.andCreateAtBetween(startTime,endTime);
        }
        return readMapper.getSupplyChainReadDataByTime(example);
    }

}
