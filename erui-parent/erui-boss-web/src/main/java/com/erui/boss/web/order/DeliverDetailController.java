package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.requestVo.DeliverDetailVo;
import com.erui.order.service.DeliverDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@RestController
@RequestMapping(value = "/order/deliverDetail")
public class DeliverDetailController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailController.class);

    @Autowired
    private DeliverDetailService deliverDetailService;

    /**
     * 查询物流-出库单详情信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result<Object> detail(@RequestParam(name = "id", required = true) Integer id) {
        DeliverDetail deliverDetail = deliverDetailService.findDetailById(id);

        DeliverDetailVo data = new DeliverDetailVo();
        data.copyFrom(deliverDetail);

        return new Result<>(data);
    }


    /**
     * 物流-出库单列表
     *
     * @param condition 条件
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> list(@RequestBody final DeliverDetailVo condition) {
        if (condition.getOpType() == null) {
            return new Result<>(ResultStatusEnum.FAIL);
        }

        Page<DeliverDetail> page = deliverDetailService.listByPage(condition);

        // 数据转换
        List<DeliverDetailVo> newContentList = null;
        if (page.hasContent()) {
            List<DeliverDetail> content = page.getContent();
            newContentList = content.stream().map(vo -> {
                DeliverDetailVo deliverDetailVo = new DeliverDetailVo();
                deliverDetailVo.setId(vo.getId());


                switch (condition.getOpType().intValue()) {
                    case 1:
                        break;
                    case 2:
                        // 质检提交以后的状态都算质检提交，之前的状态都是保存状态
                        deliverDetailVo.setStatus(vo.getStatus() > 2 ? 2 : 1);
                        break;
                    case 3:

                        break;
                    default:
                }

                return deliverDetailVo;
            }).collect(Collectors.toList());
        } else {
            newContentList = new ArrayList<>();
        }

        Page<DeliverDetailVo> data = new PageImpl<DeliverDetailVo>(newContentList, new PageRequest(page.getNumber(), page.getNumberOfElements()), page.getTotalElements());

        return new Result<>(data);
    }


    /**
     * 保存物流-出库单
     * @param deliverDetailVo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Result<Object> save(@RequestBody final DeliverDetailVo deliverDetailVo) {

        try {
            boolean flag = deliverDetailService.save(deliverDetailVo);
            if (flag) {
                return new Result<>();
            }
        }catch (Exception ex) {
            logger.error("保存异常",ex);
        }


        return new Result<>(ResultStatusEnum.FAIL);
    }

}
