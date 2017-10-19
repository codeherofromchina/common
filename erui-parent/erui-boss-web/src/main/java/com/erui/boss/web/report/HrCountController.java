package com.erui.boss.web.report;

import com.erui.report.model.HrCount;
import com.erui.report.service.HrCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lirb on 2017/10/19.
 */

@Controller
@RequestMapping(name = "/hrCount")
public class HrCountController {

    @Autowired
    private HrCountService hrCountService;

    @ResponseBody
    @RequestMapping("/findAll")
    public List<HrCount> findAll(){
        List<HrCount> list = hrCountService.findAll();
        return list;

    }
}
