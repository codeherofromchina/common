package com.erui.order.util;

import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.OrderConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther 王晓丹
 * @Date 2019/4/21 上午11:07
 */
public class CrmUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrmUtils.class);

    /**
     * 升级用户
     *
     * @param crmCode
     * @param eruiToken
     * @return
     */
    public static String autoUpgrade(String crmCode, String eruiToken) {
        String jsonParam = "{\"crm_code\":\"" + crmCode + "\"}";
        Map<String, String> header = new HashMap<>();
        header.put(CookiesUtil.TOKEN_NAME, eruiToken);
        header.put("Content-Type", "application/json");
        header.put("accept", "*/*");
        String s = HttpRequest.sendPost(OrderConf.getInstance().getCrmUrl() + "/buyer/autoUpgrade", jsonParam, header);
        LOGGER.info("调用升级CRM用户接口，CRM返回信息：" + s);
        return s;
    }
}
