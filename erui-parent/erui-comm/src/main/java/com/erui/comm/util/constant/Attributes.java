package com.erui.comm.util.constant;

public class Attributes {
	/**文件分隔符*/
	public static final String separatorChar = "/";
	

	/**********************************************************
	 *                    文件上传相关状态值                                       *
	 *********************************************************/
	public final static Integer UPLOAD_FILE_FAIL = -1; // -1 上传失败
	public final static Integer UPLOAD_FILE_SUCCESS = 0; // 0 上传成功   
	public final static Integer UPLOAD_FILE_FORMART_ERROR = 1; // 1 上传文件类型不正确  
	public final static Integer UPLOAD_FILE_OUT_SIZE = 2; // 2 上传文件超过指定大小
	
	/**********************************************************
	 *                      模块操作相关                                              *
	 *********************************************************/
	//用户
	public final static Integer ADD_USER_UPLOAD_PHOTO = 3; //添加用户上传用户头像
	public final static Integer UPDATE_USER_UPLOAD_PHOTO = 4; //修改用户上传用户头像
	
	//邮件
	public final static Integer SEND_MAIL_UPLOAD_FILE = 5; //发送邮件上传文件 
	public final static Integer SEND_MAIL_SUCCESS = 6; //邮件发送成功
	public final static Integer SEND_MAIL_FAIL = 7; //邮件发送失败
	public final static Integer SEND_WAY_SINGLE_SHOT = 8; //邮件单发
	public final static Integer SEND_WAY_MASS = 9; //邮件群发
}