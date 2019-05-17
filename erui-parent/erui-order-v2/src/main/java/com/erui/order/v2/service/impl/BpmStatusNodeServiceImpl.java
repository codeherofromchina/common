package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.BpmStatusNodeMapper;
import com.erui.order.v2.model.BpmStatusNode;
import com.erui.order.v2.model.BpmStatusNodeExample;
import com.erui.order.v2.service.BpmStatusNodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sui Yingying on 2019/04/28.
 */
@Service("bpmStatusNodeServiceImplV2")
@Transactional
public class BpmStatusNodeServiceImpl implements BpmStatusNodeService {
    @Autowired
    private BpmStatusNodeMapper bpmStatusNodeMapper;

    /**
     * 查询流程的所有审批节点
     *
     * @param bpmStatusNode tenant      租户
     *               category    业务流程类别
     *               sub_category业务流程模块类别
     * @return
     */
    @Override
    public List<BpmStatusNode> findNodeByCategory(BpmStatusNode bpmStatusNode) {
        BpmStatusNodeExample example = new BpmStatusNodeExample();
        BpmStatusNodeExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bpmStatusNode.getTenant())) {
            criteria.andTenantEqualTo(bpmStatusNode.getTenant());
        }
        if (StringUtils.isNotBlank(bpmStatusNode.getCategory())) {
            criteria.andCategoryEqualTo(bpmStatusNode.getCategory());
        }
        if (StringUtils.isNotBlank(bpmStatusNode.getSubCategory())) {
            criteria.andSubCategoryEqualTo(bpmStatusNode.getSubCategory());
        }

        List<BpmStatusNode> nodes = bpmStatusNodeMapper.selectByExample(example);

        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        return nodes;
    }
}
