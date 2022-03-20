package com.stopping.demo.two.customer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;

/**
 * 验证多消息队列处理顺序
 * */
public class WorkCustomer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        System.out.println("CUSTOMER_03等待接受消息...");
        //prefetch count 消费端最大消费数量，0则是没有限制的
        //预取值相当于权重
        channel.basicQos(1);
        channel.basicConsume(RabbitMQConfig.QUEUE,true,( consumerTag, message)->{
            System.out.println("CUSTOMER_03消费消息:"+new String(message.getBody()));
        },(message)->{
            System.out.println("消费终端");
        });
    }
}
