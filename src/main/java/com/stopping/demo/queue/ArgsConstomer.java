package com.stopping.demo.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;

/**
 * 消费者
 * */
public class ArgsConstomer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.basicConsume("priority-queue",true,((consumerTag, message) -> {
            System.out.println("消费者："+ new String(message.getBody()));
        }),((consumerTag, message) -> {

        }));
    }
}
