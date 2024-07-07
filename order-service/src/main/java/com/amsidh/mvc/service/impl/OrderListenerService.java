package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderListenerService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${spring.rabbit.exchange}")
    private String orderExchange;

    @Value("${spring.rabbit.order.email.routing_key}")
    private String orderEmailRoutingKey;

    @RabbitListener(queues = {"${spring.rabbit.order.queue}"})
    public void orderQueueListener(Order order) throws JsonProcessingException {
        log.info("order consumed by order-service-> {}", objectMapper.writeValueAsString(order));
        if (order.getIsStockConfirmed()) {
            if (order.getIsEmailConfirmed() == null) {
                rabbitTemplate.convertAndSend(orderExchange, orderEmailRoutingKey, order);
                log.info("order published by order-service to email-service-> {}", objectMapper.writeValueAsString(order));
            } else {
                log.info("Order placed successfully");
            }
        } else {
            log.info("There is some issue with stock-service. Your ordered stock did not confirmed due to some internal error");
        }
    }
}
