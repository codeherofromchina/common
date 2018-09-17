package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.service.PerformanceIndicatorsService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 * 业绩指标控制器
 */
@Controller
@RequestMapping("/report/performanceIndicators")
public class PerformanceIndicatorsController {
    @Autowired
    private PerformanceIndicatorsService performanceIndicatorsService;

    /**
     * 增加业绩指标
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> add(HttpServletRequest request,@RequestBody PerformanceIndicators performanceIndicators) {
        Result<Object> result = new Result<>();
        HttpSession session = request.getSession();
        Integer userid = (Integer)session.getAttribute("userid");
        String realname = (String)session.getAttribute("realname");
        performanceIndicators.setCreateUserId(userid);
        performanceIndicators.setCreateUserName(realname);
        Integer ptype = performanceIndicators.getPtype();
        // 检查指标类型错误
        if (ptype != null && ptype == 1) {
            if (performanceIndicators.getOrgId() == null) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("事业部参数错误");
            }
        } else if (ptype != null && ptype == 2) {
            if (StringUtils.isBlank(performanceIndicators.getCountryBn())) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("国家参数错误");
            }
        } else {
            result.setStatus(ResultStatusEnum.FAIL).setMsg("指标类型错误");
        }


        int flag = performanceIndicatorsService.add(performanceIndicators);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        } else if (2 == flag) {
            result.setStatus(ResultStatusEnum.DATA_REPEAT_ERROR);
        }
        return result;
    }

    /**
     * 删除业绩指标
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "del", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> del(@RequestBody List<Integer> ids) {
        Result<Object> result = new Result<>();
        if (ids == null || ids.size() == 0) {
            result.setStatus(ResultStatusEnum.MISS_PARAM_ERROR);
            return result;
        }
        int flag = performanceIndicatorsService.del(ids);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        }
        return result;
    }

    /**
     * 更新业绩指标
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> update(HttpServletRequest request,@RequestBody PerformanceIndicators performanceIndicators) {
        Result<Object> result = new Result<>();
        if(performanceIndicators.getId() == null){
            result.setStatus(ResultStatusEnum.MISS_PARAM_ERROR);
            return result;
        }
        HttpSession session = request.getSession();
        Integer userid = (Integer)session.getAttribute("userid");
        String realname = (String)session.getAttribute("realname");
        performanceIndicators.setCreateUserId(userid);
        performanceIndicators.setCreateUserName(realname);
        Integer ptype = performanceIndicators.getPtype();
        // 检查指标类型错误
        if (ptype != null && ptype == 1) {
            if (performanceIndicators.getOrgId() == null) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("事业部参数错误");
            }
        } else if (ptype != null && ptype == 2) {
            if (StringUtils.isBlank(performanceIndicators.getCountryBn())) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("国家参数错误");
            }
        } else {
            result.setStatus(ResultStatusEnum.FAIL).setMsg("指标类型错误");
        }
        int flag = performanceIndicatorsService.update(performanceIndicators);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        } else if (2 == flag) {
            result.setStatus(ResultStatusEnum.DATA_REPEAT_ERROR);
        } else if ( 3 == flag) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        }
        return result;
    }

    /**
     * 查询业绩指标
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody Map<String, Object> params) {
        String pageNumStr = String.valueOf(params.get("pageNum"));
        String pageSizeStr = String.valueOf(params.get("pageSize"));
        Integer pageNum = null;
        Integer pageSize = null;
        if (StringUtils.isNumeric(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
            if (pageNum < 1) {
                pageNum = new Integer(1);
            }
        } else {
            pageNum = new Integer(1);
        }
        if (StringUtils.isNumeric(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
            if (pageSize < 1) {
                pageSize = new Integer(20);
            }
        } else {
            pageSize = new Integer(20);
        }
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        Result<Object> result = new Result<>();
        PageInfo<PerformanceIndicators> pageInfo = performanceIndicatorsService.list(params);
        result.setData(pageInfo);
        return result;
    }
}
