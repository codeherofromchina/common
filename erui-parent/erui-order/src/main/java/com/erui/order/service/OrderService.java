package com.erui.order.service;


import com.erui.order.entity.CheckLog;
import com.erui.order.entity.ComplexOrder;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.requestVo.AddOrderVo;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.requestVo.OutListCondition;
import com.erui.order.util.excel.ImportDataResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 订单业务类
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderService {
    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    Order findById(Integer id);

    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    public Order findByIdLang(Integer id,String lang);

    /**
     * 根据条件分页查询订单列表
     * @param condition
     * @return
     */
    Page<Order> findByPage(OrderListCondition condition);
    /**
     * 根据条件查询门户订单列表
     * @param condition
     * @return
     */
    Page<ComplexOrder> findByOutList(OutListCondition condition);
    /**
     * 删除订单
     * @param ids
     * @return
     */
    void deleteOrder(Integer ids []);
    /**
     * 修改订单
     * @param addOrderVo
     * @return
     */
    Integer updateOrder(AddOrderVo addOrderVo) throws Exception;
    /**
     * @Author:SHIGS
     * @Description 修改订单商品
     * @Date:11:42 2018/9/11
     * @modified By
     */
    //boolean updateOrderGoods(AddOrderVo addOrderVo) throws Exception;
    /**
     * 添加订单
     * @param addOrderVo
     * @return
     */
    Integer addOrder(AddOrderVo addOrderVo) throws Exception;
     /**
      * @Author:SHIGS
      * @Description 添加订单商品
      * @Date:11:42 2018/9/11
      * @modified By
      */
    //boolean addOrderGoods(AddOrderVo addOrderVo) throws Exception;
    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Order detail(Integer orderId);
    /**
     * 订单日志
     * @param orderId
     * @return
     */
    List<OrderLog> orderLog(Integer orderId);


    void updateOrderDeliverConsignC(Set<Integer> orderId);
    /**
     * 订单确认
     * @param order
     * @return
     */
    boolean orderFinish(Order order)throws Exception;
     /**
      * @Author:SHIGS
      * @Description对接门户订单详情
      * @Date:14:47 2018/3/19
      * @modified By
      */
    Map<String,Object> findByIdOut(Integer id);
     /**
      * @Author:SHIGS
      * @Description订单列表导出
      * @Date:19:04 2018/4/18
      * @modified By
      */
    List<Order> findOrderExport(final OrderListCondition condition);


    ImportDataResponse importData(List<String[]> datas, boolean testOnly);
    /**
     * @Author:SHIGS
     * @Description导入商品
     * @Date:19:04 2018/4/18
     * @modified By
     */
    ImportDataResponse importGoods(List<String[]> datas, boolean testOnly);
    ImportDataResponse importOrder(List<String[]> datas, boolean testOnly);
    ImportDataResponse importProjectData(List<String[]> datas, boolean testOnly);

    Integer checkContractNo(String contractNo,Integer id);
    /**
     * 审核订单
     * @param order
     * @param auditorId
     * @param
     * @param
     * @return
     */
    boolean audit(Order order, String auditorId,String auditorName,AddOrderVo addOrderVo) throws Exception;

    void addOrderContract(XSSFWorkbook workbook, Map<String, Object> results);
    CheckLog fullCheckLogInfo(Integer orderId, Integer purchId,Integer auditingProcess, Integer auditorId, String auditorName, String nextAuditingProcess, String nextAuditingUserId,
                                     String auditingMsg, String operation, int type);
}
