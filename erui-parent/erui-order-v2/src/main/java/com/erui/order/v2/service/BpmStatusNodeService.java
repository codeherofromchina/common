package com.erui.order.v2.service;

import com.erui.order.v2.model.BpmStatusNode;

import java.util.List;

/**
 * 流程--流程状态节点表服务类
 */
public interface BpmStatusNodeService {

    /**
     * 查询流程的所有审批节点
     *
     * @param bpmStatusNode tenant      租户
     *               category    业务流程类别
     *               sub_category业务流程模块类别
     * @return
     */
    List<BpmStatusNode> findNodeByCategory(BpmStatusNode bpmStatusNode);
}
