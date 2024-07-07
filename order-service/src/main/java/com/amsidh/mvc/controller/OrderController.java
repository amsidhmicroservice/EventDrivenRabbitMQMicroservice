package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.Order;
import com.amsidh.mvc.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String processOrder(@RequestBody @Valid Order order) throws JsonProcessingException {
        return orderService.processOrder(order);
    }
}
