package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.AreaDao;
import com.erui.order.dao.InspectReportDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.InspectReport;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import com.erui.order.requestVo.InspectReportVo;
import com.erui.order.service.AreaService;
import com.erui.order.service.InspectReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectReportServiceImpl implements InspectReportService {

    @Autowired
    private InspectReportDao inspectReportDao;

    @Override
    public InspectReport findById(Integer id) {
        return inspectReportDao.findOne(id);
    }


    /**
     * 按照条件分页查找
     * @param condition
     * @return
     */
    @Override
    public Page<InspectReport> listByPage(InspectReportVo condition) {

        PageRequest request = new PageRequest(condition.getPage(), condition.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<InspectReport> page = inspectReportDao.findAll(new Specification<InspectReport>() {
            @Override
            public Predicate toPredicate(Root<InspectReport> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (condition.getCheckUserId() != null) {
                    list.add(cb.equal(root.get("checkUserId").as(Integer.class), condition.getCheckUserId()));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        return page;
    }


}
