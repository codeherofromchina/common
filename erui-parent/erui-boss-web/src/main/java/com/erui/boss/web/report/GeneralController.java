package com.erui.boss.web.report;

import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 16:08 2017/10/20
 * @modified By
 */
@RequestMapping("general")
@Controller
public class GeneralController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private InquiryCountService inquiryService;
    @Autowired
    private OrderCountService orderService;
    @Autowired
    private HrCountService hrCountService;
    @Autowired
    private SupplyChainService supplyChainService;
     /**
      * @Author:SHIGS
      * @Description
      * @Date:17:16 2017/10/20
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value = "memberCount",method = RequestMethod.GET)
    public Object memberCount(int days){
        //当前时期
        Date startTime = DateUtil.recedeTime(days);
        //环比时段
        Date chainDate = DateUtil.recedeTime(days*2);
        int curMemberCount = memberService.selectByTime(startTime,new Date());
        //环比时段数量
        int chainMemberCount = memberService.selectByTime(chainDate,startTime);
        //增加
        int addMemberChain = curMemberCount - chainMemberCount;
        //环比
        double chainMemberRate = RateUtil.intChainRate(addMemberChain,chainMemberCount);
        Map<String,Object> member = new HashMap<String,Object>();
        member.put("count",curMemberCount);
        member.put("add",addMemberChain);
        member.put("chainRate",chainMemberRate);
        //当期询单数
        int inquiryCount = inquiryService.inquiryCountByTime(startTime, new Date(),"",0,0,"","");
        //当期询单数环比chain
        int chainInquiryCount = inquiryService.inquiryCountByTime(chainDate, startTime,"",0,0,"","");

        int chainInquiryAdd = inquiryCount-chainInquiryCount;
        //当期询单金额
        double inquiryAmount = inquiryService.inquiryAmountByTime(startTime, new Date(),"");
        //环比
        double chainInquiryRate = RateUtil.intChainRate(chainInquiryAdd,chainInquiryCount);
        Map<String,Object> inquiry = new HashMap<String,Object>();
        inquiry.put("count",(inquiryCount/10000)+"万$");
        inquiry.put("amount",inquiryAmount);
        inquiry.put("chainAdd",chainInquiryAdd);
        inquiry.put("chainRate",chainInquiryRate);

        //当期询单数
        int orderCount = inquiryService.inquiryCountByTime(startTime, new Date(),"",0,0,"","");
        //当期询单数环比chain
        int chainOrderCount = inquiryService.inquiryCountByTime(chainDate, startTime,"",0,0,"","");

        int chainOrderAdd = orderCount-chainOrderCount;
        //当期询单金额
        double orderAmount = inquiryService.inquiryAmountByTime(startTime, new Date(),"");
        //环比
        double chainOrderRate = RateUtil.intChainRate(chainOrderAdd,chainOrderCount);
        Map<String,Object> order= new HashMap<String,Object>();
        order.put("count",(orderCount/10000)+"万$");
        order.put("amount",orderAmount);
        order.put("chainAdd",chainInquiryAdd);
        order.put("chainRate",chainInquiryRate);
        Map<String,Object> data = new HashMap<String,Object>();
        Map<String,Object> result = new HashMap<String,Object>();
        data.put("member",member);
        data.put("inquiry",inquiry);
        result.put("data",data);
        result.put("code",200);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description 会员级别
      * @Date:20:21 2017/10/20
      * @modified By
      */
    @ResponseBody
    @RequestMapping(value = "member",method = RequestMethod.POST)
    public Object singleMemberCount(){
        Map member =  memberService.selectMemberByTime();
        //黄金会员 高级会员 一般会员
        BigDecimal goldMember  = new BigDecimal(member.get("s5").toString());
        BigDecimal seniorMember  = new BigDecimal(member.get("s3").toString());
        BigDecimal generalMember  = new BigDecimal(member.get("s1").toString());
        int totalMember = goldMember.intValue() + seniorMember.intValue() + generalMember.intValue();
        double goldMemberRate = RateUtil.intChainRate(goldMember.intValue(),totalMember);
        double seniorMemberRate = RateUtil.intChainRate(seniorMember.intValue(),totalMember);
        double generalMemberRate = RateUtil.intChainRate(seniorMember.intValue(),totalMember);
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> gold = new HashMap<>();
        Map<String,Object> senior = new HashMap<>();
        Map<String,Object> general = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        gold.put("count",goldMember);
        gold.put("rebuyRate",goldMemberRate);
        senior.put("count",seniorMember);
        senior.put("rebuyRate",seniorMemberRate);
        general.put("count",generalMember);
        general.put("rebuyRate",generalMemberRate);
        data.put("goldMember",gold);
        data.put("seniorMember",senior);
        data.put("generalMember",general);
        result.put("data",data);
        result.put("code",200);
        return result;
    }
     /**
      * @Author:SHIGS
      * @Description
      * @Date:0:07 2017/10/21
      * @modified By
      */
     @RequestMapping(value = "capacity",method = RequestMethod.POST)
     @ResponseBody
     public Object capacity(int days){
         //当前时期
         Date startTime = DateUtil.recedeTime(days);
         //环比时段
         Date chainDate = DateUtil.recedeTime(days*2);
         //当前时段
         Map CurHrCountMap = hrCountService.selectHrCountByPart(startTime,new Date());
         //环比时段
         Map chainHrCountMap = hrCountService.selectHrCountByPart(chainDate,startTime);
         BigDecimal planCount = new BigDecimal(CurHrCountMap.get("s1").toString());
         BigDecimal regularCount  = new BigDecimal(CurHrCountMap.get("s2").toString());
         BigDecimal tryCount = new BigDecimal(CurHrCountMap.get("s3").toString());
         BigDecimal turnRightCount = new BigDecimal(CurHrCountMap.get("s4").toString());
         BigDecimal dimissionCount = new BigDecimal(CurHrCountMap.get("s8").toString());
         BigDecimal turnJobin  = new BigDecimal(CurHrCountMap.get("s9").toString());
         BigDecimal turnJobout = new BigDecimal(CurHrCountMap.get("s10").toString());
         //环比人数
         BigDecimal chainRegularCount  = new BigDecimal(chainHrCountMap.get("s2").toString());
         BigDecimal chainTurnRightCount = new BigDecimal(chainHrCountMap.get("s4").toString());
         //环比增加人数
         int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
         int chainTurnAdd = chainTurnRightCount.intValue() -turnRightCount.intValue();
         //环比
         double staffFullChainRate = RateUtil.intChainRate(chainFullAdd,chainRegularCount.intValue());
         double turnRightChainRate = RateUtil.intChainRate(chainTurnAdd,chainTurnRightCount.intValue());
         //满编率
         double staffFullRate = RateUtil.intChainRate(regularCount.intValue(),planCount.intValue());
         //转正率
         double turnRightRate = RateUtil.intChainRate(turnRightCount.intValue(),tryCount.intValue());
         Map<String,Object> data = new HashMap<>();
         data.put("staffFullRate",staffFullRate);
         data.put("staffFullChainRate",staffFullChainRate);
         data.put("turnRightRate",turnRightRate);
         data.put("turnRightChainRate",turnRightChainRate);
         data.put("onDuty",regularCount);
         data.put("plan",planCount);
         data.put("turnJobin",turnJobin.intValue());
         data.put("turnJobout",turnJobout);
         data.put("try",tryCount);
         data.put("turnRight",turnRightCount);
         data.put("leave",dimissionCount);
         Map<String,Object> result = new HashMap<>();
         result.put("code",200);
         result.put("data",data);
         return result;
     }
      /**
       * @Author:SHIGS
       * @Description
       * @Date:17:48 2017/10/21
       * @modified By
       *     "legend":[
      "spu完成量"
      ],
      "xAxis":[
      "9月1日",
      ],
      "yAxis":[
       */
    @RequestMapping(value = "inquiryOrderTrend",method = RequestMethod.POST)
    @ResponseBody
    public Object inquiryOrderTrend(int days,String type){
        //当前时期
        Date startTime = DateUtil.recedeTime(days);
        List<Map> supplyMap = supplyChainService.selectFinishByDate(startTime,new Date());
        List<Integer> spuList = new ArrayList<>();
        List<Integer> skuList = new ArrayList<>();
        List<Integer> supplierList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        for (Map map:supplyMap) {
        BigDecimal spu = new BigDecimal(map.get("finish_spu_num").toString());
        BigDecimal sku = new BigDecimal(map.get("finish_sku_num").toString());
        BigDecimal supplier = new BigDecimal(map.get("finish_suppli_num").toString());
        Date date = (Date) map.get("create_at");
        String dateString = DateUtil.format("MM月dd日",date);
            spuList.add(spu.intValue());
            skuList.add(sku.intValue());
            supplierList.add(supplier.intValue());
            dateList.add(dateString);
        }
        Map<String,Object> data = new HashedMap();
        if (type.equals("spu")){
            data.put("legend","SPU完成量");
            data.put("xAxis",dateList);
            data.put("yAxis",spuList);

        }else if (type.equals("sku")){
            data.put("legend","SKU完成量");
            data.put("xAxis",dateList);
            data.put("yAxis",skuList);
        }else {
            //String [] s = {"SPU完成量"};
            data.put("legend","供应商完成量");
            data.put("xAxis",dateList);
            data.put("yAxis",supplierList);
        }
        Map<String,Object> result = new HashedMap();
        result.put("data",data);
        result.put("code",200);
        return result;
    }
}
