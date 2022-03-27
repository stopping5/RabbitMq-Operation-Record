package com.stopping.demo.queue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
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

    public static void main(String[] args) throws IOException {
        queueExpire();
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
}
