package com.scienjus.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "T1")
public class Consumer1 {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("I'm consuming fron direct mode:" +msg);
    }
}
