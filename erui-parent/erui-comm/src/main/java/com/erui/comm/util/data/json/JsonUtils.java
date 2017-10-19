package com.erui.comm.util.data.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.erui.comm.util.data.string.StringUtils;
import com.google.gson.Gson;

/**
 * json工具类
 * @date 2015年9月17日 下午2:52:50
 * @history
 * 	<add>
 * 		@functionName com.common.util.JsonUtils.json2Obj(String)
 *  	@author xiongyan
 *  	@date 2015年9月17日 下午2:52:50
 *  	@remark 使用方法见方法注释详细说明
 * 	</add>
 * 	<upd> 
 *  	@author xiongyan
 *  	@date 2015年9月17日 下午2:52:50
 *  	@remark 更新类注释
 * 	</upd>
 */
public class JsonUtils {

	/**
	 * <summary>
	 * 将指定的字符串转换成json对象，并以string类型返回
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String stringToJSON(String str ,String c) {
		List<String> list = StringUtils.stringToList(str, c);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	/**
	 * <summary>
	 * 将json字符串转换成list对象
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	@SuppressWarnings("unchecked")
	public static List<String> jsonToList(String json) {
		List<String> list = null;
		try
		{
			list = new Gson().fromJson(json, List.class);
		}
		catch(Exception e)
		{
			list = null;
		}
		return list;
	}
	
	/**
	 * 将json对象转换成java对象List<Json2JavaObj>,Json2JavaObj类中属性List<Json2JavaObj>，使用方法如下：
	 * <pre>
	 * String json = "[{\"code\":\"PRO_008\",\"parent\":\"0\",\"pname\":\"true\"},{\"code\":\"PRO_009\",\"parent\":\"0\",\"pname\":\"false\"}]";
	 * List<Json2JavaObj> maps = JsonUtils.json2Obj();
	 * for (Json2JavaObj json2JavaObj : maps) {
	 * 	List<Map<String, Object>> list = json2JavaObj.getListmaps();
	 *	for (Map<String, Object> map : list) {
	 *	 	System.out.println(map.get("code") + map.get("parent") + map.get("pname"));
	 *	}
	 * }
	 * </pre>
	 * @author xiongyan
	 * @date 2015年9月17日下午12:58:19
	 * @throws 
	 * @param json
	 * @return List<Json2JavaObj>
	 */ 
	public static List<Json2JavaObj> json2Obj(String json) {
		if(!StringUtils.isEmpty(json)){
			List<Json2JavaObj> returnList = new ArrayList<>();
			List<String> list = jsonToList(json);
			Pattern p = Pattern.compile("\\{(.*?)\\}");//获取指定的两个“{”、“}”之间的字符，包含指定两个字符
			Matcher matcher = p.matcher(list.toString());
			if(null != list && list.size() > 0) list.clear();
			while (matcher.find()) {
				list.add(matcher.group(1));//去掉了指定的两个字符“{”、“}”
			}
			
			List<String []> strarr = new ArrayList<String[]>();
			for (String str : list) {
				strarr.add(str.split(","));
			}
			
			for (String[] sarr : strarr) {
				Json2JavaObj json2JavaObj = new Json2JavaObj();
				Map<String, Object> map = new HashMap<String, Object>();
				for (String str : sarr) {
					List<Map<String, Object>> listmap = new ArrayList<Map<String,Object>>();
					String left  = str.substring(0, str.indexOf("="));
					String right = str.substring(str.indexOf("=") + 1, str.length());
					map.put(left.trim(), right);
					listmap.add(map);
					json2JavaObj.setListmaps(listmap);
				}
				returnList.add(json2JavaObj);
			}
			return returnList;
		}
		return null;
	}
	
	/**
	 * <summary>
	 * 将json格式的字符串转换成普通字符串，形如：c44794fe16b44db6937e6c56c6141937,1460c4e12940439fafde464f6a6fd43a,97d5ec2af7b34067a05e59603fcdc3cd
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String jsonToString(String json , String c ,boolean boo/*是否需要使用双引号包括*/) {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> list = jsonToList(json);
		if(list!=null && list.size()>0)
		{
			String list2String = jsonToList(json).toString();
			String str = list2String.substring(1, list2String.lastIndexOf("]"));
			String [] strArr = str.split(",");
			for (String s : strArr) {
				if (boo) {
					stringBuilder.append("\"").append(s.substring(s.indexOf("=")+1, s.lastIndexOf("}"))).append("\"").append(c);
				} else {
					stringBuilder.append(s.substring(s.indexOf("=")+1, s.lastIndexOf("}"))).append(c);
				}
			}
			
			return stringBuilder.substring(0, stringBuilder.length() - 1);
		}
		return json;
	}
	
	/**
	 * <summary>
	 * 将一个对象转换成JSON字符串
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String objectToJSON(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	public  static String ObjListToJson(List<?> list){
        StringBuilder build=new StringBuilder();
        //迭代器
        Iterator<?> iterator=list.iterator();
        build.append("[");
        String separate="";
        while(iterator.hasNext()){
            build.append(separate);
            //object转json字符串
//            Object obj = iterator.next();
//            if(null != obj){
            	String str=ObjToJson(iterator.next());
                build.append(str);
//            }
            separate=",";
        }
        build.append("]");
        return build.toString();
    }
     
    @SuppressWarnings("unchecked")
    public  static String ObjToJson(Object obj){
        StringBuilder build=new StringBuilder();
        build.append("{");
        @SuppressWarnings("rawtypes")
        Class cla=null;
        try {
            //反射加载类
            cla=Class.forName(obj.getClass().getName());
        } catch (ClassNotFoundException e) {
            System.out.println(obj.getClass().toString().concat(" 未找到这个类"));
            e.printStackTrace();
            return null;
        }
         
        StringBuffer methodname=new StringBuffer();
        //获取java类的变量
        Field[] fields=cla.getDeclaredFields();
        String separate="";
        for(Field temp:fields){
        	if(!temp.getName().equals("serialVersionUID"))
        	{
	            build.append(separate);
	            build.append("\"");
	            build.append(temp.getName());
	            build.append("\":");
	             
	            methodname.append("get");
	            methodname.append(temp.getName().substring(0,1).toUpperCase());
	            methodname.append(temp.getName().substring(1));
	             
	            build.append("\"");
	            Method method=null;
	            try {
	                //获取java的get方法
	                method=cla.getMethod(methodname.toString());
	            } catch (NoSuchMethodException | SecurityException e) {
	                methodname.setLength(0);
	                e.printStackTrace();
	            }
	             
	            try {
	                //执行get方法，获取变量参数的直。
	                build.append(method.invoke(obj));
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (InvocationTargetException e) {
	                e.printStackTrace();
	            }
	 
	            build.append("\"");
	            methodname.setLength(0);
	            separate=",";
        	}
        }
         
        build.append("}");
        return build.toString();
    }
}
