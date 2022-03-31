package com.stopping.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@Slf4j
public class AdvanceConfirmServiceImpl {
    private String ADVANCE_EXCHANGE = "advance_exchange";

    private String ADVANCE_QUEUE = "advance_queue";

    private String ADVANCE_KEY = "advance_key";

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String msg){
        CorrelationData correlationData =new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(ADVANCE_EXCHANGE,ADVANCE_KEY,msg,correlationData);
        log.info("发送消息：{}",msg);
    }

}
