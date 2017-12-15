package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectApply;
import com.erui.order.requestVo.InspectApplySaveVo;

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
     * 根据采购单ID查询质检申请单列表
     * @param parchId
     * @return
     */
    List<InspectApply> findListByParchId(Integer parchId);

    /**
     * 保存报检单信息
     * @param vo
     * @return
     */
    boolean save(InspectApplySaveVo vo);

    /**
     * 重新报检报检单中不合格的商品
     * @param vo
     * @return
     */
    boolean againApply(InspectApplySaveVo vo);

    /**
     * 获取历史报检单列表 （一次提交报检和它的所有报检子类）
     * @param id
     * @return
     */
    List<InspectApply> findSameApplyList(Integer id);

    /**
     * 查询报价单详情
     * @param id
     * @return
     */
    InspectApply findDetail(Integer id);
}
