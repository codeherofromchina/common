package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.service.CommonService;
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
    @Autowired
    private CommonService commonService;

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
        String userid = String.valueOf(session.getAttribute("userid"));
        String realname = (String)session.getAttribute("realname");
        if (StringUtils.isNumeric(userid)) {
            performanceIndicators.setCreateUserId(Integer.parseInt(userid));
        }
        performanceIndicators.setCreateUserName(realname);
        Integer ptype = performanceIndicators.getPtype();
        // 检查指标类型错误
        if (ptype != null && ptype == 1) {
            if (performanceIndicators.getOrgId() == null) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("事业部参数错误");
                return result;
            }
            // 完善事业部名称信息
            Map<String, Object> orgInfoMap = commonService.findOrgInfoById(performanceIndicators.getOrgId());
            if (orgInfoMap == null || orgInfoMap.size() == 0) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("事业部不存在");
                return result;
            }
            performanceIndicators.setCountryBn(null);
            performanceIndicators.setOrgName((String) orgInfoMap.get("orgName"));
        } else if (ptype != null && ptype == 2) {
            if (StringUtils.isBlank(performanceIndicators.getCountryBn())) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("国家参数错误");
                return result;
            }
            // 完善国家信息
            Map<String, Object> countryInfoMap = commonService.findCountryInfoByBn(performanceIndicators.getCountryBn());
            if (countryInfoMap == null || countryInfoMap.size() == 0) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("国家信息不存在");
                return result;
            }
            performanceIndicators.setCountryBn((String) countryInfoMap.get("countryBn"));
            performanceIndicators.setCountryName((String) countryInfoMap.get("countryName"));
            performanceIndicators.setAreaBn((String) countryInfoMap.get("areaBn"));
            performanceIndicators.setAreaName((String) countryInfoMap.get("areaName"));
        } else {
            result.setStatus(ResultStatusEnum.FAIL).setMsg("指标类型错误");
            return result;
        }

        int flag = performanceIndicatorsService.add(performanceIndicators);
        if (flag != 0) {
            result.setStatus(ResultStatusEnum.FAIL);
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
    public Result<Object> del(@RequestBody Map<String,List<Integer>> params) {
        List<Integer> ids = params.get("ids");
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
        String userid = String.valueOf(session.getAttribute("userid"));
        String realname = (String)session.getAttribute("realname");
        if (StringUtils.isNumeric(userid)) {
            performanceIndicators.setCreateUserId(Integer.parseInt(userid));
        }
        performanceIndicators.setCreateUserName(realname);
        Integer ptype = performanceIndicators.getPtype();
        // 检查指标类型错误
        if (ptype != null && ptype == 1) {
            if (performanceIndicators.getOrgId() == null) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("事业部参数错误");
                return result;
            }
            Map<String, Object> orgInfoMap = commonService.findOrgInfoById(performanceIndicators.getOrgId());
            if (orgInfoMap == null || orgInfoMap.size() == 0) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("事业部不存在");
                return result;
            }
            performanceIndicators.setCountryBn(null);
            performanceIndicators.setOrgName((String) orgInfoMap.get("orgName"));
        } else if (ptype != null && ptype == 2) {
            List<String> area_country = performanceIndicators.getArea_country();
            if (area_country == null || area_country.size() != 2 || StringUtils.isBlank(area_country.get(1))) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("国家参数错误");
                return result;
            }
            // 完善国家信息
            Map<String, Object> countryInfoMap = commonService.findCountryInfoByBn(area_country.get(1));
            if (countryInfoMap == null || countryInfoMap.size() == 0) {
                result.setStatus(ResultStatusEnum.FAIL).setMsg("国家信息不存在");
                return result;
            }
            performanceIndicators.setCountryBn((String) countryInfoMap.get("countryBn"));
            performanceIndicators.setCountryName((String) countryInfoMap.get("countryName"));
            performanceIndicators.setAreaBn((String) countryInfoMap.get("areaBn"));
            performanceIndicators.setAreaName((String) countryInfoMap.get("areaName"));
        } else {
            result.setStatus(ResultStatusEnum.FAIL).setMsg("指标类型错误");
            return result;
        }
        int flag = performanceIndicatorsService.update(performanceIndicators);
        if ( flag != 0 ) {
            result.setStatus(ResultStatusEnum.FAIL);
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
        // 如果是地区国家查找，则设置参数
        String ptype = (String) params.get("ptype");
        if (StringUtils.equals("2", ptype)) {
            List<String> areaCountry = (List<String>)params.get("area_country");
            if (areaCountry != null) {
                if (areaCountry.size() > 0) {
                    params.put("areaBn",areaCountry.get(0));
                }
                if (areaCountry.size() > 1) {
                    params.put("countryBn",areaCountry.get(1));
                }
            }
        }

        Result<Object> result = new Result<>();
        PageInfo<PerformanceIndicators> pageInfo = performanceIndicatorsService.list(params);
        result.setData(pageInfo);
        return result;
    }
}
