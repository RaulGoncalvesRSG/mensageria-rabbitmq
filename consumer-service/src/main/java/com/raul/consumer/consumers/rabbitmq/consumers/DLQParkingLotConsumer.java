package com.raul.consumer.consumers.rabbitmq.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static constants.RabbitMQConstants.QUEUE_DLQ_PARKING_LOT;

@Slf4j
@Component
public class DLQParkingLotConsumer {

    @RabbitListener(queues = QUEUE_DLQ_PARKING_LOT)
    public void processar(){
        log.info("Mensagem recebida do parking lot queue");
        // Salva no DB ou envia uma notificação
    }
}
