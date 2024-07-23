package com.enigma.food.service.impl;

import com.enigma.food.model.*;
import com.enigma.food.repository.OrderRepository;
import com.enigma.food.service.*;
import com.enigma.food.utils.dto.CreateOrderDto;
import com.enigma.food.utils.dto.GetDistanceRequest;
import com.enigma.food.utils.dto.ItemsUpdateDto;
import com.enigma.food.utils.dto.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private OrderDetailService orderDetailService;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @Mock
    private RecipesService recipesService;

    @Mock
    private MapService mapService;

    @Mock
    private ItemsService itemsService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Order order = new Order();
        Page<Order> page = new PageImpl<>(Collections.singletonList(order), pageable, 1);

        when(orderRepository.findAll(pageable)).thenReturn(page);

        Page<Order> result = orderService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(orderRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetOne_Success() {
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order result = orderService.getOne(1);

        assertNotNull(result);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    public void testGetOne_NotFound() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> orderService.getOne(1));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Order With 1 Not Found", exception.getReason());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    public void testCreate_Success() {
        User user = new User();
        user.setId(1);
        user.setBalance(10000);

        Coordinate destination = new Coordinate();
        destination.setLatitude(-6.2);
        destination.setLongitude(106.8);

        CreateOrderDto.Recipe recipeDto = new CreateOrderDto.Recipe();
        recipeDto.setRecipeId(1);
        recipeDto.setQty(2);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setDestination(destination);
        createOrderDto.setRecipes(Collections.singletonList(recipeDto));

        Recipes recipe = new Recipes();
        recipe.setId(1);
        recipe.setPrice(1000);

        Items item = new Items();
        item.setId(1);
        item.setQty(10);

        Ingridients ingredient = new Ingridients();
        ingredient.setItem(item);
        ingredient.setQty(1);

        recipe.setIngridients(Collections.singletonList(ingredient));

        Order order = new Order();
        order.setUser(user);
        order.setDate(LocalDateTime.now());
        order.setDestination(destination.toString());
        order.setTotalPrice(2000);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setRecipes(recipe);
        orderDetail.setQty(2);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(mapService.getPriceByDistance(any(GetDistanceRequest.class))).thenReturn(2000);
        when(recipesService.getOne(1)).thenReturn(recipe);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderDetailService.create(any(OrderDetail.class))).thenReturn(orderDetail);
        when(userService.update(eq(user.getId()), any(UserUpdateDTO.class))).thenReturn(user);
        when(itemsService.update(eq(item.getId()), any(ItemsUpdateDto.class))).thenReturn(item);

        Order createdOrder = orderService.create(createOrderDto);

        assertNotNull(createdOrder);
        assertEquals(2000, createdOrder.getTotalPrice());
        assertEquals(user, createdOrder.getUser());
        verify(validationService, times(1)).validate(createOrderDto);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderDetailService, times(1)).create(any(OrderDetail.class));
        verify(userService, times(1)).update(eq(user.getId()), any(UserUpdateDTO.class));
        verify(itemsService, times(1)).update(eq(item.getId()), any(ItemsUpdateDto.class));
    }

    @Test
    public void testCreate_InsufficientBalance() {
        User user = new User();
        user.setId(1);
        user.setBalance(10);

        Coordinate destination = new Coordinate();
        destination.setLatitude(-6.2);
        destination.setLongitude(106.8);

        CreateOrderDto.Recipe recipeDto = new CreateOrderDto.Recipe();
        recipeDto.setRecipeId(1);
        recipeDto.setQty(2);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setDestination(destination);
        createOrderDto.setRecipes(Collections.singletonList(recipeDto));

        Recipes recipe = new Recipes();
        recipe.setId(1);
        recipe.setPrice(1000);

        Items item = new Items();
        item.setId(1);
        item.setQty(10);

        Ingridients ingredient = new Ingridients();
        ingredient.setItem(item);
        ingredient.setQty(1);

        recipe.setIngridients(Collections.singletonList(ingredient));

        Order order = new Order();
        order.setUser(user);
        order.setDate(LocalDateTime.now());
        order.setDestination(destination.toString());
        order.setTotalPrice(2000);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setRecipes(recipe);
        orderDetail.setQty(2);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(mapService.getPriceByDistance(any(GetDistanceRequest.class))).thenReturn(2000);
        when(recipesService.getOne(1)).thenReturn(recipe);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> orderService.create(createOrderDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Insuficient Balance, price is : 4000", exception.getReason());
        verify(validationService, times(1)).validate(createOrderDto);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testDelete_Success() {
        orderService.delete(1);

        verify(orderRepository, times(1)).deleteById(1);
    }
}
