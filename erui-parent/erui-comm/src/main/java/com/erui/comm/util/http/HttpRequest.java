package com.erui.comm.util.http;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));//防止乱码
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, Map<String, String> header) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.err.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取token
                String eruiToken = (String) ThreadLocalUtil.getObject();
                //if (StringUtils.isNotBlank(eruiToken)) {
                    //try {
                    // 根据id获取商务经办人信息
                    String jsonParam = "{\"id\":\"" + "33464" + "\"}";
                    Map<String, String> header = new HashMap<>();
                    //header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                    //auth=adf73QWyZ/BwVqlooWdK0mUHiVS/iEEESkGlt8PrD1C1zDU18EqWBm5QUvA; language=zh; eruirsakey=beafa63ae3f34c5d883b8bfaecded955; eruitoken=8cee92eef2a47fa21ce32e9f29112cbd; JSESSIONID=29386BBBD7B5C0DB66A89123EC20CC30
                    header.put("Cookie", "auth=adf73QWyZ/BwVqlooWdK0mUHiVS/iEEESkGlt8PrD1C1zDU18EqWBm5QUvA; language=zh; eruirsakey=ed55d2b71d144c0eb6ef45e6793730f9; eruitoken=952c880ba8bef88952307839250094e9; JSESSIONID=4C76CAEF4EDE097918BA1D8E42E2C554");
                    header.put("Content-Type", "application/json");
                    header.put("accept", "*/*");
                    String userInfo = HttpRequest.sendPost("http://api.erui.com/v2/user/info", jsonParam, header);
                    //钉钉通知接口头信息
                    Map<String, String> header2 = new HashMap<>();
                    header2.put("Cookie", "tu=0fe5424cc16e85ff0909c37fc731d65e; language=zh; eruirsakey=b9a04d60e5a04906923486838d9e6bd2; eruitoken=741c65c231e49108ff03e974df482a7c; JSESSIONID=D91073080AA27AE155CC740986AD3B6C");
                    header2.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                    // 获取商务经办人手机号
                    JSONObject jsonObject = JSONObject.parseObject(userInfo);
                    Integer code = jsonObject.getInteger("code");
                    String userName = null;  //商务经办人手机号
                    //if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //userName = data.getString("name");
                    //发送短信
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("toUser=").append("33464");

                    //【销售合同审批通知】您好！姜洪伟的订单，已申请销售合同审批。
                    stringBuffer.append("&message=您好！" + userName + "的订单，已申请销售合同审批。CRM客户代码：" + "shuaiGuo11133"+ "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！");
                    stringBuffer.append("&type=toUser");
                    Long startTime = System.currentTimeMillis();
                    String s1 = HttpRequest.sendPost("http://dingtalk.erui.com/notice", stringBuffer.toString(), header2);
                    Long endTime = System.currentTimeMillis();
                    System.out.println("发送通知耗费时间：" + (endTime - startTime) / 1000);
                    //}
                    //} catch (Exception e) {
                    //    throw new MyException(String.format("%s%s%s", "发送钉钉通知异常失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Sending SMS exceptions to failure"));
                    //}

              //  }
            }
        }).start();
    }
}
