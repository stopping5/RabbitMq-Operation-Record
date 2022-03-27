package com.stopping.demo.queue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqUtil;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 队列参数操作
 * */

public class ArgDemo {

    private static final String EXPIRE_QUEUE = "expire-queue";

    private static final String EXPIRE_EXCHANGE = "expire-exchange";

    private static final String LIMIT_QUEUE = "limit-queue";

    private static final String LIMIT_EXCHANGE = "limit-exchange";

    public static void main(String[] args) throws IOException {
        queueLimit();
    }

    /**
     * 队列设置expire属性，设置时间段内没有消费者链接则删除队列
     * 即使队列是持久化的队列
     * */
    public static void queueExpire() throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-expires", 1000 * 20);
        channel.queueDeclare(EXPIRE_QUEUE,true,false,false,args);
        channel.exchangeDeclare(EXPIRE_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueBind(EXPIRE_QUEUE,EXPIRE_EXCHANGE,"ex-10");
        channel.basicPublish(EXPIRE_EXCHANGE,"ex-10",null,"hello".getBytes(StandardCharsets.UTF_8));
        System.out.println("hello 已发送");
    }

    /**
     * 队列消息长度/大小限制
     * */
    public static void queueLimit() throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> args = new HashMap<String, Object>();
        //队列字节容量最大值 11B
        //常规11B，若绑定死信队列2我是咋写2则将接收失败的消息发送到死信队列
        args.put("x-max-length-bytes", 11);
        args.put("x-dead-letter-exchange", RabbitMQConfig.DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key", "dead-message");
        channel.queueDeclare(LIMIT_QUEUE,true,false,false,args);
        channel.exchangeDeclare(LIMIT_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueBind(LIMIT_QUEUE,LIMIT_EXCHANGE,"limit");
        StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("1");
        for (int i = 0; i < 10; i++) {
            String msg = i + "";
            channel.basicPublish(LIMIT_EXCHANGE,"limit",null,msg.toString().getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("hello 已发送");
    }
}
