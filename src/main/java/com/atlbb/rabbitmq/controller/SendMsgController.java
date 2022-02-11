package com.atlbb.rabbitmq.controller;

import com.atlbb.rabbitmq.config.DelayQueueConfig;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
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
 * @ClassName SendMsgController
 * @Description TODO
 * @time 2022/2/10 12:48
 */
@RestController
@Slf4j
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    //开始发消息
    @GetMapping("/sendMsg/{msg}")
    public void sendMsg(@PathVariable String msg) {
        log.info("当前时间: {},发送一条信息给两个TTL队列: {}",
                new Date().toString(), msg);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10秒" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40秒" + msg);
    }
    
    //发送可以过期消息
    @GetMapping("/sendExMsg/{msg}/{ttl}")
    public void sendMsg(@PathVariable("msg") String msg,@PathVariable("ttl") String ttl){
        log.info("当前时间: {},发送一条时长是{}毫秒的ttl信息给队列QC: {}",
                new Date().toString(),ttl, msg);
        rabbitTemplate.convertAndSend("X","XC",msg,message -> {
            message.getMessageProperties().setExpiration(ttl);
            return message;
        });
    }
    //发送基于插件的延时消息
    @GetMapping("/sendDeMsg/{msg}/{ttl}")
    public void sendMsg(@PathVariable("msg") String msg,@PathVariable("ttl") Integer ttl){
        log.info("当前时间: {},发送一条时长是{}毫秒的延迟消息信息给队列delayQueue: {}",
                new Date().toString(),ttl, msg);
        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE_NAME,DelayQueueConfig.DELAY_ROUTING_KEY,msg, message -> {
            message.getMessageProperties().setDelay(ttl);
            return message;
        });
    }
    
    //开始发消息，测试确认，测试交换机不存在时的确认，
}
