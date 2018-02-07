package com.erui.order.service;

import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 订单业务类
 * Created by wangxiaodan on 2017/12/11.
 */
public interface ProjectService {
    /**
     * 根据id查询项目信息
     *
     * @param id
     * @return
     */
    Project findById(Integer id);

    /**
     * 根据id列表查询项目信息
     *
     * @param ids
     * @return
     */
    List<Project> findByIds(List<Integer> ids);

    /**
     * 办理项目
     *
     * @param project
     * @return
     */
    boolean updateProject(Project project) throws Exception;

    /**
     * 查看项目列表
     *
     * @param condition
     * @return
     */
    Page<Project> findByPage(ProjectListCondition condition);

    /**
     * 可以生成采购单的项目列表
     *
     * @return
     */
    List<Project> purchAbleList(List<String> projectNoList,String purchaseUid) throws Exception;

    /**
     * 根据id查询项目信息
     *
     * @param id
     * @return
     */
    Project findDesc(Integer id);

    /**
     * 根据项目ID和订单id查找项目列表
     *
     * @param
     * @return
     */
    Project findByIdOrOrderId(Integer id, Integer orderId);
}
