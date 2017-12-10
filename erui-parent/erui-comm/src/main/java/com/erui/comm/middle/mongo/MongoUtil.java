package com.erui.comm.middle.mongo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoUtil {
	/**
	 * 添加过滤器访问日志
	  * <hhhsss>a</hhhsss>
	  * @author Juzhihai
	  * @date 2017年10月19日 上午9:13:27
	  * @throws 
	  * <history>aaa</history>
	  * @return void 返回类型
	 */
	public static void addRequestLog(String ip,String browser,String user,String requestUrl)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBObject log = new BasicDBObject();
		log.put("ip", ip);
		log.put("browser", browser);
		log.put("user", user);
		log.put("requestUrl", requestUrl);
		log.put("datetime", format.format(new Date()));
		MongoManager.insert("requestlog", log);
	}
	
	public static void addStaticLog(String methodName, String msg)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBObject log = new BasicDBObject();
		log.put("methodName", methodName);
		log.put("msg", msg);
		log.put("datetime", format.format(new Date()));
		MongoManager.insert("staticizelog", log);
	}
	public static void addSMSLog(String mobile,String content,String rmessage)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBObject sms = new BasicDBObject();
		sms.put("mobile", mobile);
		sms.put("content", content);
		sms.put("rmessage", rmessage);
		sms.put("datetime", format.format(new Date()));
		MongoManager.insert("sms", sms);
	}
	public static void addMTemplatLog(String op,String url,String id)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBObject temp = new BasicDBObject();
		temp.put("op", op);
		temp.put("url", url);
		temp.put("id", id);
		temp.put("datetime", format.format(new Date()));
		MongoManager.insert("mtenplat", temp);
	}
	public static void addPowerLog(String op,String userid,String dataid,String tablename)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBObject temp = new BasicDBObject();
		temp.put("op", op);
		temp.put("userid", userid);
		temp.put("id", dataid);
		temp.put("id", tablename);
		temp.put("datetime", format.format(new Date()));
		MongoManager.insert("power", temp);
	}
	public static void addClickLog(String op,String userid,String dataid,String tablename)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBObject temp = new BasicDBObject();
		temp.put("op", op);
		temp.put("userid", userid);
		temp.put("id", dataid);
		temp.put("id", tablename);
		temp.put("datetime", format.format(new Date()));
		MongoManager.insert("click", temp);
	}
}
