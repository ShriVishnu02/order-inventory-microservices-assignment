package com.assignment.microservices.order.service;

import com.assignment.microservices.order.model.Batch;
import com.assignment.microservices.order.model.Order;
import com.assignment.microservices.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private List<Batch> batches;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setProductId(1L);
        order.setQuantity(10);

        Batch batch = new Batch();
        batch.setQuantity(20);
        batches = Arrays.asList(batch);
    }

    @Test
    void testPlaceOrder_Success() {
        when(webClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(any(ParameterizedTypeReference.class))
                .block())
                .thenReturn(batches);
        when(webClient.post()
                .uri(anyString())
                .contentType(any())
                .bodyValue(any())
                .retrieve()
                .bodyToMono(String.class)
                .block())
                .thenReturn("Updated");
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        Order result = orderService.placeOrder(order);
        assertEquals("COMPLETED", result.getStatus());
        assertNotNull(result.getOrderDate());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testPlaceOrder_Failed() {
        when(webClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(any(ParameterizedTypeReference.class))
                .block())
                .thenThrow(new RuntimeException("Service unavailable"));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        Order result = orderService.placeOrder(order);
        assertEquals("FAILED", result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrderById_Found() {
        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setStatus("COMPLETED");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(savedOrder));
        Order result = orderService.getOrderById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("COMPLETED", result.getStatus());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        Order result = orderService.getOrderById(1L);
        assertNull(result);
    }
}
