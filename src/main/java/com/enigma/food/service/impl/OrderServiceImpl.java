package com.enigma.food.service.impl;

import com.enigma.food.model.Coordinate;
import com.enigma.food.model.Order;
import com.enigma.food.model.OrderDetail;
import com.enigma.food.model.Recipes;
import com.enigma.food.model.User;
import com.enigma.food.repository.OrderRepository;
import com.enigma.food.service.MapService;
import com.enigma.food.service.OrderDetailService;
import com.enigma.food.service.OrderService;
import com.enigma.food.service.RecipesService;
import com.enigma.food.service.UserService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.CreateOrderDto;
import com.enigma.food.utils.dto.GetDistanceRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ValidationService validationService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;
    private final RecipesService recipesService;
    private final MapService mapService;

    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getOne(Integer id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order With " + id + " Not Found"));
    }

    @Override
    @Transactional
    public Order create(CreateOrderDto req) {
        validationService.validate(req);

        User user = userService.getOne(req.getUserId());

        Coordinate officeCoordinate = mapService.getCityCoordinate("jakarta");
        Integer priceByDistance = mapService
                .getPriceByDistance(new GetDistanceRequest(officeCoordinate, req.getDestination()));

        AtomicInteger totalPrice = new AtomicInteger(priceByDistance);

        req.getRecipes().forEach(recipe -> {
            Recipes dbRecipes = recipesService.getOne(recipe.getRecipeId());

            totalPrice.addAndGet(dbRecipes.getPrice() * recipe.getQty());
        });

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setDestination(req.getDestination().toString());
        order.setTotalPrice(totalPrice.get());
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);

        req.getRecipes().forEach(recipe -> {
            Recipes dbRecipes = recipesService.getOne(recipe.getRecipeId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setRecipes(dbRecipes);
            orderDetail.setQty(recipe.getQty());
            OrderDetail savedOrderDetail = orderDetailService.create(orderDetail);
            savedOrder.addOrderDetail(savedOrderDetail);
        });

        return savedOrder;
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }
}
