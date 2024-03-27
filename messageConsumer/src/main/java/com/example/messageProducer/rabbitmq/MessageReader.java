package com.example.messageProducer.rabbitmq;

import com.example.messageProducer.controllers.WebSocketController;
import com.example.messageProducer.entities.*;
import com.example.messageProducer.services.ConsumptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Component
public class MessageReader {

    private final ObjectMapper objectMapper;
    private final ConsumptionService consumptionService;
    private Integer i = 1;
    private Float currentValue = 0.0f;
    private MessageSender messageSender;
    private Integer maximumConsumption;
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    public MessageReader(ConsumptionService consumptionService, SimpMessagingTemplate messagingTemplate, MessageSender messageSender) {
        this.objectMapper = new ObjectMapper();
        this.consumptionService = consumptionService;
        this.messageSender = messageSender;
        this.maximumConsumption = 0;
        this.simpMessagingTemplate = messagingTemplate;

    }

    @RabbitListener(queues = "fericire2")
    public void listenBack(Integer maximumConsumption){
        this.maximumConsumption = maximumConsumption;
    }

    @RabbitListener(queues = "fericire")
    public void listen(String message){
        try {
            Measurement measurement = objectMapper.readValue(message, Measurement.class);

            currentValue += measurement.getValue();
            System.out.println(measurement.getValue());
            System.out.println(i);

            if(i % 6 == 0){
                System.out.println(currentValue);
                currentValue = currentValue/6;
                HourlyConsumption hourlyConsumption = new HourlyConsumption(currentValue, measurement.getDeviceID());
                System.out.println("hourly cons: " +  hourlyConsumption.getValue());
                consumptionService.insert(hourlyConsumption);

                messageSender.sendMessage(measurement.getDeviceID().toString());

                WebSocketMessage webSocketMessage = new WebSocketMessage();
                webSocketMessage.setMessage(measurement.getDeviceID().toString());

                if(currentValue > this.maximumConsumption){
                    System.out.println("pericol pericol: " + currentValue);
                }
                else{
                    webSocketMessage.setMessage("none");
                }

                simpMessagingTemplate.convertAndSend("/topic/greetings", webSocketMessage.getMessage());
                currentValue = 0.0f;
            }
            i++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
