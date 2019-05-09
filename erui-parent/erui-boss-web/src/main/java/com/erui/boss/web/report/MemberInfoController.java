package com.erui.boss.web.report;

import com.erui.boss.web.util.HttpUtils;
import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.service.MemberInfoService;
import com.erui.report.util.ParamsUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "efficiency", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> efficiency(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) { // 人均效能统计 - 地区统计
            data = memberInfoService.efficiencyByArea(params);
        } else if ("2".equals(type)) { // 人均效能统计 - 国家统计
            data = memberInfoService.efficiencyByCountry(params);
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
     * 导出人均效能统计
     *
     * @return
     */
    @RequestMapping(value = "exportEfficiency")
    public Result<Object> exportEfficiency(HttpServletResponse response,
                                           String type, String startTime,
                                           String endTime,
                                           Integer sort)
            throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("sort", sort);
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = null;
        if ("1".equals(type)) { // 人均效能统计 - 地区统计
            wb = memberInfoService.exportEfficiencyByArea(params);
        } else if ("2".equals(type)) { // 人均效能统计 - 国家统计
            wb = memberInfoService.exportEfficiencyByCountry(params);
        }
        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        String fileName = "会员数据统计-人均效能统计-" + java.lang.System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 客户拜访统计
     *
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
        if ("1".equals(type)) { // 事业部
            /// 按照事业部统计客户拜访统计
            data = memberInfoService.visitStatisticsByOrg(params);
        } else if ("2".equals(type)) { // 地区
            // 按照地区统计客户拜访统计
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
     * 导出客户拜访统计
     *
     * @return
     */
    @RequestMapping(value = "exportVisitStatistics")
    public Result<Object> exportVisitStatistics(HttpServletResponse response, String type, String startTime, String endTime, Integer sort)
            throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("sort", sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = null;
        if ("1".equals(type)) { // 事业部
            wb = memberInfoService.exportVisitStatisticsByOrg(params);
        } else if ("2".equals(type)) {// 地区
            /// 按照地区统计客户拜访统计
            wb = memberInfoService.exportVisitStatisticsByArea(params);
        } else { // 国家
            /// 按照国家统计客户拜访统计
            wb = memberInfoService.exportVisitStatisticsByCountry(params);
        }
        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        String fileName = "会员数据统计-客户拜访统计-" + java.lang.System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 会员统计
     * 已废弃：逻辑修改为新增会员统计，见 statisticsAddMembership() 方法 - 2018-12-27
     *
     * @return
     * @see MemberInfoController#statisticsAddMembership(Map)
     */
    @ResponseBody
    @RequestMapping(value = "membership", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @Deprecated
    public Result<Object> membership(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) { // 地区
            /// 按照地区统计新增客户统计
            data = memberInfoService.membershipByArea(params);
        } else { // 国家
            /// 按照国家统计新增客户统计
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
     * 新增会员统计
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "statisticsAddMembership", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> statisticsAddMembership(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));
        if ("1".equals(type)) { // 地区
            /// 按照地区统计客户统计
            data = memberInfoService.statisticsAddMembershipByArea(params);
        } else { // 国家
            /// 按照国家统计客户统计
            data = memberInfoService.statisticsAddMembershipByCountry(params);
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
     * 导出会员统计
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "exportMembership")
    public Result<Object> exportMembership(HttpServletResponse response,
                                           String type,
                                           String startTime,
                                           String endTime,
                                           Integer sort)
            throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("sort", sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = null;
        if ("1".equals(type)) { // 地区
            /// 按照地区统计客户统计
            wb = memberInfoService.exportMembershipByArea(params);
        } else { // 国家
            /// 按照国家统计客户统计
            wb = memberInfoService.exportMembershipByCountry(params);
        }
        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        String fileName = "会员数据统计-会员统计-" + java.lang.System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会员等级
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "membershipGrade", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> membershipGrade(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data;
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
     * 导出会员等级
     *
     * @return
     */
    @RequestMapping(value = "exportMembershipGrade")
    public Result<Object> exportMembershipGrade(HttpServletResponse response,
                                                String type,
                                                String startTime,
                                                String endTime,
                                                Integer sort)
            throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("sort", sort);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb;
        if ("1".equals(type)) { // 地区
            /// 按照地区统计会员等级
            wb = memberInfoService.exportMembershipGradeByArea(params);
        } else { // 国家
            /// 按照国家统计会员等级
            wb = memberInfoService.exportMembershipGradeByCountry(params);
        }
        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        String fileName = "会员数据统计-会员统计-" + java.lang.System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 会员签约主体
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "signingBody", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> signingBody(@RequestBody Map<String, Object> params) {
        Map<String, List<Object>> data = null;
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        String type = String.valueOf(params.get("type"));

        if ("1".equals(type)) { // 事业部
            /// 按照事业部统计会员签约主体
            data = memberInfoService.signingBodyByOrg(params);
        } else if ("2".equals(type)) { // 地区
            /// 按照地区统计会员签约主体
            data = memberInfoService.signingBodyByArea(params);
        } else if ("3".equals(type)) { // 国家
            /// 按照国家统计会员签约主体
            data = memberInfoService.signingBodyByCountry(params);
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
     * 导出会员签约主体
     *
     * @return
     */
    @RequestMapping(value = "exportSigningBody")
    public Result<Object> exportSigningBody(HttpServletResponse response, String type, String startTime, String endTime)
            throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        HSSFWorkbook wb = null;
        if ("1".equals(type)) { // 事业部
            /// 按照事业部统计会员签约主体
            wb = memberInfoService.exportSigningBodyByOrg(params);
        } else if ("2".equals(type)) { // 地区
            /// 按照地区统计会员签约主体
            wb = memberInfoService.exportSigningBodyByArea(params);
        } else if ("3".equals(type)) { // 国家
            /// 按照国家统计会员签约主体
            wb = memberInfoService.exportSigningBodyByCountry(params);
        }
        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        String fileName = "会员数据统计-会员签约主体-" + java.lang.System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 成单客户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "singleCustomer", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> singleCustomer(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> data = null;
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
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

    /**
     * 导出成单客户
     *
     * @return
     */
    @RequestMapping(value = "exportSingleCustomer")
    public Result<Object> exportSingleCustomer(HttpServletResponse response, String startTime, String endTime) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        Map<String, Object> params02 = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params02 == null) {
            params.remove("startTime");
            params.remove("endTime");
        } else {
            params = params02;
        }
        HSSFWorkbook wb = memberInfoService.exportSingleCustomer(params);

        if (wb == null) {
            response.setContentType("text/html;charset=UTF-8");
            new Result<>(ResultStatusEnum.DATA_NULL).printResult(response.getOutputStream());
            return null;
        }
        String fileName = "会员数据统计-成单客户-" + java.lang.System.currentTimeMillis() + ".xls";
        try {
            HttpUtils.setExcelResponseHeader(response, fileName.toString());
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
