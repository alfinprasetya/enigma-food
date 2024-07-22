package com.enigma.food.service.impl;

import com.enigma.food.model.OrderDetail;
import com.enigma.food.repository.OrderDetailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceImplTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @Test
    public void testGetAll() {
        OrderDetail orderDetail1 = new OrderDetail();
        OrderDetail orderDetail2 = new OrderDetail();
        List<OrderDetail> orderDetails = Arrays.asList(orderDetail1, orderDetail2);

        when(orderDetailRepository.findAll()).thenReturn(orderDetails);

        List<OrderDetail> result = orderDetailService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orderDetail1, result.get(0));
        assertEquals(orderDetail2, result.get(1));
    }

    @Test
    public void testGetOne_Success() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailRepository.findById(1)).thenReturn(Optional.of(orderDetail));

        OrderDetail result = orderDetailService.getOne(1);

        assertNotNull(result);
        assertEquals(orderDetail, result);
    }

    @Test
    public void testGetOne_NotFound() {
        when(orderDetailRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderDetailService.getOne(1));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("OrderDetail With 1 Not Found", thrown.getReason());
    }

    @Test
    public void testCreate() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        OrderDetail result = orderDetailService.create(orderDetail);

        assertNotNull(result);
        assertEquals(orderDetail, result);
    }

    @Test
    public void testDelete_Success() {
        doNothing().when(orderDetailRepository).deleteById(1);

        orderDetailService.delete(1);

        verify(orderDetailRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDelete_NotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderDetail Not Found")).when(orderDetailRepository).deleteById(1);

        assertThrows(ResponseStatusException.class, () -> orderDetailService.delete(1));
        verify(orderDetailRepository, times(1)).deleteById(1);
    }
}
