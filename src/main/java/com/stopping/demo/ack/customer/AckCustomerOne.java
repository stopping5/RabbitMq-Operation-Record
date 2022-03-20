package com.stopping.demo.ack.customer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;

public class AckCustomerOne {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitMQConfig.ACK_QUEUE,false,( consumerTag, message)->{
                    System.out.println("消费消息:"+new String(message.getBody()));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                },(message)->{
                    System.out.println("消费终端");
                }
        );
    }
}
