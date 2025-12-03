package com.assignment.microservices.order.service;

import com.assignment.microservices.order.model.Batch;
import com.assignment.microservices.order.model.InventoryUpdateRequest;
import com.assignment.microservices.order.model.Order;
import com.assignment.microservices.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public Order placeOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        try {
            checkAvailability(order);
            InventoryUpdateRequest request = new InventoryUpdateRequest(
                    order.getProductId(),
                    order.getQuantity()
            );
            webClient.post()
                    .uri("/inventory/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            order.setStatus("COMPLETED");
            return orderRepository.save(order);
        } catch (Exception e) {
            order.setStatus("FAILED");
            return orderRepository.save(order);
        }
    }

    private void checkAvailability(Order order) {
        List<Batch> batches = webClient.get()
                .uri("/inventory/" + order.getProductId())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Batch>>() {})
                .block();
        if (batches == null || batches.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        int totalAvailable = batches.stream()
                .mapToInt(Batch::getQuantity)
                .sum();
        if (totalAvailable < order.getQuantity()) {
            throw new RuntimeException("Insufficient inventory");
        }
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
