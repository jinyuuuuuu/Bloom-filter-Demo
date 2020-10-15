package com.scienjus.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanoutMode")
public class Consumer2 {
    @RabbitHandler
    public void getMessage(String msg){
        System.out.println("I'm consuming from fanout mode(fanoutMode):" +msg);
    }
}
