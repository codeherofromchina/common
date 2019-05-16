package com.erui.boss.web.order;

/**
 * Created by GS on 2017/12/24 0024.
 */

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Project;
import com.erui.order.entity.PurchRequisition;
import com.erui.order.service.ProjectService;
import com.erui.order.service.PurchRequisitionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
     *
     * @param condition { 销售合同号：contractNo,项目号：projectNo,合同标的：projectName,项目开始日期：startDate,下发采购日期：submitDate,
     *                  要求采购到货日期：requirePurchaseDate,商务技术经办人：businessName,页码：page,页大小：rows}
     * @return {
     * contractNo:销售合同号,projectNo:项目号,projectName:合同标的,
     * businessName:商务技术经办人,startDate:项目开始日期,
     * submitDate:下发采购日期,requirePurchaseDate:要求采购到货日期,status:状态
     * }
     */
    @RequestMapping(value = "listByPage", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> listByPage(@RequestBody Map<String, String> condition) {
        Page<Map<String, Object>> page = purchRequisitionService.listByPage(condition);
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
    public Result<Object> addPurchDesc(@RequestBody Map<String, Integer> proMap) throws Exception {
        Project project = projectService.findDesc(proMap.get("proId"));
        if (project != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("pmUid", project.getManagerUid() != null ? project.getManagerUid() : project.getBusinessUid());
            map.put("contractNo", project.getContractNo());
            map.put("sendDeptId", project.getSendDeptId());
            map.put("transModeBn", project.getOrder().getTradeTerms());
            map.put("tradeMethod", project.getProjectProfit().getProjectType());
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
    public Result<Object> addPurchase(@RequestBody PurchRequisition purchRequisition, HttpServletRequest request) throws Exception {
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
            boolean flag;
            String eruiToken = CookiesUtil.getEruiToken(request);
            ThreadLocalUtil.setObject(eruiToken);
            if (purchRequisition.getId() != null) {
                flag = purchRequisitionService.updatePurchRequisition(purchRequisition);
            } else {
                flag = purchRequisitionService.insertPurchRequisition(purchRequisition);
            }
            if (flag) {
                return result;
            }
           /* try {
            } catch (Exception ex) {
                logger.error("采购申请单单操作失败：{}", purchRequisition, ex);
                if (ex instanceof DataIntegrityViolationException) {
                    result.setCode(ResultStatusEnum.DUPLICATE_ERROR.getCode());
                    result.setMsg("项目号已存在");
                    return result;
                }
            }*/
        }
        return result;
    }

    @RequestMapping(value = "checkProject", method = RequestMethod.GET)
    public Result<Object> checkProject(String projectNo, Integer id) {
        Result<Object> result = new Result<>(ResultStatusEnum.FAIL);
        Integer i = null;
        if (id != null || projectNo != null) {
            i = purchRequisitionService.checkProjectNo(projectNo, id);
        }
        if (i.equals(0)) {
            result.setCode(ResultStatusEnum.SUCCESS.getCode());
            result.setMsg(ResultStatusEnum.SUCCESS.getMsg());
            result.setEnMsg(ResultStatusEnum.SUCCESS.getEnMsg());
            result.setData(i);
        } else {
            result.setData(i);
        }
        return result;
    }

    /**
     * 采购单分单采购经办人
     *
     * @param proMap
     * @return
     */
    @RequestMapping(value = "updatePurchaseUid", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> updatePurchaseUid(@RequestBody Map<String, String> proMap, HttpServletRequest request) throws Exception {
        Result<Object> result = new Result<>();
        List<PurchRequisition> list = null;
        PurchRequisition purchRequisition = null;
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        String ids = proMap.get("id");//采购单ID
        String purchaseName = proMap.get("purchaseName");//采购经办人姓名
        String purchaseUid = proMap.get("purchaseUid");//采购单ID
        if (StringUtils.isBlank(ids) || StringUtils.equals(ids, "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("采购单ID不能为空");
        } else if (StringUtils.isBlank(purchaseName) || StringUtils.equals(purchaseName, "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("采购经办人姓名不能为空");
        } else if (StringUtils.isBlank(purchaseUid) || StringUtils.equals(purchaseUid, "")) {
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg("采购经办人ID不能为空");
        } else {
            list = new ArrayList<PurchRequisition>();
            Object userId = request.getSession().getAttribute("userid");
            Object userName = request.getSession().getAttribute("realname");
            if (ids.split(",").length > 1) {
                for (String id : ids.split(",")) {
                    purchRequisition = new PurchRequisition();
                    purchRequisition.setId(Integer.parseInt(id));
                    purchRequisition.setPurchaseUid(Integer.parseInt(purchaseUid));
                    purchRequisition.setPurchaseName(purchaseName);
                    purchRequisition.setSinglePersonId(Integer.parseInt(userId.toString()));
                    purchRequisition.setSinglePerson(userName.toString());
                    list.add(purchRequisition);
                }
            } else {
                purchRequisition = new PurchRequisition();
                purchRequisition.setId(Integer.parseInt(ids));
                purchRequisition.setPurchaseUid(Integer.parseInt(purchaseUid));
                purchRequisition.setPurchaseName(purchaseName);
                purchRequisition.setSinglePersonId(Integer.parseInt(userId.toString()));
                purchRequisition.setSinglePerson(userName.toString());
                list.add(purchRequisition);
            }
            boolean flag;
            flag = purchRequisitionService.updatePurchaseUid(list);
            if (flag) {
                return result;
            }
        }
        return result;
    }
}
