package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.AreaDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.InspectApplyGoodsDao;
import com.erui.order.dao.InstockDao;
import com.erui.order.entity.*;
import com.erui.order.service.AreaService;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.InstockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InstockServiceImpl implements InstockService {
    private static Logger logger = LoggerFactory.getLogger(InstockServiceImpl.class);

    @Autowired
    private InstockDao instockDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private InspectApplyGoodsDao inspectApplyGoodsDao;

    @Override
    public Instock findById(Integer id) {
        return instockDao.findOne(id);
    }


    /**
     * @param condition {"inspectApplyNo":"报检单号","contractNo":"销售合同号","projectNo":"项目号",
     *                  "supplierName":"供应商名称","instockDate":"入库日期","name":"仓库经办人"}
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public Page<Map<String, Object>> listByPage(Map<String, String> condition, int pageNum, int pageSize) {
        PageRequest request = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "id");

        Page<Instock> page = instockDao.findAll(new Specification<Instock>() {
            @Override
            public Predicate toPredicate(Root<Instock> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据报检单号模糊查询
                if (StringUtil.isNotBlank(condition.get("inspectApplyNo"))) {
                    list.add(cb.like(root.get("inspectApplyNo").as(String.class), "%" + condition.get("inspectApplyNo") + "%"));
                }
                // 供应商名称
                if (StringUtil.isNotBlank(condition.get("supplierName"))) {
                    list.add(cb.like(root.get("supplierName").as(String.class), "%" + condition.get("supplierName") + "%"));
                }
                // 根据入库日期查询
                if (StringUtil.isNotBlank(condition.get("instockDate"))) {
                    try {
                        list.add(cb.equal(root.get("instockDate").as(Date.class), DateUtil.parseStringToDate(condition.get("instockDate"), "yyyy-MM-dd")));
                    } catch (ParseException e) {
                        logger.error("日期转换错误", e);
                    }
                }
                // 仓库经办人
                if (StringUtil.isNotBlank(condition.get("name"))) {
                    list.add(cb.like(root.get("uname").as(String.class), "%" + condition.get("name") + "%"));
                }

                // 销售合同号 、 项目号查询
                Set<Instock> instockSet = findByProjectNoAndContractNo(condition.get("projectNo"), condition.get("contractNo"));
                if (instockSet != null && instockSet.size() > 0) {
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    for (Instock p : instockSet) {
                        idIn.value(p.getId());
                    }
                    list.add(idIn);
                }


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        // 转换为控制层需要的数据
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.hasContent()) {
            for (Instock instock : page.getContent()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", instock.getId());
                map.put("inspectApplyNo", instock.getInspectApplyNo());
                Set<String> ContractNoList = new HashSet<>();
                Set<String> projectNoList = new HashSet<>();
                // 销售合同号 和 项目号
                List<InstockGoods> instockGoodsList = instock.getInstockGoodsList();
                instockGoodsList.stream().forEach(instockGoods -> {
                    if (StringUtil.isNotBlank(instockGoods.getContractNo())){
                        ContractNoList.add(instockGoods.getContractNo());
                    }
                    Goods goods = instockGoods.getInspectApplyGoods().getPurchGoods().getGoods();

                    if (StringUtil.isNotBlank(goods.getProjectNo())){
                        projectNoList.add(goods.getProjectNo());
                    }

                });
                map.put("contractNos", StringUtils.join(ContractNoList, ","));
                map.put("projectNos", StringUtils.join(projectNoList, ","));
                map.put("department", instock.getDepartment());
                // 供应商名称
                map.put("supplierName", instock.getSupplierName());
                // 入库时间

                map.put("instockDate", instock.getInstockDate() != null?new SimpleDateFormat("yyyy-MM-dd").format(instock.getInstockDate()):null);
                map.put("status", instock.getStatus());
                map.put("uname", instock.getUname());

                list.add(map);
            }
        }
        PageImpl<Map<String, Object>> resultPage = new PageImpl<Map<String, Object>>(list, request, page.getTotalElements());

        return resultPage;
    }


    // 根据销售号和项目号查询采购列表信息
    private Set<Instock> findByProjectNoAndContractNo(String projectNo, String contractNo) {
        Set<Instock> result = null;
        if (!(StringUtils.isBlank(projectNo) && StringUtils.isBlank(contractNo))) {
            List<Instock> list = instockDao.findAll(new Specification<Instock>() {
                @Override
                public Predicate toPredicate(Root<Instock> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    Join<Instock, InstockGoods> instockGoods = root.join("instockGoodsList");
                    if (StringUtils.isNotBlank(projectNo)) {
                        list.add(cb.like(instockGoods.get("projectNo").as(String.class), "%" + projectNo + "%"));
                    }

                    if (StringUtils.isNotBlank(contractNo)) {
                        list.add(cb.like(instockGoods.get("contractNo").as(String.class), "%" + contractNo + "%"));
                    }

                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });

            if(list != null) {
                result = new HashSet<>(list);
            }

        }


        return result;
    }


    @Override
    @Transactional
    public boolean save(Instock instock) throws Exception {

        Instock dbInstock = instockDao.findOne(instock.getId());

        // 保存基本信息
        dbInstock.setUid(instock.getUid());
        dbInstock.setUname(instock.getUname());
        dbInstock.setDepartment(instock.getDepartment());
        dbInstock.setInstockDate(NewDateUtil.getDate(instock.getInstockDate()));
        dbInstock.setRemarks(instock.getRemarks());
        dbInstock.setCurrentUserId(instock.getCurrentUserId());
        dbInstock.setCurrentUserName(instock.getCurrentUserName());
        dbInstock.setStatus(instock.getStatus());
        dbInstock.setDepartment(instock.getDepartment());

        // 保存附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(dbInstock.getAttachmentList(), instock.getAttachmentList(), instock.getCurrentUserId(), instock.getCurrentUserName());
        dbInstock.setAttachmentList(attachments);


        // 处理商品信息
        Map<Integer, InstockGoods> instockGoodsMap = dbInstock.getInstockGoodsList().parallelStream().collect(Collectors.toMap(InstockGoods::getId, vo -> vo));
        for (InstockGoods instockGoods : instock.getInstockGoodsList()) {
            if (instockGoods.getInstockNum() == null || StringUtils.isBlank(instockGoods.getInstockStock())) {
                return false;
            }
            InstockGoods instockGoods02 = instockGoodsMap.remove(instockGoods.getId());
            if (instockGoods02 == null) {
                return false;
            }
            if (instockGoods.getInstockNum() != instockGoods02.getQualifiedNum().intValue()) {
                // 入库数量一定要和质检合格的商品数量相等，否则入库失败
                return false;
            }

            instockGoods02.setInstockNum(instockGoods.getInstockNum());
            instockGoods02.setInstockStock(instockGoods.getInstockStock());
            instockGoods02.setCreateUserId(dbInstock.getCurrentUserId());

            // 修改商品和采购商品中的入库数量
            if (dbInstock.getStatus() == Instock.StatusEnum.SUBMITED.getStatus()) {
                InspectApplyGoods inspectApplyGoods = instockGoods02.getInspectApplyGoods();
                inspectApplyGoods.setInstockNum(inspectApplyGoods.getInstockNum() + instockGoods02.getInstockNum());
                inspectApplyGoodsDao.save(inspectApplyGoods);


                Goods goods = inspectApplyGoods.getGoods();
                if (goods.getParentId() != null) {
                    goods = goodsDao.findOne(goods.getParentId());
                }
                goods.setInstockNum(goods.getInstockNum() + instockGoods02.getInstockNum());
                goodsDao.save(goods);
            }
        }
        if (instockGoodsMap.size() > 0) {
            throw new Exception("入库商品数量错误");
        }

        instockDao.save(dbInstock);

        return true;
    }


    @Override
    @Transactional
    public Instock detail(Integer id) {
        Instock instock = instockDao.findOne(id);
        instock.getAttachmentList().size();
        List<InstockGoods> instockGoodsList = instock.getInstockGoodsList();
        for (InstockGoods v :instockGoodsList){
            InspectApplyGoods inspectApplyGoods = v.getInspectApplyGoods();
            inspectApplyGoods.getPurchGoods().getInspectNum();
            inspectApplyGoods.getGoods().getId();
        }

        return instock;
    }
}
