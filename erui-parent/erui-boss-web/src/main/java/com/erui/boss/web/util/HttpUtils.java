package com.erui.boss.web.util;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by wangxiaodan on 2018/10/12.
 */
public class HttpUtils {
    public static void setExcelResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {


        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }
}
