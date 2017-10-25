package com.erui.boss.web.report;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lirb on 2017/10/19.
 */

@Controller
@RequestMapping("/report/receivable")
public class ReceivableController {

    @ResponseBody
    @RequestMapping("/totalReceive")
    public Map<String, Object> totalReceive(){

        Map<String,Object> map1= new HashMap<String,Object>();
        Map<String,Object> map2= new HashMap<String,Object>();
        map2.put("totalReceive",1000);
        map2.put("notReceive",600);
        map2.put("received",400);
        map1.put("code",200);
        map1.put("data",map2);
        return map1;
    }
}
