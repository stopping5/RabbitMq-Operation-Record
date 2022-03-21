package com.stopping.service.customer;

import com.stopping.common.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadQueueCustomer {

    @RabbitListener(queues = "QD")
    public void receive(Message message){
        String msg = new String(message.getBody());
        log.info("接收消息:{}",msg);
    }
}
