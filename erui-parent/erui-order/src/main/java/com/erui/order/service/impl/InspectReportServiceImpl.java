package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.AreaDao;
import com.erui.order.dao.InspectApplyDao;
import com.erui.order.dao.InspectApplyGoodsDao;
import com.erui.order.dao.InspectReportDao;
import com.erui.order.entity.*;
import com.erui.order.requestVo.InspectReportVo;
import com.erui.order.requestVo.PGoods;
import com.erui.order.service.AreaService;
import com.erui.order.service.InspectReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectReportServiceImpl implements InspectReportService {

    @Autowired
    private InspectReportDao inspectReportDao;
    @Autowired
    private InspectApplyGoodsDao inspectApplyGoodsDao;
    @Autowired
    private InspectApplyDao inspectApplyDao;

    @Override
    @Transactional
    public InspectReport findById(Integer id) {
        return inspectReportDao.findOne(id);
    }

    @Override
    public InspectReport detail(Integer id) {
        InspectReport inspectReport = inspectReportDao.findOne(id);

        return inspectReport;
    }

    /**
     * 按照条件分页查找
     *
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
                // 只查询是第一次报检单的质检信息
                list.add(cb.equal(root.get("reportFirst"), 1));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        return page;
    }

    /**
     * 保存质检单
     * 1：查看质检单是否存在
     * 2：判断是保存还是提交
     * 3：提交-判断是主质检单还是次质检单，如果不是主质检单，将主质检单的质检+1，并将质检状态修改为已完成
     * 4：提交-完善当前报检单商品信息 - 增加抽样数，不合格数，质检结论
     * 4：提交- 修改质检单的状态为合格或不合格
     * 5：保存基础信息
     * 6：保存附件信息
     * 7：如果是提交，设置本次的状态为已完成 ，如果是保存，则设置本次状态为未完成
     * 8：
     *
     * @param inspectReportVo
     * @return
     */
    @Override
    @Transactional
    public boolean save(InspectReportVo inspectReportVo) throws Exception {
        Integer id = inspectReportVo.getId();
        InspectReport inspectReport = inspectReportDao.findOne(id);
        if (inspectReport == null) {
            return false;
        }
        boolean isSubmit = inspectReportVo.getStatus() == 2;
        InspectApply inspectApply = inspectReport.getInspectApply();

        if (isSubmit) {
            // 判断是主质检单还是次质检单，将主质检单的质检+1，并将质检状态修改为已完成
            if (!inspectApply.isMaster()) {
                InspectApply parentInspectApply = inspectApply.getParent();
                InspectReport masterInspectReport = inspectReportDao.findByInspectApplyId(parentInspectApply.getId());
                masterInspectReport.setCheckTimes(masterInspectReport.getCheckTimes() + 1);
                masterInspectReport.setProcess(false);
                inspectReportDao.save(masterInspectReport);
            }
            // 提交-完善当前报检单商品信息 - 增加抽样数，不合格数，质检结论
            Map<Integer, PGoods> goodsListParam = inspectReportVo.getpGoodsList().parallelStream().collect(Collectors.toMap(PGoods::getId, vo -> vo));

            boolean standard = true; //默认设置达到合格标准
            List<InspectApplyGoods> inspectApplyGoodsList = inspectApply.getInspectApplyGoodsList();
            for (InspectApplyGoods iag : inspectApplyGoodsList) {
                PGoods pGoods = goodsListParam.get(iag.getId());
                if (pGoods == null) { // 报检了相关商品，但是没有相关质检信息，返回错误
                    throw new Exception("报检了相关商品，但是没有相关质检信息，返回错误");
                }
                iag.setSamples(pGoods.getSamples());
                Integer unqualified = pGoods.getUnqualified();
                if (unqualified > 0) {
                    iag.setUnqualified(unqualified);
                    iag.setUnqualifiedDesc(pGoods.getUnqualifiedDesc());
                    standard = false;
                }

            }
            inspectApplyGoodsDao.save(inspectApplyGoodsList);

            //提交- 修改质检单的状态为合格或不合格
            inspectApply.setStatus(standard ? 3 : 4);
            inspectApplyDao.save(inspectApply);
        }

        //保存基础信息 TODO 待添加其他字段
        inspectReport.setCheckUserId(inspectReportVo.getCheckUserId());
        inspectReport.setStatus(inspectReportVo.getStatus());
        //保存附件信息
        inspectReport.setAttachmentSet(inspectReportVo.getAttachments());
        // 如果是提交，设置本次的状态为已完成 ，如果是保存，则设置本次状态为未完成
        inspectReport.setProcess(!isSubmit);


        inspectReportDao.save(inspectReport);

        return true;
    }


    @Override
    @Transactional
    public List<InspectReport> history(Integer id) {
        List<InspectReport> result = null;
        InspectReport inspectReport = inspectReportDao.findOne(id);
        // 质检多次的才有历史
        if (inspectReport != null && inspectReport.isReportFirst() && inspectReport.getCheckTimes() > 1) {
            InspectApply inspectApply = inspectReport.getInspectApply();
            Integer parentApplyId = inspectApply.getId();
            List<InspectApply> childInspectApplyList = inspectApplyDao.findByParentIdOrderByIdAsc(parentApplyId);
            List<Integer> inspectApplyIds = childInspectApplyList.parallelStream().map(InspectApply::getId).collect(Collectors.toList());
            inspectApplyIds.add(parentApplyId);

            result = inspectReportDao.findByInspectApplyIdInOrderByIdAsc(inspectApplyIds);
        }

        if (result == null) {
            result = new ArrayList<>();
        }


        return result;
    }
}
