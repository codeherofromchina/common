package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.order.entity.Area;
import com.erui.order.entity.Company;
import com.erui.order.service.AreaService;
import com.erui.order.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * @param id
     * @return
     */
    @RequestMapping(value = "get")
    public Result<Object> get(@RequestParam(name = "id") Integer id) {
        Company company = companyService.findById(id);
        return new Result<>(company);

    }
    /**
     * 查询所有分公司
     *
     * @param
     * @return
     */
    @RequestMapping(value = "getCompany")
    public Result<Object> getCompany() {
        List<Company> companyList = companyService.findAll();
        companyList.parallelStream().forEach(vo -> {vo.setDeptSet(null);vo.setArea(null);});

        return new Result<>(companyList);

    }
}
