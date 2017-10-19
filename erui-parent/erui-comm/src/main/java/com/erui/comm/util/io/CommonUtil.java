package com.erui.comm.util.io;

import com.erui.comm.util.data.string.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 公共util
 * 
 * @date 2015年9月16日 下午2:40:43
 * @version 0.0.1
 */
public class CommonUtil {
	//当前正在运行的程序的classpath路径
	private static String projectPath = CommonUtil.class.getClassLoader().getResource("").getPath();
	
	/**
	 * 获取web工程的项目名
	 * 
	 * @author Xiongyan
	 * @date 2015年9月16日 上午10:11:39
	 * @throws
	 * @return String 返回类型
	 */
	public static String getWebProjectName() {
		if(null == projectPath) return "";
		
		//检查字符串中是否包含指定的字符串
		String str = "";
		if(projectPath.contains("/WEB-INF/classes/")) 
			str = projectPath.substring(0, projectPath.indexOf("/WEB-INF/classes/"));
		else 
			return "";
		
		if(!StringUtils.isEmpty(str)) return str.substring(str.lastIndexOf("/")/* + 1*/, str.length());
		else return "";
	}
	/**
	 * 发送http请求
	 *
	 * @return Map<String,? extends Object>返回类型
	 * @throws
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
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += (line +"\n");
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
}
