package com.enigma.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigma.food.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
  
}
