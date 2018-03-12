package com.erui.order.dao;

import com.erui.order.entity.ComplexOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;


public interface ComplexOrderDao extends JpaRepository<ComplexOrder, Serializable> {
}
