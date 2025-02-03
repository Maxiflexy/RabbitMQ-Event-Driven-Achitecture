package com.maxiflexy.stockservice.consumer;

import com.maxiflexy.stockservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "${rabbit.queue.order.name}")
    public void consume(OrderEvent event){
        log.info("Order event received: {}", event.toString());
    }
}
