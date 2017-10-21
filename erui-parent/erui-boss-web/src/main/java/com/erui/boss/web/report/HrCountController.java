package com.erui.boss.web.report;

import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.model.HrCount;
import com.erui.report.service.HrCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lirb on 2017/10/19.
 */

@Controller
@RequestMapping("/hrCount")
public class HrCountController {

    @Autowired
    private HrCountService hrCountService;
     /**
      * @Author:SHIGS
      * @Description 人力总览
      * @Date:10:55 2017/10/21
      * @modified By
      */
    @RequestMapping(value = "hrGeneral",method = RequestMethod.POST)
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
        BigDecimal chinaCount  = new BigDecimal(CurHrCountMap.get("s5").toString());
        BigDecimal foreignCount  = new BigDecimal(CurHrCountMap.get("s6").toString());
        BigDecimal newCount  = new BigDecimal(CurHrCountMap.get("s7").toString());
        BigDecimal dimissionCount = new BigDecimal(CurHrCountMap.get("s8").toString());
        BigDecimal turnJobin  = new BigDecimal(CurHrCountMap.get("s9").toString());
        BigDecimal turnJobout = new BigDecimal(CurHrCountMap.get("s10").toString());
    /*      "planCount":10,
            "staffFullCount":2,
            "staffFullRate":"0.1%",
            "staffFullChainRate":"0.1%",
            "turnRightCount":10,
            "tryCount":2,
            "tryRate":"0.1%",
            "tryChainRate":"0.1%",
            "newCount":10,
            "dimissionCount":2,
            "newAdd":"0.1%",
            "newChainRate":"0.1%",
            "groupTransferIn":8,
            "groupTransferOut":10,
            "leaveRate":"0.1%",
            "groupTransferChainRate":"0.1%",
            "chinaCount":200,
            "foreignCount":10,
            "foreignRate":"0.1%",
            "foreignChainRate":"0.1%"*/
        //环比人数
        BigDecimal chainRegularCount  = new BigDecimal(chainHrCountMap.get("s2").toString());
        BigDecimal chainTurnRightCount = new BigDecimal(chainHrCountMap.get("s4").toString());
        BigDecimal chainTryCount = new BigDecimal(chainHrCountMap.get("s3").toString());
        BigDecimal chainNewCount = new BigDecimal(chainHrCountMap.get("s7").toString());
        BigDecimal chainDimissionCount = new BigDecimal(chainHrCountMap.get("s8").toString());
        BigDecimal chainTurnJobin = new BigDecimal(chainHrCountMap.get("s9").toString());
        BigDecimal chainTurnJobout = new BigDecimal(chainHrCountMap.get("s10").toString());
        BigDecimal chainForeignCount = new BigDecimal(chainHrCountMap.get("s6").toString());
        //满编率
        double staffFullRate = RateUtil.intChainRate(regularCount.intValue(),planCount.intValue());
        //试用占比
        double tryRate = RateUtil.intChainRate(tryCount.intValue(),regularCount.intValue());
        //增长率
        double addRate = RateUtil.intChainRate(newCount.intValue(),regularCount.intValue()) - RateUtil.intChainRate(dimissionCount.intValue(),regularCount.intValue());
        //转岗流失率
        double leaveRate = RateUtil.intChainRate(turnJobout.intValue(),regularCount.intValue()) - RateUtil.intChainRate(turnJobin.intValue(),regularCount.intValue());
        //外籍占比
        double foreignRate = RateUtil.intChainRate(foreignCount.intValue(),regularCount.intValue());
        //环比增加人数
        int chainFullAdd = regularCount.intValue() - chainRegularCount.intValue();
        int chainTurnAdd = turnRightCount.intValue() - chainTurnRightCount.intValue();
        int chainAdd = newCount.intValue() - chainNewCount.intValue();
        int chainTurnJobinAdd = turnJobin.intValue() - chainTurnJobin.intValue();
        int chainForeignCountAdd = foreignCount.intValue() - chainForeignCount.intValue();
        //环比
        double staffFullChainRate = RateUtil.intChainRate(chainFullAdd,chainRegularCount.intValue());
        double tryChainRate = RateUtil.intChainRate(chainTurnAdd,chainTurnRightCount.intValue());
        double chainAddRate = RateUtil.intChainRate(chainAdd,chainNewCount.intValue());
        double groupTransferChainRate = RateUtil.intChainRate(chainTurnJobinAdd,chainTurnJobin.intValue());
        double foreignChainRate = RateUtil.intChainRate(chainForeignCount.intValue(),chainForeignCountAdd);
        Map<String,Object> data = new HashMap<>();
        data.put("turnRightCount",turnRightCount);
        data.put("newCount",newCount);
        data.put("foreignCount",foreignCount);
        data.put("chinaCount",chinaCount);
        data.put("dimissionCount",dimissionCount);
        data.put("newAdd",addRate);
        data.put("leaveRate",leaveRate);
        data.put("foreignRate",foreignRate);
        data.put("foreignChainRate",foreignChainRate);
        data.put("groupTransferChainRate",groupTransferChainRate);
        data.put("newChainRate",chainAddRate);
        data.put("staffFullRate",staffFullRate);
        data.put("staffFullChainRate",staffFullChainRate);
        data.put("tryRate",tryRate);
        data.put("turnRightChainRate",tryChainRate);
        data.put("staffFullCount",regularCount);
        data.put("planCount",planCount);
        data.put("groupTransferIn",turnJobin.intValue());
        data.put("groupTransferOut",turnJobout);
        data.put("tryCount",tryCount);
        data.put("dimissionCount",dimissionCount);
        Map<String,Object> result = new HashMap<>();

        result.put("code",200);
        result.put("data",data);
        return result;
    }
}
