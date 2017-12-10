package com.erui.comm.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件读写操作类
 * 
 * @date 2015年11月2日 下午5:49:01
 * @history
 * 	<add>
 * 		@functionName 
 *  	@author 
 *  	@date 2015年11月2日 下午5:49:01
 *  	@remark 
 * 	</add>
 * 	<upd> 
 * 		@functionName 
 *  	@author 
 *  	@date 2015年11月2日 下午5:49:01
 *  	@remark 
 * 	</upd>
 */
public class FileReadWriteUtil {

	/**
	 * 读取文件中的内容转成字符串，指定编码默认utf-8
	 * @param filepath
	 * @param charset
	 * @author Leo
	 * @return
	 */
	public static String readFile(String filepath, String charset) {
		if (filepath == null) {
			new RuntimeException("文件路径不能为空！").printStackTrace();
		}
		
		if (charset == null || charset.equals("")) {
			charset = "utf-8";
		}
		
		Path file = Paths.get(filepath);
		if (file == null) {
			new RuntimeException("文件路径不能为空！").printStackTrace();
		}
		
		return readFile(file, charset);
	}
	
	
	/**
	 * 读取文件中的内容转成字符串，指定编码默认utf-8
	 * @param filepath
	 * @param charset
	 * @author Leo
	 * @return
	 */
	public static String readFile(Path filepath, String charset) {
		
		if (filepath == null) {
			new RuntimeException("文件路径不能为空！").printStackTrace();
		}
		
		if (charset == null || charset.equals("")) {
			charset = "utf-8";
		}
		
		if(!Files.exists(filepath)) {
			new RuntimeException("文件路径不存在！").printStackTrace();
		}
		
		Charset chrst = Charset.forName(charset);
		StringBuilder sb = new StringBuilder () ;
		try (
				FileInputStream fis = new FileInputStream(filepath.toFile());
				FileChannel fc = fis.getChannel();
				) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			while ( fc.read (buffer) != - 1 )
			{
			   buffer.flip();
			   sb.append (chrst.decode(buffer)) ;
			   buffer.clear ( ) ;
			}
		} catch (IOException e) {
			e.printStackTrace();
			new RuntimeException("读取文件出现错误！").printStackTrace();
		}
		
		return sb.toString();
	}
	
	/**
	 * 读取文件中的内容转成字符串
	 * @param filepath
	 * @author Leo
	 * @return
	 */
	public static String readFile(String filepath) {
		if (filepath == null) {
			new RuntimeException("文件路径不能为空！").printStackTrace();
		}
		
		Path file = Paths.get(filepath);
		if (file == null) {
			new RuntimeException("文件路径不能为空！").printStackTrace();
		}
		
		return readFile(file);
	}
	
	
	/**
	 * 读取文件中的内容转成字符串
	 * @param filepath
	 * @author Leo
	 * @return
	 */
	public static String readFile(Path filepath) {
		
		if (filepath == null) {
			new RuntimeException("文件路径不能为空！").printStackTrace();
		}
		if(!Files.exists(filepath)) {
			new RuntimeException("文件路径不存在！").printStackTrace();
		}
		
		String text =null;
		try (
				FileInputStream fis = new FileInputStream(filepath.toFile());
				FileChannel fc = fis.getChannel();
				) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			fc.read(buffer);
			text = new String(buffer.array());
		} catch (IOException e) {
			e.printStackTrace();
			new RuntimeException("读取文件出现错误！").printStackTrace();
		}
		
		return text;
	}
	
	/**
	 * 创建文件并写入内容
	 * filepath ：文件存放路径
	 * filename ：文件名
	 * text     ：内容
	 * 
	 * @date 2015年9月8日 上午8:53:29
	 * @throws 
	 * @return void 返回类型
	 */
	public static void writeFile(String filepath, String filename, String text) {
		File path = new File(filepath);
		File file = new File(path, filename);
		try {
			if (path.exists()) 
				path.mkdirs();//如果文件夹不存在，则创建	
			if(file.exists()) 
				file.createNewFile();//如果文件不存在，则创建
			else {
				file.delete();
				file.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			out.write(text);
			if(null != out){
				out.flush();
				out.close();
				out = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			new RuntimeException("文件写入失败").printStackTrace();
		}
	}

	
	
	
	
	/**
	 * 拷贝文件
	 * 
	 * @date 2015年9月8日 上午10:17:32
	 * @throws 
	 * @return void 返回类型
	 */
	public static void copyFile(InputStream in, OutputStream out) {
		try {
			byte[] buffer = new byte[1024];  
			int length = 0;  
			while ((length = in.read(buffer)) > 0) {  
			    out.write(buffer, 0, length);  
			}
		} catch (IOException e) {
			e.printStackTrace();
			new RuntimeException("文件拷贝失败").printStackTrace();
		}  
	}

	/**
	 * 释放流资源
	 * 
	 * @date 2015年11月2日下午5:49:14
	 * @param 
	 * @return 
	 * @throws
	 */ 
	public static void colse(OutputStream out, InputStream in) {
			//关闭输出流
			try {
				if(null != out){
					out.close();
					out = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				new RuntimeException("输出流关闭失败").printStackTrace();
			}
			
			//关闭输入流
			try {
				if(null != in){
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				new RuntimeException("输入流关闭失败").printStackTrace();
			}
	}
	
	/**
	 * 获取文件所在的盘符路径
	 * 
	 * @date 2015年11月2日下午5:48:17
	 * @param 
	 * @return 
	 * @throws
	 */ 
	public static String classpath() {
		String path = FileReadWriteUtil.class.getClassLoader().getResource("").getPath();
		
		if(path.contains("WebRoot")){
			path = path.substring(1, path.indexOf("WebRoot"));
		} else if(path.contains("WebContent")){
			path = path.substring(1, path.indexOf("WebContent"));
		} else if(path.contains("target")){
			path = path.substring(1, path.indexOf("target"));
		} else if(path.contains("build")){
			path = path.substring(1, path.indexOf("build"));
		} else if(path.contains("WEB-INF")){
			path = path.substring(1, path.indexOf("WEB-INF"));
		}else {
			try {
				throw new Exception("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
}
