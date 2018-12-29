package com.erui.boss.web.report;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/getData")
public class GetDataController {

    private static final  String goodUrl="http://api.eruidev.com/v2/Report/getGoodsCount";
    private static final  String productUrl="http://api.eruidev.com/v2/Report/getProductCount";
    private static final  String supplierUrl="http://api.eruidev.com/v2/Report/getSupplierCount";
    private static final String sign="9b2a37b7b606c14d43db538487a148c7";


    @ResponseBody
    @RequestMapping(value="/supplyChainData", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object getSupplyChainData(@RequestBody Map<String, Object> map)throws Exception{

        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPut putMethod = new HttpPut(goodUrl);
        //组装请求json
        JSONObject json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> input=new HashMap<>();
        input.put("lang","zh");
        input.put("creat_at_start","2017-11-19");
        input.put("creat_at_end","2017-11-25");
        jsonObject.put("sign",sign);
        jsonObject.put("input",input);
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
        putMethod.setEntity(entity);
        //执行put请求
        CloseableHttpResponse result = client.execute(putMethod);
        //处理结果

            String resData = EntityUtils.toString(result.getEntity());
            JSONObject jsonObject1 = json.parseObject(resData);
        int code = (int) jsonObject1.get("code");
        if(code==1){//成功
            int count= (int) jsonObject1.get("count");
            int draft_count= (int) jsonObject1.get("draft_count");
            int checking_count= (int) jsonObject1.get("checking_count");
            double checking_rate= Double.parseDouble( jsonObject1.get("checking_rate").toString());
            int valid_count= (int) jsonObject1.get("valid_count");
            int invalid_count= (int) jsonObject1.get("invalid_count");
            System.out.println(count+"===="+draft_count+"===="+checking_count
            +"====="+checking_rate+"==="+valid_count+"=="+invalid_count);
        }


        return jsonObject1;
    }
}
