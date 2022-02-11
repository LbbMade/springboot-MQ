package com.atlbb.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName DeadLetterConsumer
 * @Description TODO 队列ttl 消费者
 * @time 2022/2/10 13:00
 */
@Slf4j
@Component
public class DeadLetterConsumer {
    //接收消息
    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("当前时间： {} ，收到的死信队列的消息：{}" ,new Date().toString(),msg);
    }
}
