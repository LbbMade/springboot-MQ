package com.atlbb.rabbitmq.consumer;

import com.atlbb.rabbitmq.config.DelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName DelayQueueConsumer
 * @Description TODO 基于插件的延迟消费
 * @time 2022/2/10 17:43
 */
@Component
@Slf4j
public class DelayQueueConsumer {
    //监听消息
    @RabbitListener(queues = DelayQueueConfig.DELAY_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间： {} ，收到的延迟交换机 队列的消息：{}" ,new Date().toString(),msg);
    }
}
