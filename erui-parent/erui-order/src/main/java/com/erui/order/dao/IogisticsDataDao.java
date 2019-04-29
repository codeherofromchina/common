package com.erui.order.dao;

import com.erui.order.entity.IogisticsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface IogisticsDataDao extends JpaRepository<IogisticsData, Serializable> ,JpaSpecificationExecutor<IogisticsData> {

    @Query(value = "select t.the_awb_no from logistics_data t order by t.the_awb_no desc LIMIT 1",nativeQuery = true)
    String findTheAwbNo();

    IogisticsData findById(Integer id);

    IogisticsData findByTheAwbNo(String theAwbNo);

    List<IogisticsData> findByIdIn(Set<Integer> iogisticsDataIdList);
}
