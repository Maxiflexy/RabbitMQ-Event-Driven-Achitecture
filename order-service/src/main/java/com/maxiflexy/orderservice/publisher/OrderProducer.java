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

    @Value("${rabbit.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbit.exchange.name}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderEvent orderEvent){
        log.info("Order event sent to RabbitMQ : {}", orderEvent.toString());
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, orderEvent);
    }
}
