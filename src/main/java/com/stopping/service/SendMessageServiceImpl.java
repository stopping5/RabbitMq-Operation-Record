package com.stopping.service;

import com.stopping.common.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SendMessageServiceImpl {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String msg){
        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE,RabbitMQConfig.QA_RK,msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.X_EXCHANGE,RabbitMQConfig.QB_RK,msg);
        log.info("发送成功");
    }
}
