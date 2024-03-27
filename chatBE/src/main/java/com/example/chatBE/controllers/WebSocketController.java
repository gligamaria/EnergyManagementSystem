package com.example.chatBE.controllers;

import com.example.chatBE.entities.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // /app/message
    @MessageMapping("/message") // ca sa stie frontend-ul unde sa trimita mesajul
    @SendTo("/common") // ca sa stie unde sa se uite dupa mesajele de pe backend
    public WebSocketMessage receivePublicMessage(WebSocketMessage message) {
        System.out.println(message.getMessage());
        return message;
    }

    // /user/David/private
    @MessageMapping("/private-message") // ca sa stie frontend-ul unde sa trimita mesajul
    public WebSocketMessage receivePrivateMessage(WebSocketMessage message) {
        if(message.getSender().equals("00000000-0000-0000-0000-000000000000")){
            simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message);
        }
        else {
            simpMessagingTemplate.convertAndSendToUser(message.getSender(), "/private", message);
        }
        System.out.println("in privat: " + message.getMessage() + message.getSender());
        return message;
    }
}