package com.raul.consumer.consumers.rabbitmq.consumers;

import domain.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static constants.RabbitMQConstants.EXG_DIRECT;
import static constants.RabbitMQConstants.QUEUE_DLQ;
import static constants.RabbitMQConstants.QUEUE_DLQ_PARKING_LOT;
import static constants.RabbitMQConstants.RK_C;

@AllArgsConstructor
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    private final RabbitTemplate rabbitTemplate;
    private static final String X_RETRY_HEADER = "x-dlq-retry";         //Qtd de vezes q a msg falhou no reprocessamento

    @RabbitListener(queues = QUEUE_DLQ)
    public void processar(Product message, @Headers Map<String, Object> headers){
        Integer retryHeader = (Integer) headers.get(X_RETRY_HEADER);            //Precisa ler a qtd de vezes q a msg foi processada

        if (retryHeader == null){
            retryHeader = 0;
        }

        log.info("Reprocessando venda de id " + message.getId());

        if (retryHeader < 3){
            reprocessarMensagem(message, headers, retryHeader);
        }
        else {
            log.info("Reprocessamento falhou, enviando venda de id " + message.getId() + "para a parking lot");
            rabbitTemplate.convertAndSend(QUEUE_DLQ_PARKING_LOT, message);      //Envia a msg de erro de processamento da DLQ para o Parking Lot
        }
    }

    private void reprocessarMensagem(Product message, Map<String, Object> headers, Integer retryHeader) {     //Reprocessamento da msg, reenvia a msg para a DLQ. Tenta reprocessar ela 3x e caso n funcione, descarta a msg
        Integer tryCount = retryHeader + 1;
        Map<String, Object> updatedHeaders = new HashMap<>(headers);
        updatedHeaders.put(X_RETRY_HEADER, tryCount);

        //Lógica de reprocessamento. Reenvia a msg para fila com a qtd de tentativas atualizada
        MessagePostProcessor processor = getMessagePostProcessor(updatedHeaders);
        log.info("Reenviando venda de id " + message.getId() + "para a DLQ");
        rabbitTemplate.convertAndSend(EXG_DIRECT, RK_C, message, processor);
    }

    private MessagePostProcessor getMessagePostProcessor(Map<String, Object> updatedHeaders){       //Processador para att os headers
        return message -> {
            MessageProperties properties = message.getMessageProperties();
            updatedHeaders.forEach(properties::setHeader);
            return message;
        };
    }
}
