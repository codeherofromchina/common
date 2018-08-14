package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.GoodsDao;
import com.erui.order.dao.InspectApplyGoodsDao;
import com.erui.order.dao.InstockDao;
import com.erui.order.entity.*;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.service.AttachmentService;
import com.erui.order.service.BackLogService;
import com.erui.order.service.InstockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private BackLogService backLogService;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口


    @Value("#{orderProp[SSO_USER]}")
    private String ssoUser;  //从SSO获取登录用户

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
                    if (status == 0) {
                        list.add(cb.lessThan(root.get("status").as(Integer.class), 3)); //小于
                    } else if (status == 1) {
                        list.add(cb.greaterThan(root.get("status").as(Integer.class), 2));  //大于
                    }
                }
                //是否外检（ 0：否   1：是）
                if (StringUtil.isNotBlank(condition.get("outCheck"))) {
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

                    list.add(cb.equal(root.get("uid").as(Integer.class), wareHouseman));

                }

                // 销售合同号 、 项目号查询
                if (StringUtils.isNotBlank(condition.get("projectNo")) || StringUtils.isNotBlank(condition.get("contractNo"))) {
                    Set<Integer> a = queryProjectNoAndContractNo(condition.get("projectNo"), condition.get("contractNo"));
                    Integer[] objectArray2 = a.toArray(new Integer[a.size()]);
                    if (objectArray2.length == 0) {
                        list.add(root.get("id").in(-1));
                    } else {
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
                List<String> contractNoList = new ArrayList<>();
                List<String> projectNoList = new ArrayList<>();
                List<String> purchNoList = new ArrayList<>();
                // 销售合同号 和 项目号
                List<InstockGoods> instockGoodsList = instock.getInstockGoodsList();
                instockGoodsList.stream().forEach(instockGoods -> {
                    if (StringUtil.isNotBlank(instockGoods.getContractNo())) {
                        contractNoList.add(instockGoods.getContractNo());
                    }
                    PurchGoods purchGoods = instockGoods.getInspectApplyGoods().getPurchGoods();
                    Goods goods = purchGoods.getGoods();
                    purchNoList.add(purchGoods.getPurch().getPurchNo());

                    if (StringUtil.isNotBlank(goods.getProjectNo())) {
                        projectNoList.add(goods.getProjectNo());
                    }

                });
              /*  Set<String> cNoList = new HashSet<>(contractNoList);
                Set<String> pNoList = new HashSet<>(projectNoList);
                Set<String> prNoList = new HashSet<>(purchNoList);*/
                map.put("contractNos", StringUtils.join(removeRepeat(contractNoList), ","));
                map.put("projectNos", StringUtils.join(removeRepeat(projectNoList), ","));
                map.put("department", instock.getDepartment());
                // 供应商名称
                map.put("supplierName", instock.getSupplierName());
                // 入库时间

                map.put("instockDate", instock.getInstockDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(instock.getInstockDate()) : null);
                map.put("status", instock.getStatus());
                map.put("uname", instock.getUname());
                map.put("uid", instock.getUid());
                map.put("outCheck", instock.getOutCheck());//是否外检（ 0：否   1：是）
                if (purchNoList.size() > 0) {
                    map.put("purchNo", StringUtils.join(removeRepeat(purchNoList), ","));   //采购合同号
                }

                list.add(map);
            }
        }
        PageImpl<Map<String, Object>> resultPage = new PageImpl<Map<String, Object>>(list, request, page.getTotalElements());

        return resultPage;
    }

    private List<String> removeRepeat(List<String> list) {
        List<String> myList = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!myList.contains(list.get(i))) {
                    myList.add(list.get(i));
                }
            }
            return myList;
        }
        return null;
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

            if (list != null) {
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
                one.setUid(instock.getUid());   //入库经办人id
                goodsDao.saveAndFlush(one);
                applicationContext.publishEvent(new OrderProgressEvent(one.getOrder(), 6));
            }

            //入库提交的时候   删除分单员的信息推送   办理分单
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENU.getNum());    //功能访问路径标识
            backLog.setHostId(dbInstock.getId());
            backLogService.updateBackLogByDelYn(backLog);

            //入库提交的时候   删除分单人的信息推送   办理入库
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTINSTOCK.getNum());    //功能访问路径标识
            backLog2.setHostId(dbInstock.getId());
            backLogService.updateBackLogByDelYn(backLog2);

        }else if(instock.getStatus() == 2){

            //入库提交的时候   删除分单员的信息推送   办理分单
            BackLog backLog = new BackLog();
            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENU.getNum());    //功能访问路径标识
            backLog.setHostId(dbInstock.getId());
            backLogService.updateBackLogByDelYn(backLog);

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
        }
        if (instockGoodsMap.size() > 0) {
            throw new Exception(String.format("%s%s%s", "入库商品数量错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The number of goods in the warehouse"));
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
        for (InstockGoods v : instockGoodsList) {
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
    public boolean instockDeliverAgent(Instock instock) throws Exception {


        //获取经办分单人信息
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Map<String, String> map1 = ssoUser(eruiToken);
        String name = map1.get("name");
        String id = map1.get("id");

        //保存经办人信息
        Instock dbInstock = instockDao.findOne(instock.getId());
        // 保存基本信息
        dbInstock.setUid(instock.getUid());  //仓库经办人ID
        dbInstock.setUname(instock.getUname());  //仓库经办人名字
        if (StringUtil.isBlank(dbInstock.getSubmenuName())) {
            dbInstock.setSubmenuName(name); //分单员经办人姓名
        }
        if (dbInstock.getSubmenuId() == null) {
            dbInstock.setSubmenuId(Integer.parseInt(id));   //入库分单人Id
        }
        Instock instockSave = instockDao.save(dbInstock);


        //V2.0入库转交经办人：入库分单员转交推送给入库经办人
        Map<String, Object> map = new HashMap();
        map.put("projectNo", instockSave.getInstockGoodsList().get(0).getProjectNo());  //项目号
        map.put("inspectApplyNo", instockSave.getInspectApplyNo()); //报检单号
        map.put("submenuName", instockSave.getSubmenuName());   //入库分单员名称
        map.put("logisticsUserId", instockSave.getUid());   //入库经办人id
        map.put("yn", 1); //入库
        try {
            sendSms(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //指派分单人的时候   删除分单员的信息推送  办理分单
        BackLog backLog2 = new BackLog();
        backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.INSTOCKSUBMENU.getNum());    //功能访问路径标识
        backLog2.setHostId(instockSave.getId());
        backLogService.updateBackLogByDelYn(backLog2);


        //推送给分单人待办事项  办理入库
        BackLog newBackLog = new BackLog();
        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.TRANSACTINSTOCK.getMsg());  //功能名称
        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.TRANSACTINSTOCK.getNum());    //功能访问路径标识
        InspectReport inspectReport = dbInstock.getInspectReport();

        List<String> projectNoList = new ArrayList<>();
        List<InstockGoods> instockGoodsLists = dbInstock.getInstockGoodsList();
        instockGoodsLists.stream().forEach(instockGoods -> {
            PurchGoods purchGoods = instockGoods.getInspectApplyGoods().getPurchGoods();
            Goods goods = purchGoods.getGoods();
            if (StringUtil.isNotBlank(goods.getProjectNo())) {
                projectNoList.add(goods.getProjectNo());
            }
        });
        String inspectApplyNo = inspectReport.getInspectApplyNo();  //报检单号
        newBackLog.setReturnNo(inspectApplyNo);  //返回单号
        String supplierName = inspectReport.getSupplierName();  //供应商名称
        newBackLog.setInformTheContent(StringUtils.join(projectNoList,",")+" | "+supplierName);  //提示内容
        newBackLog.setHostId(instockSave.getId());    //父ID，列表页id
        newBackLog.setUid(dbInstock.getUid());   ////经办人id
        backLogService.addBackLogByDelYn(newBackLog);

        return true;
    }


    @Transactional(readOnly = true)
    public Set queryProjectNoAndContractNo(String projectNo, String contractNo) {

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
        for (Instock page1 : page) {
            idSer.add(page1.getId());
        }
        return idSer;
    }


    //V2.0入库转交经办人：入库分单员转交推送给入库经办人
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(eruiToken)) {
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            try {
                // 根据入库经办人id获取人员信息
                String jsonParam = "{\"id\":\"" + map1.get("logisticsUserId") + "\"}";
                String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + s);
                JSONObject jsonObject = JSONObject.parseObject(s);
                Integer code = jsonObject.getInteger("code");
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    String mobile = data.getString("mobile");  //获取入库经办人手机号
                    //发送短信
                    Map<String, String> map = new HashMap();
                    map.put("areaCode", "86");
                    map.put("to", "[\"" + mobile + "\"]");

                    if (Integer.valueOf((Integer) map1.get("yn")) == 1) {
                        map.put("content", "您好，项目号：" + map1.get("projectNo") + "，报检单号：" + map1.get("inspectApplyNo") + "，入库分单员：" + map1.get("submenuName") + "，请及时处理。感谢您对我们的支持与信任！");
                    } else {
                        map.put("content", "您好，销售合同号：" + map1.get("projectNo") + "，产品放行单号：" + map1.get("inspectApplyNo") + "，出库分单员：" + map1.get("submenuName") + "，请及时处理。感谢您对我们的支持与信任！");
                    }
                    map.put("subType", "0");
                    map.put("groupSending", "0");
                    map.put("useType", "订单");
                    String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                    logger.info("发送短信返回状态" + s1);
                }

            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s", "发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Failure to send SMS"));
            }

        }
    }


    //获取当前登录用户
    public Map<String, String> ssoUser(String eruiToken) {
        if (StringUtils.isNotBlank(eruiToken)) {
            Map<String, String> header = new HashMap<>();
            String jsonParam = "{\"token\":\"" + eruiToken + "\"}";
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            String s = HttpRequest.sendPost(ssoUser, jsonParam, header);
            logger.info("CRM返回信息：" + s);

            JSONObject jsonObject = JSONObject.parseObject(s);

            Map mapUser = new HashMap<>();
            if (jsonObject.getInteger("code") == 200) {
                mapUser.put("name", jsonObject.getString("name"));
                mapUser.put("id", jsonObject.getString("id"));
            }
            return mapUser;
        }
        return null;
    }


}
