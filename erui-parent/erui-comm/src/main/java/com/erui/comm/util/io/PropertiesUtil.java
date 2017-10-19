package com.erui.comm.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <div style="color:green;font-weight: bolder;">
 * 	<h3>Properties工具</h3>
 * 	&emsp;&emsp;描述：Properties文件读写工具集。
 * </div>
 * @author xiongyan
 * @date 2016年2月1日 上午11:24:46
 * @history
 * 	<add>
 * 		@functionName yan.file.read.PropertiesUtil.openProperties(String)
 *  	@author xiongyan
 *  	@date 2016年2月1日 上午11:24:46
 *  	@remark 详情见具体方法注释
 * 	</add>
 * 	<upd> 
 * 		@functionName 
 *  	@author 
 *  	@date 2016年2月1日 上午11:24:46
 *  	@remark 
 * 	</upd>
 */
public class PropertiesUtil {
	
	private static InputStream in;
	
	private PropertiesUtil(){}
	
	private PropertiesUtil(String filename){
		in = PropertiesUtil.class.getResourceAsStream("/" + filename);
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>读取Properties文件</h3>
	 * 	&emsp;&emsp;描述：读取指定文件名的Properties文件，并返回Properties对象。
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月1日上午11:31:36
	 * @param 
	 * 		filename：文件名
	 * @throws RuntimeException
	 * @return Properties
	 */
	public static Properties openProperties(String filename) {
		Properties properties = null;
		try {
			new PropertiesUtil(filename);
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载Properties文件错误");
		} finally {
			if(null != in){
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("InputStream资源释放失败");
				}
			}
		}
		return properties;
	}
	
}
