package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.report.model.SalesmanNums;
import com.erui.report.service.SalesmanNumsService;
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
 * 销售人员数据控制器
 */
@Controller
@RequestMapping("/report/salesmanNums")
public class SalesmanNumsController {


    @Autowired
    private SalesmanNumsService salesmanNumsService;

    /**
     * 增加销售人员数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> add(HttpServletRequest request,@RequestBody Map<String,List<SalesmanNums>> params) {
        List<SalesmanNums> salesmanNumsList = params.get("data");
        HttpSession session = request.getSession();
        String userid2 = String.valueOf(session.getAttribute("userid"));
        Integer userid = null;
        if (StringUtils.isNumeric(userid2)) {
            userid = Integer.parseInt(userid2);
        }
        Integer userid3= userid;
        String realname = (String)session.getAttribute("realname");
        Result<Object> result = new Result<>();
        if (salesmanNumsList != null && salesmanNumsList.size() > 0) {
            salesmanNumsList.parallelStream().forEach(vo -> {
                vo.setCreateUserId(userid3);
                vo.setCreateUserName(realname);
            });
            try {
                int flag = salesmanNumsService.add(salesmanNumsList);
                if (1 == flag) {
                    result.setStatus(ResultStatusEnum.FAIL);
                } else if (2 == flag) {
                    result.setStatus(ResultStatusEnum.DATA_REPEAT_ERROR);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
                result.setStatus(ResultStatusEnum.FAIL).setMsg(ex.getMessage());
            }
        } else {
            result.setStatus(ResultStatusEnum.PARAM_ERROR);
        }
        return result;
    }

    /**
     * 删除销售人员数据
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
        int flag = salesmanNumsService.del(ids);
        if (1 == flag) {
            result.setStatus(ResultStatusEnum.FAIL);
        }
        return result;
    }

    /**
     * 更新销售人员数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> update(HttpServletRequest request,@RequestBody SalesmanNums salesmanNums) {
        Result<Object> result = new Result<>();
        if(salesmanNums.getId() == null){
            result.setStatus(ResultStatusEnum.MISS_PARAM_ERROR);
            return result;
        }
        HttpSession session = request.getSession();
        String userid = String.valueOf(session.getAttribute("userid"));
        String realname = (String)session.getAttribute("realname");
        if (StringUtils.isNumeric(userid)) {
            salesmanNums.setCreateUserId(Integer.parseInt(userid));
        }
        salesmanNums.setCreateUserName(realname);
        int flag = salesmanNumsService.update(salesmanNums);
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
     * 查询销售人员数据
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Result<Object> list(@RequestBody Map<String, Object> params) {
        Result<Object> result = new Result<>();
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
        PageInfo<SalesmanNums> pageInfo = salesmanNumsService.list(params);
        result.setData(pageInfo);
        return result;
    }
}
