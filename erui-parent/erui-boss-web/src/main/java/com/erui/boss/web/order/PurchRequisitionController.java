package com.erui.boss.web.order;

/**
 * Created by GS on 2017/12/24 0024.
 */

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.Project;
import com.erui.order.entity.PurchRequisition;
import com.erui.order.service.ProjectService;
import com.erui.order.service.PurchRequisitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/12.
 */
@RestController
@RequestMapping("order/" +
        "")
public class PurchRequisitionController {
    private final static Logger logger = LoggerFactory.getLogger(PurchRequisitionController.class);

    @Autowired
    private PurchRequisitionService purchRequisitionService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "queryPurchRequisition", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<PurchRequisition> queryPurchRequisition(@RequestBody Map<String, Integer> map) {
        PurchRequisition purchRequisition = purchRequisitionService.findById(map.get("id"), map.get("orderId"));
        if (purchRequisition != null) {
            purchRequisition.setProject(null);
            purchRequisition.getGoodsList().iterator().next().setOrder(null);
        }
        return new Result<>(purchRequisition);
    }

    /**
     * 采购申请单信息
     *
     * @return
     */
    @RequestMapping(value = "addPurchDesc", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addPurchDesc(@RequestBody Map<String, Integer> proMap) {
        Project project = projectService.findDesc(proMap.get("proId"));
        Map<String, Object> map = new HashMap<>();
        map.put("pmUid", project.getManagerUid());
        //   map.put("department",project.getOrder().getTechnicalIdDept());
        map.put("transModeBn", project.getOrder().getTradeTerms());
        map.put("goodList", project.getOrder().getGoodsList());
        return new Result<>(map);
    }

    /**
     * 新增采购单
     *
     * @param purchRequisition
     * @return
     */
    @RequestMapping(value = "addPurchaseRequestion", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> addPurchase(@RequestBody PurchRequisition purchRequisition) {

        // TODO 参数检查略过,检查采购数量必须大于（如果存在替换商品则可以等于0）0
        try {
            boolean flag = false;
            if (purchRequisition.getId() != null) {
                flag = purchRequisitionService.updatePurchRequisition(purchRequisition);
            } else {
                flag = purchRequisitionService.insertPurchRequisition(purchRequisition);
            }
            if (flag) {
                return new Result<>();
            }
        } catch (Exception ex) {
            logger.error("采购申请单单操作失败：{}", purchRequisition, ex);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }
}
