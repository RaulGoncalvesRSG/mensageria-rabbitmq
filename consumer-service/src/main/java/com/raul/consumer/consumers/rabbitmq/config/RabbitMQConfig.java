package com.raul.consumer.consumers.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static constants.RabbitMQConstants.DLX_EXG_DIRECT;
import static constants.RabbitMQConstants.EXG_DIRECT;
import static constants.RabbitMQConstants.EXG_FANOUT;
import static constants.RabbitMQConstants.QUEUE_A;
import static constants.RabbitMQConstants.QUEUE_B;
import static constants.RabbitMQConstants.QUEUE_C;
import static constants.RabbitMQConstants.QUEUE_DLQ;
import static constants.RabbitMQConstants.QUEUE_DLQ_PARKING_LOT;
import static constants.RabbitMQConstants.RK_A;
import static constants.RabbitMQConstants.RK_B;
import static constants.RabbitMQConstants.RK_C;
import static constants.RabbitMQConstants.RK_DLX;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queueA(){
        return new Queue(QUEUE_A, false);
    }

    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B, false);
    }

    @Bean
    public Queue queueC(){
        Integer priority = 10;
        Long tempo = 60000L * 3;        //Tempo em ms 60000L = 60s

        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", priority);           //Prioridade de consumo da msg
        args.put("x-message-ttl", tempo);               //time-to-live: tempo de vida da msg
        //Ligando a fila original na fila de DLQ
        args.put("x-dead-letter-exchange", DLX_EXG_DIRECT);         //Msgs com erro serão direcionadas para essa exchange
        args.put("x-dead-letter-routing-key", RK_DLX);              //Envia diretamente para fila, não passa pela exchange

        return new Queue(QUEUE_C, true, false, false, args);
    }

    @Bean
    public Queue deadLetterQueue(){
        return new Queue(QUEUE_DLQ, true);
    }

    @Bean
    public Queue parkingLotQueue(){
        return new Queue(QUEUE_DLQ_PARKING_LOT, true);     //QUEUE_DLQ_PARKING_LOT não tem bind
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXG_DIRECT, false, false);
    }

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DLX_EXG_DIRECT, true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXG_FANOUT, false, false);
    }

    @Bean
    public Binding bindingAFonout(){
        return BindingBuilder
                .bind(queueA())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingBFonout(){
        return BindingBuilder
                .bind(queueB())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingADirect(){
        return BindingBuilder
                .bind(queueA())
                .to(directExchange())
                .with(RK_A);
    }

    @Bean
    public Binding bindingBDirect(){
        return BindingBuilder
                .bind(queueB())
                .to(directExchange())
                .with(RK_B);
    }

    @Bean
    public Binding bindingCDirect(){
        return BindingBuilder
                .bind(queueC())
                .to(directExchange())
                .with(RK_C);
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(RK_DLX);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean       //Configuração para enviar o obj para a fila em formato json
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return  rabbitTemplate;
    }
}
