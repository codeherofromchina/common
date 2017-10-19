package com.erui.report.service.impl;

import com.erui.report.dao.HrCountMapper;
import com.erui.report.model.HrCount;
import com.erui.report.model.HrCountExample;
import com.erui.report.service.HrCountService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lirb on 2017/10/19.
 */

@Service
public class HrCountServiceImpl extends BaseService<HrCountMapper> implements HrCountService{


    /*
    * 人力资源数据列表
    *
    * */
    @Override
    public List<HrCount> findAll() {
        return  this.readMapper.findAll();

    }


}
