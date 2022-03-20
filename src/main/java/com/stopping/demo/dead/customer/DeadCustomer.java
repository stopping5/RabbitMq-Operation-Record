package com.stopping.demo.dead.customer;

import com.stopping.common.RabbitMQConfig;
import com.stopping.utils.RabbitMqBase;

import java.io.IOException;

/**
 * 死信队列
 * */
public class DeadCustomer {
    public static void main(String[] args) throws IOException {
        RabbitMqBase rabbitMqBase = new RabbitMqBase();
        rabbitMqBase.customer(RabbitMQConfig.DEAD_QUEUE,true,((consumerTag, message) -> {
            System.out.println("死信队列处理消息:"+new String(message.getBody()));
        }),(consumerTag -> {}));
    }
}
