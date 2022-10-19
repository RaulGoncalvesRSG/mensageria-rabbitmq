package com.raul.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXG_NAME_MARKETPLACE = "marketplace.direct";     //Exchange
    public static final String QUEUE_PRODUCT_LOG = "product.log";   //Queue
    public static final String RK_PRODUCT_LOG = "product.log";      //Routing key

    @Bean
    public Queue queue(){
        //Qnd a conexão com a fila terminar, ela n irá se deletar automaticamente
        return new Queue(QUEUE_PRODUCT_LOG, false, false, false);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXG_NAME_MARKETPLACE, false, false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(directExchange())
                .with(RK_PRODUCT_LOG);
    }
}
