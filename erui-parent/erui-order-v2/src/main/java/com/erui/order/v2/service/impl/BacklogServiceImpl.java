package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.BacklogMapper;
import com.erui.order.v2.model.Backlog;
import com.erui.order.v2.model.BacklogExample;
import com.erui.order.v2.service.BacklogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/5/6 下午8:44
 */
@Service("backlogServiceImplV2")
public class BacklogServiceImpl implements BacklogService {
    @Autowired
    private BacklogMapper backlogMapper;

    @Override
    public void addBackLogByDelYn(Backlog backlog) {
        backlog.setCreateDate(new SimpleDateFormat("yyyyMMdd").format(new Date())); //提交时间
        if (StringUtils.isBlank(backlog.getPlaceSystem())) {
            backlog.setPlaceSystem("订单");   //所在系统
        }
        backlog.setDelYn(1);

        String informTheContent = backlog.getInformTheContent(); //返回内容


        BacklogExample example = new BacklogExample();
        BacklogExample.Criteria criteria = example.createCriteria();
        criteria.andFunctionExplainIdEqualTo(backlog.getFunctionExplainId()).andHostIdEqualTo(backlog.getHostId())
                .andHostIdEqualTo(backlog.getHostId()).andUidEqualTo(backlog.getUid()).andDelYnEqualTo(1)
                .andInformTheContentEqualTo(informTheContent);
        if (backlog.getFollowId() != null) {
            criteria.andFollowIdEqualTo(backlog.getFollowId());
        }
        List<Backlog> backLogList = backlogMapper.selectByExample(example);
        if (backLogList == null || backLogList.size() == 0) {
            backlogMapper.insert(backlog);
        }
    }
}
