package com.erui.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * SSO的token工具
 * Created by wangxiaodan on 2018/2/28.
 */
public class EruitokenUtil {
    private static Logger logger = LoggerFactory.getLogger(EruitokenUtil.class);
    public static String TOKEN_NAME = "eruitoken";

    /**
     * 获取sso的token
     * @param request
     * @return
     */
    public static String getEruiToken(ServletRequest request) {
        String token = null;
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // 获取cookie中的token
            Cookie eruitokenCookie = WebUtils.getCookie(httpRequest, TOKEN_NAME);
            if (eruitokenCookie != null) {
                return eruitokenCookie.getValue();
            }
            // 如果cookie中不存在eruitoken,则获取header中的token信息
            Enumeration headerNames = httpRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = ((String) headerNames.nextElement()).toLowerCase();
                if (TOKEN_NAME.equals(headerName)) {
                    token = httpRequest.getHeader(token);
                }
            }
        }catch (Exception ex) {
            logger.error("获取用户token异常",ex);
        }
        return token;
    }


}
