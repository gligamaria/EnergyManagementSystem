package com.example.chatBE.services;

import com.example.chatBE.entities.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessageToCommonChannel(String message) {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setMessage(message);
        webSocketMessage.setReceiver("677c503a-6615-48a8-9575-4b0d4eb8b551");
        webSocketMessage.setSender("f84c8aed-ff1a-4e6c-a08c-bbdc121f8846");
        webSocketMessage.setMessage("message");
        simpMessagingTemplate.convertAndSend("/common", webSocketMessage);
    }

    public void sendMessageToPrivate(WebSocketMessage message) {
        simpMessagingTemplate.convertAndSend("/user/c76c28dd-a1b7-43d8-94a8-ffe9ba4f6470/private", message);
    }

}
