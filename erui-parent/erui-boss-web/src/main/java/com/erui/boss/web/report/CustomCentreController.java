package com.erui.boss.web.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.model.CateDetailVo;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.OrderCountService;
import com.erui.report.util.InquiryAreaVO;
import com.erui.report.util.CustomerNumSummaryVO;

/**
 * 客户中心
 * Created by lirb on 2017/10/20.
 */
@Controller
@RequestMapping("/report_customCentre")
public class CustomCentreController {
    @Autowired
    private InquiryCountService inquiryService;
    @Autowired
    private OrderCountService orderService;


    /*
    * 询单总览
    * */
    @ResponseBody
    @RequestMapping(value = "/inquiryPandect",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Object inquiryPandect(@RequestBody(required = true) Map<String, Object> reqMap ){
        Map<String,Object> result=new HashMap<String,Object>();
        int days = (int) reqMap.get("days");
        Date startTime = DateUtil.recedeTime(days);
        Date chainDate = DateUtil.recedeTime(days*2);//环比起始时间
        Map<String,Object> Datas=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> inquiryMap=new HashMap<String,Object>();//询单统计信息
        Map<String,Object> proIsOilMap=new HashMap<String,Object>();//油气产品分析
        Map<String,Object> proTop3Map=new HashMap<String,Object>();//产品分类Top3

        //当期询单数
        int count = inquiryService.inquiryCountByTime(startTime, new Date(),null,0,0,"","");
        //当期询单金额
        double amount = inquiryService.inquiryAmountByTime(startTime, new Date(),"");
        //当期询单数环比chain
        int chainCount = inquiryService.inquiryCountByTime(chainDate, startTime,null,0,0,"","");
        int chain=count-chainCount;
        double chainRate = RateUtil.intChainRate(chain, chainCount);//环比
        inquiryMap.put("count",count);
        inquiryMap.put("amount",amount);
        inquiryMap.put("chainAdd",chain);
        inquiryMap.put("chainRate",count);


        //当前产品总数量
        int proCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"");
        int oilCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"油气");
        int notOilCount = inquiryService.selectProCountByIsOil(startTime, new Date(),"非油气");
        //同期产品总数量
        int chainOilCount = inquiryService.selectProCountByIsOil(chainDate, startTime,"油气");
        int chainNotOilCount = inquiryService.selectProCountByIsOil(chainDate, startTime,"非油气");
        proIsOilMap.put("oil",oilCount);
        proIsOilMap.put("notOil",notOilCount);
        proIsOilMap.put("oiProportionl",RateUtil.intChainRate(oilCount,oilCount+notOilCount));
        proIsOilMap.put("chainRate",RateUtil.intChainRate(oilCount-chainOilCount,chainOilCount));


