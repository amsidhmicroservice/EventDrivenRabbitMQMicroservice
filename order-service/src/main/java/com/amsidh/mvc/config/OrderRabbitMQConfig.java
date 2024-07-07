package com.amsidh.mvc.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitMQConfig {

    @Value("${spring.rabbit.exchange}")
    private String orderExchange;

    @Value("${spring.rabbit.order.queue}")
    private String orderQueue;

    @Value("${spring.rabbit.stock.queue}")
    private String stockQueue;

    @Value("${spring.rabbit.email.queue}")
    private String emailQueue;

    @Value("${spring.rabbit.order.stock.routing_key}")
    private String orderStockRoutingKey;

    @Value("${spring.rabbit.order.email.routing_key}")
    private String orderEmailRoutingKey;

    @Value("${spring.rabbit.order.routing_key}")
    private String orderRoutingKey;

    // spring bean for stock queue
    @Bean
    public Queue stockQueue() {
        return new Queue(stockQueue);
    }

    // spring bean for stock queue
    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    // spring bean for order queue
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    //spring bean for order exchange common to all queue
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(orderExchange);
    }


    //binding between order-service and stock-service using orderStockRoutingKey
    @Bean
    public Binding orderStockExchangeQueueBinding() {
        return BindingBuilder
                .bind(stockQueue())
                .to(exchange())
                .with(orderStockRoutingKey);
    }

    //binding between order-service and stock-service using orderStockRoutingKey
    @Bean
    public Binding orderEmailExchangeQueueBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(orderEmailRoutingKey);
    }

    //binding between order-service and stock-service using orderStockRoutingKey
    @Bean
    public Binding orderExchangeQueueBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate getAmqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
