package com.atlbb.rabbitmq.consumer;

import com.atlbb.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName WarningConsumer
 * @Description TODO
 * @time 2022/2/11 14:55
 */
@Component
@Slf4j
public class WarningConsumer {
    //接收报警消息
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMessage(Message message){
        String msg = new String(message.getBody());
        log.warn("报警发现不可路由消息{}" + msg);
    }
}
