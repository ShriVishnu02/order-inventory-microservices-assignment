package com.assignment.microservices.order.service;

import com.assignment.microservices.order.model.InventoryUpdateRequest;
import com.assignment.microservices.order.model.Order;
import com.assignment.microservices.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public Order placeOrder(Order order) {
        try {
            String checkResponse = webClient.get()
                    .uri("/inventory/" + order.getProductId())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            InventoryUpdateRequest request = new InventoryUpdateRequest(
                    order.getProductId(),
                    order.getQuantity()
            );

            String updateResponse = webClient.post()
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

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
