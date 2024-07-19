package com.enigma.food.controller;

import com.enigma.food.service.OrderService;
import com.enigma.food.utils.PageWrapper;
import com.enigma.food.utils.Res;
import com.enigma.food.utils.dto.CreateOrderDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return Res.renderJson(
                new PageWrapper<>(orderService.getAll(pageable)),
                HttpStatus.OK,
                "Succes Get all Order"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                orderService.getOne(id),
                HttpStatus.OK,
                "Succes Get Order " + id
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateOrderDto req){
        return Res.renderJson(
                orderService.create(req),
                HttpStatus.CREATED,
                "Succes Create New Order"
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        orderService.delete(id);
    }
}
