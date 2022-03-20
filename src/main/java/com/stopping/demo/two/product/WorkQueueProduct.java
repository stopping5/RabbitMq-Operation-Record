package com.stopping.demo.two.product;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 验证队列接收参数生产者
 * */
public class WorkQueueProduct {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel =  connection.createChannel();
        channel.exchangeDeclare(RabbitMQConfig.EXCHANGE, BuiltinExchangeType.DIRECT);
        /**
         * 1. 声明队列名称
         * 2. 是否持久化
         * 3. 是否排查其他消费者消费队列消失
         * 4. 是否自动删除
         * 5. 队列参数
         * */
        channel.queueDeclare(RabbitMQConfig.QUEUE,false,false,false,null);
        channel.queueBind(RabbitMQConfig.QUEUE,RabbitMQConfig.EXCHANGE,"test1");
        String message = "hello work queue %s";
        for (int i = 0; i < 100; i++) {
            String result = String.format(message,i);
            /**
             * 1. 声明发布交换机名称
             * 2. 路由间
             * 3. 消息发送参数(MessageProperties.PERSISTENT_TEXT_PLAIN持久化消息数据)
             * 4. 发送数据二进制
             * */
            channel.basicPublish(RabbitMQConfig.EXCHANGE,"test1", MessageProperties.PERSISTENT_TEXT_PLAIN,result.getBytes(StandardCharsets.UTF_8));
        }
    }
}
