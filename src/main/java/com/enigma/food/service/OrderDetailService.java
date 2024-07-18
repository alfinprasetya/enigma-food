package com.enigma.food.service;

import java.util.List;

import com.enigma.food.model.OrderDetail;

public interface OrderDetailService {
    List<OrderDetail> getAll();
    OrderDetail getOne(Integer id);
    OrderDetail create(OrderDetail req);
    void delete(Integer id);
}
