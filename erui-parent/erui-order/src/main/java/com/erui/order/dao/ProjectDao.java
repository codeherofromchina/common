package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface ProjectDao extends JpaRepository<Project, Serializable>, JpaSpecificationExecutor<Project> {
    List<Project> findByPurchReqCreateAndPurchDone(Integer purchReqCreate, Boolean b);


    List<Project> findByPurchReqCreateAndPurchDoneAndPurchaseUid(Integer purchReqCreate, Boolean b,Integer purchaseUid);


    @Modifying
    @Query("update Project p set p.purchDone = true where p.id in :ids")
    public void updateProjectPurchDone(@Param(value = "ids") List<Integer> ids);

    /**
     * 根据项目ID查找项目列表
     *
     * @param projectIds
     * @return
     */
    List<Project> findByIdIn(List<Integer> projectIds);

    /**
     * 根据项目ID和订单id查找项目列表
     *
     * @param
     * @return
     */
    Project findByIdOrOrderId(@Param(value = "id") Integer id, @Param(value = "orderId") Integer orderId);
    /**
     * 判断项目名称是否重复
     *
     * @param projectName
     * @return Long
     */
    @Query(value = "select t1.id from Project t1 where t1.projectName= :projectName")
    Integer findIdByProjectName(@Param("projectName")String projectName);
    @Query(value = "select count(t1) from Project t1 where t1.projectName = :projectName")
    int countByProjectName(String projectName);
}
