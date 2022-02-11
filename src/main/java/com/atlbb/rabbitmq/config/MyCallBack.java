package com.atlbb.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName MyCallBack
 * @Description TODO 发布确认回调实现接口
 * @time 2022/2/11 13:59
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @PostConstruct
    public void init(){
        //注入
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    
    
    
    
    
    
    
    /**
     * 交换机确认回调方法
     * 1.发消息 交换机接收到了 回调
     * 1.1 保存回调消息的ID 以及相关消息
     * 1.2 交换机是否收到消息
     * 1.3 原因
     * 2.发消息，交换机接收消息失败
     */
    
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : null;
        if (ack) {
            log.info("交换机已经接到ID为：{}的消息" ,id);
        }else {
            log.info("交换机已经接到ID为：{}的消息,由于原因：{}" ,id,cause);
        }
    }
    //交换机回退消息，将消息不可达时，交换机把消息回退给生产者
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息{}，被交换机{}退回，退回原因：{}，路由Key：{}",
                returned.getMessage(),returned.getExchange(),returned.getReplyText(),returned.getRoutingKey());
    
    }
}
