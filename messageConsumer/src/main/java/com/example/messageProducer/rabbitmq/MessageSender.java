package com.example.messageProducer.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String deviceId) {
        System.out.println(deviceId);
        rabbitTemplate.convertAndSend("fericire2", deviceId);
        System.out.println(deviceId);
    }


}
