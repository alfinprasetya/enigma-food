package com.enigma.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigma.food.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  
}
