package com.enigma.food.service.impl;

import com.enigma.food.model.OrderDetail;
import com.enigma.food.repository.OrderDetailRepository;
import com.enigma.food.service.OrderDetailService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail getOne(Integer id) {
        return orderDetailRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"OrderDetail With " + id + " Not Found"));
    }

    @Override
    public OrderDetail create(OrderDetail req) {
        return orderDetailRepository.save(req);
    }

    @Override
    public void delete(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}
