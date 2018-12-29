package com.erui.out.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.encrypt.MD5;
import com.erui.out.web.util.Result;
import com.erui.out.web.util.ResultStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by wangxiaodan on 2018/3/19.
 * 统一门户验证
 */
@Component("userVerifyFilter")
public class UserVerifyFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(UserVerifyFilter.class);

    // 验证用户的秘钥
    @Value("#{webProp['user_verify_key']}")
    private String apiKeyOrder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);

        String jsonStr = StreamUtils.copyToString(requestWrapper.getInputStream(), Charset.forName("UTF-8"));
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        try {
            Integer buyerId = jsonObject.getInteger("buyer_id");
            String buyerNo = jsonObject.getString("buyer_no");
            String reqTime = jsonObject.getString("req_time");
            String hash = jsonObject.getString("hash");

            if (buyerId == null || StringUtils.isAnyBlank(buyerNo, reqTime, hash)) {
                logger.info("用户验证参数失败");
                throw new Exception("用户验证参数失败");
            }
            verfiy(buyerId, buyerNo, reqTime, hash);
        } catch (Exception ex) {
            PrintWriter writer = servletResponse.getWriter();
            writer.write(new Result<Object>(ResultStatusEnum.FAIL).setMsg(ex.getMessage()).toJsonString());
            writer.close();
            return;
        }

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
    }

    /**
     * 验证
     *
     * @param buyer_id
     * @param buyer_no
     * @param req_time
     * @param hash
     * @throws Exception
     */
    private void verfiy(Integer buyer_id, String buyer_no, String req_time, String hash) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiKeyOrder).append('#').append(buyer_id).append('#').append(buyer_no).append('#').append(req_time).append('#').append(apiKeyOrder);
        String encode = MD5.encode(stringBuilder.toString());
        if (!StringUtils.equals(hash, encode)) {
            logger.info("用户验证未通过");
            throw new Exception("用户验证未通过");
        }
    }
}
