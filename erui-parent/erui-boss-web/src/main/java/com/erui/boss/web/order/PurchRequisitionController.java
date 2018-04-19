package com.erui.boss.web.order;

/**
 * Created by GS on 2017/12/24 0024.
 */

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.EruitokenUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.entity.Project;
import com.erui.order.entity.PurchRequisition;
import com.erui.order.service.ProjectService;
import com.erui.order.service.PurchRequisitionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/12.
 */
@RestController
@RequestMapping("order/purchRequestion")
public class PurchRequisitionController {
    private final static Logger logger = LoggerFactory.getLogger(PurchRequisitionController.class);

    @Autowired
    private PurchRequisitionService purchRequisitionService;
    @Autowired
    private ProjectService projectService;

    /**
     * 采购申请列表
     * @param condition
     *  { 销售合同号：contractNo,项目号：projectNo,项目名称：projectName,项目开始日期：startDate,下发采购日期：submitDate,
     *      要求采购到货日期：requirePurchaseDate,商务技术经办人：businessName,页码：page,页大小：rows}
     * @return {
     *          contractNo:销售合同号,projectNo:项目号,projectName:项目名称,
     *          businessName:商务技术经办人,startDate:项目开始日期,
     *          submitDate:下发采购日期,requirePurchaseDate:要求采购到货日期,status:状态
     *      }
     */
    @RequestMapping(value = "listByPage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> listByPage(@RequestBody Map<String,String> condition) {
        Page<Map<String,Object>> page =  purchRequisitionService.listByPage(condition);
        return new Result<>(page);
    }



    @RequestMapping(value = "queryPurchRequisition", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<PurchRequisition> queryPurchRequisition(@RequestBody Map<String, Integer> map) {
        PurchRequisition purchRequisition = purchRequisitionService.findById(map.get("id"), map.get("orderId"));
        if (purchRequisition != null) {
            purchRequisition.setProject(null);
            return new Result<>(purchRequisition);
        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 采购申请单信息
     *
     * @return
     */
    @RequestMapping(value = "addPurchDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addPurchDesc(@RequestBody Map<String, Integer> proMap) {
        Project project = projectService.findDesc(proMap.get("proId"));
        if (project != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("pmUid", project.getManagerUid() != null ? project.getManagerUid() : project.getBusinessUid());
            map.put("contractNo", project.getContractNo());
            map.put("sendDeptId", project.getSendDeptId());
            map.put("transModeBn", project.getOrder().getTradeTerms());
            map.put("goodList", project.getOrder().getGoodsList());
            return new Result<>(map);

        }
        return new Result<>(ResultStatusEnum.DATA_NULL);
    }

    /**
     * 新增采购单
     *
     * @param purchRequisition
     * @return
     */
    @RequestMapping(value = "addPurchaseRequestion", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addPurchase(@RequestBody PurchRequisition purchRequisition , HttpServletRequest request) {
        Result<Object> result = new Result<>();
        if (StringUtils.isBlank(purchRequisition.getProjectNo()) || StringUtils.equals(purchRequisition.getProjectNo(), "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("项目号不能为空");
        } else if (purchRequisition.getSubmitDate() == null) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("下发时间不能为空");
        } else if (StringUtils.isBlank(purchRequisition.getTradeMethod()) || StringUtils.equals(purchRequisition.getTradeMethod(), "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("贸易方式不能为空");
        } else if (StringUtils.isBlank(purchRequisition.getDeliveryPlace()) || StringUtils.equals(purchRequisition.getDeliveryPlace(), "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("交货地点不能为空");
        } else {
            try {
                boolean flag;
                String eruiToken = EruitokenUtil.getEruiToken(request);
                ThreadLocalUtil.setObject(eruiToken);
                if (purchRequisition.getId() != null) {
                    flag = purchRequisitionService.updatePurchRequisition(purchRequisition);
                } else {
                    flag = purchRequisitionService.insertPurchRequisition(purchRequisition);
                }
                if (flag) {
                    return result;
                }
            } catch (Exception ex) {
                logger.error("采购申请单单操作失败：{}", purchRequisition, ex);
                if (ex instanceof DataIntegrityViolationException) {
                    result.setCode(ResultStatusEnum.DUPLICATE_ERROR.getCode());
                    result.setMsg("项目号已存在");
                    return result;
                }
            }
        }
        return result;
    }
}
