package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.model.Order;
import com.amsidh.mvc.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.rabbit.exchange}")
    private String orderExchange;

    @Value("${spring.rabbit.order.routing_key}")
    private String orderExchangeKey;

    @RabbitListener(queues = {"${spring.rabbit.email.queue}"})
    @Override
    public void confirmEmail(Order order) throws JsonProcessingException {
        log.info("Order consumed by stock-service -> {}", objectMapper.writeValueAsString(order));
        order.setIsEmailConfirmed(Boolean.TRUE);
        rabbitTemplate.convertAndSend(orderExchange, orderExchangeKey, order);
        log.info("Order published by stock-service -> {}", objectMapper.writeValueAsString(order));


    }
}
