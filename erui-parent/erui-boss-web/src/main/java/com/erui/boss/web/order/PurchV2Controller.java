package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Order;
import com.erui.order.entity.Purch;
import com.erui.order.entity.PurchGoods;
import com.erui.order.requestVo.PurchParam;
import com.erui.order.service.PurchService;
import com.erui.order.service.PurchV2Service;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/12.
 */
@RestController
@RequestMapping("order/v2/purch")
public class PurchV2Controller {
    private final static Logger logger = LoggerFactory.getLogger(PurchV2Controller.class);

    @Autowired
    private PurchV2Service purchV2Service;

    /**
     * 新增采购单
     *
     * @param purch
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(HttpServletRequest request, @RequestBody Purch purch) {
        String eruiToken = CookiesUtil.getEruiToken(request);
        ThreadLocalUtil.setObject(eruiToken);
        boolean continueFlag = true;
        String errorMsg = null;
        // 状态检查
        Purch.StatusEnum statusEnum = Purch.StatusEnum.fromCode(purch.getStatus());
        // 不是提交也不是保存
        if (statusEnum != Purch.StatusEnum.BEING && statusEnum != Purch.StatusEnum.READY) {
            continueFlag = false;
            errorMsg = "数据的状态不正确";
        }

        if (continueFlag) {
            try {
                boolean flag = false;
                if (purch.getId() != null) {
                    flag = purchV2Service.update(purch);
                } else {
                    flag = purchV2Service.insert(purch);
                }
                if (flag) {
                    return new Result<>();
                }
            } catch (Exception ex) {
                logger.error("采购单操作失败：{}", purch, ex);
                errorMsg = ex.getMessage();
            }
        }

        return new Result<>(ResultStatusEnum.FAIL).setMsg(errorMsg);
    }
}
