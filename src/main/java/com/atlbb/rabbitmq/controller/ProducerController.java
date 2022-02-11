package com.atlbb.rabbitmq.controller;

import com.atlbb.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName ProducerController
 * @Description TODO
 * @time 2022/2/11 13:42
 */
@RestController
@Slf4j
@RequestMapping("/confirm")
public class ProducerController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    
    //开始发消息，测试确认，测试交换机不存在时的确认，消息会存在一个缓存中
    @GetMapping("/sendMessage/{msg}")
    public void sendMessage(@PathVariable("msg") String msg){
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY +"2" ,msg,correlationData);
        log.info("发送消息内容为：{}",msg);
    }
}
