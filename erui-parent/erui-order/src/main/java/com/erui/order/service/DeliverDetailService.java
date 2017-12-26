package com.erui.order.service;

import com.erui.order.entity.DeliverDetail;
import com.erui.order.requestVo.DeliverD;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverDetailService {
    /**
     * 根据id查询出库详情信息
     *
     * @param id
     * @return
     */
    DeliverDetail findById(Integer id);


    /**
     * 查询物流-出库单详情
     *
     * @param id
     * @return
     */
    DeliverDetail findDetailById(Integer id);


    /**
     * 出库管理
     *
     * @param deliverD
     * @return
     */
    Page<DeliverDetail> outboundManage(DeliverD deliverD);

    /**
     * 分页查询出库质检列表
     *
     * @param condition {
     *                  "deliverDetailNo": "产品放行单号",
     *                  "contractNo": "销售合同号",
     *                  "projectNo": "项目号",
     *                  "checkerName": "检验员",
     *                  "checkDate": "检验日期"
     *                  }
     * @param pageNum   页码
     * @param pageSize      页大小
     * @return
     */
    Page<Map<String, Object>> listQualityByPage(Map<String, String> condition, int pageNum, int pageSize);

    /**
     * 保存出库质检信息
     * @param deliverDetail
     * @return
     */
    boolean saveQuality(DeliverDetail deliverDetail);
}
