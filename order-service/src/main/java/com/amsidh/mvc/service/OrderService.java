package com.amsidh.mvc.service;

import com.amsidh.mvc.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderService {
    String processOrder(Order order) throws JsonProcessingException;

}
