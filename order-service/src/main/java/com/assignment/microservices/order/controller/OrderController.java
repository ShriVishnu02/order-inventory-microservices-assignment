package com.assignment.microservices.order.controller;

import com.assignment.microservices.order.model.Order;
import com.assignment.microservices.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        Order placedOrder = orderService.placeOrder(order);

        if ("COMPLETED".equals(placedOrder.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(placedOrder);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(placedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }
}
