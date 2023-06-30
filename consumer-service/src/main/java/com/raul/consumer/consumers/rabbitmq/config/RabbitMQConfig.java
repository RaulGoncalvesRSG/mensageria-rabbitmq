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

import static constants.RabbitMQConstants.EXG_DIRECT;
import static constants.RabbitMQConstants.EXG_FANOUT;
import static constants.RabbitMQConstants.EXG_NAME_MARKETPLACE;
import static constants.RabbitMQConstants.QUEUE_A;
import static constants.RabbitMQConstants.QUEUE_B;
import static constants.RabbitMQConstants.QUEUE_PRODUCT_LOG;
import static constants.RabbitMQConstants.RK_A;
import static constants.RabbitMQConstants.RK_B;
import static constants.RabbitMQConstants.RK_PRODUCT_LOG;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queueProduct(){       //Qnd a conexão com a fila terminar, ela n irá se deletar automaticamente
        return new Queue(QUEUE_PRODUCT_LOG, false, false, false);
    }

    @Bean
    public Queue queueA(){
        return new Queue(QUEUE_A, false);
    }

    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B, false);
    }

    @Bean
    public DirectExchange directExchangeProduct(){
        return new DirectExchange(EXG_NAME_MARKETPLACE, false, false);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXG_DIRECT, false, false);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXG_FANOUT, false, false);
    }

    @Bean
    public Binding bindingProduct(){
        return BindingBuilder
                .bind(queueProduct())
                .to(directExchangeProduct())
                .with(RK_PRODUCT_LOG);
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
