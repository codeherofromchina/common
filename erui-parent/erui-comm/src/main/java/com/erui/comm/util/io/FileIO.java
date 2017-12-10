package com.erui.comm.util.io;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Date;


public class FileIO {
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>读取文件</h3>
	 * 	&emsp;&emsp;描述：读取提供的文本的内容。
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月9日上午8:53:55
	 * @param 
	 * @throws
	 * @return
	 */
	public static String content() {
		InputStream in = FileIO.class.getResourceAsStream("/email.tmpl");
		Reader reader = null;
		
		StringBuilder builder = new StringBuilder(); 
        try { 
        	reader = new InputStreamReader(in, "utf-8"); 
            char[] c = new char[1024];
            int len = 0;
            while ((len = reader.read(c)) != -1) {
            	builder.append(c, 0, len); 
			}
        } catch (UnsupportedEncodingException e) { 
                e.printStackTrace(); 
        } catch (FileNotFoundException e) { 
                e.printStackTrace(); 
        } catch (IOException e) { 
                e.printStackTrace(); 
        } finally {
    		try {
    			if(reader != null){
    				reader.close();
    				reader = null;
    			}
    			if(in != null){
    				in.close();
    				in = null;
    			}
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
		return builder.toString();
	}
	
	public static byte[] file2byte(String filePath) {
		return file2byte(new File(filePath));
	}
	
	public static byte[] file2byte(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>读取文件</h3>
	 * 	&emsp;&emsp;描述：读取提供的文本的内容。
	 * </div>
	 * @author xiongyan
	 * @email javadevyan@163.com
	 * @date 2016年3月9日上午8:53:55
	 * @param 
	 * @throws
	 * @return
	 */
	public static String content(File file) {
		StringBuilder builder = readText(file);
		return builder.toString();
	}
	

	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>读取文件</h3>
	 * 	&emsp;&emsp;描述：读取提供的文本的内容。
	 * </div>
	 * @author xiongyan
	 * @email javadevyan@163.com
	 * @date 2016年3月9日上午8:53:55
	 * @param 
	 * @throws
	 * @return
	 */
	public static String content(String filename) {
		return readText(new File(filename)).toString();
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：夺取text文本的内容并返回。
	 * </div>
	 * @author xiongyan
	 * @email javadevyan@163.com
	 * @date 2016年4月8日上午11:20:33
	 * @param 
	 * @throws
	 * @return
	 */
	private static StringBuilder readText(File file) {
		StringBuilder builder = new StringBuilder();
		try (
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
		){
			String s = "";
			while ((s = reader.readLine()) != null) {// 使用readLine方法，一次读一行
				builder.append(s).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder;
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：根据文件名创建文件并向文件中写入数据（文本文件）。jdk必须是1.7或更高的版本。
	 * </div>
	 * @author xi0ongyan
	 * @email javadevyan@163.com
	 * @date 2016年5月11日上午9:37:14
	 * @param fileName 文件名，可以为null，如果输入需要写入扩展名，如果未输入需要设置扩展名。
	 * @param content 文件内容
	 * @throws
	 * @return
	 */
	public static File touchByFileName(String fileName, String content, boolean isReadOnly) {
		return execTouch(fileName, content, null, isReadOnly);
	}
	
	public static void main(String[] args) {
		touchByFileName("D:\\test.html", "fgiuwyfiuwe", false);
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：创建文件并向其中写入内容
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月9日上午8:55:50
	 * @param fileName 文件名，可以为null，如果输入需要写入扩展名，如果未输入需要设置扩展名。
	 * @param content 文件内容
	 * @param ext 扩展名。如：xml,java,sql
	 * @param isReadOnly 是否将文件设置只读。true：只读，false：可读可写
	 * @throws
	 * @return
	 */
	private static File execTouch(String fileName, String content, String ext, boolean isReadOnly) {
		File file = null;
		if(!StringUtil.isEmpty(fileName)){
			file = new File(fileName);
		} else {
			if (StringUtil.isEmpty(ext)) new RuntimeException("扩展名不能为空").printStackTrace();
			else file = new File("default_" + DateUtil.formatDate2String(new Date(), "yyyy-MM-dd") + "." + ext);
		}
		
		try (
			FileOutputStream out = new FileOutputStream(file, true);
			OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
		) {
			bufferedWriter.write(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**
		 * 设置文件为只读
		 */
		if(isReadOnly){
			if(!file.setReadOnly()){
				new RuntimeException("文件只读权限设置失败").printStackTrace();
				file.deleteOnExit();
			};
		}
		
		return file;
	}
}