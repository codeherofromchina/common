package com.erui.comm.util.data.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Util class, returns a Map in the format Ext JS expects
 * 
 * Sample project presented at BrazilJS
 * Brazilian JavaScript Conference
 * http://braziljs.com.br/2011
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Component
public class ExtJSReturn {
	
	/**
	 * Generates modelMap to return in the modelAndView
	 * @param list
	 * @return
	 */
	public static Map<String,Object> mapOK(List<?> list, int total, String msg) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", total);
		modelMap.put("message", msg);
		try {
			modelMap.put("data", convertListBean2ListMap(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	public static Map<String,Object> mapOK(List<?> list, String msg) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try {
			modelMap.put("data", convertListBean2ListMap(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.put("message", msg);
		modelMap.put("success", true);
		return modelMap;
		
	}
	public static Map<String,Object> mapOK(String data, String msg) {
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("data", data);
		modelMap.put("message", msg);
		modelMap.put("success", true);
		return modelMap;
		
	}
	
	/**
	 * Generates modelMap to return in the modelAndView in case
	 * of exception
	 * @param msg message
	 * @return
	 */
	public static Map<String,Object> mapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	} 
	
	public static Map<String,Object> mapError(Object data, String msg) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("data", data);
		modelMap.put("message", msg);
		modelMap.put("success", false);
		return modelMap;
	}
	
	public static Map<String,Object> mapOK(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", true);

		return modelMap;
	} 
	
	public static Map<String,Object> mapOK(boolean isValid){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("isValid", isValid);
		modelMap.put("success", true);

		return modelMap;
	} 
	
	public static Map<String,Object> mapOKOrError(boolean success,String msg){
		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", success);
		
		return modelMap;
	}
	
	 /** 
     * 将 List<JavaBean>对象转化为List<Map>
     * @author wyply115
     * @param beanList 要转化的类型
     * @return Object对象
     * @version 2016年3月20日 11:03:01
     */  
    public static List<Map<String,Object>> convertListBean2ListMap(List<?> beanList) throws Exception {  
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for(int i=0, n=beanList.size(); i<n; i++){
            Object bean = beanList.get(i);
            Map<String, Object> map = convertBean2Map(bean);
            mapList.add(map);
        }
        return mapList;  
    }
    /** 
     * 将 JavaBean对象转化为 Map   此方法未测试
     * @param bean 要转化的类型 
     * @return Map对象
     * @version 2016年3月20日 11:03:01
     */   
    public static Map<String, Object> convertBean2Map(Object bean) throws Exception {  
        Class<? extends Object> type = bean.getClass();  
        Map<String, Object> returnMap = new HashMap<String, Object>();  
        BeanInfo beanInfo = Introspector.getBeanInfo(type);  
        PropertyDescriptor[] propertyDescriptors = beanInfo  
                .getPropertyDescriptors();  
        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {  
            PropertyDescriptor descriptor = propertyDescriptors[i];  
            String propertyName = descriptor.getName();  
            if (!propertyName.equals("class")) {  
                Method readMethod = descriptor.getReadMethod();  
                Object result = readMethod.invoke(bean, new Object[0]);  
                String npropertyName = propertyName.replaceFirst(propertyName.substring(0, 1),propertyName.substring(0, 1).toLowerCase()) ;
                if (result != null) {  
                    returnMap.put(npropertyName, result);  
                } else {  
                    returnMap.put(npropertyName, "");  
                }  
            }  
        }  
        return returnMap;  
    }  
}
