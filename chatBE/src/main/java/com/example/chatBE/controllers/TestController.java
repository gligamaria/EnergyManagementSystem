package com.example.chatBE.controllers;

import com.example.chatBE.entities.WebSocketMessage;
import com.example.chatBE.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping(value = "/sendMessage")
    public void insertPerson() {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setMessage("iti raspund");
        webSocketMessage.setSender("f84c8aed-ff1a-4e6c-a08c-bbdc121f8846");
        webSocketMessage.setReceiver("c76c28dd-a1b7-43d8-94a8-ffe9ba4f6470");
        webSocketMessage.setType("message");
        //webSocketMessage.setCurrentTime(System.currentTimeMillis());
        testService.sendMessageToPrivate(webSocketMessage);
    }

}
