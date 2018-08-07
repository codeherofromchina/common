package com.erui.report.util;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class Demo2 {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            URL url = new URL("http://www.baidu.com");
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress("172.21.1.8",80);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            URLConnection conn = url.openConnection(proxy);

            //以下三行是在需要验证时，输入帐号密码信息
            String headerkey = "Proxy-Authorization";
//            String headerValue = "Basic "+Base64.encodeToString("atco:atco".getBytes(), false); //帐号密码用:隔开，base64加密方式
//            conn.setRequestProperty(headerkey, headerValue);

            InputStream in = conn.getInputStream();
            // InputStream in = url.openStream();
            String s = IOUtils.toString(in, "utf-8");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
