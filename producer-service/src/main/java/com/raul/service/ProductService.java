package com.raul.service;

import domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static constants.RabbitMQConstants.EXG_DIRECT;
import static constants.RabbitMQConstants.EXG_FANOUT;
import static constants.RabbitMQConstants.EXG_NAME_MARKETPLACE;
import static constants.RabbitMQConstants.RK_A;
import static constants.RabbitMQConstants.RK_B;
import static constants.RabbitMQConstants.RK_PRODUCT_LOG;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RabbitTemplate rabbitTemplate;

    private static final String MSG = "Sending a message to exchange ";

    public void createProduct(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_NAME_MARKETPLACE, RK_PRODUCT_LOG, product);
    }

    public void sendA(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_DIRECT, RK_A, product);
    }

    public void sendB(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_DIRECT, RK_B, product);
    }

    public void sendFanout(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_FANOUT,"", product);
    }
}
