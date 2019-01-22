package com.erui.boss.web.order;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.order.entity.BackLog;
import com.erui.order.service.BackLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order/backlog")
public class BackLogController {
    private final static Logger logger = LoggerFactory.getLogger(DeliverDetailwController.class);

    @Autowired
    private BackLogService backLogService;


    /**
     * 待办列表查询
     *
     * @param backLog
     * @return
     */
    @RequestMapping(value = "findBackLogByList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody()
    public Result<Object> findBackLogByList(@RequestBody BackLog backLog) {
        try {
            Page<BackLog> backLogList = backLogService.findBackLogByList(backLog);
            if (backLogList.getTotalPages() > 0) {
                for (BackLog backLog1 : backLogList) {
                    String returnNo = backLog1.getReturnNo();
                    if (null != returnNo) {
                        backLog1.setTitleContent(returnNo);
                    }
                }
            }
            return new Result<>(backLogList);
        } catch (Exception e) {
            logger.error("待办列表查询错误", e);
        }
        return new Result<>(ResultStatusEnum.FAIL);
    }


    /**
     * 待办事项新增
     *
     * @param backLog
     * @return
     */
    @RequestMapping(value = "addBackLogByDelYn", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody()
    public Result<Object> addBackLogByDelYn(@RequestBody BackLog backLog) {
        Result<Object> result = new Result<>();
        try {
            backLogService.addBackLogByDelYn(backLog);
        } catch (Exception e) {
            logger.error("待办列表查询错误", e);
            result.setCode(ResultStatusEnum.FAIL.getCode());
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 待办事项逻辑删除
     *
     * @param backLog
     * @return
     */
    @RequestMapping(value = "updateBackLogByDelYn", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody()
    public Result<Object> updateBackLogByDelYn(@RequestBody BackLog backLog) {
        try {
            backLogService.updateBackLogByDelYn(backLog);
        } catch (Exception e) {
            logger.error("待办列表查询错误", e);
            return new Result<>(ResultStatusEnum.FAIL).setMsg(e.getMessage());
        }
        return new Result<>();
    }


}
