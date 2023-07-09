package com.raul.consumer.consumers.rabbitmq.consumers;

import domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static constants.RabbitMQConstants.QUEUE_A;
import static constants.RabbitMQConstants.QUEUE_B;
import static constants.RabbitMQConstants.QUEUE_C;

@Slf4j
@Component
public class ProductConsumer {

    private static final String MSG = "Mensagem consumida pela fila ";

    @RabbitListener(queues = QUEUE_A)
    public void consumerQueueA(Product message){
        log.info(MSG + QUEUE_A + ": " + message.toString());
    }

    @RabbitListener(queues = QUEUE_B)
    public void consumerQueueB(Product message){
        log.info(MSG + QUEUE_B + ": " + message.toString());
    }

    @RabbitListener(queues = QUEUE_C)
    public void consumerQueueC(Product message){
        log.info(MSG + QUEUE_C + ": " + message.toString());

        if (message.getPrice() <= 0){
            throw new RuntimeException("O preço do produto não pode ser 0 ou negativo");
        }
    }
}
