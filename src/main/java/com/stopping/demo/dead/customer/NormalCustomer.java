package com.stopping.demo.dead.customer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;

/**
 * 死信队列正常消费者
 * */
public class NormalCustomer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.DEAD_EXCHANGE,BuiltinExchangeType.DIRECT,false,false,null);
        channel.queueDeclare(RabbitMQConfig.DEAD_QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.DEAD_QUEUE,RabbitMQConfig.DEAD_EXCHANGE,"dead-message");

        channel.basicConsume(RabbitMQConfig.NORMAL_QUEUE,false,((consumerTag, message) -> {
            System.out.println(RabbitMQConfig.NORMAL_QUEUE+"接受消息:"+new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),true);
        }),(message)->{
            System.out.println("消费失败");
        });
    }
}
