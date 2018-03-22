package com.erui.comm.middle.fastdfs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.erui.comm.middle.fastdfs.model.FileModel;
import com.erui.comm.util.constant.Attributes;
import com.erui.comm.util.constant.PublicStaticVariable;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.data.string.StringUtils;
import com.erui.comm.util.data.uuid.UUIDGenerator;
import com.erui.comm.util.io.CommonUtil;
import com.erui.comm.util.io.FileReadWriteUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;


/**
 * <div style="color:green;font-weight: bolder;">
 * 	<h3>fastDFS工具</h3>
 * 	&emsp;&emsp;描述：使用faseDFS分布式文件系统对文件进行相关的操作。主要涉及一下一些操作：<br>
 *  &emsp;&emsp;&emsp;1、文件上传：org.fastdfs.util.FastDFSUtils.upload_file(String, String, NameValuePair[])<br>
 *  &emsp;&emsp;&emsp;2、文件下载：org.fastdfs.util.FastDFSUtils.download_file(String, String, String)<br>
 *  &emsp;&emsp;&emsp;3、文件删除：org.fastdfs.util.FastDFSUtils.delete_file(String, String)<br>
 *  &emsp;&emsp;&emsp;4、获取文件信息：org.fastdfs.util.FastDFSUtils.get_file_info(String, String)<br>
 *  &emsp;&emsp;&emsp;5、获取文件元数据：org.fastdfs.util.FastDFSUtils.get_metadata(String, String)
 * </div>
 * @date 2015年12月11日 下午2:35:27
 * @history
 */
public class FastDFSUtil {
	private static String ng_Server;
	/*//存储当前class文件所在的classpath路径
	private static String classpath = StringUtils.getClassPath(FastDFSUtil.class.getClassLoader().getResource("").getPath());
	//存储fdfs_client文件地址
	private static String conf_filename = classpath + "fdfs_client.conf";
	private static StorageClient storageClient;
	
	static{
		try {
			ClientGlobal.init(conf_filename);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient(trackerServer, storageServer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}*/
	
	private static StorageClient storageClient;
	
