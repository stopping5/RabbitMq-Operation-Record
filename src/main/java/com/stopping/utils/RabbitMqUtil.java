package com.stopping.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMq 工具类
 * */
public class RabbitMqUtil {
    /**
     * 获取链接
     * */
   public static Connection getConnection(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.24.242.44");
        // 默认监听端口
        connectionFactory.setPort(34566);
        connectionFactory.setUsername("stopping");
        connectionFactory.setPassword("xdp123456");
        //创建rabbitmq
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
