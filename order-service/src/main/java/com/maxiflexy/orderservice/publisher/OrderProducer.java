package com.maxiflexy.orderservice.publisher;

import com.maxiflexy.orderservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${rabbit.routing.key.order.stock}")
    private String orderStockRoutingKey;

    @Value("${rabbit.routing.key.order.email}")
    private String orderEmailRoutingKey;

    @Value("${rabbit.exchange.name}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderEvent orderEvent){
        log.info("Order event sent to RabbitMQ : {}", orderEvent.toString());

        // send an order event to order-stock queue
        rabbitTemplate.convertAndSend(exchange, orderStockRoutingKey, orderEvent);

        // send an order event to order-email queue
        rabbitTemplate.convertAndSend(exchange, orderEmailRoutingKey, orderEvent);
    }
}
