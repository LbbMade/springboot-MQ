package com.atlbb.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName DelayQueueConfig
 * @Description TODO
 * @time 2022/2/10 16:54
 */
@Configuration
public class DelayQueueConfig {
    //交换机名称
    public static final String DELAY_EXCHANGE_NAME="delay_Exchange";
    //队列名称
    public static final String DELAY_QUEUE_NAME="delay_Queue";
    //routingKey
    public static final String DELAY_ROUTING_KEY="delay_routing_key";
    //基于插件的交换机
    @Bean("delayExchange")
    public CustomExchange delayExchange(){
        /**
         * 1.交换机名称
         * 2.交换机类型
         * 3.是否持久化
         * 4.是否自动删除
         * 5.其他参数
         */
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-delayed-type","direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,arg);
    }
    //声明队列
    @Bean
    public Queue delayQueue(){
        return new Queue(DELAY_QUEUE_NAME);
    }
    //绑定
    @Bean
    public Binding delayedBindingDelayedExchange(@Qualifier("delayExchange") CustomExchange customExchange,
                                                 @Qualifier("delayQueue") Queue Queue){
        //noargs()构建
        return BindingBuilder.bind(Queue).to(customExchange).with(DELAY_ROUTING_KEY).noargs();
    }
    
}
