package com.stopping.demo.comfirm.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 发布确认
 * */
public class ConfirmMessageProducer {
    private static final int PUBLISH_COUNT = 1000;

    public static void main(String[] args) throws IOException, InterruptedException {
        //simpleConfirmMessage();//消息发送耗时：14492ms
        //batchConfirmMessage();//批量确认消息发送耗时：268ms
        //asynConfirmMessage();//异步确认消息发送耗时：36ms
    }

    /**
     * 单独确认
     * */
    public static void simpleConfirmMessage() throws IOException, InterruptedException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.ACK_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明队列关闭了自动确认
        channel.queueDeclare(RabbitMQConfig.ACK_QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.ACK_QUEUE,RabbitMQConfig.ACK_EXCHANGE,"ACK");
        //开启消息确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            String message = i + "";
            channel.basicPublish(RabbitMQConfig.ACK_EXCHANGE,"ACK",null,message.getBytes(StandardCharsets.UTF_8));
            boolean flag = channel.waitForConfirms();
            if (!flag){
                System.out.println("消息发送失败");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("消息发送耗时："+(end-begin)+"ms");
    }

    /**
     * 批量发布确认
     * */
    public static void batchConfirmMessage() throws IOException, InterruptedException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.ACK_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明队列关闭了自动确认
        channel.queueDeclare(RabbitMQConfig.ACK_QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.ACK_QUEUE,RabbitMQConfig.ACK_EXCHANGE,"ACK");
        //开启消息确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        int batchSize = 100;
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            String message = i + "";
            channel.basicPublish(RabbitMQConfig.ACK_EXCHANGE,"ACK",null,message.getBytes(StandardCharsets.UTF_8));
            if (i%batchSize == 0){
                boolean flag = channel.waitForConfirms();
                if (flag){
                    System.out.println("消息发送成功");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批量确认消息发送耗时："+(end-begin)+"ms");
    }

    /**
     * 批量发布确认
     * */
    public static void asynConfirmMessage() throws IOException, InterruptedException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.ACK_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明队列关闭了自动确认
        channel.queueDeclare(RabbitMQConfig.ACK_QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.ACK_QUEUE,RabbitMQConfig.ACK_EXCHANGE,"ACK");
        //开启消息确认
        channel.confirmSelect();
        //设置异步监听回调方法
        ConfirmCallback ackCallback = (deliveryTag,multipl)->{
            System.out.println("消息发布确认成功:"+deliveryTag);
        };

        ConfirmCallback unAckCallback = (deliveryTag,multipl)->{
            System.out.println("消息发布失败:"+deliveryTag);
        };
        channel.addConfirmListener(ackCallback,unAckCallback);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            String message = i + "";
            channel.basicPublish(RabbitMQConfig.ACK_EXCHANGE,"ACK",null,message.getBytes(StandardCharsets.UTF_8));
        }
        long end = System.currentTimeMillis();
        System.out.println("异步确认消息发送耗时："+(end-begin)+"ms");
    }

}
