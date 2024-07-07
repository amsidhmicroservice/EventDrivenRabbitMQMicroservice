package com.amsidh.mvc.service;


import com.amsidh.mvc.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockService {
    void confirmStock(Order order) throws JsonProcessingException;
}
