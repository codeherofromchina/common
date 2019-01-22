package com.erui.order.event;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.OrderConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知积分项目事件
 * Created by wangxiaodan on 2019/1/22.
 */
public class NotifyPointProjectEvent extends AsyncBaseEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyPointProjectEvent.class);
    private final Integer orderId;
    private final ApplicationContext applicationContext;
    private final String token;

    public NotifyPointProjectEvent(Object source, Integer orderId, String token) {
        super(source);
        this.applicationContext = (ApplicationContext) source;
        this.orderId = orderId;
        this.token = token;
    }

    @Override
    public void execute() {
        LOGGER.info("通知积分系统orderId:{}", orderId);
        try {
            OrderConf orderConf = applicationContext.getBean(OrderConf.class);
            String memberPointsUrl = orderConf.getMemberPoints();
            if (StringUtil.isNotBlank(memberPointsUrl)) {
                sendNotify(memberPointsUrl);
            } else {
                LOGGER.error("积分系统URL未配置，通知失败。orderId:{}", orderId);
            }
        } catch (Exception ex) {
            LOGGER.error("通知积分系统异常，通知失败。orderId:{}\terr:{}", orderId, ex);
            ex.printStackTrace();
        }
    }



    private void sendNotify(String memberPointsUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("order_type", "1");
        params.put("order_id", String.valueOf(orderId));

        Map<String, String> header = new HashMap<>();
        header.put(CookiesUtil.TOKEN_NAME, token);
        header.put("Content-Type", "application/json");
        header.put("accept", "*/*");

        String responseMsg = HttpRequest.sendPost(memberPointsUrl, JSONObject.toJSONString(params), header);
        LOGGER.info("通知积分系统并返回，返回信息：{}\t orderId:{}", responseMsg, orderId);

        JSONObject jsonObject = JSONObject.parseObject(responseMsg);
        Integer code = jsonObject.getInteger("code");   //获取查询状态
        if (code != 1) { //查询数据正确返回 1
            LOGGER.error("通知积分系统失败。msg:{}\torderId:{}", responseMsg, orderId);
        }
    }

}
