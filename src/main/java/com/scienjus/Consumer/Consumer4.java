package com.scienjus.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topicMode1")
public class Consumer4 {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("I'm consuming from topic mode:" +msg);
    }
}
