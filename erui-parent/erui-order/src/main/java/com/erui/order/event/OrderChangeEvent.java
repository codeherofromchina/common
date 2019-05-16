package com.erui.order.event;

import com.erui.order.dao.*;
import com.erui.order.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 订单变更事件
 * Created by shigs on 2019/1/22.
 */
@Component
public class OrderChangeEvent implements ApplicationListener<ChangeEvent> {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PurchRequisitionDao purchRequisitionDao;
    @Autowired
    private PurchDao purchDao;
    @Autowired
    private DeliverConsignDao deliverConsignDao;
    @Autowired
    private InspectApplyDao inspectApplyDao;
    @Autowired
    private InspectReportDao inspectReportDao;
    @Autowired
    private InstockDao instockDao;

    @Override
    public void onApplicationEvent(ChangeEvent changeEvent) {
        Integer orderId = changeEvent.getOrderId();
        Order oldOrder = orderDao.findOne(orderId);
        if (oldOrder != null) {

            oldOrder.setStatus(5);
            orderDao.save(oldOrder);
            if (oldOrder.getDeliverConsign() != null && oldOrder.getDeliverConsign().size() > 0) {
                for (DeliverConsign deliverConsign : oldOrder.getDeliverConsign()) {
                    //订舱状态为5是变更状态
                    deliverConsign.setStatus(5);
                }
                deliverConsignDao.save(oldOrder.getDeliverConsign());
            }
            if (oldOrder.getProject() != null) {
                Project oldProject = oldOrder.getProject();
                oldProject.setProjectStatus("CHANGE");
                projectDao.save(oldProject);
                if (oldProject.getPurchs() != null && oldProject.getPurchs().size() > 0) {
                    List<InspectApply> inspectApplyList = null;
                    List<Integer> inspectReportList = null;
                    List<String> inspectApplyNos = null;
                    for (Purch purch : oldProject.getPurchs()) {
                        //采购状态状态为5是变更状态
                        purch.setStatus(5);
                        if (purch.getId() != null) {
                            inspectApplyList = inspectApplyDao.findByPurchIdAndMasterOrderByCreateTimeAsc(purch.getId(), true);
                        }
                    }
                    if (inspectApplyList != null && inspectApplyList.size() > 0) {
                        for (InspectApply inspect : inspectApplyList) {
                            inspectReportList.add(inspect.getId());
                            inspectApplyNos.add(inspect.getInspectApplyNo());
                            //质检状态为5是变更状态
                            inspect.setStatus(5);
                        }
                    }
                    if (inspectReportList != null && inspectReportList.size() > 0) {
                        List<InspectReport> inspectReports = inspectReportDao.findByInspectApplyIdInOrderByIdAsc(inspectReportList);
                        for (InspectReport inspectReport : inspectReports) {
                            //报检状态为5是变更状态
                            inspectReport.setStatus(5);
                        }
                        inspectReportDao.save(inspectReports);
                    }
                    if (inspectApplyNos != null && inspectApplyNos.size() > 0) {
                        List<Instock> instocks = instockDao.findByInspectApplyNo(inspectApplyNos);
                        if (instocks != null && instocks.size() > 0) {
                            for (Instock instock : instocks) {
                                //入库状态为5是变更状态
                                instock.setStatus(5);
                            }
                        }
                    }
                    purchDao.save(oldProject.getPurchs());
                }
                if (oldProject.getPurchReqCreate() == 3 && oldProject.getPurchRequisition() != null) {
                    PurchRequisition oPurchRequisition = oldProject.getPurchRequisition();
                    oPurchRequisition.setStatus(4);
                    purchRequisitionDao.save(oPurchRequisition);
                }
            }
        }
    }

}
