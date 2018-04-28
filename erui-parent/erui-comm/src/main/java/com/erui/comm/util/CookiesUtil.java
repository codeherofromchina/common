package com.erui.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * SSO的token工具
 * Created by wangxiaodan on 2018/2/28.
 */
public class CookiesUtil {
    private static Logger logger = LoggerFactory.getLogger(CookiesUtil.class);
    public static String TOKEN_NAME = "eruitoken";
    public static String LANG_KEY_NAME = "language";

    /**
     * 获取sso的token
     *
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
                logger.info("eruitokenCookie-token : " + eruitokenCookie.getValue());
                return eruitokenCookie.getValue();
            }
            // 如果cookie中不存在eruitoken,则获取header中的token信息
            token = httpRequest.getHeader(TOKEN_NAME);
        } catch (Exception ex) {
            logger.error("获取用户token异常", ex);
        }
        logger.info("getEruiToken-Token : " + String.valueOf(token));
        return token;
    }


    /**
     * 获取sso的token
     *  默认中文
     * @param request
     * @return
     */
    public static String getLang(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 获取cookie中的lang - 语言
        Cookie langCookie = WebUtils.getCookie(httpRequest, LANG_KEY_NAME);
        if (langCookie != null) {
            logger.info("lang-cookie : " + langCookie.getValue());
            return langCookie.getValue();
        }
        return "zh";
    }

}
