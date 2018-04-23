package com.erui.order.service;

import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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
     * 获取可采购的项目列表
     * @param projectNoList
     * @param purchaseUid
     * @param pageNum
     * @param pageSize
     * @return  {id:项目ID,projectNo:项目编号,projectName:项目名称}
     * @throws Exception
     */
    Page<Map<String,Object>> purchAbleByPage(List<String> projectNoList, String purchaseUid, int pageNum, int pageSize) throws Exception;

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
     /**
      * @Author:SHIGS
      * @Description 项目列表导出
      * @Date:19:48 2018/4/18
      * @modified By
      */
    List<Project> findProjectExport(ProjectListCondition condition);
}
