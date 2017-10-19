package com.erui.report.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;


public class BaseService<E> {
	protected E readMapper;
	protected E writeMapper;
	private Class<E> classType;
	
	public BaseService() {
		Class<? extends BaseService> class1 = this.getClass();
		Type genericSuperclass = class1.getGenericSuperclass();
		Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
		classType = (Class<E>) actualTypeArguments[0];
	}
	
	@Resource(name = "sqlSessionMaster")
	public void setWriteMapper(SqlSessionTemplate sqlSession) {
		this.writeMapper = sqlSession.getMapper(classType);
	}
	@Resource(name = "sqlSessionSlave")
	public void setReadMapper(SqlSessionTemplate sqlSession) {
		this.readMapper = sqlSession.getMapper(classType);
	}
}
