package com.erui.boss.web.filter;

import com.erui.comm.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by juzhihai on 17/10/21.
 */
@Component("crossDomainFilter")
public class crossDomainFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(crossDomainFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // 跨域
            String origin = httpRequest.getHeader("Origin");
            if (origin == null) {
                httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            } else {
                httpResponse.addHeader("Access-Control-Allow-Origin", origin);
            }
//            httpResponse.addHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type, Accept,X-Cookie,token");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
            if ( httpRequest.getMethod().equals("OPTIONS") ) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Exception in crossDomainFilter.doFilter", e);
            throw e;
        }
    }
    @Override
    public void destroy() {
    }
}