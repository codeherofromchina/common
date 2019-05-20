package com.erui.order.event;

import com.erui.order.dao.*;
import com.erui.order.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
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
    @Autowired
    private DeliverDetailDao deliverDetailDao;
    @Autowired
    private IogisticsDataDao iogisticsDataDao;
    @Autowired
    private PurchContractGoodsDao purchContractGoodsDao;
    @Autowired
    private PurchContractDao purchContractDao;

    @Override
    public void onApplicationEvent(ChangeEvent changeEvent) {
        Integer orderId = changeEvent.getOrderId();
        Order oldOrder = orderDao.findOne(orderId);
        if (oldOrder != null) {
            //订单状态为5为变更状态
            oldOrder.setStatus(5);
            orderDao.save(oldOrder);
            //订舱管理和出库信息管理
            if (oldOrder.getDeliverConsign() != null && oldOrder.getDeliverConsign().size() > 0) {
                for (DeliverConsign deliverConsign : oldOrder.getDeliverConsign()) {
                    //订舱状态为5为变更状态
                    deliverConsign.setStatus(5);
                    if (deliverConsign.getDeliverDetail() != null) {
                        DeliverDetail deliverDetail = deliverConsign.getDeliverDetail();
                        //出库质检状态为5为变更状态
                        deliverDetail.setStatus(9);
                        deliverDetailDao.save(deliverDetail);
                    }
                }
                deliverConsignDao.save(oldOrder.getDeliverConsign());
            }
            //物流跟踪管理
            List<IogisticsData> iogisticsDatas = iogisticsDataDao.findByContractNo(oldOrder.getContractNo());
            if (iogisticsDatas != null && iogisticsDatas.size() > 0) {
                for (IogisticsData iogisticsData : iogisticsDatas) {
                    //物流跟踪状态为5为变更状态
                    iogisticsData.setStatus(9);
                }
                iogisticsDataDao.save(iogisticsDatas);
            }
            if (oldOrder.getProject() != null) {
                //项目管理
                Project oldProject = oldOrder.getProject();
                oldProject.setProjectStatus("CHANGE");
                projectDao.save(oldProject);
                //采购申请管理
                if (oldProject.getPurchReqCreate() == 3 && oldProject.getPurchRequisition() != null) {
                    PurchRequisition oPurchRequisition = oldProject.getPurchRequisition();
                    //采购申请状态为5为变更状态
                    oPurchRequisition.setPurchStatus(5);
                    purchRequisitionDao.save(oPurchRequisition);
                }
                List<PurchContractGoods> purchContractGoods = purchContractGoodsDao.findByContractNo(oldOrder.getContractNo());
                if (purchContractGoods != null && purchContractGoods.size() > 0) {
                    PurchContract purchContract = purchContractGoods.get(0).getPurchContract();
                    //采购申合同状态为5为变更状态
                    purchContract.setStatus(5);
                    purchContractDao.save(purchContract);
                }
                if (oldProject.getPurchs() != null && oldProject.getPurchs().size() > 0) {
                    List<InspectApply> inspectApplyList = new ArrayList<>();
                    List<Integer> inspectReportList = null;
                    List<String> inspectApplyNos = null;
                    //报检 和采购
                    for (Purch purch : oldProject.getPurchs()) {
                        //采购状态状态为5为变更状态
                        purch.setStatus(5);
                        if (purch.getId() != null) {
                            List<InspectApply> inspectApplys = inspectApplyDao.findByPurchIdAndMasterOrderByCreateTimeAsc(purch.getId(), true);
                            inspectApplyList.addAll(inspectApplys);
                        }
                    }
                    if (inspectApplyList != null && inspectApplyList.size() > 0) {
                        inspectReportList = new ArrayList<>();
                        inspectApplyNos = new ArrayList<>();
                        for (InspectApply inspect : inspectApplyList) {
                            inspectReportList.add(inspect.getId());
                            inspectApplyNos.add(inspect.getInspectApplyNo());
                            //质检状态为5为变更状态
                            inspect.setStatus(5);
                        }
                        if (inspectReportList != null && inspectReportList.size() > 0) {
                            List<InspectReport> inspectReports = inspectReportDao.findByInspectApplyIdInOrderByIdAsc(inspectReportList);
                            for (InspectReport inspectReport : inspectReports) {
                                //报检状态为5为变更状态
                                inspectReport.setStatus(5);
                            }
                            inspectReportDao.save(inspectReports);
                        }
                        if (inspectApplyNos != null && inspectApplyNos.size() > 0) {
                            List<Instock> instocks = instockDao.findByInspectApplyNoIn(inspectApplyNos);
                            if (instocks != null && instocks.size() > 0) {
                                for (Instock instock : instocks) {
                                    //入库状态为5为变更状态
                                    instock.setStatus(5);
                                }
                                instockDao.save(instocks);
                            }
                        }
                        inspectApplyDao.save(inspectApplyList);
                    }
                    purchDao.save(oldProject.getPurchs());

                }
            }
        }
    }

}
