package com.raul.consumer.consumers.rabbitmq.consumers;

import domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static constants.RabbitMQConstants.RK_PRODUCT_LOG;

@Slf4j
@Component
public class ProductConsumer {

    @RabbitListener(queues = RK_PRODUCT_LOG)
    public void consumer(Product message){
        //Faz regra de negócio com obj recebido
        log.info("Mensagem consumida: " + message.toString());
    }
}
