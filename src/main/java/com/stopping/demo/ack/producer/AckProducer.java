package com.stopping.demo.ack.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 消息确认生产者
 * */
public class AckProducer {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.ACK_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明队列关闭了自动确认
        channel.queueDeclare(RabbitMQConfig.ACK_QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.ACK_QUEUE,RabbitMQConfig.ACK_EXCHANGE,"ACK");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            channel.basicPublish(RabbitMQConfig.ACK_EXCHANGE,"ACK",null, scanner.next().getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息:"+scanner.next());
        }

    }
}
