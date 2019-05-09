package com.erui.order.util;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/1/30.
 */
public class SsoUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoUtils.class);

    //获取当前登录用户
    public static JSONObject ssoUserInfo(String ssoUrl, String eruiToken) {
        if (StringUtils.isNotBlank(eruiToken)) {
            try {
                Map<String, String> header = new HashMap<>();
                String jsonParam = "{\"token\":\"" + eruiToken + "\"}";
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String s = HttpRequest.sendPost(ssoUrl, jsonParam, header);
                LOGGER.info("CRM返回信息：{}", s);

                JSONObject jsonObject = JSONObject.parseObject(s);

                if (jsonObject.getInteger("code") == 200) {
                    return jsonObject;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
