package com.stopping.demo.one.customer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.stopping.common.RabbitMQConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 消费者
 * */
public class RabbitCustomer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.24.242.44");
        // 默认监听端口
        connectionFactory.setPort(34566);
        connectionFactory.setUsername("stopping");
        connectionFactory.setPassword("xdp123456");

        //创建rabbitmq
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.basicConsume(RabbitMQConfig.QUEUE,true,( consumerTag, message)->{
            System.out.println("消费消息:"+new String(message.getBody()));
        },(message)->{
            System.out.println("消费终端");
        });
    }
}
