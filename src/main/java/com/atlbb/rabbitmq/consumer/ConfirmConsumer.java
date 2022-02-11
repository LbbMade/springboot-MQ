package com.atlbb.rabbitmq.consumer;

import com.atlbb.rabbitmq.config.ConfirmConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName ConfirmConsumer
 * @Description TODO 接收消息
 * @time 2022/2/11 13:47
 */
@Component
public class ConfirmConsumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message){
        System.out.println(new String(message.getBody()) );
    }
}
