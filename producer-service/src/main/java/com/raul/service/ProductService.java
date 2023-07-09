package com.raul.service;

import domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static constants.RabbitMQConstants.EXG_DIRECT;
import static constants.RabbitMQConstants.EXG_FANOUT;
import static constants.RabbitMQConstants.RK_A;
import static constants.RabbitMQConstants.RK_B;
import static constants.RabbitMQConstants.RK_C;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RabbitTemplate rabbitTemplate;

    private static final String MSG = "Sending a message to exchange ";

    public void sendA(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_DIRECT, RK_A, product);
    }

    public void sendB(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_DIRECT, RK_B, product);
    }

    public void sendC(Product product){
        log.info(MSG + product);
        MessagePostProcessor processor = getMessagePostProcessor(product);
        rabbitTemplate.convertAndSend(EXG_DIRECT, RK_C, product, processor);
    }

    public void sendFanout(Product product){
        log.info(MSG + product);
        rabbitTemplate.convertAndSend(EXG_FANOUT,"", product);
    }

    public void sendFanoutPriority(Product product){
        log.info(MSG + product);
        MessagePostProcessor processor = getMessagePostProcessor(product);
        rabbitTemplate.convertAndSend(EXG_FANOUT,"", product, processor);
    }

    private MessagePostProcessor getMessagePostProcessor(Product product){
        return message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setPriority(verifyPriority(product.getPrice()));
            return message;
        };
    }

    private Integer verifyPriority(Double price){
        return price >= 10.0 ? 5 : 1;
    }
}
