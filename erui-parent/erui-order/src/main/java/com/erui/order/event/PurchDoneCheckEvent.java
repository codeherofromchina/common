package com.erui.order.event;

import com.erui.order.dao.OrderDao;
import com.erui.order.dao.ProjectDao;
import com.erui.order.dao.PurchRequisitionDao;
import com.erui.order.entity.BackLog;
import com.erui.order.entity.Goods;
import com.erui.order.entity.Project;
import com.erui.order.entity.PurchRequisition;
import com.erui.order.service.BackLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购申请判断采购是否完成事件
 * Created by wangxiaodan on 2018/4/2.
 */
public class PurchDoneCheckEvent extends BaseEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchDoneCheckEvent.class);

    private final PurchRequisitionDao purchRequisitionDao;
    private final ProjectDao projectDao;
    private final BackLogService backLogService;
    private final OrderDao orderDao;
    private final List<Integer> projectIds;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PurchDoneCheckEvent(Object source,
                               List<Integer> projectIds,
                               ProjectDao projectDao,
                               BackLogService backLogService,
                               OrderDao orderDao,
                               PurchRequisitionDao purchRequisitionDao) {
        super(source);
        this.projectIds = projectIds;
        this.projectDao = projectDao;
        this.orderDao = orderDao;
        this.backLogService = backLogService;
        this.purchRequisitionDao = purchRequisitionDao;
    }

    /**
     * 执行具体业务逻辑
     */
    @Override
    public void execute() {
        try {
            LOGGER.info("检查商品采购是否完成，并设置项目和采购申请的采购状态 {}", projectIds);
            List<Integer> updateIds = new ArrayList<>();
            List<Integer> proIds = new ArrayList<>();
            List<Integer> orderIds = new ArrayList<>();
            int size = projectIds.size();
            if (size > 0) {
                int toIndex = 0;
                for (int fromIndex = 0; fromIndex < size; fromIndex = toIndex) {
                    toIndex = fromIndex + 5;
                    toIndex = toIndex > size ? size : toIndex;
                    List<Project> projectList = projectDao.findByIdIn(projectIds.subList(fromIndex, toIndex));
                    for (Project project : projectList) {
                        PurchRequisition purchRequisition = project.getPurchRequisition();
                        List<Goods> goodsList = project.getOrder().getGoodsList();
                        boolean purchDone = true;
                        for (Goods goods : goodsList) {
                            if (goods.getPurchasedNum() == null || (!goods.getExchanged() && goods.getPurchasedNum() < goods.getContractGoodsNum())) {
                                purchDone = false;
                                break;
                            }
                        }
                        if (purchDone) {
                            updateIds.add(project.getId());
                            if (project.getOrderCategory() != null && project.getOrderCategory().equals(6)) {
                                proIds.add(project.getId());
                                orderIds.add(project.getOrder().getId());
                            }
                            //采购数量是已完毕 ，删除   “办理采购订单”  待办提示
                            BackLog backLog = new BackLog();
                            backLog.setFunctionExplainId(BackLog.ProjectStatusEnum.PURCHORDER.getNum());    //功能访问路径标识
                            backLog.setHostId(project.getId());
                            try {
                                // 待办删除未成功不影响正常业务
                                backLogService.updateBackLogByDelYn(backLog);
                            } catch (Exception e) {
                                LOGGER.error("待办删除失败projectId:{},FunctionExplainId:{},err:{}", project.getId(), BackLog.ProjectStatusEnum.PURCHORDER.getNum(), e);
                                e.printStackTrace();
                            }
                            LOGGER.info("采购完成。projectId:{}", project.getId());
                            if (purchRequisition != null) {
                                purchRequisition.setPurchStatus(PurchRequisition.PurchStatusEnum.DONE.getCode());
                            }
                        } else {
                            LOGGER.info("采购未完成。projectId:{}", project.getId());
                            if (purchRequisition != null) {
                                purchRequisition.setPurchStatus(PurchRequisition.PurchStatusEnum.BEING.getCode());
                            }
                        }
                        if (purchRequisition != null) {
                            purchRequisitionDao.save(purchRequisition);
                        }
                    }
                }
                if (updateIds.size() > 0) {
                    //项目采购完成
                    projectDao.updateProjectPurchDone(updateIds);
                    //当订单类型为国内订单时项目完结 订单状态修改为已完成 项目状态为已完成 流程进度修改为已发运
                    if (proIds.size() > 0) {
                        projectDao.updateProjectStatus(proIds);
                        orderDao.updateOrderStatus(orderIds);
                    }
                }
            }
            LOGGER.info("采购是否完成事件结束。");
        } catch (Exception ee) {
            LOGGER.error("采购是否完成事件结束。err:{}", ee);
            ee.printStackTrace();
        }
    }
}
