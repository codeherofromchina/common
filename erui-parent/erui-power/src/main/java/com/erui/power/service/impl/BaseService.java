package com.erui.power.service.impl;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseService<E> {
    protected E readMapper;
    protected E writeMapper;
    protected SqlSessionTemplate writerSession;
    protected SqlSessionTemplate readerSession;
    private Class<E> classType;

    public BaseService() {
        Class<? extends BaseService> class1 = this.getClass();
        Type genericSuperclass = class1.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        classType = (Class<E>) actualTypeArguments[0];
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteMapper(SqlSessionTemplate sqlSession) {
        writerSession = sqlSession;
        this.writeMapper = sqlSession.getMapper(classType);
    }

    @Resource(name = "sqlSessionSlave")
    public void setReadMapper(SqlSessionTemplate sqlSession) {
        readerSession = sqlSession;
        this.readMapper = sqlSession.getMapper(classType);
    }
}
