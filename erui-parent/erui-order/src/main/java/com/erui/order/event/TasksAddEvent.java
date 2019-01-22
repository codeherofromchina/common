package com.erui.order.event;

import com.erui.order.entity.BackLog;
import com.erui.order.service.BackLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 增加待办事件
 * Created by wangxiaodan on 2019/1/22.
 */
public class TasksAddEvent extends AsyncBaseEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksAddEvent.class);
    private final BackLogService backLogService;
    private final BackLog.ProjectStatusEnum backEnum;
    private final String returnNo;
    private final String infoContent;
    private final Integer hostId;
    private final Integer[] userIds;

    public TasksAddEvent(Object source, BackLogService backLogService, BackLog.ProjectStatusEnum backEnum,
                         String returnNo, String infoContent, Integer hostId, Integer... userId) {
        super(source);
        this.backLogService = backLogService;
        this.backEnum = backEnum;
        this.returnNo = returnNo;
        this.infoContent = infoContent;
        this.hostId = hostId;
        this.userIds = userId;
    }

    @Override
    public void execute() {
        BackLog backLog;
        for (Integer userId : userIds) { //分单员有几个人推送几条
            LOGGER.info("增加待办信息[userId:{}\tinfoContent:{}\thostId:{}\treturnNo:{}\tfunction:{}]",
                    userId, infoContent, hostId, returnNo, backEnum);
            backLog = new BackLog();
            backLog.setFunctionExplainName(backEnum.getMsg());  //功能名称
            backLog.setFunctionExplainId(backEnum.getNum());    //功能访问路径标识
            backLog.setReturnNo(returnNo);  //返回单号
            backLog.setInformTheContent(infoContent);  //提示内容
            backLog.setHostId(hostId);    //父ID，列表页id
            backLog.setUid(userId);   //经办人id
            try {
                backLogService.addBackLogByDelYn(backLog);
            } catch (Exception e) {
                LOGGER.error("增加待办信息失败[userId:{}\tinfoContent:{}\thostId:{}\treturnNo:{}\tfunction:{}\terr:{}]",
                        userId, infoContent, hostId, returnNo, backEnum, e);
                e.printStackTrace();
            }
        }
    }
}
