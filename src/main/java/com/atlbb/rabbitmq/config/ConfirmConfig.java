package com.atlbb.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName ConfirmConfig
 * @Description TODO 高级内容，发布确认 交换机确认
 * @time 2022/2/11 13:34
 */
@Configuration
public class ConfirmConfig {
    //交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    //队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    //routingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";
    
    //备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    //报警队列
    public static final String WARNING_QUEUE_NAME = "warning_queue";
    
    //这里是声明备份交换机和原有交换机的绑定
    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }
    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }
    //绑定
    @Bean
    public Binding queueBindExchange(@Qualifier("confirmExchange")DirectExchange exchange,@Qualifier("confirmQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }
    
    //备份交换机
    @Bean
    public FanoutExchange backUpExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }
    //备份队列
    @Bean
    public Queue backUpQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }
    //报警队列
    @Bean
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }
    //绑定
    @Bean
    public Binding backUpQueueBindBackUpExchange(@Qualifier("backUpExchange")FanoutExchange exchange,@Qualifier("backUpQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean
    public Binding warningQueueBindBackUpExchange(@Qualifier("backUpExchange")FanoutExchange exchange,@Qualifier("warningQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
