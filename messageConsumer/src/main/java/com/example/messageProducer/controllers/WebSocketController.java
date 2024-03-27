package com.example.messageProducer.controllers;

import com.example.messageProducer.entities.WebSocketMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello") // ca sa stie frontend-ul unde sa trimita mesajul
    @SendTo("/topic/greetings") // ca sa stie unde sa se uite dupa mesajele de pe backend
    public String greet(WebSocketMessage message) throws Exception {
        Thread.sleep(5000);
        System.out.println(message.getMessage());
        return message.getMessage();
    }

}