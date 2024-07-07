package com.amsidh.mvc.service;


import com.amsidh.mvc.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EmailService {
    void confirmEmail(Order order) throws JsonProcessingException;
}
