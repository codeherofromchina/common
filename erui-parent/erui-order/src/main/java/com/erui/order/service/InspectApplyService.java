package com.erui.order.service;

import com.erui.order.entity.InspectApply;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InspectApplyService {
    /**
     * 根据id查询报检信息
     * @param id
     * @return
     */
    InspectApply findById(Integer id);

    /**
     * 根据采购单ID查询主报检单列表
     * @param parchId
     * @return
     */
    List<InspectApply> findMasterListByParchId(Integer parchId);


    /**
     * 新增报检单
     * @param inspectApply
     * @return
     */
    public boolean insert(InspectApply inspectApply) throws Exception;

    /**
     * 更新报检单信息
     * @param inspectApply
     * @return
     */
    boolean save(InspectApply inspectApply) throws Exception;

    /**
     * 重新报检报检单中不合格的商品
     * @param inspectApply
     * @return
     */
    boolean againApply(InspectApply inspectApply) throws Exception;

    /**
     * 根据父报检单获取下面的报检单
     * @param parentId
     * @return
     */
    List<InspectApply> findByParentId(Integer parentId);

    /**
     * 查询报价单详情
     * @param id
     * @return
     */
    InspectApply findDetail(Integer id);

    /**
     * 完善临时整改意见
     * @param id
     * @param tmpMsg
     */
    void fullTmpMsg(Integer id, String tmpMsg);

    /**
     * 通过父质检单ID，查找子最后一次质检失败的报检单
     * @param parentId
     * @return
     */
    InspectApply findSonFailDetail(Integer parentId);
}
