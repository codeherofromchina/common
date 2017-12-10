package com.erui.comm.util.constant;

/**
 * 公共变量。比如：状态值，操作类型等等。
 * 
 * @author Xiongyan
 * @date 2015年7月15日 下午4:54:34
 * @version 0.0.1
 */
public class PublicStaticVariable {
	
	/**********************************************************
	 *                    文件上传相关状态值                                       *
	 *********************************************************/
	public final static Integer UPLOAD_FILE_FAIL = -1; // -1 上传失败
	public final static Integer UPLOAD_FILE_SUCCESS = 0; // 0 上传成功   
	public final static Integer UPLOAD_FILE_FORMART_ERROR = 1; // 1 上传文件类型不正确  
	public final static Integer UPLOAD_FILE_OUT_SIZE = 2; // 2 上传文件超过指定大小
	public final static Integer UPLOAD_EMPTY_FILE_DENIED = 3; // 3 不允许上传空文件
	public final static Integer UPLOAD_FILE_NOT_EXIST = 4; // 4 上传文件不存在
	
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
	
	/**********************************************************
	 *                    其他                                       *
	 *********************************************************/
	public final static String REDIS_CACHE_KEY = "redis_cache_key_";
	//缓存过期时间，单位秒
	public final static int CACHE_EXPIRED_INTERVAL = 10;
	public final static String URL_RULE_CLASSID = "{classid}";
	public final static String URL_RULE_ID = "{id}";
	//获取标签属性的正则表达式(不能匹配没有写单引号或者双引号的属性)
	public final static String GET_LABEL_ATTR_REGEX = "(?<name>[^\\s'\"=]*?)\\s*=\\s*(?<tag>['|\"])(?<value>.*?)\\k<tag>";
	//获得没有写双引号或者单引号的属性
	public final static String GET_EMPTY_SPLIT_TAG_LABEL_ATTR_REGEX = "(?<name>[^\\s=]*?)=(?<value>[^\\s\"']*?)(?<tail>[\\s;>\\$])";
	public final static String PUBLISHED_CONTENT_IDS = "published_content_ids";
	public static final String TEMPLATE_DIC = "templates_dic";
	public static final String TEMPLATE_CUSTOM_ATTRIBUTE_MODEL = "customDataModel";
	public static final String AJAX_RECORD = "ajaxRecord";
	public static final String AJAX_PAGE_RECORD = "ajaxPageRecord";
}
