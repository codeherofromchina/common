package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.MemberInfoService;
import com.erui.report.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 会员数据统计
 * Created by wangxiaodan on 2018/9/26.
 */
@Controller
@RequestMapping("/report/memberInfo")
public class MemberInfoController {
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 人均效能统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "efficiency", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> efficiency() {


        return null;
    }


    /**
     * 客户拜访统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "visitStatistics", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> visitStatistics(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) { // 地区
            /// 按照地区统计客户拜访统计
            data = memberInfoService.visitStatisticsByArea(params);
        } else { // 国家
            /// 按照国家统计客户拜访统计
            data = memberInfoService.visitStatisticsByCountry(params);
        }
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 会员统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "membership", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> membership(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) { // 地区
            /// 按照地区统计客户统计
            data = memberInfoService.membershipByArea(params);
        } else { // 国家
            /// 按照国家统计客户统计
            data = memberInfoService.membershipByCountry(params);
        }
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }



    /**
     * 会员等级
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "membershipGrade", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> membershipGrade(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) { // 地区
            /// 按照地区统计会员等级
            data = memberInfoService.membershipGradeByArea(params);
        } else { // 国家
            /// 按照国家统计会员等级
            data = memberInfoService.membershipGradeByCountry(params);
        }
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }


    /**
     * 会员签约主体
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "signingBody", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> signingBody() {


        return null;
    }


    /**
     * 成单客户
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "singleCustomer", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> singleCustomer(@RequestBody Map<String, Object> params) {
        List<Map<String,Object>> data = null;
        Map<String,Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        data = memberInfoService.singleCustomer(params);
        Result<Object> result = new Result<>();
        if (data == null || data.size() == 0) {
            result.setStatus(ResultStatusEnum.DATA_NULL);
        } else {
            result.setData(data);
        }
        return result;
    }

}
