package com.stopping.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdvanceCustomer {

    private String ADVANCE_QUEUE = "advance_queue";

    @RabbitListener(queues = "advance_queue")
    public void receive(Message message){
        String msg = new String(message.getBody());
        log.info("接收消息:{}",msg);
    }
}
