package com.raul.consumer.consumers.rabbitmq.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringConsumer {

    public static final String QUEUE_PRODUCT_LOG = "product.log";   //Queue

    @RabbitListener(queues = QUEUE_PRODUCT_LOG)
    public void consumer(String message){
        log.info("Mensagem consumida: " + message);
    }
}
