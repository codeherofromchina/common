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
import com.erui.order.event.PurchDoneCheckEvent;
import com.erui.order.event.TasksAddEvent;
import com.erui.order.requestVo.PurchParam;
import com.erui.order.service.*;
import com.erui.order.util.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.math.MathContext;
import java.math.RoundingMode;
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
    private PurchRequisitionDao purchRequisitionDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private PurchGoodsDao purchGoodsDao;
    @Autowired
    private PurchContractGoodsDao purchContractGoodsDao;
    @Autowired
    private PurchPaymentDao purchPaymentDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private BackLogService backLogService;
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
            List<Attachment> attachments = attachmentDao.findByRelObjIdAndCategory(puch.getId(), Attachment.AttachmentCategory.PURCH.getCode());
            if (attachments != null && attachments.size() > 0) {
                puch.setAttachments(attachments);
                puch.getAttachments().size(); // 获取采购的附件信息
            }
            List<PurchGoods> purchGoodsList = puch.getPurchGoodsList();
            if (purchGoodsList.size() > 0) {
                for (PurchGoods purchGoods : purchGoodsList) {
                    purchGoods.setgId(purchGoods.getGoods().getId());
                    purchGoods.setPcgId(purchGoods.getPurchContractGoods().getId());
                    purchGoods.getGoods().setPurchGoods(null);
                    purchGoods.getPurchContractGoods().setPurchGoods(null);
                    purchGoods.getPurchContractGoods().setGoods(null);
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
        Purch purch = findDetailInfo(purchId);
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
        String auditingProcess = purch.getAuditingProcess();
        String auditingUserId = purch.getAuditingUserId();
        Integer curAuditProcess = null;
        boolean isComeMore = Boolean.FALSE;// 是否来自并行的审批，且并行还没走完。
        if (StringUtils.equals(auditorId, auditingUserId)) {
            curAuditProcess = Integer.parseInt(auditingProcess);
        } else {
            if (auditingUserId.indexOf(auditorId) >= 0) {
                curAuditProcess = Integer.parseInt(auditingProcess.split(",")[auditingUserId.indexOf(auditorId) == 0 ? 0 : 1]);
            }
        }
        if (curAuditProcess == null) {
            return false;
        }
        auditorIds.append("," + auditorId + ",");

        // 定义最后处理结果变量，最后统一操作
        Integer auditingStatus_i = 2; // 默认状态为审核中
        String auditingProcess_i = null; // 项目审核当前进度
        String auditingUserId_i = null; // 项目审核当前人
        CheckLog checkLog_i = null; // 审核日志
        if (rejectFlag) { // 如果是驳回，则直接记录日志，修改审核进度
            auditingStatus_i = 3;
            // 驳回到采购订单办理
            auditingProcess_i = "20"; //驳回到采购订单 处理
            auditingUserId_i = purch.getCreateUserId() + ""; // 要驳回给谁
            // 驳回的日志记录的下一处理流程和节点是当前要处理的节点信息
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.PURCH.getCode(), purch.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, purch.getAuditingProcess().toString(), purch.getAuditingUserId().toString(), reason, "-1", 3);
        } else {
            switch (curAuditProcess) {
                case 21: // 采购经理审核核
                    if (purch.getAuditingProcess().indexOf("22") == -1) {//同级事业部项目负责人是否已审核
                        if (purch.getContractVersion() != null && "1".equals(purch.getContractVersion())) {//是否为标准版合同，是标准则越过法务审批
                            auditingProcess_i = "24";
                            auditingUserId_i = purch.getFinanceAuditerId() + "";
                        } else {//需要法务、财务审批
                            auditingProcess_i = "23,24";
                            auditingUserId_i = String.format("%d,%d", purch.getLegalAuditerId(), purch.getFinanceAuditerId());
                        }
                    } else {
                        isComeMore = true;
                        auditingProcess_i = "22";
                        auditingUserId_i = purch.getBusinessAuditerId() + "";
                    }
                    break;
                case 22://商务技术职称改为->事业部项目负责人审核
                    if (purch.getAuditingProcess().indexOf("21") == -1) {//同级采购经理是否已审批
                        if (purch.getContractVersion() != null && "1".equals(purch.getContractVersion())) {//是否为标准版合同，是标准则越过法务审批
                            auditingProcess_i = "24";
                            auditingUserId_i = purch.getFinanceAuditerId() + "";
                        } else {//需要法务、财务审批
                            auditingProcess_i = "23,24";
                            auditingUserId_i = String.format("%d,%d", purch.getLegalAuditerId(), purch.getFinanceAuditerId());
                        }
                    } else {
                        isComeMore = true;
                        auditingProcess_i = "21";
                        auditingUserId_i = purch.getPurchAuditerId() + "";
                    }
                    break;
                case 23://法务审核
                    if (purch.getAuditingProcess().indexOf("24") == -1) {//同级财务审核是否已审批
                        auditingProcess_i = "25";
                        auditingUserId_i = purch.getBuVpAuditerId() + "";
                    } else {
                        isComeMore = true;
                        auditingProcess_i = "24";
                        auditingUserId_i = purch.getFinanceAuditerId() + "";
                    }
                    break;
                case 24://财务审核
                    if (purch.getAuditingProcess().indexOf("23") == -1) {//同级法务审核是否已审批
                        auditingProcess_i = "25";
                        auditingUserId_i = purch.getBuVpAuditerId() + "";
                    } else {
                        isComeMore = true;
                        auditingProcess_i = "23";
                        auditingUserId_i = purch.getLegalAuditerId() + "";
                    }
                    break;
                case 25://供应链中心总经理
                    if (purch.getTotalPrice() != null && purch.getTotalPrice().doubleValue() < 1000000) {
                        auditingStatus_i = 4; // 完成
                        auditingProcess_i = "999";
                        auditingUserId_i = null;
                    } else {
                        auditingProcess_i = "26";
                        auditingUserId_i = purch.getChairmanId() + "";
                    }
                    break;
                case 26://总裁审核
                    //如果采购金额 小于等于三百万 总裁审批完成
                    if (purch.getTotalPrice() != null && purch.getTotalPrice().doubleValue() <= 3000000) {
                        auditingStatus_i = 4; // 完成
                        auditingProcess_i = "999";
                        auditingUserId_i = null;
                        //如果订单金额大于三百万需要董事长审批
                    } else {
                        auditingProcess_i = "27"; // 董事长审核
                        auditingUserId_i = purch.getChairmanBoardId() + "";
                    }
                    break;
                case 27://董事长审核
                    auditingStatus_i = 4; // 完成
                    auditingProcess_i = "999";
                    auditingUserId_i = null;
                    break;
                default:
                    return false;
            }
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.PURCH.getCode(), purch.getId(), curAuditProcess, Integer.parseInt(auditorId), auditorName, purch.getAuditingProcess().toString(), purch.getAuditingUserId().toString(), reason, "2", 3);
        }
        checkLogService.insert(checkLog_i);
        purch.setAuditingStatus(auditingStatus_i);
        purch.setAuditingProcess(auditingProcess_i);
        purch.setAuditingUserId(auditingUserId_i);
        if (auditingUserId_i != null && !isComeMore) {
            for (String user : auditingUserId_i.split(",")) {
                sendDingtalk(purch, user, rejectFlag);
            }
        }
        if (auditingStatus_i == 4 && "999".equals(auditingProcess_i)) {
            if (purch.getProjects().size() > 0 && purch.getProjects().get(0).getOrderCategory().equals(6) && purch.getStatus() > 1) {
                purch.setStatus(Purch.StatusEnum.DONE.getCode()); // TODO 这里是何意？
            }
        }
        purch.setAudiRemark(auditorIds.toString());
        purchDao.save(purch);

        auditBackLogHandle(purch, rejectFlag, auditingUserId_i, auditorId, isComeMore);

        return true;
    }

    /**
     * @param isComeMore //true是同级处理  false不是同级处理或提交审核
     */
    private void auditBackLogHandle(Purch purch, boolean rejectFlag, String auditingUserId, String auditorId, boolean isComeMore) {
        try {
            // 删除上一个待办
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCH_REJECT.getNum());    //功能访问路径标识
            backLog2.setHostId(purch.getId());
            backLogService.updateBackLogByDelYn(backLog2);
            if (isComeMore) {
                backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCH_AUDIT.getNum());    //功能访问路径标识
                backLogService.updateBackLogByDelYnNew(backLog2, auditorId);
            } else {
                backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCH_AUDIT.getNum());    //功能访问路径标识
                backLogService.updateBackLogByDelYn(backLog2);
            }

            if (auditingUserId != null && !isComeMore) {//并行未走完不用推送待办
                for (String user : auditingUserId.split(",")) {
                    // 推送待办事件
                    String infoContent = String.format("%s", purch.getSupplierName());
                    String purchNo = purch.getPurchNo();
                    Integer followId = 0;
                    applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                            rejectFlag ? BackLog.ProjectStatusEnum.PURCH_REJECT : BackLog.ProjectStatusEnum.PURCH_AUDIT,
                            purchNo,
                            infoContent,
                            purch.getId(),
                            followId,
                            "采购",
                            Integer.parseInt(user)));
                }
            }
            int orderCate = 0;
            //获取订单类型，当时国内订单时不推送质检
            if (purch.getProjects() != null && purch.getProjects().size() > 0) {
                orderCate = purch.getProjects().get(0).getOrder().getOrderCategory();
            }
            if (orderCate != 6 && purch.getAuditingStatus() == 4 && "999".equals(purch.getAuditingProcess())) {
                // 推动
                String returnNo = purch.getPurchNo(); // 返回单号
                String infoContent = purch.getSupplierName();//提示内容
                Integer hostId = purch.getId();
                Integer followId = 0;
                Integer userId = purch.getAgentId(); //经办人id
                // 推送增加待办事件，通知采购经办人办理报检单
                applicationContext.publishEvent(new TasksAddEvent(applicationContext, backLogService,
                        BackLog.ProjectStatusEnum.INSPECTAPPLY,
                        returnNo,
                        infoContent,
                        hostId,
                        followId,
                        "采购",
                        userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                // 根据当前审核进度
                if (condition.getAuditingProcess() != null && condition.getAuditingProcess().length() > 0) {
                    list.add(cb.like(root.get("auditingProcess").as(String.class), "%" + condition.getAuditingProcess() + "%"));
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

                Predicate auditCondition = null;
//                if (condition.getAuditingUserId() != null) {
//                    Predicate auditingUserIdP = cb.like(root.get("auditingUserId").as(String.class),
//                            "%" + condition.getAuditingUserId() + "%");
//                    Predicate auditingUserId02 = cb.like(root.get("audiRemark").as(String.class),
//                            "%," + condition.getAuditingUserId() + ",%");
//                    auditCondition = cb.or(auditingUserIdP, auditingUserId02);
//                }

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                Predicate and = cb.and(predicates);
                if (auditCondition != null) {
                    return cb.or(and, auditCondition);
                } else {
                    return and;
                }
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

    /**
     * 填充导出采购合同模板
     *
     * @param workbook
     * @param purchId
     * @throws Exception
     */
    @Override
    public void fillTempExcelData(XSSFWorkbook workbook, int purchId) throws Exception {
        Purch purch = purchDao.findOne(purchId);
        List<Attachment> attachments = attachmentDao.findByRelObjIdAndCategory(purch.getId(), Attachment.AttachmentCategory.PURCH.getCode());
        if (purch == null || purch.getStatus() == 1 || purch.getAuditingStatus() != 4) {
            // 采购为空、采购状态未进行、采购未审核都不能导出
            throw new Exception("采购状态未进行或采购未审核错误");
        }
        List<CheckLog> checkLogList = checkLogService.findCheckLogsByPurchId(purchId);
        List<Project> projects = purch.getProjects();
        StringBuffer businessUnitNames = new StringBuffer(); // 执行事业部
        Set<String> businessUnitNameSet = new HashSet<>();
        StringBuffer signingComs = new StringBuffer(); // 签约主体
        StringBuffer contractNos = new StringBuffer(); // 销售合同号
        for (Project project : projects) {
            Order order = project.getOrder();
            businessUnitNameSet.add(project.getBusinessUnitName());
            signingComs.append(order.getSigningCo()).append("、");
            contractNos.append(order.getContractNo()).append("、");
        }
        businessUnitNames.append(StringUtils.join(businessUnitNameSet.toArray(new String[businessUnitNameSet.size()]), "、"));

        String tmpStr = null;
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        String checkedStr = row.getCell(4).getStringCellValue();
        String unCheckedStr = row.getCell(5).getStringCellValue();
        row.getCell(4).setCellValue("");
        row.getCell(5).setCellValue("");
        // 填充数据内容
        row = sheet.getRow(1);

        row.getCell(1).setCellValue(StringUtils.strip(businessUnitNames.toString(), "、")); // 部门 -- 执行事业部
        row.getCell(3).setCellValue(purch.getAgentName()); // 经办人-- 采购经办人

        row = sheet.getRow(2);
        row.getCell(1).setCellValue(StringUtils.strip(signingComs.toString(), "、")); // 签约主体	-- 签约主体
        //row.getCell(3).setCellValue(""); // 合同编号前缀 -- YRC
        row = sheet.getRow(3);
        row.getCell(1).setCellValue(StringUtils.strip(businessUnitNames.toString(), "、")); // 审核单位	-- 执行事业部
        row.getCell(3).setCellValue(DateUtil.format(DateUtil.SHORT_FORMAT_STR, purch.getSigningDate())); // 合同签订日期

        row = sheet.getRow(4);
        row.getCell(1).setCellValue(purch.getPurchNo()); // 合同编号	-- YRC+年+四位序列号
        row.getCell(3).setCellValue(purch.getContractTag()); // 合同标的物

        row = sheet.getRow(5);
        row.getCell(1).setCellValue(StringUtils.strip(contractNos.toString(), "、")); // 项目编号 -- 销售合同号
        row.getCell(3).setCellValue(StringUtils.strip(signingComs.toString(), "、")); // 签约公司-- 签约主体

        row = sheet.getRow(6);
        row.getCell(1).setCellValue(purch.getSupplierName()); // 供应商名称
        row.getCell(3).setCellValue(purch.getSupplyArea()); // 供应商地区

        row = sheet.getRow(7);
        tmpStr = row.getCell(1).getStringCellValue();
        String contractVersion = purch.getContractVersion();
        if ("1".equals(contractVersion)) {
            row.getCell(1).setCellValue(tmpStr.replace(unCheckedStr + "标准版本", checkedStr + "标准版本")); // 合同版本 --  □标准版本    □非标版本
        } else if ("2".equals(contractVersion)) {
            row.getCell(1).setCellValue(tmpStr.replace(unCheckedStr + "非标版本", checkedStr + "非标版本")); // 合同版本 --  □标准版本    □非标版本
        }

        row = sheet.getRow(9);
        row.getCell(1).setCellValue(purch.getCurrencyBn()); // 币别
        BigDecimal rate = purch.getExchangeRate();
        if (rate != null) {
            row.getCell(3).setCellValue(rate.setScale(2).toString()); // 汇率
        }

        row = sheet.getRow(10);
        row.getCell(1).setCellValue(purch.getTotalPrice().setScale(2, BigDecimal.ROUND_DOWN).toString()); // 合同金额
        Integer taxBearing = purch.getTaxBearing();
        if (taxBearing != null && 1 == taxBearing) {
            row.getCell(2).setCellValue(checkedStr + "含税"); // 含税
        } else if (taxBearing != null && 2 == taxBearing) {
            row.getCell(3).setCellValue(checkedStr + "不含税 "); // 不含税
        }

        row = sheet.getRow(11);
        BigDecimal goalCost = purch.getGoalCost();
        if (goalCost != null) {
            row.getCell(1).setCellValue(goalCost.setScale(2).toString()); // 目标成本（人民币）
        }
        BigDecimal saveAmount = purch.getSaveAmount();
        if (saveAmount != null) {
            row.getCell(3).setCellValue(saveAmount.setScale(2).toString()); // 节约资金（人民币）
        }

        row = sheet.getRow(12);
        String priceMode = "无";
        String saveMode = "无";
        switch (purch.getPriceMode()) { // 定价方式
            case "1":
                priceMode = "招标";
                break;
            case "2":
                priceMode = "招标转竞争性谈判";
                break;
            case "3":
                priceMode = "小额采购谈判";
                break;
            case "4":
                priceMode = "询比价";
                break;
            case "5":
                priceMode = "执行集中谈判（框架协议）价格";
                break;
            case "6":
                priceMode = "参考历史价格";
                break;
        }
        switch (purch.getSaveMode()) { // 节约方式
            case "1":
                saveMode = "对比投标";
                break;
            case "2":
                saveMode = "对比项目交付";
                break;
            case "3":
                saveMode = "对比预算";
                break;
            case "4":
                saveMode = "对比历史（含历史对比返点）";
                break;
        }
        row.getCell(1).setCellValue(priceMode); // 定价方式
        row.getCell(3).setCellValue(saveMode); // 节约方式

        row = sheet.getRow(13);
        Integer payType = purch.getPayType();
        switch (payType) {
            case 1:
                row.getCell(1).setCellValue("货到验收合格后付款"); // 付款方式
                break;
            case 2:
                row.getCell(1).setCellValue("款到发货"); // 付款方式
                break;
            default:
                row.getCell(1).setCellValue("其他"); // 付款方式
        }
        Date arraivalDate = purch.getPurChgDate();
        if (arraivalDate == null) {
            arraivalDate = purch.getArrivalDate();
        }
        row.getCell(3).setCellValue(DateUtil.format(DateUtil.SHORT_FORMAT_STR, arraivalDate)); // 交货时间

        row = sheet.getRow(14);
        BigDecimal profitPercent = purch.getProfitPercent();
        if (profitPercent != null) {
            row.getCell(1).setCellValue(profitPercent.setScale(2, BigDecimal.ROUND_DOWN).toString()); // 利润率
        }

        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        if (attachments != null && attachments.size() > 0) {

            for (Attachment attach : attachments) {
                Integer type = attach.getType();
                if (type == null) {
                    sb3.append(attach.getTitle()).append("\r\n");
                } else if (1 == type) {
                    sb1.append(attach.getTitle()).append("\r\n");
                } else if (2 == type) {
                    sb2.append(attach.getTitle()).append("\r\n");
                } else {
                    sb3.append(attach.getTitle()).append("\r\n");
                }

            }
        }
        row = sheet.getRow(16);
        row.getCell(1).setCellValue(StringUtils.strip(sb1.toString(), "\r\n")); // 合同（PDF格式）及技术协议

        row = sheet.getRow(17);
        row.getCell(1).setCellValue(StringUtils.strip(sb2.toString(), "\r\n")); // "定价资料（含报价单） 询比价必须上传原始报价单"

        row = sheet.getRow(18);
        row.getCell(1).setCellValue(StringUtils.strip(sb3.toString(), "\r\n")); // 其他附件

        row = sheet.getRow(19);
        row.getCell(1).setCellValue(purch.getRemarks()); // 备注

        // 审批历史记录信息
        if (checkLogList != null && checkLogList.size() > 0) {
            int index = 23;
            for (CheckLog checkLog : checkLogList) {
                row = sheet.createRow(index);
                row.createCell(0).setCellValue(DateUtil.format(DateUtil.FULL_FORMAT_STR, checkLog.getCreateTime())); // 日期
                CheckLog.AuditProcessingEnum anEnum = CheckLog.AuditProcessingEnum.findEnum(3, checkLog.getAuditingProcess());
                row.createCell(1).setCellValue(anEnum.getName() + "/" + checkLog.getAuditingUserName()); // 审核节点/审核人

                String operation = checkLog.getOperation();
                if ("2".equals(operation)) {
                    row.createCell(2).setCellValue("通过"); // 审核结果
                } else if ("-1".equals(operation)) {
                    row.createCell(2).setCellValue("驳回"); // 审核结果
                } else if ("1".equals(operation)) {
                    row.createCell(2).setCellValue("立项"); // 审核结果
                }
                row.createCell(3).setCellValue(checkLog.getAuditingMsg()); // 审核意见
                index++;
            }
        }
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
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Date now = new Date();
        /*String lastedByPurchNo = purchDao.findLastedByPurchNo();
        Long count = purchDao.findCountByPurchNo(lastedByPurchNo);
        if (count != null && count > 1) {
            throw new Exception(String.format("%s%s%s", "采购合同号重复", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Repeat purchase contract number"));
        }*/
        // 设置基础数据 StringUtil.genPurchNo(lastedByPurchNo) 采购合同号修改为由采购合同带出
        purch.setSigningDate(NewDateUtil.getDate(purch.getSigningDate()));
        purch.setArrivalDate(NewDateUtil.getDate(purch.getArrivalDate()));
        purch.setCreateTime(now);
        // 处理结算方式,新增，所以讲所有id设置为null，并添加新增时间
        purch.getPurchPaymentList().parallelStream().forEach(vo -> {
            vo.setId(null);
            vo.setCreateTime(now);
        });
        // 处理商品信息
        List<PurchGoods> purchGoodsList = new ArrayList<>();
        Set<Project> projectSet = new HashSet<>();
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
            //获取采购合同商品
            PurchContractGoods purchContractGoods = purchContractGoodsDao.findOne(purchGoods.getPcgId());
            if (goods == null || goods.getExchanged()) {
                // 给定的商品不存在或者是被替换的商品，则错误
                throw new Exception(String.format("%s%s%s", "商品不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Goods do not exist"));
            }
            //项目
            Project project = goods.getProject();
            //采购合同
            PurchContract purchContract = purchContractGoods.getPurchContract();
            // 必须是已创建采购申请单并未完成采购的项目 修改为 必须是已经创建采购合同并且为完成采购的项目
            if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                throw new Exception(String.format("%s%s%s", "项目必须提交采购申请", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The project must submit a purchase application"));

            }
            if (purchContract.getStatus() != 2) {
                throw new Exception(String.format("%s%s%s", "采购合同必须为执行中状态", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The purchContract must submit"));
            }
            if (project.getPurchDone()) {
                throw new Exception(String.format("%s%s%s", "项目采购已完成，不能再次采购", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project procurement has been completed and can not be repurchased"));
            }
            projectSet.add(project);
            PurchGoods son = handleAddNewPurchGoods(project, purch, goods, purchGoods, purchContractGoods);
            purchGoods.setPurchContractGoods(purchContractGoods);
            purchGoods.setPurchContract(purchContractGoods.getPurchContract());
            purchGoodsList.add(purchGoods);
            if (son != null) {
                purchGoodsList.add(son);
            }
            int intPurchaseNum = purchGoods.getPurchaseNum();
            if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                // 如果是提交则设置商品的已采购数量并更新
                goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
                //提交时更新采购合同已采购数量
                purchContractGoods.setPurchasedNum(purchContractGoods.getPurchaseNum() + intPurchaseNum);
                // 完善商品的项目执行跟踪信息
                setGoodsTraceData(goods, purch);
                if (!goods.getOrder().getOrderCategory().equals(6)) {
                    applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3, eruiToken));
                }
            }
            // 增加采购合同预采购数量
            //goods.setPrePurchContractNum(goods.getPrePurchContractNum() + intPurchaseNum);
            purchContractGoods.setPrePurchContractNum(goods.getPrePurchContractNum() + intPurchaseNum);
            // 直接更新商品，放置循环中存在多次修改同一个商品错误
            purchContractGoodsDao.save(purchContractGoods);
            goodsDao.save(goods);
        }
        if (purchGoodsList.size() == 0) {
            throw new Exception(String.format("%s%s%s", "必须存在要采购的商品", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "There must be goods to be purchased"));
        }
        purch.setPurchGoodsList(purchGoodsList);
        List<Project> projectList = new ArrayList<>(projectSet);
        purch.setProjects(projectList);
        // 采购审批添加部分
        if (purch.getStatus() == Purch.StatusEnum.READY.getCode()) {
            purch.setAuditingStatus(0);
        } else if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            purch.setAuditingProcess("21,22");
            purch.setAuditingStatus(1);
            purch.setAuditingUserId(String.format("%d,%d", purch.getPurchAuditerId(), purch.getBusinessAuditerId()));

        }
        CheckLog checkLog_i = null; //审批流日志

        Purch save = purchDao.save(purch);
        // 添加附件
        if (purch.getAttachments() != null && purch.getAttachments().size() > 0) {
            attachmentService.addAttachments(purch.getAttachments(), save.getId(), Attachment.AttachmentCategory.PURCH.getCode());
        }
        if (save.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            if (save.getPurchAuditerId() != null) {
                sendDingtalk(purch, purch.getPurchAuditerId().toString(), false);
            }
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.PURCH.getCode(), save.getId(), 20, save.getCreateUserId(), save.getCreateUserName(), save.getAuditingProcess().toString(), save.getPurchAuditerId().toString(), save.getAuditingReason(), "1", 3);
            checkLogService.insert(checkLog_i);
            // 待办
            auditBackLogHandle(save, false, save.getAuditingUserId(), "", false);
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
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Purch dbPurch = findDetailInfo(purch.getId());
        // 之前的采购必须不能为空且未提交状态
        if (dbPurch == null || dbPurch.getDeleteFlag()) {
            throw new Exception(String.format("%s%s%s", "采购信息不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Procurement information does not exist"));
        } else if (dbPurch.getStatus() != Purch.StatusEnum.BEING.getCode() && dbPurch.getAuditingStatus() == 4) {
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
            payment2.setDays(payment.getDays());
            return payment2;
        }).collect(Collectors.toList());
        dbPurch.setPurchPaymentList(paymentList);
        // 删除废弃的结算方式
        if (collect.size() > 0) {
            purchPaymentDao.delete(collect.values());
        }
        // 处理商品
        List<PurchGoods> purchGoodsList = new ArrayList<>(); // 声明最终采购商品容器
        Set<Project> projectSet = new HashSet<>(); // 声明项目的容器
        // 数据库现在的采购商品信息
        Map<Integer, PurchGoods> dbPurchGoodsMap = dbPurch.getPurchGoodsList().parallelStream().collect(Collectors.toMap(PurchGoods::getId, vo -> vo));
        Set<Integer> existId = new HashSet<>();
        Set<Integer> existPurchContractId = new HashSet<>();
        // 处理参数中的采购商品信息
        for (PurchGoods pg : purch.getPurchGoodsList()) {
            Integer pgId = pg.getId();
            Integer cId = pg.getPcgId();
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
                // 获取要采购合同的商品
                PurchContractGoods purchContractGoods = purchContractGoodsDao.findOne(pg.getPcgId());
                if (goods == null || goods.getExchanged()) {
                    throw new Exception(String.format("%s%s%s", "商品不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Goods do not exist"));
                }
                //获取采购合同
                PurchContract purchContract = purchContractGoods.getPurchContract();
                //获取项目
                Project project = goods.getProject();
                // 必须是已创建采购申请单并未完成采购的项目
                if (Project.PurchReqCreateEnum.valueOfCode(project.getPurchReqCreate()) != Project.PurchReqCreateEnum.SUBMITED) {
                    throw new Exception(String.format("%s%s%s", "项目必须提交采购申请", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The project must submit a purchase application"));
                }
                if (purchContract.getStatus() != 2) {
                    throw new Exception(String.format("%s%s%s", "采购合同必须为执行中状态", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "The purchContract must submit"));
                }
                if (project.getPurchDone()) {
                    throw new Exception(String.format("%s%s%s", "项目采购已完成，不能再次采购", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Project procurement has been completed and can not be repurchased"));
                }
                projectSet.add(project);
                // 查看是否存在替换商品
                PurchGoods son = handleAddNewPurchGoods(project, dbPurch, goods, pg, purchContractGoods);
                pg.setPurchContractGoods(purchContractGoods);
                pg.setPurchContract(purchContractGoods.getPurchContract());
                purchGoodsList.add(pg);
                if (son != null) {
                    purchGoodsList.add(son);
                }
                Integer intPurchaseNum = pg.getPurchaseNum();
                // 更新商品的采购数量和预采购数量
                if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    // 更新已采购数量
                    goods.setPurchasedNum(goods.getPurchasedNum() + intPurchaseNum);
                    // 更新采购合同已预采购数量
                    purchContractGoods.setPrePurchContractNum(purchContractGoods.getPrePurchContractNum() + intPurchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                    if (!goods.getOrder().getOrderCategory().equals(6)) {
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3, eruiToken));
                    }
                }
                //goods.setPrePurchsedNum(goods.getPrePurchsedNum() + intPurchaseNum);
                purchContractGoods.setPrePurchContractNum(purchContractGoods.getPrePurchContractNum() + intPurchaseNum);
                purchContractGoodsDao.save(purchContractGoods);
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
                //商品
                existId.add(pgId);
                //采购合同
                existPurchContractId.add(cId);
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
                // 从数据库查询一次商品做修改
                PurchContractGoods purchContractGoods = purchContractGoodsDao.findOne(purchGoods.getPcgId());
                if (hasSon) {
                    // 处理替换商品
                    PurchGoods son = pg.getSon();
                    handleExchangedPurchGoods(project, goods, dbPurch, purchGoods, son);
                    purchGoodsList.add(son);
                }
                // 提交则修改商品的已采购数量
                if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
                    goods.setPurchasedNum(goods.getPurchasedNum() + purchaseNum);
                    //设置采购已采购商品数量
                    purchContractGoods.setPurchasedNum(purchContractGoods.getPurchasedNum() + purchaseNum);
                    //设置采购合同预采购商品数量
                    purchContractGoods.setPrePurchContractNum(purchContractGoods.getPrePurchContractNum() + purchaseNum - oldPurchaseNum);
                    // 设置商品的项目跟踪信息
                    setGoodsTraceData(goods, purch);
                    if (!goods.getOrder().getOrderCategory().equals(6)) {
                        applicationContext.publishEvent(new OrderProgressEvent(goods.getOrder(), 3, eruiToken));
                    }
                }
                // 判断采购是否超限,预采购数量大于合同数量，则错误
               /* if (goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum > goods.getContractGoodsNum()) {
                    throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

                }*/
                // 判断采购合同是否超限,采购合同预采购数量大于合同数量，则错误
                if (purchContractGoods.getPrePurchContractNum() + purchaseNum - oldPurchaseNum > purchContractGoods.getPurchaseNum()) {
                    throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

                }
                if (purchaseNum > 0 &&
                        (purchGoods.getPurchasePrice() == null || purchGoods.getPurchasePrice().compareTo(BigDecimal.ZERO) != 1)) {
                    throw new Exception(String.format("%s%s%s", "要采购的商品单价错误【sku :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unit price error to be purchased [SKU: " + goods.getSku() + "]"));

                }

                //goods.setPrePurchsedNum(goods.getPrePurchsedNum() + purchaseNum - oldPurchaseNum);
                purchContractGoods.setPrePurchContractNum(purchContractGoods.getPrePurchContractNum() + purchaseNum - oldPurchaseNum);
                purchContractGoodsDao.save(purchContractGoods);
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
       /* if (dbPurch.getProjects().size() > 0 && dbPurch.getProjects().get(0).getOrderCategory().equals(6) && purch.getStatus() > 1) {
            dbPurch.setStatus(3);
        }*/
        // 采购审批添加部分
        if (purch.getStatus() == Purch.StatusEnum.READY.getCode()) {
            dbPurch.setAuditingStatus(0);
        } else if (purch.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            dbPurch.setAuditingProcess("21,22");
            dbPurch.setAuditingStatus(1);
            dbPurch.setAuditingUserId(String.format("%d,%d", purch.getPurchAuditerId(), purch.getBusinessAuditerId()));
        }
        CheckLog checkLog_i = null; //审批流日志

        Purch save = purchDao.save(dbPurch);
        // 处理附件信息 attachmentList 库里存在附件列表 dbAttahmentsMap前端传来参数附件列表
        //save.setAttachmentList(save.getAttachmentList());
        List<Attachment> attachmentList = new ArrayList<>();
        if (purch.getAttachments() != null) {
            attachmentList = purch.getAttachments();
        }
        Map<Integer, Attachment> dbAttahmentsMap = dbPurch.getAttachments().parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        attachmentService.updateAttachments(attachmentList, dbAttahmentsMap, dbPurch.getId(), Attachment.AttachmentCategory.PURCH.getCode());

        if (save.getStatus() == Purch.StatusEnum.BEING.getCode()) {
            checkLog_i = orderService.fullCheckLogInfo(null, CheckLog.checkLogCategory.PURCH.getCode(), save.getId(), 20, save.getCreateUserId(), save.getCreateUserName(), save.getAuditingProcess().toString(), save.getPurchAuditerId().toString(), save.getAuditingReason(), "1", 3);
            checkLogService.insert(checkLog_i);
            if (save.getPurchAuditerId() != null) {
                sendDingtalk(purch, purch.getPurchAuditerId().toString(), false);
            }
            auditBackLogHandle(dbPurch, false, dbPurch.getAuditingUserId(), "", false);
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

            }

        }
        // 检查项目是否已经采购完成
        List<Integer> projectIdList = projectSet.parallelStream().map(Project::getId).collect(Collectors.toList());
        checkProjectPurchDone(projectIdList);
        return true;
    }

    // 处理新增采购信息，如果采购信息有替换的商品，则返回处理后的替换信息
    private PurchGoods handleAddNewPurchGoods(Project project, Purch purch, Goods goods, PurchGoods newPurchGoods, PurchContractGoods purchContractGoods) throws Exception {
        // 设置新采购的基本信息
        String contractNo = project.getContractNo();
        String projectNo = project.getProjectNo();
        newPurchGoods.setId(null);
        newPurchGoods.setProject(project);
        newPurchGoods.setContractNo(contractNo);
        newPurchGoods.setProjectNo(projectNo);
        newPurchGoods.setPurch(purch);
        newPurchGoods.setGoods(goods);
        newPurchGoods.setPurchContract(purchContractGoods.getPurchContract());
        newPurchGoods.setPurchContractGoods(purchContractGoods);
        Integer purchaseNum = newPurchGoods.getPurchaseNum();
        purchaseNum = purchaseNum != null && purchaseNum > 0 ? purchaseNum : 0;
        Integer prePurchaseNum = purchContractGoods.getPrePurchContractNum();
        //预采购合同数量
        prePurchaseNum = prePurchaseNum != null && prePurchaseNum > 0 ? prePurchaseNum : 0;
        newPurchGoods.setPurchaseNum(purchaseNum);
        // 判断采购是否超限,预采购数量大于采购合同数量，则错误
        if (prePurchaseNum + purchaseNum > purchContractGoods.getPurchaseNum()) {
            throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sk" +
                    "u :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

        }
        // 判断采购是否超限,预采购数量大于订单合同数量，则错误
       /* if (goods.getPrePurchContractNum() + purchaseNum > goods.getContractGoodsNum()) {
            throw new Exception(String.format("%s%s%s", "采购数量超过合同数量【sk" +
                    "u :" + goods.getSku() + "】", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Quantity of purchase exceeds the number of contracts [SKU: " + goods.getSku() + "]"));

        }*/
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
        applicationContext.publishEvent(new PurchDoneCheckEvent(this, projectIds, projectDao, backLogService, orderDao, purchRequisitionDao));
    }

}
