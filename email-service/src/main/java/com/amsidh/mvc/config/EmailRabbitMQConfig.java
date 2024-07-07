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
public class EmailRabbitMQConfig {

    @Value("${spring.rabbit.exchange}")
    private String orderExchange;


    @Value("${spring.rabbit.order.routing_key}")
    private String orderRoutingKey;

    @Value("${spring.rabbit.order.queue}")
    private String orderQueue;

    // spring bean for order queue
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    //spring bean for order exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(orderExchange);
    }


    // binding between order-service and stock-service using orderStockRoutingKey
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
