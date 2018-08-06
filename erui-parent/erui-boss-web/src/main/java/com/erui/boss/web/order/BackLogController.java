package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.BackLog;
import com.erui.order.entity.Company;
import com.erui.order.service.BackLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order/backlog")
public class BackLogController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailwController.class);

    @Autowired
    private BackLogService backLogService;


    /**
     * 待办列表查询
     * @param backLog
     * @return
     */
    @RequestMapping(value = "findBackLogByList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody()
    public Result<Object> findBackLogByList(@RequestBody BackLog backLog) {
        try {
            /*Result<BackLog> backLogList= backLogService.findBackLogByList(backLog);
            return new Result<>(backLogList);*/
        }catch (Exception e){
            logger.error("待办列表查询错误", e);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }





}
