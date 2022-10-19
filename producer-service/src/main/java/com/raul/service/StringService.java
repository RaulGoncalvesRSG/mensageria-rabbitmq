package com.raul.service;

import com.raul.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.raul.config.RabbitMQConfig.EXG_NAME_MARKETPLACE;
import static com.raul.config.RabbitMQConfig.RK_PRODUCT_LOG;

@Slf4j
@Service
@RequiredArgsConstructor
public class StringService {

    private final RabbitTemplate rabbitTemplate;

    public void producer(String message){
        log.info("Mensagem recebida: " + message);
        rabbitTemplate.convertAndSend(EXG_NAME_MARKETPLACE, RK_PRODUCT_LOG, message);
    }
}
