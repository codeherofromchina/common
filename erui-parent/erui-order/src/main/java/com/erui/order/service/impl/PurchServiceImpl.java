package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.*;
import com.erui.order.entity.Order;
import com.erui.order.event.OrderProgressEvent;
import com.erui.order.requestVo.PurchParam;
import com.erui.order.service.*;
import com.erui.order.util.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class PurchServiceImpl implements PurchService {
    private static Logger logger = LoggerFactory.getLogger(DeliverDetailServiceImpl.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PurchDao purchDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PurchGoodsDao purchGoodsDao;
    @Autowired
    private PurchPaymentDao purchPaymentDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private BackLogService backLogService;
    @Autowired
    private BackLogDao backLogDao;

    @Autowired
    private CheckLogDao checkLogDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CheckLogService checkLogService;
    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口
    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms;  //发钉钉通知接口

    @Override
    public Purch findBaseInfo(Integer id) {
        return purchDao.findOne(id);
    }


    /**
     * 查询采购页面详情信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Purch findDetailInfo(Integer id) {
        if (id != null && id > 0) {
            Purch puch = purchDao.findOne(id);
            puch.getPurchPaymentList().size(); /// 获取合同结算类型信息
            puch.getAttachments().size(); // 获取采购的附件信息
            List<PurchGoods> purchGoodsList = puch.getPurchGoodsList();
            if (purchGoodsList.size() > 0) {
                for (PurchGoods purchGoods : purchGoodsList) {
                    purchGoods.getGoods().setPurchGoods(null);
                }
            }
            List<String> projectNoList = new ArrayList<>();
            List<String> teachcals = new ArrayList<>();
            for (Project p : puch.getProjects()) {
                projectNoList.add(p.getProjectNo());
                teachcals.add(p.getBusinessUid().toString());
            }
            puch.setProjectNos(StringUtils.join(projectNoList, ","));
            return puch;
        }
        return null;
    }


    /**
     * 查询采购页面详情信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Purch findPurchAndGoodsInfo(Integer id) {
        Purch puch = purchDao.findOne(id);
        puch.getPurchGoodsList().size(); //获取采购商品信息
        return puch;
    }

    /**
     * 根据订单id查询(进行中/已完成)采购列表
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listByOrderId(Integer orderId) throws Exception {
        Order order = orderDao.findOne(orderId);
        if (order == null) {
            throw new Exception(String.format("%s%s%s", "不存在的订单", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Nonexistent order"));
        }

        List<Map<String, Object>> result = new ArrayList<>();
        List<Purch> list = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                Join<Purch, Project> projectJoin = root.join("projects");
                Join<Project, Order> orderJoin = projectJoin.join("order");
                list.add(criteriaBuilder.equal(orderJoin.get("id").as(Integer.class), orderId));

                // (进行中/已完成)
                list.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class), Purch.StatusEnum.READY.getCode()));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });

        if (list != null && list.size() > 0) {
            Set<Integer> idSet = new HashSet<>();
            for (Purch purch : list) {
                Integer id = purch.getId();
                if (idSet.contains(id)) {
                    continue;
                }
                idSet.add(id);
                Map<String, Object> map = new HashMap<>();
                List<String> contractNoList = new ArrayList<>();
                List<String> projectNoList = new ArrayList<>();
                purch.getProjects().stream().forEach(project -> {
                    projectNoList.add(project.getProjectNo());
                    contractNoList.add(project.getContractNo());
                });
                map.put("id", purch.getId());
                map.put("purchNo", purch.getPurchNo());
                map.put("projectNos", StringUtils.join(projectNoList, ","));
                map.put("contractNos", StringUtils.join(contractNoList, ","));
                map.put("agentName", purch.getAgentName());
                map.put("agentId", purch.getAgentId());
                map.put("signingDate", purch.getSigningDate());

                result.add(map);
            }
        }
        return result;
    }

    /**
     * 采购订单审核项目操作
     *
     * @param purchId
     * @param auditorId
     * @param paramPurch 参数项目
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean audit(Integer purchId, String auditorId, String auditorName, PurchParam paramPurch) {
        Purch purch = purchDao.findOne(purchId);
        //@param rejectFlag true:驳回项目   false:审核项目
        StringBuilder auditorIds = null;
        if (purch.getAudiRemark() != null) {
            auditorIds = new StringBuilder(purch.getAudiRemark());
        } else {
            auditorIds = new StringBuilder("");
        }
        boolean rejectFlag = "-1".equals(paramPurch.getAuditingType());
        String reason = paramPurch.getAuditingReason();

        // 获取当前审核进度
        Integer auditingProcess = purch.getAuditingProcess();
        Integer auditingUserId = purch.getAuditingUserId();
        Integer curAuditProcess = null;
        if (StringUtils.equals(auditorId,auditingUserId.toString())) {
            curAuditProcess = auditingProcess;
        }
        if (curAuditProcess == null) {
            return false;
        }
        auditorIds.append("," + auditorId + ",");

        // 定义最后处理结果变量，最后统一操作
        Integer auditingStatus_i = 2; // 默认状态为审核中
        Integer auditingProcess_i = null; // 项目审核当前进度
        Integer auditingUserId_i = null; // 项目审核当前人
        CheckLog checkLog_i = null; // 审核日志
        if (rejectFlag) { // 如果是驳回，则直接记录日志，修改审核进度
            auditingStatus_i = 3;
            // 驳回到采购订单办理
            auditingProcess_i = 20; //驳回到采购订单 处理
            auditingUserId_i = purch.getCreateUserId(); // 要驳回给谁
            // 驳回的日志记录的下一处理流程和节点是当前要处理的节点信息
            checkLog_i = orderService.fullCheckLogInfo(purch.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, purch.getAuditingProcess().toString(), purch.getAuditingUserId().toString(), reason, "-1", 3);
        } else {
            switch (curAuditProcess) {
                case 21: // 采购经办人审核核算
                    auditingProcess_i = 22;
                    auditingUserId_i = purch.getBusinessAuditerId();
                    break;
                case 22://商务技术经办人审核
                    List<Project> projects = purch.getProjects();
                    Set<Integer> businessNames = new HashSet<>();
                    if (projects.size()>1){
                        for (Project project:projects) {
                            businessNames.add(project.getBusinessUid());
                        }
                    } else {
                        auditingProcess_i = 23;
                        auditingUserId_i = purch.getLegalAuditerId();
                    }
                    auditingProcess_i = 23;
                    auditingUserId_i = purch.getLegalAuditerId();
                    break;
                case 23://法务经办人审核
                    auditingProcess_i = 24;
                    auditingUserId_i = purch.getFinanceAuditerId();
                    break;
                case 24://财务经办人审核
                    auditingProcess_i = 25;
                    auditingUserId_i = purch.getBuVpAuditerId();
                    break;
                case 25://事业部vp审核
                    if (purch.getTotalPrice() != null && purch.getTotalPrice().doubleValue() <= 1000000) {
                        auditingProcess_i = 26;
                        auditingUserId_i = purch.getChairmanId();
                    } else {
                        auditingStatus_i = 4; // 完成
                        auditingProcess_i = null;
                        auditingUserId_i = null;
                    }
                    break;
                case 26://总裁审核
                    auditingStatus_i = 4; // 完成
                    auditingProcess_i = null;
                    auditingUserId_i = null;
                    break;
                default:
                    return false;
            }
            checkLog_i = orderService.fullCheckLogInfo(purch.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, purch.getAuditingProcess().toString(), purch.getAuditingUserId().toString(), reason, "-1", 2);
        }
        checkLogService.insert(checkLog_i);
        if (!paramPurch.getAuditingType().equals("-1")) {
            purch.setAuditingStatus(auditingStatus_i);
        }
        purch.setAuditingStatus(auditingStatus_i);
        purch.setAuditingProcess(auditingProcess_i);
        purch.setAuditingUserId(auditingUserId_i);
        if (auditingUserId_i != null) {
            sendDingtalk(purch, auditingUserId_i.toString(), rejectFlag);
        }
        purchDao.save(purch);
        return true;
    }

    //钉钉通知 审批人
    private void sendDingtalk(Purch purch, String user, boolean rejectFlag) {
        //获取token
        final String eruiToken = (String) ThreadLocalUtil.getObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("发送短信的用户token:" + eruiToken);
                // 根据id获取商务经办人信息
                String jsonParam = "{\"id\":\"" + user + "\"}";
                Map<String, String> header = new HashMap<>();
                header.put(CookiesUtil.TOKEN_NAME, eruiToken);
                header.put("Content-Type", "application/json");
                header.put("accept", "*/*");
                String userInfo = HttpRequest.sendPost(memberInformation, jsonParam, header);
                logger.info("人员详情返回信息：" + userInfo);
                //钉钉通知接口头信息
                Map<String, String> header2 = new HashMap<>();
                header2.put("Cookie", eruiToken);
                header2.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                JSONObject jsonObject = JSONObject.parseObject(userInfo);
                Integer code = jsonObject.getInteger("code");
                String userNo = null;
                String userName = null;  //商务经办人手机号
                if (code == 1) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    //获取通知者姓名员工编号
                    //userName = data.getString("name");
                    userNo = data.getString("user_no");
                    Long startTime = System.currentTimeMillis();
                    Date sendTime = new Date(startTime);
                    String sendTime02 = DateUtil.format(DateUtil.FULL_FORMAT_STR, sendTime);
                    //发送钉钉通知
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("toUser=").append(userNo);
                    if (!rejectFlag) {
                        stringBuffer.append("&message=您好！" + purch.getAgentName() + "的采购合同，已申请合同审批。采购合同号:" + purch.getPurchNo() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    } else {
                        stringBuffer.append("&message=您好！" + purch.getAgentName() + "的采购合同，已申请的合同审核未通过。采购合同号:" + purch.getPurchNo() + "，请您登录BOSS系统及时处理。感谢您对我们的支持与信任！" +
                                "" + sendTime02 + "");
                    }
                    stringBuffer.append("&type=userNo");
                    String s1 = HttpRequest.sendPost(dingSendSms, stringBuffer.toString(), header2);
                    logger.info("发送钉钉通知返回状态" + s1);
                }
            }
        }).start();

    }

    /**
     * 根据条件分页查询采购单信息
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Purch> findByPage(final Purch condition) {
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getRows(), Sort.Direction.DESC, "id");

        Page<Purch> page = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (StringUtil.isNotBlank(condition.getPurchNo())) {
                    list.add(cb.like(root.get("purchNo").as(String.class), "%" + condition.getPurchNo() + "%"));
                }
                // 根据采购经办人查询
                if (condition.getAgentId() != null) {
                    list.add(cb.equal(root.get("agentId").as(Integer.class), condition.getAgentId()));
                }
                // 根据采购签约日期查询
                if (condition.getSigningDate() != null) {
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(condition.getSigningDate())));
                }
                // 根据采采购合同规定交货日期查询
                if (condition.getArrivalDate() != null) {
                    list.add(cb.equal(root.get("arrivalDate").as(Date.class), NewDateUtil.getDate(condition.getArrivalDate())));
                }

                // 根据项目号和销售合同号查询
                if (!(StringUtils.isBlank(condition.getProjectNos()) && StringUtils.isBlank(condition.getContractNos()))) {
                    Set<Purch> purchSet = findByProjectNoAndContractNo(condition.getProjectNos(), condition.getContractNos());
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (purchSet != null && purchSet.size() > 0) {
                        for (Purch p : purchSet) {
                            idIn.value(p.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);
                }

                //根据供应商过滤条件
                if (condition.getSupplierId() != null) {
                    list.add(cb.equal(root.get("supplierId").as(Integer.class), condition.getSupplierId()));
                }

                Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(condition.getStatus());
                if (statusEnum != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), statusEnum.getCode()));
                }


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        if (page.hasContent()) {
            page.getContent().stream().forEach(vo -> {
                List<String> projectNoList = new ArrayList<>();
                List<String> contractNoList = new ArrayList<>();
                vo.getProjects().stream().forEach(project -> {
                    projectNoList.add(project.getProjectNo());
                    contractNoList.add(project.getContractNo());
                });
                vo.setProjectNos(StringUtils.join(projectNoList, ","));
                vo.setContractNos(StringUtils.join(contractNoList, ","));
                vo.getPurchGoodsList().size();
            });
        }

        return page;
    }

    // 根据销售号和项目号查询采购列表信息
    private Set<Purch> findByProjectNoAndContractNo(String projectNo, String contractNo) {
        Set<Purch> result = null;
        List<Purch> list = purchDao.findAll(new Specification<Purch>() {
            @Override
            public Predicate toPredicate(Root<Purch> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Join<Purch, Project> projects = root.join("projects");
                if (StringUtils.isNotBlank(projectNo)) {
                    list.add(cb.like(projects.get("projectNo").as(String.class), "%" + projectNo + "%"));
                }
                if (StringUtils.isNotBlank(contractNo)) {
                    list.add(cb.like(projects.get("contractNo").as(String.class), "%" + contractNo + "%"));
                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        });
        if (list != null && list.size() > 0) {
            result = new HashSet<>(list);
        }
        return result;
    }


    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Purch purch) throws Exception {
        Date now = new Date();
        String lastedByPurchNo = purchDao.findLastedByPurchNo();
        Long count = purchDao.findCountByPurchNo(lastedByPurchNo);
        if (count != null && count > 1) {
            throw new Exception(String.format("%s%s%s", "采购合同号重复", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Repeat purchase contract number"));
        }
        // 设置基础数据 自动生成采购合同号
        purch.setPurchNo(StringUtil.genPurchNo(lastedByPurchNo));
        purch.setSigningDate(NewDateUtil.getDate(purch.getSigningDate()));
        purch.setArrivalDate(NewDateUtil.getDate(purch.getArrivalDate()));
        purch.setCreateTime(now);

        // 处理结算方式,新增，所以讲所有id设置为null，并添加新增时间
        purch.getPurchPaymentList().parallelStream().forEach(vo -> {
            vo.setId(null);
            vo.setCreateTime(now);
        });
        // 处理附件信息
        List<Attachment> attachments = attachmentService.handleParamAttachment(null, purch.getAttachments(), purch.getCreateUserId(), purch.getCreateUserName());
        purch.setAttachments(attachments);
        // 处理商品信息
        List<PurchGoods> purchGoodsList = new ArrayList<>();
        Set<Project> projectSet = new HashSet<>();
        //List<Goods> updateGoods = new ArrayList<>();
        for (PurchGoods purchGoods : purch.getPurchGoodsList()) {
            // 检查是否传入采购数量或者替换商品
            Integer purchaseNum = purchGoods.getPurchaseNum(); // 获取采购数量
            PurchGoods tSon = purchGoods.getSon(); // 获取替换商品
            if ((purchaseNum == null || purchaseNum <= 0) && tSon == null) {
                // 传入的商品没有数量，表示不采购此商品
                continue;
            }
            // 获取要采购的商品
            Goods goods = goodsDao.findOne(purchGoods.getgId());
            if (goods == null || goods.getExchanged()) {
                // 给定的商品不存在或者是被替换的商品，则错误
                throw new Exception(String.format("%s%s%s", "商品不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Goods do not exist"));
            }
            Project project = goods.getProject();
            // 必须是已创建采购申请单并未完成采购的项目
            if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                throw new Exception(String.format("%s%s%s", "项目必须提交采购申请", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The project must submit a purchase application"));

            }
            if (project.getPurchDone()) {
                throw new Exception(String.format("%s%s%s", "项目采购已完成，不能再次采购", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project procurement has been completed and can not be repurchased"));
            }
            projectSet.add(project);
            PurchGoods son = handleAddNewPurchGoods(project, purch, goods, purchGoods);
            purchGoodsList.add(purchGoods);
            if (son != null) {
                purchGoodsList.add(son);
            }
            int intPurchaseNum = purchGoods.getPurchaseNum();
            if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                // 如果是提交则设置商品的已采购数量并更新
                goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
                // 完善商品的项目执行跟踪信息
                setGoodsTraceData(goods, purch);
                if (!goods.getOrder().getOrderCategory().equals(6)) {
                    applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3));
                }
            }
            // 增加预采购数量
            goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
            // 直接更新商品，放置循环中存在多次修改同一个商品错误
            goodsDao.save(goods);
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
        }
        purch.setPurchGoodsList(purchGoodsList);
        List<Project> projectList = new ArrayList<>(projectSet);
        purch.setProjects(projectList);
        // 保存采购单
        if (purch.getProjects().size() > 0 && purch.getProjects().get(0).getOrderCategory().equals(6) && purch.getStatus() > 1) {
            purch.setStatus(3);
        }
        // 采购审批添加部分
        if (purch.getStatus() == Purch.StatusEnum.READY.getCode()) {
            purch.setAuditingStatus(0);
        } else if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            purch.setAuditingProcess(21);
            purch.setAuditingStatus(1);
            purch.setAuditingUserId(purch.getPurchAuditerId());
        }
        CheckLog checkLog_i = null;//审批流日志

        Purch save = purchDao.save(purch);
        if (save.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            checkLog_i = orderService.fullCheckLogInfo(save.getId(), 20, save.getCreateUserId(), save.getCreateUserName(), save.getAuditingProcess().toString(), save.getPurchAuditer().toString(), save.getAuditingReason(), "1", 3);
            checkLogService.insert(checkLog_i);
        }
        if (save.getStatus() == 2) {
            List<Project> projects = save.getProjects();
            Set<String> projectNoSet = new HashSet<>();
            if (projects.size() > 0) {
                for (Project project : projects) {
                    String projectNo = project.getProjectNo();
                    if (projectNo != null) {
                        projectNoSet.add(projectNo);
                    }
                }

                //采购新增提交以后  通知采购经办人办理报检单
                BackLog newBackLog = new BackLog();
                newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.INSPECTAPPLY.getMsg());  //功能名称
                newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTAPPLY.getNum());    //功能访问路径标识
                newBackLog.setReturnNo(purch.getPurchNo());  //返回单号    返回空，两个标签
                newBackLog.setInformTheContent(StringUtils.join(projectNoSet, ",") + " | " + save.getSupplierName());  //提示内容
                newBackLog.setHostId(save.getId());    //父ID，列表页id   采购id
                Integer purchaseUid = save.getAgentId();//采购经办人id
                newBackLog.setUid(purchaseUid);   ////经办人id
                backLogService.addBackLogByDelYn(newBackLog);

            }

        }

        // 检查项目是否已经采购完成
        List<Integer> projectIds = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIds);
        return true;
    }


    private void setGoodsTraceData(Goods goods, Purch purch) {
        // 完善商品的项目执行跟踪信息 (只设置第一次的信息)
        if (goods.getPurChgDate() == null) {
            goods.setPurChgDate(purch.getPurChgDate());
        }
        if (goods.getSigningDate() == null) {
            goods.setSigningDate(purch.getSigningDate());
        }
        if (goods.getAgentId() == null) {
            goods.setAgentId(purch.getAgentId());
        }
        if (goods.getArrivalDate() == null) {
            goods.setArrivalDate(purch.getArrivalDate());
        }
    }


    /**
     * 更新采购单
     *
     * @param purch
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Purch purch) throws Exception {
        Purch dbPurch = purchDao.findOne(purch.getId());
        // 之前的采购必须不能为空且未提交状态
        if (dbPurch == null || dbPurch.getDeleteFlag()) {
            throw new Exception(String.format("%s%s%s", "采购信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Procurement information does not exist"));
        } else if (dbPurch.getStatus() != Purch.StatusEnum.READY.getCode()) {
            throw new Exception(String.format("%s%s%s", "采购信息已提交不能修改", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase information has been submitted and can not be modified"));
        }
        final Date now = new Date();
        // 设置基本信息
        dbPurch.setBaseInfo(purch);
        dbPurch.setUpdateTime(now);

        // 处理结算方式
        Map<Integer, PurchPayment> collect = dbPurch.getPurchPaymentList().parallelStream().collect(Collectors.toMap(PurchPayment::getId, vo -> vo));
        List<PurchPayment> paymentList = purch.getPurchPaymentList().parallelStream().filter(vo -> {
            Integer payId = vo.getId();
            // 过滤掉可能是其他采购信息的结算方式
            return payId == null || collect.containsKey(payId);
        }).map(payment -> {
            Integer paymentId = payment.getId();
            if (paymentId == null) {
                payment.setCreateTime(now);
                return payment;
            }
            PurchPayment payment2 = collect.remove(paymentId);
            payment2.setReceiptDate(payment.getReceiptDate());
            payment2.setType(payment.getType());
            payment2.setMoney(payment.getMoney());
            payment2.setTitle(payment.getTitle());
            return payment2;
        }).collect(Collectors.toList());
        dbPurch.setPurchPaymentList(paymentList);
        // 删除废弃的结算方式
        if (collect.size() > 0) {
            purchPaymentDao.delete(collect.values());
        }
        // 处理附件信息
        List<Attachment> attachmentlist = attachmentService.handleParamAttachment(dbPurch.getAttachments(), purch.getAttachments(), purch.getCreateUserId(), purch.getCreateUserName());
        dbPurch.setAttachments(attachmentlist);

        // 处理商品
        List<PurchGoods> purchGoodsList = new ArrayList<>(); // 声明最终采购商品容器
        Set<Project> projectSet = new HashSet<>(); // 声明项目的容器
        // 数据库现在的采购商品信息
        Map<Integer, PurchGoods> dbPurchGoodsMap = dbPurch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();
        // 处理参数中的采购商品信息
        for (PurchGoods pg : purch.getPurchGoodsList()) {
            Integer pgId = pg.getId();
            if (pgId == null) { // 新增加的采购商品信息
                // 检查是否传入采购数量或者替换商品
                Integer purchaseNum = pg.getPurchaseNum(); // 获取采购数量
                PurchGoods tSon = pg.getSon(); // 获取替换商品
                if ((purchaseNum == null || purchaseNum <= 0) && tSon == null) {
                    // 传入的商品没有数量，表示不采购此商品
                    continue;
                }
                // 获取要采购的商品
                Goods goods = goodsDao.findOne(pg.getgId());
                if (goods == null || goods.getExchanged()) {
                    throw new Exception(String.format("%s%s%s", "商品不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Goods do not exist"));
                }
                Project project = goods.getProject();
                // 必须是已创建采购申请单并未完成采购的项目
                if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                    throw new Exception(String.format("%s%s%s", "项目必须提交采购申请", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The project must submit a purchase application"));
                }
                if (project.getPurchDone()) {
                    throw new Exception(String.format("%s%s%s", "项目采购已完成，不能再次采购", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project procurement has been completed and can not be repurchased"));
                }
                projectSet.add(project);
                // 查看是否存在替换商品
                PurchGoods son = handleAddNewPurchGoods(project, dbPurch, goods, pg);
                purchGoodsList.add(pg);
                if (son != null) {
                    purchGoodsList.add(son);
                }
                Integer intPurchaseNum = pg.getPurchaseNum();
                // 更新商品的采购数量和预采购数量
                if (purch.getStatus() == Purch.StatusEnum.DONE.getCode()) {
                    // 更新已采购数量
                    goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                    if (!goods.getOrder().getOrderCategory().equals(6)) {
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3));
                    }
                }
                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
                goodsDao.save(goods);
            } else if (dbPurchGoodsMap.containsKey(pgId)) {
                Integer paramPurchaseNum = pg.getPurchaseNum();
                // 验证是删除还是修改，传入的商品采购数量0也作为删除
                if (paramPurchaseNum == null || paramPurchaseNum <= 0) {
                    PurchGoods purchGoods = dbPurchGoodsMap.get(pgId);
                    if (purchGoods.getExchanged()) {
                        // 是替换后的商品，则直接删除（跳过处理后在循环外删除）
                        continue;
                    } else if (pg.getSon() == null) { // 不是替换商品，查看是否存在替换后商品，如果不存在，则删除
                        PurchGoods sonPurchGoods = purchGoodsDao.findByPurchIdAndParentId(dbPurch.getId(), pgId);
                        if (sonPurchGoods == null) {
                            continue;
                        } else {
                            // 查找参数中的替换后商品
                            PurchGoods paramSonPurchGoods = purch.getPurchGoodsList().parallelStream().filter(vo -> {
                                Integer id = vo.getId();
                                if (id != null && id.intValue() == sonPurchGoods.getId()) {
                                    return true;
                                }
                                return false;
                            }).findFirst().get();
                            if (paramSonPurchGoods == null) {
                                continue;
                            }
                            Integer paramSonPurchaseNum = paramSonPurchGoods.getPurchaseNum();
                            if (paramSonPurchaseNum == null || paramSonPurchaseNum <= 0) {
                                continue;
                            }
                        }
                    } else {
                        Integer purchaseNum = pg.getSon().getPurchaseNum();
                        if (purchaseNum == null || purchaseNum <= 0) {
                            continue;
                        }
                    }
                }
                // 编辑原来的采购商品
                PurchGoods purchGoods = dbPurchGoodsMap.remove(pgId);
                existId.add(pgId);
                Project project = purchGoods.getProject();

                boolean hasSon = false;
                if (purchGoods.getExchanged()) {
                    // 是替换商品，查看父商品是否存在
                    PurchGoods parentPurchGoods = purchGoods.getParent();
                    Integer pId = parentPurchGoods.getId();
                    if (!existId.contains(pId)) {
                        // 查找替换前的商品先处理
                        PurchGoods paramParentPurchGoods = purch.getPurchGoodsList().parallelStream().filter(vo -> vo.getId().intValue() == pId).findFirst().get();
                        if (paramParentPurchGoods == null) {
                            throw new Exception(String.format("%s%s%s", "父采购商品信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The parent purchase commodity information does not exist"));
                        }
                    }
                } else {
                    // 不是替换商品，查看是否添加了替换商品
                    hasSon = pg.getSon() != null;
                }
                // 正常添加
                projectSet.add(project);
                int oldPurchaseNum = purchGoods.getPurchaseNum();
                purchGoods.setPurchaseNum(pg.getPurchaseNum() == null && pg.getPurchaseNum() < 0 ? 0 : pg.getPurchaseNum()); // 采购商品数量
                purchGoods.setPurchasePrice(pg.getPurchasePrice()); // 采购单价
                purchGoods.setPurchaseTotalPrice(pg.getPurchaseTotalPrice()); //  采购总金额
                purchGoods.setPurchaseRemark(pg.getPurchaseRemark()); // 采购说明
                // 计算含税价格和不含税单价以及总价款
                String currencyBn = purch.getCurrencyBn();
                if (purchGoods.getPurchaseNum() > 0) {
                    if (purchGoods.getPurchasePrice() == null || purchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1) {
                        throw new Exception(String.format("%s%s%s", "商品的采购价格错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase price error of goods"));
                    }
                    if ("CNY".equals(currencyBn)) {
                        // 人民币是默认含税价格
                        purchGoods.setTaxPrice(purchGoods.getPurchasePrice());
                        purchGoods.setNonTaxPrice(purchGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
                    } else {
                        purchGoods.setNonTaxPrice(purchGoods.getPurchasePrice());
                    }
                    // 总价款
                    purchGoods.setTotalPrice(purchGoods.getPurchasePrice().multiply(new BigDecimal(purchGoods.getPurchaseNum().intValue())));
                }
                purchGoodsList.add(purchGoods);

                int purchaseNum = purchGoods.getPurchaseNum();
                // 从数据库查询一次商品做修改
                Goods goods = goodsDao.findOne(purchGoods.getGoods().getId());
                if (hasSon) {
                    // 处理替换商品
                    PurchGoods son = pg.getSon();
                    handleExchangedPurchGoods(project, goods, dbPurch, purchGoods, son);
                    purchGoodsList.add(son);
                }
                // 提交则修改商品的已采购数量
                if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                    if (!goods.getOrder().getOrderCategory().equals(6)) {
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3));
                    }
                }
                // 判断采购是否超限,预采购数量大于合同数量，则错误
                if (goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum > goods.getContractGoodsNum()) {
                    throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

                }
                if (purchaseNum > 0 &&
                        (purchGoods.getPurchasePrice() == null || purchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
                    throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

                }

                goods.setPrePurchsedNum(goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum);
                goodsDao.save(goods);
            } else {
                throw new Exception(String.format("%s%s%s", "不存在的采购商品信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Non existent procurement of commodity information"));

            }
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));

        }
        dbPurch.setPurchGoodsList(purchGoodsList);
        List<Project> projectList = new ArrayList<>(projectSet);
        dbPurch.setProjects(projectList);

        // 删除不关联的商品信息
        if (dbPurchGoodsMap.size() > 0) {
            Collection<PurchGoods> values = dbPurchGoodsMap.values();
            // 修改商品的预采购数量然后再删除
            List<Goods> deleteGoods = new ArrayList<>();
            for (PurchGoods pg : values) {
                Integer purchaseNum = pg.getPurchaseNum();
                Goods one = goodsDao.findOne(pg.getGoods().getId());
                if (one.getExchanged()) {
                    // 是替换后的商品，则将此商品删除，并增加父商品的合同数量
                    Goods parentOne = goodsDao.findOne(one.getParentId());
                    parentOne.setContractGoodsNum(parentOne.getContractGoodsNum() + purchaseNum);
                    goodsDao.save(parentOne);
                    //goodsDao.delete(one);
                    deleteGoods.add(one);
                } else {
                    one.setPrePurchsedNum(one.getPrePurchsedNum() - purchaseNum);
                    goodsDao.save(one);
                }
            }
            purchGoodsDao.delete(values);
            if (deleteGoods.size() > 0) {
                // 后删除商品
                goodsDao.delete(deleteGoods);
            }
        }
        // 更新采购单
        if (dbPurch.getProjects().size() > 0 && dbPurch.getProjects().get(0).getOrderCategory().equals(6) && purch.getStatus() > 1) {
            dbPurch.setStatus(3);
        }
        // 采购审批添加部分
        if (purch.getStatus() == Purch.StatusEnum.READY.getCode()) {
            dbPurch.setAuditingStatus(0);
        } else if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            dbPurch.setAuditingProcess(21);
            dbPurch.setAuditingStatus(1);
            dbPurch.setAuditingUserId(purch.getPurchAuditerId());
        }
        CheckLog checkLog_i = null;//审批流日志

        Purch save = purchDao.save(dbPurch);
        if (save.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            checkLog_i = orderService.fullCheckLogInfo(save.getId(), 20, save.getCreateUserId(), save.getCreateUserName(), save.getAuditingProcess().toString(), save.getPurchAuditer().toString(), save.getAuditingReason(), "1", 3);
            checkLogService.insert(checkLog_i);
        }
        if (save.getStatus() == 2) {
            List<Project> projects = save.getProjects();
            Set<String> projectNoSet = new HashSet<>();
            if (projects.size() > 0) {
                for (Project project : projects) {
                    String projectNo = project.getProjectNo();
                    if (projectNo != null) {
                        projectNoSet.add(projectNo);
                    }
                }

                //采购新增提交以后  通知采购经办人办理报检单
                BackLog newBackLog = new BackLog();
                newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.INSPECTAPPLY.getMsg());  //功能名称
                newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.INSPECTAPPLY.getNum());    //功能访问路径标识
                newBackLog.setReturnNo(dbPurch.getPurchNo());  //返回单号    返回空，两个标签
                newBackLog.setInformTheContent(StringUtils.join(projectNoSet, ",") + " | " + save.getSupplierName());  //提示内容
                newBackLog.setHostId(save.getId());    //父ID，列表页id
                Integer purchaseUid = save.getAgentId();//采购经办人id
                newBackLog.setUid(purchaseUid);   ////经办人id
                backLogService.addBackLogByDelYn(newBackLog);

            }

        }


        // 检查项目是否已经采购完成
        List<Integer> projectIdList = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIdList);
        return true;
    }

    // 处理新增采购信息，如果采购信息有替换的商品，则返回处理后的替换信息
    private PurchGoods handleAddNewPurchGoods(Project project, Purch purch, Goods goods, PurchGoods newPurchGoods) throws Exception {
        // 设置新采购的基本信息
        String contractNo = project.getContractNo();
        String projectNo = project.getProjectNo();
        newPurchGoods.setId(null);
        newPurchGoods.setProject(project);
        newPurchGoods.setContractNo(contractNo);
        newPurchGoods.setProjectNo(projectNo);
        newPurchGoods.setPurch(purch);
        newPurchGoods.setGoods(goods);
        Integer purchaseNum = newPurchGoods.getPurchaseNum();
        purchaseNum = purchaseNum != null && purchaseNum > 0 ? purchaseNum : 0;
        newPurchGoods.setPurchaseNum(purchaseNum);
        // 判断采购是否超限,预采购数量大于合同数量，则错误
        if (goods.getPrePurchsedNum() + purchaseNum > goods.getContractGoodsNum()) {
            throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sk" +
                    "u :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

        }
        if (purchaseNum > 0 &&
                (newPurchGoods.getPurchasePrice() == null || newPurchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
            throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

        }
        newPurchGoods.setExchanged(false);
        newPurchGoods.setInspectNum(0); // 设置已报检数量
        newPurchGoods.setPreInspectNum(0); // 设置预报检数量
        newPurchGoods.setGoodNum(0); // 设置检验合格商品数量
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purch.getCurrencyBn();
        if (newPurchGoods.getPurchaseNum() > 0) {
            // 只有商品采购数量不为空才计算价格等
            if (newPurchGoods.getPurchasePrice() == null) {
                // 有采购数量，但采购价格为空，则错误
                throw new Exception(String.format("%s%s%s", "商品的采购价格错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Purchase price error of goods"));
            }
            // 人民币币种默认是含税价格
            if ("CNY".equals(currencyBn)) {
                // 人民币是默认含税价格
                newPurchGoods.setTaxPrice(newPurchGoods.getPurchasePrice());
                newPurchGoods.setNonTaxPrice(newPurchGoods.getPurchasePrice().divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
            } else {
                newPurchGoods.setNonTaxPrice(newPurchGoods.getPurchasePrice());
            }
            // 总价款
            newPurchGoods.setTotalPrice(newPurchGoods.getPurchasePrice().multiply(new BigDecimal(newPurchGoods.getPurchaseNum().intValue())));
        }
        newPurchGoods.setCreateTime(new Date());

        // 查看是否存在替换商品
        PurchGoods son = newPurchGoods.getSon();
        if (son != null) {
            // 处理替换商品
            handleExchangedPurchGoods(project, goods, purch, newPurchGoods, son);
        }
        return son;
    }


    /**
     * 处理替换后的商品信息
     */
    private void handleExchangedPurchGoods(Project project, Goods beforeGoods, Purch purch, PurchGoods beforePurchGoods, PurchGoods son) throws Exception {
        Integer purchaseNum = son.getPurchaseNum();
        // 检查采购数量、采购单价
        if (purchaseNum == null || purchaseNum <= 0) {
            throw new Exception(String.format("%s%s%s", "替换商品采购数量错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement of commodity purchase quantity error"));
        }
        BigDecimal purchasePrice = son.getPurchasePrice();
        if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) != 1) {
            throw new Exception(String.format("%s%s%s", "替换商品采购单价错误", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement of commodity purchase unit price error"));
        }
        // 检查采购数量是否超合同数量
        if (purchaseNum + beforeGoods.getPrePurchsedNum() > beforeGoods.getContractGoodsNum()) {
            throw new Exception(String.format("%s%s%s", "替换商品采购超限【父SKU:" + beforeGoods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement of commodity purchase overrun [father SKU:" + beforeGoods.getSku() + "]"));
        }
        //  插入替换后的新商品
        Goods sonGoods = son.getGoods();
        if (sonGoods == null) {
            throw new Exception(String.format("%s%s%s", "替换采购的商品信息不完整【父SKU:" + beforeGoods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Replacement purchase information is incomplete (parent SKU:" + beforeGoods.getSku() + ")"));
        }
        /*else if (StringUtils.isAnyBlank(sonGoods.getSku(),sonGoods.getNameEn(),sonGoods.getNameZh())) {
            throw new Exception("替换采购的商品信息不正确（sku/名称）【父SKU:" + beforeGoods.getSku() + "】");
        }*/
        sonGoods.setParentId(beforeGoods.getId());
        sonGoods.setExchanged(true);
        sonGoods.setOrder(beforeGoods.getOrder());
        sonGoods.setProject(project);
        sonGoods.setContractNo(beforeGoods.getContractNo());
        sonGoods.setProjectNo(beforeGoods.getProjectNo());
        // 设置商品合同数量，同时将父商品的合同数量减少
        sonGoods.setContractGoodsNum(purchaseNum);
        beforeGoods.setContractGoodsNum(beforeGoods.getContractGoodsNum() - purchaseNum);
        // 设置客户需求描述信息
        sonGoods.setClientDesc(beforeGoods.getClientDesc());
        // 其他字段
        sonGoods.setRequirePurchaseDate(beforeGoods.getRequirePurchaseDate());
        if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            // 如果是提交则设置商品的已采购数量并更新
            sonGoods.setPurchasedNum(purchaseNum);
            // 完善商品的项目执行跟踪信息
            setGoodsTraceData(sonGoods, purch);
        } else {
            sonGoods.setPurchasedNum(0);
        }
        sonGoods.setPrePurchsedNum(purchaseNum);
        sonGoods.setInspectNum(0);
        sonGoods.setInstockNum(0);
        sonGoods.setOutstockApplyNum(0);
        sonGoods.setOutstockNum(0);
        sonGoods = goodsDao.save(sonGoods);

        // 处理替换后的采购信息
        son.setProject(project);
        son.setProjectNo(project.getProjectNo());
        son.setParent(beforePurchGoods);
        son.setContractNo(beforeGoods.getContractNo());
        son.setPurch(purch);
        son.setGoods(sonGoods);
        son.setExchanged(true);
        // 计算含税价格和不含税单价以及总价款
        String currencyBn = purch.getCurrencyBn();
        // 人民币默认是含税价格
        if ("CNY".equals(currencyBn)) {
            // 人民币是默认含税价格
            son.setTaxPrice(purchasePrice);
            son.setNonTaxPrice(purchasePrice.divide(new BigDecimal("1.17"), 4, BigDecimal.ROUND_DOWN));
        } else {
            son.setNonTaxPrice(purchasePrice);
        }
        // 总价款
        son.setTotalPrice(purchasePrice.multiply(new BigDecimal(purchaseNum.intValue())));
        son.setGoodNum(0);
        son.setInspectNum(0);
        son.setPreInspectNum(0);
        son.setCreateTime(new Date());
    }


    /**
     * 检查采购项目的采购数量是否已完毕
     *
     * @param projectIds 项目ID列表
     */
    private void checkProjectPurchDone(List<Integer> projectIds) throws Exception {
        List<Integer> updateIds = new ArrayList<>();
        List<Integer> proIds = new ArrayList<>();
        List<Integer> orderIds = new ArrayList<>();
        if (projectIds.size() > 0) {
            List<Project> projectList = projectDao.findByIdIn(projectIds);
            for (Project project : projectList) {
                List<Goods> goodsList = project.getOrder().getGoodsList();
                boolean purchDone = true;
                for (Goods goods : goodsList) {
                    if (!goods.getExchanged() && goods.getPurchasedNum() < goods.getContractGoodsNum()) {
                        purchDone = false;
                        break;
                    }
                }
                if (purchDone) {
                    updateIds.add(project.getId());
                    if (project.getOrderCategory().equals(6)) {
                        proIds.add(project.getId());
                        orderIds.add(project.getOrder().getId());
                    }

                    //采购数量是已完毕 ，删除   “办理采购订单”  待办提示
                    BackLog backLog = new BackLog();
                    backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCHORDER.getNum());    //功能访问路径标识
                    backLog.setHostId(project.getId());
                    backLogService.updateBackLogByDelYn(backLog);

                }
            }
            if (updateIds.size() > 0) {
                //项目采购完成
                projectDao.updateProjectPurchDone(updateIds);
                //当订单类型为国内订单时项目完结 订单状态修改为已完成 项目状态为已完成 流程进度修改为已发运
                if (proIds.size() > 0) {
                    projectDao.updateProjectStatus(proIds);
                    orderDao.updateOrderStatus(orderIds);
                }
            }
        }
    }

}
