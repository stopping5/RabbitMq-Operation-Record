package com.stopping.utils;

import com.rabbitmq.client.*;
import com.stopping.common.RabbitMQConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMqBase {
    /**
     * 创建生产者
     * */
    public void producer(String exchange, String queue, String routingKey,String type,String message, Map<String, Object> arg) throws IOException, TimeoutException {
        //创建rabbitmq
        Connection connection = RabbitMqUtil.getConnection();
        //get channel
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange,type);
        channel.queueDeclare(queue,false,false,false,arg);
        channel.queueBind(queue,exchange,routingKey);
        //发送消息
        channel.basicPublish(exchange,routingKey,null,message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送成功");
    }

    /**
     * 创建消费者
     * */
    public void customer(String queue,boolean ack, DeliverCallback deliverCallback, CancelCallback cancelCallback) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(queue,ack,deliverCallback,cancelCallback);
    }
}
