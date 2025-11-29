package com.assignment.microservices.order.service;

import com.assignment.microservices.order.model.Order;
import com.assignment.microservices.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setProductId(1L);
        testOrder.setQuantity(5);
    }

    @Test
    void testPlaceOrderSuccess() {
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.placeOrder(testOrder);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Order result = orderService.getOrderById(999L);

        assertEquals(null, result);
    }
}
