package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.PurchContract;
import com.erui.order.service.PurchContractService;
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

@RestController
@RequestMapping("order/purchContract")
public class PurchContractController {
    private final static Logger logger = LoggerFactory.getLogger(PurchContractController.class);

    @Autowired
    private PurchContractService purchContractService;


    /**
     * 获取采购合同列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(HttpServletRequest request, @RequestBody PurchContract condition) {
        // 获取当前用户ID
        Object userId = request.getSession().getAttribute("userid");
        if (userId != null) {
            String ui = String.valueOf(userId);
            if (StringUtils.isNotBlank(ui) && StringUtils.isNumeric(ui)) {
                // 填充条件的当前采购人，查询列表条件使用
                condition.setAgentId(Integer.parseInt(ui));
            }
        }

        int page = condition.getPage();
        if (page < 1) {
            return new Result<>(ResultStatusEnum.PAGE_ERROR);
        }
        Page<PurchContract> purchContractPage = purchContractService.findByPage(condition);
        // 转换为控制层需要的数据
        if (purchContractPage.hasContent()) {
            purchContractPage.getContent().forEach(vo -> {
                vo.setAttachments(null);
                vo.setPurchGoodsList(null);
            });
        }
        return new Result<>(purchContractPage);
    }


    /**
     * 新增采购合同
     *
     * @param purchContract
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(HttpServletRequest request, @RequestBody PurchContract purchContract) {
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        boolean continueFlag = true;
        String errorMsg = null;
        // 状态检查
        PurchContract.StatusEnum statusEnum = PurchContract.StatusEnum.fromCode(purchContract.getStatus());
        // 不是提交也不是保存
        if (statusEnum != PurchContract.StatusEnum.BEING && statusEnum != PurchContract.StatusEnum.READY) {
            continueFlag = false;
            errorMsg = "数据的状态不正确";
        }

        if (continueFlag) {
            try {
                boolean flag = false;
                if (purchContract.getId() != null) {
                    flag = purchContractService.update(purchContract);
                } else {
                    flag = purchContractService.insert(purchContract);
                }
                if (flag) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                logger.error("采购合同操作失败：{}", purchContract, ex);
                errorMsg = ex.getMessage();
            }
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
    }

    /**
     * 获取采购合同详情信息
     *
     * @param purchContract {id:采购合同ID}
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> detail(@RequestBody PurchContract purchContract) {
        if (purchContract == null || purchContract.getId() == null) {
            return new Result<>(ResultStatusEnum.MISS_PARAM_ERROR);
        }
        PurchContract data = purchContractService.findDetailInfo(purchContract.getId());
        if (data != null) {
            return new Result<>(data);
        }

        return new Result<>(ResultStatusEnum.FAIL);
    }

}
