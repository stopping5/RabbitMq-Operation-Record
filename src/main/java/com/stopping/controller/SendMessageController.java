package com.stopping.controller;

import com.stopping.service.AdvanceConfirmServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/send")
public class SendMessageController {

    @Resource
    private AdvanceConfirmServiceImpl advanceConfirmService;

    @GetMapping("/advance/{msg}")
    public String sendAdvanceMsg(@PathVariable("msg")String msg){
        advanceConfirmService.send(msg);
        return "发送完成";
    }
}
