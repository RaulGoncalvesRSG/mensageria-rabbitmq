package com.raul.consumer.consumers.rabbitmq.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringConsumer {

    @RabbitListener(queues = "product.log")
    public void consumer(String message){
        log.info("Mensagem consumida: " + message);
    }
}
