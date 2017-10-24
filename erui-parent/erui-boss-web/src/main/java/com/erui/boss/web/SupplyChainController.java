package com.erui.boss.web;

import com.erui.comm.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lirb on 2017/10/24.
 * 报表系统-供应链
 */
@Controller
@RequestMapping("/report-supplyChain")
public class SupplyChainController {

    @ResponseBody
    @RequestMapping(value = "/supplyGeneral",method = RequestMethod.POST,produces = "application/json;charset=utf8")
    public Object supplyGeneral(@RequestBody(required = true) Map<String, Object> reqMap){
        Map<String,Object> result=new HashMap<String,Object>();
        int days = (int) reqMap.get("days");
        Date startTime = DateUtil.recedeTime(days);
        return null;
    }
}
