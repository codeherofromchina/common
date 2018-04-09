package com.erui.boss.web.order;
import com.erui.boss.web.util.Result;
import com.erui.order.entity.Iogistics;
import com.erui.order.service.IogisticsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * 物流
 *
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/iogisticsData")
public class IogisticsDataController {

    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailsController.class);


    @Autowired
    private IogisticsDataService iogisticsDataService;

  /*  *//**
     * 物流跟踪管理（V 2.0）   列表页查询
     *
     * @param
     * @return
     *//*
    @RequestMapping(value = "trackingList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> trackingList(@RequestBody Iogistics iogistics) {
        Page<Iogistics> iogisticsList = iogisticsService.trackingList(iogistics);   //查询合并信息

        for(Iogistics v :iogisticsList){    //查询列表展示信息

            Set<String> contractNoList = new HashSet<>(); //销售合同号
            Set releaseDateList = new HashSet<>(); //放行日期

            List<Iogistics> iogistics1 = iogisticsService.findByPid(v.getId()); //获取出库信息
            for (Iogistics iogistics2 : iogistics1){
                contractNoList.add(iogistics2.getContractNo()); //销售合同号
                releaseDateList.add(new SimpleDateFormat("yyyy-MM-dd").format(iogistics2.getDeliverDetail().getReleaseDate())); //放行日期
            }


            v.setContractNo(contractNoList.toString());
            v.setReleaseDateList(releaseDateList.toString());
        }






        return new Result<>(iogisticsList);
    }*/




}