	static{
		try {
			init();
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient(trackerServer, storageServer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();

		}
	}
	
	private static void init() throws IOException, FileNotFoundException, MyException {
//		Properties properties = new Properties();
//		properties.load(new BufferedInputStream(FastDFSUtil.class.getResourceAsStream("/comm.properties")));
//		ng_Server = properties.getProperty("fdfs_ng_server");
//		StringBuilder text = new StringBuilder();
//		text.append("connect_timeout=").append(properties.getProperty("fdfs_connect_timeout")).append("\n")
//			.append("network_timeout=").append(properties.getProperty("fdfs_network_timeout")).append("\n")
//			.append("charset=").append(properties.getProperty("fdfs_charset")).append("\n")
//			.append("http.anti_steal_token=").append(properties.getProperty("fdfs_http.anti_steal_token")).append("\n")
//			.append("tracker_server=").append(properties.getProperty("fdfs_tracker_server"));
//		FileReadWriteUtil.writeFile(StringUtil.classpath(), "fdfs_client.conf", text.toString());
//
		String configPath = FastDFSUtil.class.getResource("/fdfs_client.conf").getFile();
		ClientGlobal.init(configPath);
//
//		File file = new File(StringUtil.classpath() + "fdfs_client.conf");
//		if(file.exists() && file.isFile()){
//			file.deleteOnExit();
//		}
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>上传文件</h3>
	 * 	&emsp;&emsp;描述：返回值String [] results，返回值说明如下：<br>
	 *  &emsp;&emsp;&emsp;results[0]：组名<br>
	 *  &emsp;&emsp;&emsp;results[1]：文件名<br>
	 *  &emsp;&emsp;&emsp;上传失败返回null
	 * </div>
	 * @date 2015年12月11日下午2:42:06
	 * @param 
	 * @throws
	 * @return
	 */
	public static String[] uploadFile(String local_filename, String file_ext_name, NameValuePair [] meta_list) throws IOException, MyException {
		return storageClient.upload_file(local_filename, file_ext_name, meta_list);
	}
	public static String[] uploadFile(String groupname,byte [] bytefile, String file_ext_name, NameValuePair [] meta_list) throws IOException, MyException {
		return storageClient.upload_file(groupname,bytefile, file_ext_name, meta_list);
	}


	/**
	 * 获取文件的内容，通过数据库中保存的文件路径
	 * @param path
	 * @return content string
	 */
	public static String getFileTextUsePath(String path) {
		if(StringUtil.isNotBlank(path)) {
			String fastUrl = FastDFSUtil.getNg_Server();
			String url = fastUrl+path;
			String str = CommonUtil.sendGet(url, null);
			if (StringUtil.isNotBlank(str)) {
				return str;
			}
		}
		return "";
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>文件下载</h3>
	 * 	&emsp;&emsp;描述：
	 * </div>
	 * @date 2015年12月11日下午2:47:21
	 * @param 
	 * @throws
	 * @return
	 */
	public static void downloadFile(String group_name, String remote_filename, String save_path) throws IOException, MyException {
		byte [] b = storageClient.download_file(group_name, remote_filename);
		String file_extension_name = StringUtils.getExtension(remote_filename);
		char last_char = save_path.charAt(save_path.length() - 1);
		IOUtils.write(b, new FileOutputStream((File.separatorChar == last_char ? "" : File.separator) + save_path + File.separator + UUID.randomUUID().toString() + "." + file_extension_name));
	}
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>文件下载</h3>
	 * 	&emsp;&emsp;描述：返回文件名
	 * </div>
	 * @date 2015年12月11日下午2:47:21
	 * @param
	 * @throws
	 * @return saveFailName
	 */
	public static String downloadFile2(String group_name, String remote_filename, String save_path) throws IOException, MyException {
		byte [] b = storageClient.download_file(group_name, remote_filename);
		String file_extension_name = StringUtils.getExtension(remote_filename);
		char last_char = save_path.charAt(save_path.length() - 1);
		String randomFileName=File.separator + UUID.randomUUID().toString();
		IOUtils.write(b, new FileOutputStream((File.separatorChar == last_char ? "" : File.separator) + save_path +randomFileName + "." + file_extension_name));
		return  randomFileName + "." + file_extension_name;
	}

	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>删除文件</h3>
	 * 	&emsp;&emsp;描述：返回值 == 0，删除成功，否则失败
	 * </div>
	 * @date 2015年12月11日下午2:48:15
	 * @param 
	 * @throws
	 * @return
	 */
	public static Integer deleteFile(String group_name, String remote_filename) throws IOException, MyException {
		return storageClient.delete_file(group_name, remote_filename);
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>获取文件信息</h3>
	 * 	&emsp;&emsp;描述：从文件名中得到文件信息，如有必要，从存储器中取出。<br>
	 *  &emsp;&emsp;&emsp;返回FileInfo对象为成功，返回null为失败。
	 * </div>
	 * @date 2015年12月11日下午2:50:04
	 * @param 
	 * @throws
	 * @return
	 */
	public static FileInfo getFileInfo(String group_name, String remote_filename) throws IOException, MyException {
		return storageClient.get_file_info(group_name, remote_filename);
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>获取元数据</h3>
	 * 	&emsp;&emsp;描述：从存储服务器获取所有元数据项目。<br>
	 *  &emsp;&emsp;&emsp;返回NameValuePair[]为成功，返回null为失败。
	 * </div>
	 * @date 2015年12月11日下午2:52:16
	 * @param 
	 * @throws
	 * @return
	 */
	public static NameValuePair [] getMetadata(String group_name, String remote_filename) throws IOException, MyException {
		return storageClient.get_metadata(group_name, remote_filename);
	}
	
	/**
	 * 上传文件到分布式文件系统fastdfs中，成功后将保存在临时目录下的文件删除，并返回上传的状态信息。
	 * 多文件上传时，文件路径参数filepath以“;”分割，比如："D:/DHSJK.PNG;E:/HDJ.DOC"。
	 * 
	 * @date 2017年01月03日 下午6:07:06
	 * @throws 
	 * @return List<FileName> 返回类型
	 * @param 
	 * 		userId           ：当前登录用户ID<br>
	 * 		maxFileSizeCount ：指定要上传的文件大小<br>
	 * 		fileTypes        ：指定允许上传的文件类型<br>
	 * 		files            ：上传的文件
	 *      type             : 0 临时文件  1 上传文件
	 */
	public static List<FileModel> uploadFastdfs(String userId, Float maxFileSizeCount, String fileTypes, List<MultipartFile> files, Integer type) {
		List<FileModel> list = new ArrayList<>();
		String path = "";
		
		switch (type) {
		case 0:
			path = "upload" + Attributes.separatorChar + "temp" + Attributes.separatorChar + DateUtil.formatDateToString(new Date() ,"yyyy-MM-dd") + Attributes.separatorChar + userId + Attributes.separatorChar;//时间+用户做为文件保存路径。
			break;
		case 1:
			path = "upload" + Attributes.separatorChar + DateUtil.formatDateToString(new Date() ,"yyyy-MM-dd") + Attributes.separatorChar + userId + Attributes.separatorChar;//时间+用户做为文件保存路径。
			break;
		default:
			break;
		}
		
		File savePath = new File(FileReadWriteUtil.classpath() + Attributes.separatorChar + path);
		//判断该路径是否存在
		if (!savePath.exists()) {
			boolean isMkdirs = savePath.mkdirs();//创建目录
			if(isMkdirs){
				if (!savePath.canWrite()) {//判断目录是否有写权限。true：具有写权限 false：不具有写权限
					savePath.setWritable(true, false);//对所有用户设置写权限。
				}
			}
		} else {
			if (!savePath.canWrite()) {//判断目录是否有写权限。true：具有写权限 false：不具有写权限
				savePath.setWritable(true, false);//对所有用户设置写权限。
			}
		}
		
		//判断文件总大小
		Float fileSizeCount = 0f;
		for (MultipartFile multipartFile : files) {
			fileSizeCount += Float.valueOf(multipartFile.getSize())/Float.valueOf(1024)/Float.valueOf(1024);
		}
		if (fileSizeCount > maxFileSizeCount) {
			FileModel fileModel = new FileModel();
			fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_OUT_SIZE);
			list.add(fileModel);
			return list;
		}
		
		//判断上传文件类型是否合法
		for (MultipartFile multipartFile : files) {
			if (!fileTypes.toUpperCase().contains(StringUtils.getExtension(multipartFile.getOriginalFilename()).toUpperCase())) {
				FileModel fileModel = new FileModel();
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FORMART_ERROR);
				list.add(fileModel);
				return list;
			}
		}
		
		for (MultipartFile multipartFile : files) {
			FileModel fileModel = new FileModel();
			//1、更新文件的名字
			String uuid = UUIDGenerator.getUUID();
			switch (type) {
			case 0:
				fileModel.setFileName(uuid + "." + StringUtils.getExtension(multipartFile.getOriginalFilename()));
				break;
			case 1:
				fileModel.setFileName(uuid + "." + StringUtils.getExtension(multipartFile.getOriginalFilename()));
				break;
			default:
				break;
			}
			fileModel.setUrl(path + fileModel.getFileName())
					 .setContentType(multipartFile.getContentType())
					 .setFileFormat(StringUtils.getExtension(multipartFile.getOriginalFilename()))
					 .setFileSize(multipartFile.getSize())
					 .setName(multipartFile.getOriginalFilename());
			
			try {
				//2、上传文件
				switch (type) {
				case 0:
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(savePath, uuid + "." + StringUtils.getExtension(multipartFile.getOriginalFilename())));
					break;
				case 1:
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(savePath, uuid + "." + StringUtils.getExtension(multipartFile.getOriginalFilename())));
					File dfile = new File(savePath.getPath()+Attributes.separatorChar+uuid + "." + StringUtils.getExtension(multipartFile.getOriginalFilename()));
					String [] retVals = FastDFSUtil.uploadFile(dfile.getPath(), null, null);
					if(retVals == null)
					{
						new RuntimeException("文件上传失败").getStackTrace();
						fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FAIL);
					}
					else
					{
						fileModel.setUrl(retVals[0] + Attributes.separatorChar +retVals[1]);
						if (dfile.exists()) {
							dfile.delete();
						}
					}
					break;
				default:
					break;
				}
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_SUCCESS);
			} catch (IOException e) {
				e.printStackTrace();
				new RuntimeException("文件上传失败").getStackTrace();
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FAIL);
			} catch (MyException e) {
				e.printStackTrace();
				new RuntimeException("文件上传失败").getStackTrace();
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FAIL);
			}
			list.add(fileModel);
		}
		return list;
	}
	
	
	/**
	 * 上传文件到分布式文件系统fastdfs中，成功后将保存在临时目录下的文件删除，并返回上传的状态信息。
	 * 多文件上传时，文件路径参数filepath以“;”分割，比如："D:/DHSJK.PNG;E:/HDJ.DOC"。
	 * 
	 * @date 2017年01月03日 下午6:07:06
	 * @throws 
	 * @return List<FileName> 返回类型
	 * @param 
	 * 		userId           ：当前登录用户ID<br>
	 * 		maxFileSizeCount ：指定要上传的文件大小<br>
	 * 		fileTypes        ：指定允许上传的文件类型<br>
	 * 		files            ：上传的文件
	 *      type             : 0 临时文件  1 上传文件
	 */
	public static List<FileModel> uploadFastdfs(String userId, Float maxFileSizeCount, String fileTypes, File file, Integer type) {
		List<FileModel> list = new ArrayList<>();
		String path = "";
		
		switch (type) {
		case 0:
			path = "upload" +Attributes.separatorChar + "temp" + Attributes.separatorChar + DateUtil.formatDateToString(new Date() ,"yyyy-MM-dd") + Attributes.separatorChar + userId + Attributes.separatorChar;//时间+用户做为文件保存路径。
			break;
		case 1:
			path = "upload" + Attributes.separatorChar + DateUtil.formatDateToString(new Date() ,"yyyy-MM-dd") + Attributes.separatorChar + userId + Attributes.separatorChar;//时间+用户做为文件保存路径。
			break;
		default:
			break;
		}
		
		
		File savePath = new File(FileReadWriteUtil.classpath() + Attributes.separatorChar + path);
		//判断该路径是否存在
		if (!savePath.exists()) {
			boolean isMkdirs = savePath.mkdirs();//创建目录
			if(isMkdirs){
				if (!savePath.canWrite()) {//判断目录是否有写权限。true：具有写权限 false：不具有写权限
					savePath.setWritable(true, false);//对所有用户设置写权限。
				}
			}
		} else {
			if (!savePath.canWrite()) {//判断目录是否有写权限。true：具有写权限 false：不具有写权限
				savePath.setWritable(true, false);//对所有用户设置写权限。
			}
		}
		
		if (file == null || !file.exists()) {
			FileModel fileModel = new FileModel();
			fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_NOT_EXIST);
			list.add(fileModel);
			return list;
		}
		
		long fileLength = file.length();
		if (fileLength <= 0) {
			FileModel fileModel = new FileModel();
			fileModel.setStatus(PublicStaticVariable.UPLOAD_EMPTY_FILE_DENIED);
			list.add(fileModel);
			return list;
		}
		
		//判断文件总大小
		Float fileSizeCount = 0f;
		fileSizeCount += Float.valueOf(fileLength)/Float.valueOf(1024)/Float.valueOf(1024);
		
		if (fileSizeCount > maxFileSizeCount) {
			FileModel fileModel = new FileModel();
			fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_OUT_SIZE);
			list.add(fileModel);
			return list;
		}
		
			//判断上传文件类型是否合法            
			if (!fileTypes.toUpperCase().contains(StringUtils.getExtension(file.getName()).toUpperCase())) {
				FileModel fileModel = new FileModel();
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FORMART_ERROR);
				list.add(fileModel);
				return list;
			}
		
			FileModel fileModel = new FileModel();
			//1、更新文件的名字
			String uuid = UUIDGenerator.getUUID();
			switch (type) {
			case 0:
				fileModel.setFileName(uuid + "." + StringUtils.getExtension(file.getName()));
				break;
			case 1:
				fileModel.setFileName(uuid + "." + StringUtils.getExtension(file.getName()));
				break;
			default:
				break;
			}
			fileModel.setUrl(path + fileModel.getFileName())
					 .setContentType("ftl")
					 .setFileFormat(StringUtils.getExtension(file.getName()))
					 .setFileSize(file.length())
					 .setName(file.getName());
			InputStream inps = null;
			try {
				inps = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			try {
				//2、上传文件
				switch (type) {
				case 0:
					FileUtils.copyInputStreamToFile(inps, new File(savePath, uuid + "." + StringUtils.getExtension(file.getName())));
					break;
				case 1:
					FileUtils.copyInputStreamToFile(inps, new File(savePath, uuid + "." + StringUtils.getExtension(file.getName())));
					File dfile = new File(savePath.getPath()+Attributes.separatorChar+uuid + "." + StringUtils.getExtension(file.getName()));
					String [] retVals = FastDFSUtil.uploadFile(dfile.getPath(), null, null);
					if(retVals == null)
					{
						new RuntimeException("文件上传失败").getStackTrace();
						fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FAIL);
					}
					else
					{
						fileModel.setUrl(retVals[0] + Attributes.separatorChar +retVals[1]);
						if (dfile.exists()) {
							dfile.delete();
						}
					}
					break;
				default:
					break;
				}
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_SUCCESS);
			} catch (IOException e) {
				e.printStackTrace();
				new RuntimeException("文件上传失败").getStackTrace();
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FAIL);
			} catch (MyException e) {
				e.printStackTrace();
				new RuntimeException("文件上传失败").getStackTrace();
				fileModel.setStatus(PublicStaticVariable.UPLOAD_FILE_FAIL);
			}
			list.add(fileModel);
		
		return list;
	}
	
	/**
	 * byte数组转换成16进制字符串
	 *  
	 * @author Xiongyan
	 * @date 2015年7月27日 下午6:09:44
	 * @param
	 * @throws 
	 * @return String 返回类型
	 */
	public static String bytesToHexString(byte[] srcbyte){ 
		byte[] src = null; 
		if(null != srcbyte && srcbyte.length > 0) src = srcbyte;
		else src = new byte[1024];
		
        StringBuilder stringBuilder = new StringBuilder();     
        if (null == src || src.length <= 0) {     
            return null;     
        }     
        for (int i = 0; i < src.length; i++) {     
            int v = src[i] & 0xFF;     
            String hv = Integer.toHexString(v);     
            if (hv.length() < 2) {     
                stringBuilder.append(0);     
            }     
            stringBuilder.append(hv);     
        }     
        return stringBuilder.toString();     
    }
	
	/**
		XML (xml)，文件头：3C3F786D6C
		HTML (html)，文件头：68746D6C3E
		Email [thorough only] (eml)，文件头：44656C69766572792D646174653A
		Outlook Express (dbx)，文件头：CFAD12FEC5FD746F 
		Outlook (pst)，文件头：2142444E 
		MS Word/Excel (xls.or.doc)，文件头：D0CF11E0
		MS Access (mdb)，文件头：5374616E64617264204A
		WordPerfect (wpd)，文件头：FF575043
		Postscript. (eps.or.ps)，文件头：252150532D41646F6265
		Adobe Acrobat (pdf)，文件头：255044462D312E
		Quicken (qdf)，文件头：AC9EBD8F 
		Windows Password (pwl)，文件头：E3828596 
		ZIP Archive (zip)，文件头：504B0304 
		RAR Archive (rar)，文件头：52617221 
		Wave (wav)，文件头：57415645 
		AVI (avi)，文件头：41564920 
		Real Audio (ram)，文件头：2E7261FD 
		Real Media (rm)，文件头：2E524D46 
		MPEG (mpg)，文件头：000001BA 
		MPEG (mpg)，文件头：000001B3
		Quicktime (mov)，文件头：6D6F6F76 
		Windows Media (asf)，文件头：3026B2758E66CF11 
		MIDI (mid)，文件头：4D546864
	*/
	public static String fileContentType(String type) {
		String _type = type.toUpperCase();
		if(_type.contains("FFD8FF")){
			return "jpg";
		} else if (_type.contains("89504E47")){
			return "png";
		} else if (_type.contains("47494638")){
			return "gif";
		} else if (_type.contains("49492A00")){
			return "tif";
		} else if (_type.contains("41433130")){
			return "dwg";
		} else if (_type.contains("38425053")){
			return "psd";
		} else if (_type.contains("7B5C727466")){
			return "rtf";
		} else if (_type.contains("68746D6C3E")){
			return "html";
		} else if (_type.contains("D0CF11E0")){
			return "MS Word/Excel (xls.or.doc)";
		} else if (_type.contains("504B0304")){
			return "zip";
		} else {
			return "未知类型";
		}
	}
	
	public static String getNg_Server() {
		return ng_Server;
	}
	
	public static void main(String [] aa)
	{
//		Path ftl = Paths.get("C:/Users/Leo/Desktop/test.ftl");
//		List<FileModel> fileModels = FastDFSUtil.uploadFastdfs("admin", 10000000f, "ftl", ftl.toFile(), new Integer(1));
//		for (FileModel fileModel : fileModels) {
//			String filePath = fileModel.getUrl();
//			System.out.println(filePath);
//		}
		try {
			//FileInfo f= FastDFSUtil.getFileInfo("group1/M00/00/02", "wKgBLVhKRHSAPgBaABoYS7G0Eks945.JPG");
			//System.out.println(null==f);
			int code = FastDFSUtil.deleteFile("group1/M00/00/02", "wKgBLVhKRHSAPgBaABoYS7G0Eks945.JPG");
			System.out.println(code);
		} catch (IOException | MyException e) {
			e.printStackTrace();
		}
	}
	
}