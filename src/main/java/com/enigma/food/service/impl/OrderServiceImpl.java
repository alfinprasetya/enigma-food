package com.enigma.food.service.impl;

import com.enigma.food.model.Coordinate;
import com.enigma.food.model.Items;
import com.enigma.food.model.Order;
import com.enigma.food.model.OrderDetail;
import com.enigma.food.model.Recipes;
import com.enigma.food.model.User;
import com.enigma.food.repository.OrderRepository;
import com.enigma.food.service.AuthService;
import com.enigma.food.service.ItemsService;
import com.enigma.food.service.MapService;
import com.enigma.food.service.OrderDetailService;
import com.enigma.food.service.OrderService;
import com.enigma.food.service.RecipesService;
import com.enigma.food.service.UserService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.CreateOrderDto;
import com.enigma.food.utils.dto.GetDistanceRequest;
import com.enigma.food.utils.dto.ItemsUpdateDto;
import com.enigma.food.utils.dto.UserUpdateDTO;

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
    private final AuthService authService;
    private final RecipesService recipesService;
    private final MapService mapService;
    private final ItemsService itemsService;

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

        User user = authService.getAuthenticatedUser();

        Integer priceByDistance = mapService
                .getPriceByDistance(new GetDistanceRequest(req.getDestination()));

        AtomicInteger totalPrice = new AtomicInteger(priceByDistance);
        
        req.getRecipes().forEach(recipe -> {
            Recipes dbRecipes = recipesService.getOne(recipe.getRecipeId());
            
            totalPrice.addAndGet(dbRecipes.getPrice() * recipe.getQty());
        });
        
        Integer balance = user.getBalance() - totalPrice.get();
        if (balance < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insuficient Balance, price is : " + totalPrice);
        }
        user = userService.update(user.getId(), new UserUpdateDTO(null, null, balance));
        
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

            dbRecipes.getIngridients().forEach(ingredient -> {
                Items item = ingredient.getItem();

                Integer qty = item.getQty() - (ingredient.getQty() * recipe.getQty());
                if (qty < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredients not available");
                }
                itemsService.update(item.getId(), new ItemsUpdateDto(null, qty));
            });
        });

        return savedOrder;
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }
}
