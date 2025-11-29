package com.assignment.microservices.order.service;

import com.assignment.microservices.order.model.Order;

public interface OrderService {
    Order placeOrder(Order order);
    Order getOrderById(Long id);
}
