package com.stopping.config;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvanceConfirmConfig {

    private String ADVANCE_EXCHANGE = "advance_exchange";

    private String ADVANCE_QUEUE = "advance_queue";

    private String ADVANCE_KEY = "advance_key";

    @Bean
    public DirectExchange adviceExchange(){
        return new DirectExchange(ADVANCE_EXCHANGE);
    }

    @Bean
    public Queue advanceQueue(){
        return QueueBuilder.durable(ADVANCE_QUEUE).build();
    }

    @Bean
    public Binding advanceBinding(DirectExchange adviceExchange, Queue advanceQueue){
        return BindingBuilder.bind(advanceQueue).to(adviceExchange).with(ADVANCE_KEY);
    }


}
