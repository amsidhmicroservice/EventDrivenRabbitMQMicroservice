package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.model.Order;
import com.amsidh.mvc.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${spring.rabbit.exchange}")
    private String orderExchange;


    @Value("${spring.rabbit.order.stock.routing_key}")
    private String orderStockRoutingKey;

    @Override
    public String processOrder(Order order) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(orderExchange, orderStockRoutingKey, order);
        log.info("Initial Order published by order-service to stock-service -> {}", objectMapper.writeValueAsString(order));
        return "Order published successfully";
    }



}
