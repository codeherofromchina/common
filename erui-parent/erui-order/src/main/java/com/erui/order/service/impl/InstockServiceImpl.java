package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.InspectApplyGoodsDao;
import com.erui.order.dao.InstockDao;
import com.erui.order.entity.*;
import com.erui.order.event.MyEvent;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.InstockService;
import javassist.expr.NewArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private ApplicationContext applicationContext;
    @Autowired
    private InstockDao instockDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private InspectApplyGoodsDao inspectApplyGoodsDao;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
                //TODO 入库状态
                if (StringUtil.isNotBlank(condition.get("status"))) {
                    //Status   0未入库   1已入库
                    int status = Integer.parseInt(condition.get("status"));
                    if(status == 0){
                        list.add(cb.lessThan(root.get("status").as(Integer.class), 3)); //小于
                    }else if(status == 1){
                        list.add(cb.greaterThan(root.get("status").as(Integer.class), 2));  //大于
                    }
                }
                //是否外检（ 0：否   1：是）
                if (StringUtil.isNotBlank(condition.get("outCheck"))){
                    list.add(cb.equal(root.get("outCheck").as(Integer.class), condition.get("outCheck")));
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
                if (StringUtil.isNotBlank(condition.get("wareHouseman"))) {

                    Integer wareHouseman = Integer.parseInt(condition.get("wareHouseman"));
                    /*Join<Instock,InstockGoods> instockGoodsRoot = root.join("instockGoodsList");
                    Join<InstockGoods, InspectApplyGoods> inspectApplyGoodsRoot = instockGoodsRoot.join("inspectApplyGoods");
                    Join<InspectApplyGoods, Goods> goodsRoot = inspectApplyGoodsRoot.join("goods");
                    Join<Goods, Project> projectRoot = goodsRoot.join("project");*/

                    list.add(cb.equal(root.get("uid").as(Integer.class), wareHouseman));

                }

              /*  // 仓库经办人
                if (StringUtil.isNotBlank(condition.get("wareHouseman"))) {

                    Integer wareHouseman = Integer.parseInt(condition.get("wareHouseman"));

                    Join<Instock,InspectReport> inspectReportRoot = root.join("inspectReport");
                    Join<InspectReport,InspectApply> inspectApplyRoot = inspectReportRoot.join("inspectApply");
                    Join<InspectApply, Purch> purchRoot = inspectApplyRoot.join("purch");
                    Join<Purch, Project> projectRoot = purchRoot.join("projects");
                    list.add(cb.equal(projectRoot.get("warehouseUid").as(Integer.class), wareHouseman));

                }*/

                // 销售合同号 、 项目号查询
                if(StringUtils.isNotBlank(condition.get("projectNo")) || StringUtils.isNotBlank(condition.get("contractNo")) ){
                   Set<Integer> a= queryProjectNoAndContractNo(condition.get("projectNo"),condition.get("contractNo"));
                    Integer[] objectArray2 = a.toArray(new Integer[a.size()]);
                    if(objectArray2.length == 0){
                        list.add(root.get("id").in(-1));
                    }else{
                        list.add(root.get("id").in(objectArray2));
                    }
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
                map.put("uid",instock.getUid());
                map.put("outCheck",instock.getOutCheck());//是否外检（ 0：否   1：是）

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
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Instock instock) throws Exception {

        Instock dbInstock = instockDao.findOne(instock.getId());

        // 保存基本信息
        dbInstock.setUid(instock.getUid());
        dbInstock.setUname(instock.getUname());
        dbInstock.setDepartment(instock.getDepartment());

        dbInstock.setInstockDate(NewDateUtil.getDate(instock.getInstockDate()));    //入库日期

        dbInstock.setRemarks(instock.getRemarks());
        dbInstock.setCurrentUserId(instock.getCurrentUserId());
        dbInstock.setCurrentUserName(instock.getCurrentUserName());
        dbInstock.setStatus(instock.getStatus());
        dbInstock.setDepartment(instock.getDepartment());

        List<InstockGoods> instockGoodsList = dbInstock.getInstockGoodsList();
        if (instock.getStatus() == 3) {
            for (InstockGoods instockGoods : instockGoodsList) {
                //当入库提交的时候才保存  入库日期
                Goods one = goodsDao.findOne(instockGoods.getInspectApplyGoods().getGoods().getId());
                one.setInstockDate(dbInstock.getInstockDate()); //入库日期
                one.setUid(instockGoods.getInspectApplyGoods().getGoods().getProject().getWarehouseUid());   //仓库经办人id
                goodsDao.saveAndFlush(one);
                applicationContext.publishEvent(new OrderProgressEvent(one.getOrder(), 6));
            }
        }
        // 保存附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(dbInstock.getAttachmentList(), instock.getAttachmentList(), instock.getCurrentUserId(), instock.getCurrentUserName());
        dbInstock.setAttachmentList(attachments);
        // 处理商品信息
        Map<Integer, InstockGoods> instockGoodsMap = instockGoodsList.parallelStream().collect(Collectors.toMap(InstockGoods::getId, vo -> vo));
        for (InstockGoods instockGoods : instock.getInstockGoodsList()) {
            if (instockGoods.getInstockNum() == null) {
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
                if (goods != null) {
                    goods.setInstockNum(goods.getInstockNum() + instockGoods02.getInstockNum());

                    if (instock.getOutCheck() != null) {
                        if (instock.getOutCheck() == 0) {  //是否外检（ 0：否   1：是）
                            Integer nullInstockNum = goods.getNullInstockNum();
                            goods.setNullInstockNum(nullInstockNum = nullInstockNum == null ? 0 : nullInstockNum + instockGoods02.getInstockNum());  //质检入库数量
                        } else {
                            Integer inspectInstockNum = goods.getInspectInstockNum();
                            goods.setInspectInstockNum(inspectInstockNum = inspectInstockNum == null ? 0 : inspectInstockNum + instockGoods02.getInstockNum());  //质检入库数量
                        }

                        goodsDao.save(goods);
                    }
                }
            }
            if (instockGoodsMap.size() > 0) {
                throw new Exception(String.format("%s%s%s","入库商品数量错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"The number of goods in the warehouse"));
            }
        }
            instockDao.save(dbInstock);

            return true;
        }



    @Override
    @Transactional(readOnly = true)
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


    /**
     * 入库详情信息   转交经办人
     *
     * @param instock
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean instockDeliverAgent(Instock instock) {

            Instock dbInstock = instockDao.findOne(instock.getId());

            // 保存基本信息
            dbInstock.setUid(instock.getUid());  //仓库经办人ID
            dbInstock.setUname(instock.getUname());  //仓库经办人名字

            instockDao.save(dbInstock);

            return true;
    }


    @Transactional(readOnly = true)
    public Set queryProjectNoAndContractNo(String projectNo ,String contractNo) {

        List<Instock> page = instockDao.findAll(new Specification<Instock>() {
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

        Set<Integer> idSer = new HashSet<Integer>();
        for (Instock page1 : page){
            idSer.add(page1.getId());
        }
        return idSer;
    }
}
