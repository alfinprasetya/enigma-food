package com.enigma.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.enigma.food.model.Order;
import com.enigma.food.utils.dto.CreateOrderDto;

public interface OrderService {
    Page<Order> getAll(Pageable pageable);
    Order getOne(Integer id);
    Order create(CreateOrderDto req);
    void delete(Integer id);
}
