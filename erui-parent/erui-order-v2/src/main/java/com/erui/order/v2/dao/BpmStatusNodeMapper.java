package com.erui.order.v2.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Sui Yingying on 2019/4/28.
 * 流程状态节点
 */
public interface BpmStatusNodeMapper {

    /**
     * 查询流程定义所有节点
     *
     * @param params
     * @return
     */
    List<Map<String, String>> findNodeByCategory(Map<String, String> params);
}
