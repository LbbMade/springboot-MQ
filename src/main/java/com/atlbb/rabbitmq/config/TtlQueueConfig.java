package com.atlbb.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiangQingBin
 * @version 1.00
 * @ClassName TtlQueueConfig
 * @Description TODO
 * @time 2022/2/10 12:11
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机的名称
    public static final String X_EXCHANGE = "X";
    //死信交换机的名称
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    
    //普通队列的名称
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    //死信队列的名称
    public static final String DEAD_LETTER_QUEUE = "QD";
    
    //普通队列C
    public static final String QUEUE_C = "QC";
    
    //声明队列,队列C是优化
    @Bean("queueC")
    public Queue queueC() {
        //设置持久化，名称，死信交换机名称，死信队列名称，过期时间
        return  QueueBuilder.durable(QUEUE_C).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").build();
    }
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExChange") DirectExchange xExChange){
    return BindingBuilder.bind(queueC).to(xExChange).with("XC");
    }
    
    //声明xExchange 普通交换机
    @Bean("xExChange")
    public DirectExchange xExChange() {
        return new DirectExchange(X_EXCHANGE);
    }
    
    //声明YExchange 死信交换机
    @Bean("yExChange")
    public DirectExchange yExChange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    
    //声明队列
    @Bean("queueA")
    public Queue queueA() {
        //设置持久化，名称，死信交换机名称，死信队列名称，过期时间
        return  QueueBuilder.durable(QUEUE_A).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").ttl(10000).build();
    }
    //声明队列
    @Bean("queueB")
    public Queue queueB() {
        //设置持久化，名称，死信交换机名称，死信队列名称，过期时间
        return  QueueBuilder.durable(QUEUE_B).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("YD").ttl(40000).build();
    }
    
    //死信队列
    @Bean("queueD")
    public Queue queueD(){
        return  QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    
    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExChange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    //绑定
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExChange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    //绑定死信
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExChange") DirectExchange yExChange){
        return BindingBuilder.bind(queueD).to(yExChange).with("YD");
    }
}
