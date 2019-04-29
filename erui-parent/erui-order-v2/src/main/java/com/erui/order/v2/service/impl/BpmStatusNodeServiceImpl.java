package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.BpmStatusNodeMapper;
import com.erui.order.v2.model.BpmStatusNode;
import com.erui.order.v2.service.BpmStatusNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sui Yingying on 2019/04/28.
 */
@Service
@Transactional
public class BpmStatusNodeServiceImpl implements BpmStatusNodeService {
    @Autowired
    private BpmStatusNodeMapper bpmStatusNodeMapper;

    /**
     * 查询流程的所有审批节点
     *
     * @param params tenant      租户
     *               category    业务流程类别
     *               sub_category业务流程模块类别
     * @return
     */
    @Override
    public List<BpmStatusNode> findNodeByCategory(Map<String, String> params) {
        List<BpmStatusNode> nodes = new ArrayList<>();
        List<Map<String, String>> ml = bpmStatusNodeMapper.findNodeByCategory(params);
        for (Map<String, String> map : ml) {
            String actId = map.get("act_id");
            String actName = map.get("act_name");
            BpmStatusNode node = new BpmStatusNode();
            node.setActId(actId);
            node.setActName(actName);
            nodes.add(node);
        }
        return nodes;
    }
}
