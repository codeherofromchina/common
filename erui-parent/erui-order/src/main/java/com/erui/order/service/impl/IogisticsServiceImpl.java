package com.erui.order.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.IogisticsDao;
import com.erui.order.dao.IogisticsDataDao;
import com.erui.order.entity.*;
import com.erui.order.service.IogisticsDataService;
import com.erui.order.service.IogisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 出库信息管理（V 2.0）   查询列表页
     *
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Iogistics> queIogistics(Iogistics iogistics) throws Exception {
        PageRequest request = new PageRequest(iogistics.getPage()-1, iogistics.getRows(), Sort.Direction.DESC, "id");

        Page<Iogistics> page = iogisticsDao.findAll(new Specification<Iogistics>() {
            @Override
            public Predicate toPredicate(Root<Iogistics> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                //根据 销售合同号
                if(StringUtil.isNotBlank(iogistics.getContractNo())){
                    list.add(cb.like(root.get("contractNo").as(String.class),"%"+iogistics.getContractNo()+"%"));
                }

                //根据出口通知单号
                if(StringUtil.isNotBlank(iogistics.getDeliverConsignNo())){
                    list.add(cb.like(root.get("deliverConsignNo").as(String.class),"%"+iogistics.getDeliverConsignNo()+"%"));
                }

                //根据 项目号
                if(StringUtil.isNotBlank(iogistics.getProjectNo())){
                    list.add(cb.like(root.get("projectNo").as(String.class),"%"+iogistics.getProjectNo()+"%"));
                }

                Join<Iogistics, DeliverDetail> deliverDetailRoot = root.join("deliverDetail"); //获取出库

                //根据经办部门
                if(StringUtil.isNotBlank(iogistics.getHandleDepartment())){
                    list.add(cb.equal(deliverDetailRoot.get("handleDepartment").as(String.class),iogistics.getHandleDepartment()));
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
                if(iogistics.getOutCheck() != null){
                    list.add(cb.equal(root.get("outCheck").as(Integer.class), iogistics.getOutCheck()));
                }

                //是否已合并  （0：否  1：是）     查询未合并
                list.add(cb.equal(root.get("outYn").as(Integer.class),0));

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
        if(iogisticsById == null){
            throw  new Exception("数据信息为空");
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
        if(ids.length == 0){
            throw new Exception("未选择商品信息");
        }

        IogisticsData iogisticsData = new IogisticsData();
        iogisticsData.setTheAwbNo(createTheAwbNo());
        iogisticsData.setStatus(5);
        IogisticsData save = iogisticsDataDao.save(iogisticsData);


        Integer id1 = save.getId(); //物流信息id    （父id）
        for (String id : ids){
            Iogistics one = iogisticsDao.findOne(new Integer(id));
            if(one == null){
                throw new Exception("出库详情信息id："+id+" 不存在");
            }
            if (one.getOutYn() == 1){
                throw new Exception("出库详情信息id："+id+" 已合并");
            }

            one.setOutYn(1);
            one.setPid(id1);
            iogisticsDao.save(one);
        }


        return true;
    }



    /**
     * \//生成产品运单号
     * @return
     */

    public String createTheAwbNo(){
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyy");

        //查询最近插入的运单号
        String theAwbNo= iogisticsDataDao.findTheAwbNo();
        if(theAwbNo == null){
            String formats = simpleDateFormats.format(new Date());  //当前年份
            return formats+String.format("%04d",1);     //第一个
        }else{
            String substring = theAwbNo.substring(0, 4); //获取到产品放行单的年份
            String formats = simpleDateFormats.format(new Date());  //当前年份
            if(substring.equals(formats)){   //判断年份
                String substring1 = theAwbNo.substring(4);
                return formats + String.format("%04d", (Integer.parseInt(substring1) + 1));//最大的数值上加1
            }else{
                return formats+String.format("%04d",1);     //第一个
            }
        }

    }



}
