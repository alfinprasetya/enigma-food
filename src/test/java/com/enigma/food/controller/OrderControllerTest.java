package com.enigma.food.controller;

import com.enigma.food.model.Order;
import com.enigma.food.service.OrderService;
import com.enigma.food.utils.PageWrapper;
import com.enigma.food.utils.dto.CreateOrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetAllOrders() throws Exception {
    }

    @Test
    public void testGetOneOrder() throws Exception {
        Order order = new Order();
        order.setId(1);

        when(orderService.getOne(1)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)));

        verify(orderService).getOne(1);
    }

    @Test
    public void testGetOneOrderNotFound() throws Exception {
        when(orderService.getOne(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id 1 not found"));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrder() throws Exception {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        // Add required fields to createOrderDto as per your implementation

        Order order = new Order();
        order.setId(1);

        when(orderService.create(any(CreateOrderDto.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", is(1)));

        verify(orderService).create(any(CreateOrderDto.class));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        doNothing().when(orderService).delete(1);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());

        verify(orderService, times(1)).delete(1);
    }
}
