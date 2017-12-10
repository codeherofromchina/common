package com.erui.comm.util.data.json;

import java.util.List;
import java.util.Map;

/**
 * 将json对象转换成java对象，封装json key到java对象属性，实现一一对应。
 * @author xiongyan
 * @date 2015年9月17日 上午11:09:26
 * @history
 * 	<add>
 * 		@functionName 
 *  	@author 
 *  	@date 2015年9月17日 上午11:09:26
 *  	@remark 
 * 	</add>
 * 	<upd> 
 * 		@functionName 
 *  	@author 
 *  	@date 2015年9月17日 上午11:09:26
 *  	@remark 
 * 	</upd>
 */
public class Json2JavaObj {
	private List<Map<String, Object>> listmaps;

	public List<Map<String, Object>> getListmaps() {
		return listmaps;
	}

	public void setListmaps(List<Map<String, Object>> listmaps) {
		this.listmaps = listmaps;
	}

}
