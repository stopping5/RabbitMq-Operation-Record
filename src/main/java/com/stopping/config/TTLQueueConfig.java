package com.stopping.config;

import com.stopping.common.RabbitMQConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 延时队列配置
 * */
@Configuration
public class TTLQueueConfig {

    @Bean
    public DirectExchange xExchange(){
        return new DirectExchange(RabbitMQConfig.X_EXCHANGE);
    }

    @Bean
    public DirectExchange yExchange(){
        return new DirectExchange(RabbitMQConfig.Y_EXCHANGE);
    }


    @Bean
    public Queue queueA(){
        return QueueBuilder
                .durable(RabbitMQConfig.QA_QUEUE)
                .ttl(10000)
                .deadLetterExchange(RabbitMQConfig.Y_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQConfig.QD_RK)
                .build();
    }

    @Bean
    public Queue queueB(){
        return QueueBuilder
                .durable(RabbitMQConfig.QB_QUEUE)
                .ttl(2000)
                .deadLetterExchange(RabbitMQConfig.Y_EXCHANGE)
                .deadLetterRoutingKey(RabbitMQConfig.QD_RK)
                .build();
    }

    @Bean
    public Queue queueD(){
        return QueueBuilder
                .durable(RabbitMQConfig.QD_QUEUE)
                .build();
    }

    @Bean
    public Binding queueABindingX(Queue queueA,DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with(RabbitMQConfig.QA_RK);
    }

    @Bean
    public Binding queueBBindingX(Queue queueB, DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with(RabbitMQConfig.QB_RK);
    }

    @Bean
    public Binding queueDBindingY(Queue queueD, DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with(RabbitMQConfig.QD_RK);
    }
}
