package com.raul.consumer.consumers.rabbitmq.consumers;

import domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static constants.RabbitMQConstants.QUEUE_A;
import static constants.RabbitMQConstants.QUEUE_B;
import static constants.RabbitMQConstants.QUEUE_PRODUCT_LOG;

@Slf4j
@Component
public class ProductConsumer {

    private static final String MSG = "Mensagem consumida pela fila ";

    @RabbitListener(queues = QUEUE_PRODUCT_LOG)
    public void consumer(Product message){
        log.info(MSG + QUEUE_PRODUCT_LOG + ": " + message.toString());
    }

    @RabbitListener(queues = QUEUE_A)
    public void consumerQueueA(Product message){
        log.info(MSG + QUEUE_A + ": " + message.toString());
    }

    @RabbitListener(queues = QUEUE_B)
    public void consumerQueueB(Product message){
        log.info(MSG + QUEUE_B + ": " + message.toString());
    }
}
