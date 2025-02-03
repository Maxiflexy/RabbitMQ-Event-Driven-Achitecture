package com.maxiflexy.emailservice.consumer;

import com.maxiflexy.emailservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "${rabbit.queue.order.email.name}")
    public void consume(OrderEvent orderEvent){
        log.info("Order event received in email service : {}", orderEvent.toString());
    }
}
