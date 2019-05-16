package com.erui.order.v2.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Sui Yingying on 2019/4/28.
 * 流程状态节点
 */
public interface BpmStatusNodeMapper {

    /**
     * 查询流程的所有审批节点
     *
     * @param params tenant      租户
     *               category    业务流程类别
     *               sub_category业务流程模块类别
     * @return
     */
    List<Map<String, String>> findNodeByCategory(Map<String, String> params);
}
