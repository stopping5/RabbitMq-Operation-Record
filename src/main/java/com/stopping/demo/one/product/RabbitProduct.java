package com.stopping.demo.one.product;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.stopping.common.RabbitMQConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 生产者代码
 * */
public class RabbitProduct {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.24.242.44");
        // 默认监听端口
        connectionFactory.setPort(34566);
        connectionFactory.setUsername("stopping");
        connectionFactory.setPassword("xdp123456");

        //创建rabbitmq
        Connection connection = connectionFactory.newConnection();
        //get channel
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(RabbitMQConfig.QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.QUEUE,RabbitMQConfig.EXCHANGE,"test1");
        //发送消息
        String message = "hello world 1";
        channel.basicPublish(RabbitMQConfig.EXCHANGE,"test1",null,message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送成功");
    }
}

