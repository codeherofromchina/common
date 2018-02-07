package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Area;
import com.erui.order.entity.Company;
import com.erui.order.service.AreaService;
import com.erui.order.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 根据ID获取分公司信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Result<Object> get(@RequestBody Map<String, Integer> map) {
        Company company = companyService.findById(map.get("id"));
        return new Result<>(company);

    }

    /**
     * 查询所有分公司
     *
     * @param
     * @return
     */
    @RequestMapping(value = "getCompany", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Result<Object> getCompany(@RequestBody Map<String, String> map) {
        if (!map.containsKey("areaBn")) {
            map.put("areaBn", "");
        }
        List<Company> companyList = companyService.findAll(map.get("areaBn"),map.get("name"));
        companyList.parallelStream().forEach(vo -> {
            vo.setDeptSet(null);
            vo.setArea(null);
        });
        return new Result<>(companyList);
    }
}
