package com.maxiflexy.orderservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbit.queue.order.stock.name}")
    private String orderStockQueue;

    @Value("${rabbit.queue.order.email.name}")
    private String orderEmailQueue;


    @Value("${rabbit.exchange.name}")
    private String exchange;


    @Value("${rabbit.routing.key.order.stock}")
    private String orderStockRoutingKey;

    @Value("${rabbit.routing.key.order.email}")
    private String orderEmailRoutingKey;


    // spring bean for rabbitmq queue
    @Bean
    public Queue orderStockQueue(){
        return new Queue(orderStockQueue);
    }

    @Bean
    public Queue orderEmailQueue(){
        return new Queue(orderEmailQueue);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // Bing between queue and exchange using routing key
    @Bean
    public Binding bindingForStock(){
        return BindingBuilder.bind(orderStockQueue())
                .to(exchange())
                .with(orderStockRoutingKey);
    }

    @Bean
    public Binding bindingForEmail(){
        return BindingBuilder.bind(orderEmailQueue())
                .to(exchange())
                .with(orderEmailRoutingKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
