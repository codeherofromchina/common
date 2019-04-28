package com.erui.order.v2.service;

import com.erui.order.v2.model.BpmStatusNode;

import java.util.List;
import java.util.Map;

/**
 * 流程--流程状态节点表服务类
 */
public interface BpmStatusNodeService {

    List<BpmStatusNode> findNodeByCategory(Map<String, String> params);
}
