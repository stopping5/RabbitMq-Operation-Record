package com.stopping.controller;

import com.stopping.service.SendMessageServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/send")
public class SendMessageController {

    @Resource
    private SendMessageServiceImpl sendMessageService;

    @GetMapping("/{msg}")
    public String sendMsg(@PathVariable("msg")String msg){
        sendMessageService.send(msg);
        return "发送完成";
    }
}