        Map<String,Object> params=new HashMap<String,Object>();
        params.put("startTime",DateUtil.recedeTime(days));
        params.put("endTime",new Date());
        List<Map<String,Object>> list = inquiryService.selectProTop3(params);
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                Map<String, Object> top3 = list.get(i);
                BigDecimal s = new BigDecimal(top3.get("proCount").toString());
                int top3Count = s.intValue();
                top3.put("proProportionl",RateUtil.intChainRate(top3Count,proCount));
                proTop3Map.put("top"+(i+1),top3);
            }
        }

        Datas.put("inquiry",inquiryMap);
        Datas.put("isOil",proIsOilMap);
        Datas.put("proTop3",proTop3Map);
        //查询Top3产品数据
        result.put("code",200);
        result.put("data",Datas);
        return result;
    }

    /*
  * 订单总览
  * */
    @ResponseBody
    @RequestMapping(value = "/orderPandect",method =RequestMethod.POST,produces = "application/json;charset=utf8")
    public Object orderPandect(@RequestBody(required = true) Map<String, Object> reqMap ){
        Map<String,Object> result=new HashMap<String,Object>();
        int days = (int) reqMap.get("days");
        Date startTime = DateUtil.recedeTime(days);
        Date chainDate = DateUtil.recedeTime(days*2);//环比起始时间
        Map<String,Object> Datas=new HashMap<String,Object>();//订单统计信息


        //当期订单数
        int count = orderService.orderCountByTime(startTime, new Date(),"","","");
        //当期询单金额
        double amount = orderService.orderAmountByTime(startTime, new Date(),"");
        //上期订单数
        int chainCount = orderService.orderCountByTime(chainDate,startTime,"","","");
        double chainRate=0.00;
        if(chainCount>0){
            chainRate=RateUtil.intChainRate(count-chainCount,chainCount);
        }
        Map<String,Object> orderMap=new HashMap<String,Object>();//询单统计信息
        orderMap.put("count",count);
        orderMap.put("amount",amount);
        orderMap.put("chainAdd",count-chainCount);
        orderMap.put("chainRate",chainRate);

        //利润率
        Double profitRate = orderService.selectProfitRate(startTime, new Date());
        Double chainProfitRate = orderService.selectProfitRate(chainDate, startTime);
        Map<String,Object> profitMap=new HashMap<String,Object>();
        profitMap.put("profitRate",profitRate);
        profitMap.put("chainRate",chainProfitRate);
        //成单率
        double successOrderRate=0.00;
        double successOrderChian=0.00;//环比
        int successOrderCount = orderService.orderCountByTime(startTime, new Date(),"正常完成","","");
        int successInquiryCount = inquiryService.inquiryCountByTime(startTime, new Date(), "已报价",0,0,"","");
        int successOrderChianCount = orderService.orderCountByTime(chainDate,startTime,"正常完成","","");
        if(successInquiryCount>0){
            successOrderRate = RateUtil.intChainRate(successOrderCount, successInquiryCount);
        }
        if(successOrderChianCount>0){
            successOrderChian=RateUtil.intChainRate((successOrderCount-successOrderChianCount),successOrderChianCount);
        }
        Map<String,Object> sucessOrderMap=new HashMap<String,Object>();
        sucessOrderMap.put("successOrderRate",successOrderRate);
        sucessOrderMap.put("successOrderChian",successOrderChian);
        //top3
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("startTime",startTime);
        params.put("endTime",new Date());
        List<Map<String, Object>> list = orderService.selectOrderProTop3(params);
        Map<String,Object> proTop3Map=new HashMap<String,Object>();
        for (int i = 0; i <list.size() ; i++) {
            proTop3Map.put("top"+(i+1),list.get(i));
                }
        //组装数据
        Datas.put("order",orderMap);
        Datas.put("profitRate",profitMap);
        Datas.put("sucessOrderMap",sucessOrderMap);
        Datas.put("top3",proTop3Map);

        result.put("code",200);
        result.put("data",Datas);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/inquiryDetail")
    public Object inquiryDetail(){
        Map<String,Object> result=new HashMap<String,Object>();
        int quotedCount = inquiryService.inquiryCountByTime(null, null, "已报价",0,0,"","");
        int noQuoteCount = inquiryService.inquiryCountByTime(null, null, "未报价",0,0,"","");
        int quotingCount = inquiryService.inquiryCountByTime(null, null, "报价中",0,0,"","");
        int totalCount=quotedCount+noQuoteCount+quotingCount;
        double quotedInquiryRate=0.00;
        double quotingInquiryRate=0.00;
        double noQuoteInquiryRate=0.00;
        if(totalCount>0){
            quotedInquiryRate=RateUtil.intChainRate(quotedCount,totalCount);
            quotingInquiryRate=RateUtil.intChainRate(quotingCount,totalCount);
            noQuoteInquiryRate=RateUtil.intChainRate(noQuoteCount,totalCount);
        }
        HashMap<String, Object> inquiryDetailMap = new HashMap<>();
        inquiryDetailMap.put("quotedCount",quotedCount);
        inquiryDetailMap.put("noQuoteCount",noQuoteCount);
        inquiryDetailMap.put("quotingCount",quotingCount);
        inquiryDetailMap.put("quotedInquiryRate",quotedInquiryRate);
        inquiryDetailMap.put("quotingInquiryRate",quotingInquiryRate);
        inquiryDetailMap.put("noQuoteInquiryRate",noQuoteInquiryRate);

        result.put("code",200);
        result.put("data",inquiryDetailMap);
        return result;
    }

    //询单时间分布分析
    @ResponseBody
    @RequestMapping(value = "/inquiryTimeDistrbute",method =RequestMethod.POST,produces = "application/json;charset=utf8")
    public Object inquiryTimeDistrbute(@RequestBody(required = true)Map<String, Object> reqMap ){

        Map<String,Object> result=new HashMap<String,Object>();
        int days = (int) reqMap.get("days");
        Date startTime = DateUtil.recedeTime(days);
        int totalCount = inquiryService.inquiryCountByTime(null, null, "",0,0,"","");
        int count1=inquiryService.inquiryCountByTime(startTime, new Date(), "",1,4,"","");
        int count2=inquiryService.inquiryCountByTime(startTime, new Date(), "",4,8,"","");
        int count3=inquiryService.inquiryCountByTime(startTime, new Date(), "",8,16,"","");
        int count4=inquiryService.inquiryCountByTime(startTime, new Date(), "",16,24,"","");
        int count5=inquiryService.inquiryCountByTime(startTime, new Date(), "",24,48,"","");
        HashMap<String, Object> quoteTimeMap = new HashMap<>();
        quoteTimeMap.put("oneCount",count1);
        quoteTimeMap.put("fourCount",count2);
        quoteTimeMap.put("eightCount",count3);
        quoteTimeMap.put("sixteenCount",count4);
        quoteTimeMap.put("twentyFourCount",count5);
        if(totalCount>0){
            quoteTimeMap.put("oneCountRate",RateUtil.intChainRate(count1,totalCount));
            quoteTimeMap.put("fourCountRate",RateUtil.intChainRate(count2,totalCount));
            quoteTimeMap.put("eightCountRate",RateUtil.intChainRate(count3,totalCount));
            quoteTimeMap.put("sixteenCountRate",RateUtil.intChainRate(count4,totalCount));
            quoteTimeMap.put("twentyFourCountRate",RateUtil.intChainRate(count5,totalCount));
        }
        result.put("code",200);
        result.put("data",quoteTimeMap);
        return result;
    }

    //询订单趋势图
    @ResponseBody
    @RequestMapping(value = "/tendencyChart",method =RequestMethod.POST,produces = "application/json;charset=utf8")
    public Object tendencyChart(@RequestBody(required = true)Map<String, Object> reqMap){
        Map<String,Object> result=new HashMap<String,Object>();
        Map<String,Object> data=new HashMap<String,Object>();

        int days = (int) reqMap.get("days");
        //封装日期,X轴
        if(days<=30){
            String[] dates=new String[days];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
            ArrayList<Date> dateList = new ArrayList<>();
            for (int i = 0; i < dates.length; i++) {
                Date datetime = DateUtil.recedeTime(days - (i+1) );
                dateList.add(datetime);
                String date = dateFormat.format(datetime);
                dates[i]=date;
            }
            //封装查询订单和询单数据
            Date sTime = DateUtil.recedeTime(days);
            Integer[] inCounts=new Integer[dateList.size()];//询单数数组
            Integer[] orCounts=new Integer[dateList.size()];//订单数数组

            for (int i = 0; i < dateList.size(); i++) {
                if(i==0){

                }else {

                }

            }
            data.put("dates",dates);
            data.put("inquiry",inCounts);
            data.put("order",orCounts);
        }

        result.put("code",200);
        result.put("data",data);
        return  result;
    }

    //事业部明细
    @ResponseBody
    @RequestMapping(value = "/busUnitDetail",method = RequestMethod.POST)
    public Object busUnitDetail(){
        HashMap<String, Object> result = new HashMap<>();//结果集
        HashMap<String, Object> data = new HashMap<>();//数据集
        //事业部列表
        List<String> orgList=inquiryService.selectOrgList();
        String[] orgs=new String[orgList.size()];
        Integer[] inqCounts=new Integer[orgList.size()];
        Integer[] ordCounts=new Integer[orgList.size()];
        Double[] successOrdCounts=new Double[orgList.size()];
        for (int i=0;i<orgList.size();i++) {
            int inqCount = inquiryService.inquiryCountByTime(null, null, "", 0, 0, orgList.get(i),"");
            int ordCount=orderService.orderCountByTime(null,null,"",orgList.get(i),"");
            int successOrdCount=orderService.orderCountByTime(null,null,"正常完成",orgList.get(i),"");
            inqCounts[i]=inqCount;
            ordCounts[i]=ordCount;
            double successRate = 0.0;
            if(ordCount!=0){
                successRate= RateUtil.intChainRate(successOrdCount, ordCount);
            }
            successOrdCounts[i]=successRate;
            orgs[i]=orgList.get(i);
        }
        data.put("busUnit",orgs);
        data.put("inquiryCount",inqCounts);
        data.put("orderCount",ordCounts);
        data.put("seccessOrderCount",successOrdCounts);

        result.put("code",200);
        result.put("data",data);
        return result;
    }

    //区域明细对比
    @ResponseBody
    @RequestMapping("/areaDetailContrast")
    public Object areaDetailContrast(){
        HashMap<String, Object> result = new HashMap<>();//结果集
        //询单
        List<String> areaList = inquiryService.selectAreaList();
        String[] areaInqCounts=new String[areaList.size()+1];//询单数量区域列表
        String[] areaInqAmounts=new String[areaList.size()+1];//询单金额区域列表
        Integer[] inqCounts=new Integer[areaList.size()+1];//询单数量列表
        Double[] inqAmounts=new Double[areaList.size()+1];//询单金额列表
        areaInqCounts[0]="询单总数量";
        areaInqAmounts[0]="询单总金额";
        int inqTotalCount = inquiryService.inquiryCountByTime(null, null, "", 0, 0, "", "");
        Double inqTotalAmount = inquiryService.inquiryAmountByTime(null, null,"");
        inqCounts[0]=inqTotalCount;
        inqAmounts[0]=inqTotalAmount;
        //订单
        String[] areaOrdCounts=new String[areaList.size()+1];//订单数量区域列表
        String[] areaOrdAmounts=new String[areaList.size()+1];//订单金额区域列表
        Integer[] OrdCounts=new Integer[areaList.size()+1];//订单数量列表
        Double[] OrdAmounts=new Double[areaList.size()+1];//订单金额列表
        areaOrdCounts[0]="订单总数量";
        areaOrdAmounts[0]="订单总金额";
        int orderTotalCount = orderService.orderCountByTime(null, null, "", "","");
        Double orderTotalAmount = orderService.orderAmountByTime(null, null,"");
        OrdCounts[0]=orderTotalCount;
        OrdAmounts[0]=orderTotalAmount;
        for (int i = 0; i <areaList.size() ; i++) {
            areaInqCounts[i+1]=areaList.get(i);
            areaInqAmounts[i+1]=areaList.get(i);
            areaOrdCounts[i+1]=areaList.get(i);
            areaOrdAmounts[i+1]=areaList.get(i);
            int inqCount = inquiryService.inquiryCountByTime(null, null, "", 0, 0, "", areaList.get(i));
            Double inqAmount = inquiryService.inquiryAmountByTime(null, null, areaList.get(i));
            int ordCount = orderService.orderCountByTime(null, null, "", "", areaList.get(i));
            Double ordAmount = orderService.orderAmountByTime(null, null, areaList.get(i));
            inqCounts[i+1]=inqCount;
            inqAmounts[i+1]=inqAmount;
            OrdCounts[i+1]=ordCount;
            OrdAmounts[i+1]=ordAmount;
        }

        HashMap<String, Object> data = new HashMap<>();//结果集
        HashMap<String, Object> inqCount = new HashMap<>();//结果集
        inqCount.put("marketArea",areaInqCounts);
        inqCount.put("inqCounts",inqCounts);
        HashMap<String, Object> inqAmount= new HashMap<>();//结果集
        inqAmount.put("marketArea",areaInqAmounts);
        inqAmount.put("inqAmount",inqAmounts);
        HashMap<String, Object> ordCount = new HashMap<>();//结果集
        ordCount.put("marketArea",areaOrdCounts);
        ordCount.put("ordCounts",OrdCounts);
        HashMap<String, Object> orderAmount = new HashMap<>();//结果集
        orderAmount.put("marketArea",areaOrdAmounts);
        orderAmount.put("ordAmount",OrdAmounts);
        data.put("inqCount",inqCount);
        data.put("inqAmount",inqAmount);
        data.put("ordCount",ordCount);
        data.put("orderAmount",orderAmount);


        result.put("code",200);
        result.put("data",data);
        return result;
    }


    //品类明细
    @ResponseBody
    @RequestMapping("/catesDetail")
    public Object catesDetail(){
        HashMap<String, Object> result = new HashMap<>();//结果集
        HashMap<String, Object> data = new HashMap<>();//结果集
        List<CateDetailVo> list=inquiryService.selectInqDetailByCategory();
        int inqTotalCount = inquiryService.inquiryCountByTime(null, null, "", 0, 0, "", "");
        int ordTotalCount = orderService.orderCountByTime(null, null, "", "", "");
        Double inqTotalAmount = inquiryService.inquiryAmountByTime(null, null, "");
        Double ordTotalAmount = orderService.orderAmountByTime(null, null, "");


        result.put("data",data);
        return  result;
    }


	/**
	 * 获取区域明细中的所有大区和大区中的所有国家列表
	 * 
	 * @return
	 */
	@RequestMapping("/areaList")
	@ResponseBody
	public Object areaList() {
		Map<String, Object> result = new HashMap<>();

		result.put("success", true);
		result.put("desc", "");

		List<InquiryAreaVO> arayList = inquiryService.selectAllAreaAndCountryList();
		result.put("data", arayList);

		return result;
	}
    

	/**
	 * 客户中心的订单和询单数据明细
	 */
	@RequestMapping("/areaDetail")
	@ResponseBody
	public Object areaDetail(@RequestParam(name = "area", required = false) String areaName,
			@RequestParam(name = "country", required = false) String countryName) {
		Map<String, Object> result = new HashMap<String, Object>();

		CustomerNumSummaryVO orderNumSummary = orderService.numSummary(areaName, countryName);
		CustomerNumSummaryVO inquiryNumSummary = inquiryService.numSummary(areaName, countryName);

		result.put("success", true);
		result.put("desc", "");
		Map<String, Object> data = new HashMap<String, Object>();

		String[] xTitleArr = new String[] { "询单数量", "油气数量", "非油气数量", "", "订单数量", "油气数量", "非油气数量", };
		Integer[] yValueArr = new Integer[] { inquiryNumSummary.getTotal(), inquiryNumSummary.getOil(),
				inquiryNumSummary.getNonoil(), 0, orderNumSummary.getTotal(), orderNumSummary.getOil(),
				orderNumSummary.getNonoil() };
		data.put("x", xTitleArr);
		data.put("y", yValueArr);
		result.put("data", data);

		return result;
	}
}
