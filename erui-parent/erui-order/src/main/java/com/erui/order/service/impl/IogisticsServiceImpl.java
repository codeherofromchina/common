package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.IogisticsDao;
import com.erui.order.dao.IogisticsDataDao;
import com.erui.order.entity.*;
import com.erui.order.service.BackLogService;
import com.erui.order.service.IogisticsDataService;
import com.erui.order.service.IogisticsService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class IogisticsServiceImpl implements IogisticsService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private IogisticsDao iogisticsDao;

    @Autowired
    private IogisticsDataDao iogisticsDataDao;

    @Autowired
    private InstockServiceImpl getInstockServiceImpl;

    @Autowired
    private BackLogService backLogService;

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation;  //查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms;  //发短信接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList;  //查询人员信息调用接口

    /**
     * 出库信息管理（V 2.0）   查询列表页
     *
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Iogistics> queIogistics(Iogistics iogistics) throws Exception {
        PageRequest request = new PageRequest(iogistics.getPage() - 1, iogistics.getRows(), Sort.Direction.DESC, "id");

        Page<Iogistics> page = iogisticsDao.findAll(new Specification<Iogistics>() {
            @Override
            public Predicate toPredicate(Root<Iogistics> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                //根据 销售合同号
                if (StringUtil.isNotBlank(iogistics.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + iogistics.getContractNo() + "%"));
                }

                //根据 产品放行单号
                if (StringUtil.isNotBlank(iogistics.getDeliverDetailNo())) {
                    list.add(cb.like(root.get("deliverDetailNo").as(String.class), "%" + iogistics.getDeliverDetailNo() + "%"));
                }

                //根据出口通知单号
                if (StringUtil.isNotBlank(iogistics.getDeliverConsignNo())) {
                    list.add(cb.like(root.get("deliverConsignNo").as(String.class), "%" + iogistics.getDeliverConsignNo() + "%"));
                }

                //根据 项目号
                if (StringUtil.isNotBlank(iogistics.getProjectNo())) {
                    list.add(cb.like(root.get("projectNo").as(String.class), "%" + iogistics.getProjectNo() + "%"));
                }

                Join<Iogistics, DeliverDetail> deliverDetailRoot = root.join("deliverDetail"); //获取出库

                //根据经办部门
                if (StringUtil.isNotBlank(iogistics.getHandleDepartment())) {
                    list.add(cb.equal(deliverDetailRoot.get("handleDepartment").as(String.class), iogistics.getHandleDepartment()));
                }

                //根据开单日期
                if (iogistics.getBillingDate() != null) {
                    list.add(cb.equal(deliverDetailRoot.get("billingDate").as(Date.class), iogistics.getBillingDate()));
                }
                //根据放行日期
                if (iogistics.getReleaseDate() != null) {
                    list.add(cb.equal(deliverDetailRoot.get("releaseDate").as(Date.class), iogistics.getReleaseDate()));
                }

                //根据仓库经办人id
                if (iogistics.getWareHouseman() != null) {
                    list.add(cb.equal(deliverDetailRoot.get("wareHouseman").as(Integer.class), iogistics.getWareHouseman()));
                }

                //是否外检
                if (iogistics.getOutCheck() != null) {
                    list.add(cb.equal(root.get("outCheck").as(Integer.class), iogistics.getOutCheck()));
                }

                //是否已合并  （0：否  1：是）     查询未合并
                list.add(cb.equal(root.get("outYn").as(Integer.class), 0));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);

            }
        }, request);

        if (page.hasContent()) {
            for (Iogistics iogistics1 : page.getContent()) {
                iogistics1.getDeliverDetail().getId();
            }
        }

        return page;
    }


    /**
     * 出库信息管理（V 2.0）   出库详情信息（查看）
     *
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Iogistics queryById(Iogistics iogistics) throws Exception {
        Iogistics iogisticsById = iogisticsDao.findById(iogistics.getId());
        if (iogisticsById == null) {
            throw new Exception(String.format("%s%s%s", "数据信息为空", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Data information is empty"));
        }
        return iogisticsById;
    }


    /**
     * 出库信息管理（V 2.0）   合并出库信息，分单员
     *
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean mergeData(Map<String, String> params) throws Exception {
        String[] ids = params.get("ids").split(",");
        if (ids.length == 0) {
            throw new Exception(String.format("%s%s%s", "未选择商品信息", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Unselected commodity information"));
        }


        //获取经办分单人信息
        String eruiToken = (String) ThreadLocalUtil.getObject();
        Map<String, String> stringStringMap = getInstockServiceImpl.ssoUser(eruiToken);
        String name = stringStringMap.get("name");
        String submenuId = stringStringMap.get("id");


        //推送数据
        IogisticsData iogisticsData = new IogisticsData();
        iogisticsData.setTheAwbNo(createTheAwbNo());    //物流号
        iogisticsData.setStatus(5); //物流状态
        iogisticsData.setLogisticsUserId(Integer.parseInt(params.get("logisticsUserId"))); //物流经办人id
        iogisticsData.setLogisticsUserName(params.get("logisticsUserName")); //物流经办人名称
        if(StringUtil.isBlank(iogisticsData.getSubmenuName())){
            iogisticsData.setSubmenuName(name); //分单员经办人姓名
        }
        if(iogisticsData.getSubmenuId() == null){
            iogisticsData.setSubmenuId(Integer.parseInt(submenuId));   //入库分单人Id
        }
        IogisticsData save = iogisticsDataDao.save(iogisticsData);  //物流信息




        String[] arr = new String[ids.length];  //销售合同号对比是否相同
        Set<String> contractNoSet = new HashSet<>();//销售合同号
        Set<String> deliverDetailNoSet = new HashSet<>(); //产品放行单号
        Set releaseDateSSet = new HashSet(); //放行日期  数据库存储的拼接字段
        Iogistics iogistics = null; //获取分单信息，获取物流经办人信息



         Set<String> deliverConsignNoSet = new HashSet<>(); //出口发货通知单号

        int i = 0;
        for (String id : ids) {
            Iogistics one = iogisticsDao.findById(new Integer(id));
            deliverConsignNoSet.add(one.getDeliverConsignNo());
            if (one == null) {
                throw new Exception(String.format("%s%s%s", "出库详情信息id：" + id + " 不存在", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,
                        "Out of Library details id:" + id + " does not exist"));
                // "出库详情信息id："+id+" 不存在"
            }
            if (one.getOutYn() == 1) {
                throw new Exception(String.format("%s%s%s", "出库详情信息id：" + id + " 已合并", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,
                        "Out of Library details id:" + id + " has been merged"));
            }

            arr[i] = one.getContractNo(); //获取销售合同号
            i++;

            iogistics = iogistics == null ? one : iogistics;

            contractNoSet.add(one.getContractNo());//销售合同号
            deliverDetailNoSet.add(one.getDeliverDetailNo()); //产品放行单号
            Date releaseDate = one.getDeliverDetail().getReleaseDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (releaseDate != null) {
                releaseDateSSet.add(simpleDateFormat.format(releaseDate)); //放行日期
            }

            one.setOutYn(1);
            one.setIogisticsData(save);
            Iogistics save1 = iogisticsDao.save(one);

            //出库信息管理添加经办人确定以后删除  确认出库  待办信息
            BackLog backLog2 = new BackLog();
            backLog2.setFunctionExplainId(BackLog.ProjectStatusEnum.LOGISTICS.getNum());    //功能访问路径标识
            backLog2.setHostId(save1.getId());
            backLogService.updateBackLogByDelYn(backLog2);

        }

        String a = arr[0];  //获取随便一个销售合同号
        for (String contractNo : arr) {
            if (!a.equals(contractNo)) {    //判断销售合同号是否相同
                throw new Exception(String.format("%s%s%s", "销售合同号不相同", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL, "Sales contract number is different"));
            }
        }
        if (contractNoSet.size() != 0) {
            save.setContractNo(org.apache.commons.lang3.StringUtils.join(contractNoSet, ",")); //销售合同号 拼接存库
        }
        if (deliverDetailNoSet.size() != 0) {
            save.setDeliverDetailNo(org.apache.commons.lang3.StringUtils.join(deliverDetailNoSet, ",")); //产品放行单号 拼接存库
        }
        if (releaseDateSSet.size() != 0) {
            save.setReleaseDateS(org.apache.commons.lang3.StringUtils.join(releaseDateSSet, ","));//放行日期 拼接存库
        }

        IogisticsData save1 = iogisticsDataDao.save(save);
        Map<String, Object> map = new HashMap();
        map.put("contractNo",save.getContractNo());  //销售合同号
        map.put("theAwbNo",save.getTheAwbNo()); //运单号
        map.put("submenuName",save.getSubmenuName());   //物流分单员名称
        map.put("logisticsUserId",save.getLogisticsUserId());   //物流经办人id
        sendSms(map);


        //出库信息管理合并出库以后添加
        BackLog newBackLog = new BackLog();
        newBackLog.setFunctionExplainName(BackLog.ProjectStatusEnum.LOGISTICSDATA.getMsg());  //功能名称
        newBackLog.setFunctionExplainId(BackLog.ProjectStatusEnum.LOGISTICSDATA.getNum());    //功能访问路径标识
        newBackLog.setReturnNo(save1.getDeliverDetailNo());  //返回单号    产品放行单号
        newBackLog.setInformTheContent(StringUtils.join(deliverConsignNoSet,",")+" | "+save1.getContractNo());  //提示内容   出口发货通知单号  销售合同号
        newBackLog.setHostId(save1.getId());    //父ID，列表页id
        newBackLog.setUid(save1.getLogisticsUserId());   ////经办人id
        backLogService.addBackLogByDelYn(newBackLog);

        return true;
    }


    /**
     * \//生成产品运单号
     *
     * @return
     */

    public String createTheAwbNo() {
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");

        //查询最近插入的运单号
        String theAwbNo = iogisticsDataDao.findTheAwbNo();
        if (theAwbNo == null) {
            String formats = simpleDateFormats.format(new Date());  //当前年份
            return formats + String.format("%04d", 1);     //第一个
        } else {
            String substring = theAwbNo.substring(0, 4); //获取到产品放行单的年份
            String formats = simpleDateFormats.format(new Date());  //当前年份
            if (substring.equals(formats)) {   //判断年份
                String substring1 = theAwbNo.substring(4);
                return formats + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
            } else {
                return formats + String.format("%04d", 1);     //第一个
            }
        }

    }


    //V2.0出库信息管理：转交经办人的时候通知物流经办人
    public void sendSms(Map<String, Object> map1) throws Exception {

        //获取token
        String eruiToken = (String) ThreadLocalUtil.getObject();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(eruiToken)) {


            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");

            try {
                //判断物流经办人是否是物流分单员    如果是分单员不用发送短信
                //将合格发送给仓库分单员
                String jsonParams = "{\"role_no\":\"O020\"}";
                String s2 = HttpRequest.sendPost(memberList, jsonParams, header);
                logger.info("人员详情返回信息：" + s2);

                // 获取人员手机号
                JSONObject jsonObjects = JSONObject.parseObject(s2);
                Integer codes = jsonObjects.getInteger("code");
                List<Integer> listAll = new ArrayList<>();
                if (codes == 1) {    //判断请求是否成功
                    // 获取数据信息
                    JSONArray data1 = jsonObjects.getJSONArray("data");
                    for (int i = 0; i < data1.size(); i++){
                        JSONObject ob  = (JSONObject)data1.get(i);
                        listAll.add(ob.getInteger("id"));    //获取物流分单员id
                    }
                }

                if(!listAll.contains(map1.get("logisticsUserId"))){     //如果某一个物流分单员和仓库经办人id相同则不用发送短信
                    // 根据物流经办人id获取人员信息
                    String jsonParam = "{\"id\":\"" + map1.get("logisticsUserId") + "\"}";
                    String s = HttpRequest.sendPost(memberInformation, jsonParam, header);
                    logger.info("人员详情返回信息：" + s);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    Integer code = jsonObject.getInteger("code");
                    if (code == 1) {

                        JSONObject data = jsonObject.getJSONObject("data");
                        String  mobile = data.getString("mobile");  //获取物流经办人手机号
                        //发送短信
                        Map<String, String> map = new HashMap();
                        map.put("areaCode", "86");
                        map.put("to", "[\"" + mobile + "\"]");
                        map.put("content", "您好，销售合同号："+map1.get("contractNo")+"，运单号："+map1.get("theAwbNo")+"，物流分单员："+map1.get("submenuName")+"，请及时处理。感谢您对我们的支持与信任！");
                        map.put("subType", "0");
                        map.put("groupSending", "0");
                        map.put("useType", "订单");
                        String s1 = HttpRequest.sendPost(sendSms, JSONObject.toJSONString(map), header);
                        logger.info("发送短信返回状态" + s1);
                    }
                }

            } catch (Exception e) {
                throw new Exception(String.format("%s%s%s","发送短信失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Failure to send SMS"));
            }

        }
    }


}
