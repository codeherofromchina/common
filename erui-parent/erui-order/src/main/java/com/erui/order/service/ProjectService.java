package com.erui.order.service;

import com.erui.order.entity.Project;
import com.erui.order.requestVo.ProjectListCondition;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    List<Integer> findByProjectNos(List<String> projectNos);

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
    List<Project> purchAbleList(List<String> projectNoList, String purchaseUid) throws Exception;

    /**
     * 获取可采购的项目列表
     *
     * @param projectNoList
     * @param purchaseUid
     * @param pageNum
     * @param
     * @return {id:项目ID,projectNo:项目编号,projectName:合同标的}
     * @throws Exception
     */
    Page<Map<String, Object>> purchAbleByPage(List<String> projectNoList, String purchaseUid, int pageNum, int pageSizeString, String contractNo, String projectName) throws Exception;

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

    /**
     * 审核项目
     * @param project
     * @param auditorId
     * @param paramsProject  请求的参数
     * @return
     */
    boolean audit(Project project, String auditorId, String auditorName, Project paramsProject);
    boolean audit(Integer projectId, String auditorId, String auditorName, Project paramsProject);
    /**
     * 导出核算利润表
     * @param workbook
     * @param results  请求的参数
     * @return
     */
    void addProfitData(XSSFWorkbook workbook, Map<String, Object> results);


}
