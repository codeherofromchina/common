package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Attachment;
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
public interface AttachmentDao extends JpaRepository<Attachment, Serializable>,JpaSpecificationExecutor<Attachment> {
    //查询附件
    @Query(value = "SELECT a1.id,a1.group,a1.title,a1.url,a1.user_id,a1.user_name,a1.front_date,a1.delete_flag,a1.delete_time,a1.create_time,a1.type,a1.category,a1.rel_obj_id FROM attachment a1 where a1.category =:category and a1.rel_obj_id =:relObjId",nativeQuery = true)
    List<Attachment> findByIdIn(Integer relObjId,String category);

    //@Modifying
    //@Query("update attachment o set  where o.id in :ids")
    /*void updateAttachment(@Param(value = "ids") List<Integer> ids);*/
}
