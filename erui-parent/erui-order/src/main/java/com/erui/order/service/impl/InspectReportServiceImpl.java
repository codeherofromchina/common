package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.InspectReportService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class InspectReportServiceImpl implements InspectReportService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private InspectReportDao inspectReportDao;
    @Autowired
    private InspectApplyGoodsDao inspectApplyGoodsDao;
    @Autowired
    private InspectApplyDao inspectApplyDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private PurchDao purchDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private InstockDao instockDao;
    @Autowired
    private PurchGoodsDao purchGoodsDao;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口


    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Override
    @Transactional(readOnly = true)
    public InspectReport findById(Integer id) {
        return inspectReportDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public InspectReport detail(Integer id) {
        InspectReport inspectReport = inspectReportDao.findOne(id);
        if (inspectReport != null) {
            inspectReport.getAttachments().size();
            inspectReport.getInspectGoodsList().size();
            InspectApply inspectApply = inspectReport.getInspectApply();
            inspectReport.setPurchNo(inspectApply.getPurchNo());
        }

        return inspectReport;
    }

    /**
     * 按照条件分页查找
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InspectReport> listByPage(InspectReport condition) {

        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<InspectReport> page = inspectReportDao.findAll(new Specification<InspectReport>() {
            @Override
            public Predicate toPredicate(Root<InspectReport> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 报检单单号模糊查询
                if (StringUtils.isNotBlank(condition.getInspectApplyNo())) {
                    list.add(cb.like(root.get("inspectApplyNo").as(String.class), "%" + condition.getInspectApplyNo() + "%"));
                }

                Join<InspectReport, InspectApply> inspectApply = root.join("inspectApply");
                list.add(cb.equal(inspectApply.get("master").as(Boolean.class), Boolean.TRUE)); // 只查询主质检单
                if (!(StringUtils.isBlank(condition.getProjectNo()) && StringUtils.isBlank(condition.getContractNo()))) {
                    CriteriaBuilder.In<Object> idIn = cb.in(inspectApply.get("id"));
                    Set<InspectApply> inspectApplySet = findByProjectNoAndContractNo(condition.getProjectNo(), condition.getContractNo());
                    if (inspectApplySet != null && inspectApplySet.size() > 0) {
                        for (InspectApply p : inspectApplySet) {
                            idIn.value(p.getId());
                        }
                    } else {
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                // 最后质检完成时间过滤
                if (condition.getDoneDate() != null) {
                    list.add(cb.equal(root.get("lastDoneDate").as(Date.class), NewDateUtil.getDate(condition.getDoneDate())));
                }

                // 报检时间过滤
                if (condition.getInspectDate() != null) {
                    list.add(cb.equal(inspectApply.get("inspectDate").as(Date.class), NewDateUtil.getDate(condition.getInspectDate())));
                }

                // 质检检验时间过滤
                if (condition.getCheckDate() != null) {
                    list.add(cb.equal(root.get("checkDate").as(Date.class), NewDateUtil.getDate(condition.getCheckDate())));
                }

                // 质检次数过滤
                if (condition.getCheckTimes() != null) {
                    list.add(cb.equal(root.get("checkTimes").as(Integer.class), condition.getCheckTimes()));
                }


                // 根据采购合同号模糊查询
                if (StringUtils.isNotBlank(condition.getPurchNo())) {
                    list.add(cb.like(inspectApply.get("purchNo").as(String.class), "%" + condition.getPurchNo() + "%"));
                }

                // 供应商名称模糊查询
                if (StringUtils.isNotBlank(condition.getSupplierName())) {
                    list.add(cb.like(root.get("supplierName").as(String.class), "%" + condition.getSupplierName() + "%"));
                }

                // 检验员查询
                if (condition.getCheckUserId() != null) {
                    list.add(cb.equal(root.get("checkUserId").as(Integer.class), condition.getCheckUserId()));
                }

                // 是否外检查询
                if (condition.getDirect() != null) {
                    list.add(cb.equal(inspectApply.get("direct"), condition.getDirect()));
                }

                // 质检状态查询
                if (condition.getProcess() != null) {
                    list.add(cb.equal(root.get("process").as(Boolean.class), condition.getProcess()));
                }
                // 只查询是第一次报检单的质检信息
                list.add(cb.equal(root.get("reportFirst"), Boolean.TRUE));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);
        if (page.hasContent()) {
            // 获取报检单和商品信息
            page.getContent().stream().forEach(inspectReport -> {
                inspectReport.getInspectApply().getPurchNo();
                // 销售合同号,保持顺序用list
                List<String> contractNoList = new ArrayList<String>();
                // 项目号,保持顺序用list
                List<String> projectNoList = new ArrayList<String>();
                inspectReport.getInspectGoodsList().forEach(vo -> {
                    Goods goods = vo.getGoods();
                    String contractNo = goods.getContractNo();
                    String projectNo = goods.getProjectNo();
                    if (!contractNoList.contains(contractNo)) {
                        contractNoList.add(contractNo);
                    }
                    if (!projectNoList.contains(projectNo)) {
                        projectNoList.add(projectNo);
                    }
                });
                inspectReport.setContractNo(StringUtils.join(contractNoList, ","));
                inspectReport.setProjectNo(StringUtils.join(projectNoList, ","));
            });
        }


        return page;
    }


    // 根据销售号和项目号查询报检列表信息
    private Set<InspectApply> findByProjectNoAndContractNo(String projectNo, String contractNo) {
        Set<InspectApply> result = null;
        if (!(StringUtils.isBlank(projectNo) && StringUtils.isBlank(contractNo))) {
            List<InspectApply> list = inspectApplyDao.findAll(new Specification<InspectApply>() {

                @Override
                public Predicate toPredicate(Root<InspectApply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();


                    Join<InspectApply, InspectApplyGoods> inspectApplyGoods = root.join("inspectApplyGoodsList");
                    Join<InspectApplyGoods, Goods> goods = inspectApplyGoods.join("goods");

                    // 销售合同号模糊查询
                    if (StringUtils.isNotBlank(contractNo)) {
                        list.add(cb.like(goods.get("contractNo").as(String.class), "%" + contractNo + "%"));
                    }

                    // 根据项目号模糊查询
                    if (StringUtils.isNotBlank(projectNo)) {
                        list.add(cb.like(goods.get("projectNo").as(String.class), "%" + projectNo + "%"));
                    }


                    Predicate[] predicates = new Predicate[list.size()];
                    predicates = list.toArray(predicates);
                    return cb.and(predicates);
                }
            });
            if (list != null) {
                result = new HashSet<>(list);
            }
        }


        return result;
    }


    /**
     * 保存质检单
     *
     * @param inspectReport
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(InspectReport inspectReport) throws Exception {
        InspectReport dbInspectReport = inspectReportDao.findOne(inspectReport.getId());
        if (dbInspectReport == null) {
            throw new Exception("质检单不存在");
        }
        InspectReport.StatusEnum statusEnum = InspectReport.StatusEnum.fromCode(inspectReport.getStatus());
        if (statusEnum == null || statusEnum == InspectReport.StatusEnum.INIT) {
            throw new Exception("状态提交错误");
        }
        if (dbInspectReport.getStatus() == InspectReport.StatusEnum.DONE.getCode()) {
            throw new Exception("质检单已提交，不可修改");
        }

        // 处理基本数据
        dbInspectReport.setCheckUserId(inspectReport.getCheckUserId());
        dbInspectReport.setCheckUserName(inspectReport.getCheckUserName());
        dbInspectReport.setCheckDeptId(inspectReport.getCheckDeptId());
        dbInspectReport.setCheckDeptName(inspectReport.getCheckDeptName());
        dbInspectReport.setNcrNo(inspectReport.getNcrNo());
        dbInspectReport.setCheckDate(inspectReport.getCheckDate());
        dbInspectReport.setDoneDate(inspectReport.getDoneDate());
        dbInspectReport.setLastDoneDate(inspectReport.getDoneDate());
        dbInspectReport.setReportRemarks(inspectReport.getReportRemarks());
        dbInspectReport.setStatus(statusEnum.getCode());


        // 处理附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(dbInspectReport.getAttachments(), inspectReport.getAttachments(), inspectReport.getCreateUserId(), inspectReport.getCreateUserName());
        dbInspectReport.setAttachments(attachments);

        // 处理商品信息
        Map<Integer, InspectApplyGoods> inspectGoodsMap = inspectReport.getInspectGoodsList().parallelStream().
                collect(Collectors.toMap(InspectApplyGoods::getId, vo -> vo)); // 参数的质检商品
        List<InspectApplyGoods> inspectGoodsList = dbInspectReport.getInspectGoodsList(); // 数据库原来报检商品
        if (inspectGoodsMap.size() != inspectGoodsList.size()) {
            throw new Exception("传入质检商品数量不正确");
        }
        boolean hegeFlag = true;
        int hegeNum = 0;    //合格商品总数量

        int sum=0;  //不合格商品总数量
        Project project = null; //项目信息

        for (InspectApplyGoods applyGoods : inspectGoodsList) {
            InspectApplyGoods paramApplyGoods = inspectGoodsMap.get(applyGoods.getId());
            if (paramApplyGoods == null) {
                throw new Exception("传入质检商品不正确");
            }

            PurchGoods purchGoods = applyGoods.getPurchGoods();
            Goods goods = purchGoods.getGoods();


            Integer samples = paramApplyGoods.getSamples();
            Integer unqualified = paramApplyGoods.getUnqualified();
            if (samples == null || samples <= 0) {
                throw new Exception("抽样数错误【SKU:" + goods.getSku() + "】");
            }
            if (unqualified == null || unqualified < 0 || unqualified > samples) {
                throw new Exception("不合格数据错误【SKU:" + goods.getSku() + "】");
            }
            if (unqualified > 0) {
                sum += unqualified;
                hegeFlag = false;
            }
            applyGoods.setSamples(samples);
            applyGoods.setUnqualified(unqualified);
            // 如果有不合格商品，则必须有不合格描述
            if (!hegeFlag && StringUtils.isBlank(paramApplyGoods.getUnqualifiedDesc()) && unqualified > 0) {
                throw new Exception("商品(SKU:"+goods.getSku()+")的不合格描述不能为空");
            }
            applyGoods.setUnqualifiedDesc(paramApplyGoods.getUnqualifiedDesc());
            // 设置采购商品的已合格数量
            if (statusEnum == InspectReport.StatusEnum.DONE) { // 提交动作

                project=project==null?goods.getProject():project;

                // 合格数量
                int qualifiedNum = applyGoods.getInspectNum() - unqualified;
                hegeNum += qualifiedNum; // 统计合格总数量
                if (qualifiedNum < 0) {
                    throw new Exception("传入不合格数量参数不正确【SKU:" + goods.getSku() + "】");
                }
                if (purchGoods.getInspectNum() < purchGoods.getGoodNum() + qualifiedNum) {
                    // 合格数量大于报检数量，数据错误
                    throw new Exception("采购的合格数量错误【purchGoodsId:" + purchGoods.getId() + "】");
                }
                purchGoods.setGoodNum(purchGoods.getGoodNum() + qualifiedNum);
                purchGoodsDao.save(purchGoods);

                if (goods.getCheckUerId() == null) {
                    goods.setCheckUerId(dbInspectReport.getCheckUserId());
                }
                if (goods.getCheckDate() == null) {
                    // 检验时间
                    goods.setCheckDate(dbInspectReport.getCheckDate());
                }
                if (dbInspectReport.getDoneDate() != null) {
                    // 检验完成时间
                    goods.setCheckDoneDate(dbInspectReport.getDoneDate());
                }
                goodsDao.save(goods);
            }
        }

        // 设置父质检单的最后检验完成日期
        InspectApply inspectApply = dbInspectReport.getInspectApply();
        InspectApply inspectApplyParent = inspectApply.getParent();
        if(!dbInspectReport.getReportFirst()){
            InspectReport firstInspectReport = inspectReportDao.findByInspectApplyId(inspectApplyParent.getId());
            firstInspectReport.setLastDoneDate(dbInspectReport.getDoneDate());
            inspectReportDao.save(firstInspectReport);
        }


        // 提交动作 则设置第一次质检，和相应的报检信息
        if (statusEnum == InspectReport.StatusEnum.DONE) {

            //质检结果通知：质检人员将不合格商品通知采购经办人
            disposeData(hegeFlag,hegeNum ,sum ,dbInspectReport ,project);


            dbInspectReport.setProcess(false);

            if (hegeFlag && !dbInspectReport.getReportFirst()) {
                // 将第一次质检设置为完成
                InspectReport firstInspectReport = inspectReportDao.findByInspectApplyId(inspectApplyParent.getId());
                firstInspectReport.setProcess(false);
                inspectReportDao.save(firstInspectReport);
            } else if (dbInspectReport.getReportFirst() && !hegeFlag) {
                dbInspectReport.setProcess(true);
            }

            if (inspectApplyParent != null) {
                inspectApplyParent.setPubStatus(hegeFlag ? InspectApply.StatusEnum.QUALIFIED.getCode() : InspectApply.StatusEnum.UNQUALIFIED.getCode());
                inspectApplyDao.save(inspectApplyParent);
            }
            inspectApply.setStatus(hegeFlag ? InspectApply.StatusEnum.QUALIFIED.getCode() : InspectApply.StatusEnum.UNQUALIFIED.getCode());
            inspectApply.setPubStatus(hegeFlag ? InspectApply.StatusEnum.QUALIFIED.getCode() : InspectApply.StatusEnum.UNQUALIFIED.getCode());
            inspectApplyDao.save(inspectApply);
        }
        inspectReportDao.save(dbInspectReport);

        // 最后判断采购是否完成，且存在合格的商品数量
        if (statusEnum == InspectReport.StatusEnum.DONE && hegeNum > 0) {
            Purch purch = dbInspectReport.getInspectApply().getPurch();
            List<PurchGoods> purchGoodsList = purch.getPurchGoodsList();
            boolean doneFlag = true;
            for (PurchGoods pg : purchGoodsList) {
                if (pg.getGoodNum() < pg.getPurchaseNum()) {
                    doneFlag = false;
                    break;
                }
            }
            if (doneFlag) {
                purch.setStatus(Purch.StatusEnum.DONE.getCode());
                purchDao.save(purch);
            }


            // 推送数据到入库部门
            Instock instock = new Instock();
            instock.setInspectReport(dbInspectReport);
           if(project != null){
               instock.setUid(project.getWarehouseUid());
               instock.setUname(project.getWarehouseName());
           }
            instock.setInspectApplyNo(dbInspectReport.getInspectApplyNo()); // 报检单号
            instock.setSupplierName(dbInspectReport.getInspectApply().getPurch().getSupplierName()); // 供应商
            instock.setStatus(Instock.StatusEnum.INIT.getStatus());
            instock.setCreateTime(new Date());
            List<InstockGoods> instockGoodsList = new ArrayList<>();
            // 入库商品
            for (InspectApplyGoods inspectGoods : dbInspectReport.getInspectGoodsList()) {
                int qualifiedNum = inspectGoods.getInspectNum() - inspectGoods.getUnqualified();
                if (qualifiedNum <= 0) {
                    // 全部不合格商品则不添加到入库
                    continue;
                }
                InstockGoods instockGoods = new InstockGoods();
                instockGoods.setInstock(instock);
                instockGoods.setContractNo(inspectGoods.getGoods().getContractNo());
                instockGoods.setProjectNo(inspectGoods.getGoods().getProjectNo());
                instockGoods.setInspectApplyGoods(inspectGoods);
                instockGoods.setQualifiedNum(qualifiedNum);
                instockGoods.setInstockNum(instockGoods.getQualifiedNum()); // 入库数量
                Date date = new Date();
                instockGoods.setCreateTime(date);
                instockGoods.setUpdateTime(date);
                instockGoods.setCreateUserId(dbInspectReport.getCreateUserId());

                instockGoodsList.add(instockGoods);
            }
            instock.setInstockGoodsList(instockGoodsList);

            instockDao.save(instock);
        }

        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public List<InspectReport> history(Integer id) {
        List<InspectReport> result = null;
        InspectReport inspectReport = inspectReportDao.findOne(id);
        // 质检多次的才有历史
        if (inspectReport != null && inspectReport.getReportFirst() != null && inspectReport.getReportFirst() && inspectReport.getCheckTimes() > 1) {
            InspectApply inspectApply = inspectReport.getInspectApply();
            Integer parentApplyId = inspectApply.getId();
            List<InspectApply> childInspectApplyList = inspectApplyDao.findByParentIdOrderByIdAsc(parentApplyId);
            List<Integer> inspectApplyIds = childInspectApplyList.parallelStream().map(InspectApply::getId).collect(Collectors.toList());
            inspectApplyIds.add(parentApplyId);
            result = inspectReportDao.findByInspectApplyIdInOrderByIdAsc(inspectApplyIds);
        }


        return result;
    }



    //质检结果通知：质检人员将不合格商品通知采购经办人
    public void sendSms(Map<String,Object> map1) throws  Exception {
        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (StringUtils.isNotBlank(eruiToken)) {
            try{
                Map<String, String> header = new HashMap<>();
                header.put(EruitokenUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");

                Integer purchaseUid = (Integer) map1.get("purchaseUid");//采购经办人id
                Integer warehouseUid = (Integer) map1.get("warehouseUid");//仓库经办人id
                Integer yn = (Integer) map1.get("yn");  //获取状态


                // 根据id获取人员信息
                String s = queryMessage(purchaseUid, eruiToken);    //将不合格发送给采购经办人
                String s2 = queryMessage(warehouseUid, eruiToken);  //将合格发送给仓库经办人

                Map<String,String> map= new HashMap();
                map.put("areaCode","86");
                map.put("subType","0");
                map.put("groupSending","0");
                map.put("useType","订单");

                //判断状态
                if (yn == 1){   //  1:  部分合格,部分不合格

                    if(s != null){
                        //发送短信
                        map.put("to","[\""+s+"\"]");
                        map.put("content","您好，采购合同号："+map1.get("purchNo")+"，报检单号："+map1.get("inspectApplyNo")+"，共计"+map1.get("sum")+"件商品出现不合格情况，请及时处理。感谢您对我们的支持与信任！");
                    }

                    if(s2 != null){
                        //发送短信
                        map.put("to","[\""+s2+"\"]");
                        map.put("content","您好，项目号："+map1.get("purchaseNames")+"，报检单号："+map1.get("inspectApplyNo")+"，共计"+map1.get("hegeNum")+"件商品已质检合格，请及时处理。感谢您对我们的支持与信任！");
                    }
                }else if(yn == 2){  // 2 全部不合格
                    // 根据id获取人员信息
                    if(s != null){
                        //发送短信
                        map.put("to","[\""+s+"\"]");
                        map.put("content","您好，采购合同号："+map1.get("purchNo")+"，报检单号："+map1.get("inspectApplyNo")+"，共计"+map1.get("sum")+"件商品出现不合格情况，请及时处理。感谢您对我们的支持与信任！");
                    }
                }else{   // 3 全部合格
                    if(s2 != null){
                        //发送短信
                        map.put("to","[\""+s2+"\"]");
                        map.put("content","您好，项目号："+map1.get("purchaseNames")+"，报检单号："+map1.get("inspectApplyNo")+"，共计"+map1.get("hegeNum")+"件商品已质检合格，请及时处理。感谢您对我们的支持与信任！");

                    }
                }

                String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                logger.info("发送短信返回状态"+s1);

            }catch (Exception e){
                throw new Exception("发送短信失败");
            }

        }
    }


    //查询人员信息

    public String  queryMessage(Integer id , String eruiToken){
        if( id != null ){
            String jsonParam = "{\"id\":\"" + id + "\"}";
            Map<String, String> header = new HashMap<>();
            header.put(EruitokenUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
            logger.info("人员详情返回信息：" + s);

            // 获取人员手机号
            JSONObject jsonObject = JSONObject.parseObject(s);
            Integer code = jsonObject.getInteger("code");
            if(code == 1) {
                JSONObject data = jsonObject.getJSONObject("data");
                return data.getString("mobile");    //获取手机号
            }
        }
        return  null;
    }



    //处理数据信息
    public void disposeData(boolean hegeFlag , int hegeNum , int sum ,InspectReport dbInspectReport , Project project) throws Exception {
        //yn    1:部分合格,部分不合格     2.全部不合格     3.全部合格

        Map<String,Object> map = new HashMap<>();
        map.put("inspectApplyNo",dbInspectReport.getInspectApplyNo());    //报检单号
        if (project != null){
            map.put("purchaseUid",project.getPurchaseUid());       //采购经办人id
            map.put("warehouseUid",project.getWarehouseUid());       //仓库经办人id
            map.put("purchaseNames",project.getProjectNo());      //项目号
        }
        map.put("purchNo",dbInspectReport.getInspectApply().getPurch().getPurchNo());      //采购合同号
        map.put("sum",sum);   //商品不合格数量
        map.put("hegeNum",hegeNum);   //商品合格数量

        if (!hegeFlag) {
            if(hegeNum != 0){   //部分合格,部分不合格
                map.put("yn",1);
            }else {//全部不合格
                map.put("yn",2);
            }
        }else { // 全部合格
            map.put("yn",3);
        }

        sendSms(map);
    }


}
