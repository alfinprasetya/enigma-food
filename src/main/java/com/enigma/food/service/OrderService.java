package com.enigma.food.service;

import java.util.List;

import com.enigma.food.model.Order;
import com.enigma.food.utils.dto.CreateOrderDto;

public interface OrderService {
    List<Order> getAll();
    Order getOne(Integer id);
    Order create(CreateOrderDto req);
    void delete(Integer id);
}
