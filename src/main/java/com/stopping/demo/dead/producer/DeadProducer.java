package com.stopping.demo.dead.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqBase;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列
 * */
public class DeadProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMqBase rabbitMqBase = new RabbitMqBase();
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-dead-letter-exchange",RabbitMQConfig.DEAD_EXCHANGE);
        arg.put("x-dead-letter-routing-key","dead-message");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().expiration("10000").build();
        rabbitMqBase.producer(
                RabbitMQConfig.NORMAL_EXCHANGE,
                RabbitMQConfig.NORMAL_QUEUE,
                "normal",
                BuiltinExchangeType.DIRECT.getType(),
                "测试超时死信队列",
                arg,10,basicProperties);
    }
}
