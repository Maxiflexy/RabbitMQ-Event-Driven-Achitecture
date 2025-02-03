package com.maxiflexy.orderservice.controller;

import com.maxiflexy.orderservice.dto.Order;
import com.maxiflexy.orderservice.dto.OrderEvent;
import com.maxiflexy.orderservice.publisher.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping("/publish")
    public ResponseEntity<String> placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent event = new OrderEvent();
        event.setStatus("PENDING");
        event.setMessage("Order is in pending status");

        orderProducer.sendMessage(event);

        return ResponseEntity.ok("Order sent to RabbitMQ...");
    }
}
