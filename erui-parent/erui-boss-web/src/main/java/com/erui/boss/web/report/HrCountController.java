package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.HrCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by lirb on 2017/10/19.
 */

@Controller
@RequestMapping("/report/hrCount")
public class HrCountController {

    @Autowired
    private HrCountService hrCountService;

    /**
     * @Author:SHIGS
     * @Description 人力总览
     * @Date:10:55 2017/10/21
     * @modified By
     */
    @RequestMapping(value = "hrGeneral", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Object hrGeneral(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String, Object> data = hrCountService.selectHrCountByPart(startTime, endTime);
        Result<Map<String, Object>> result = new Result<>(data);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:04 2017/10/25
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "queryDepart", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object queryArea() {
        Map<String, List<String>> mapBigDepart = hrCountService.selectBigDepart();
        Result<Map> result = new Result<>(mapBigDepart);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 数据比较
     * @Date:21:29 2017/10/24
     * @modified By
     */
    @RequestMapping(value = "dataCompare", method = RequestMethod.POST)
    @ResponseBody
    public Object dataCompare(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("depart")) {
            throw new MissingServletRequestParameterException("depart", "String");
        }
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        //当前时期
        Map<String, Object> mapData = hrCountService.selectHrCountByDepart(startTime, endTime, map.get("depart").toString());
        Result<Map<String, Object>> result = new Result<>(mapData);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 部门明细
     * @Date:15:55 2017/10/25
     * @modified By
     */
    @RequestMapping(value = "departmentDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Object departmentDetail(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("startTime")) {
            throw new MissingServletRequestParameterException("startTime", "String");
        }
        if (!map.containsKey("endTime")) {
            throw new MissingServletRequestParameterException("endTime", "String");
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        List<Map> departList = hrCountService.selectDepartmentCount(startTime, endTime);
        Result<List<Map>> result = new Result<List<Map>>(departList);
        return result;
    }
}
