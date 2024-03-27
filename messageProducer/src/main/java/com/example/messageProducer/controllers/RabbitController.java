package com.example.messageProducer.controllers;

import com.example.messageProducer.entities.CSVReader;
import com.example.messageProducer.entities.JSONCreator;
import com.example.messageProducer.rabbitmq.MessageSender;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/consumption")
public class RabbitController {

    private final MessageSender messageSender;
    private final JSONCreator jsonCreator;
    private final CSVReader csvReader;
    private int currentIndex = 0;
    private boolean shouldSendScheduledMessages = false;

    @Autowired
    public RabbitController(MessageSender messageSender, JSONCreator jsonCreator) {
        this.messageSender = messageSender;
        this.jsonCreator = jsonCreator;
        this.csvReader = new CSVReader();
    }

    @GetMapping("/start")
    public Boolean startScheduling() {
        shouldSendScheduledMessages = true;
        return true;
    }

    @GetMapping("/stop")
    public Boolean stopScheduling() {
        shouldSendScheduledMessages = false;
        return false;
    }

    @Scheduled(fixedRate = 5000)
    public void sendCSVValuesAsJSON() {
        if (shouldSendScheduledMessages) {
            List<String> stringValues = csvReader.getStringValues();

            if (!stringValues.isEmpty()) {
                if (currentIndex >= stringValues.size()) {
                    currentIndex = 0; // Reset the index if it exceeds the list size
                }

                String value = stringValues.get(currentIndex);
                currentIndex++;

                ObjectNode jsonObject = jsonCreator.createJson(value);
                messageSender.sendJSON(jsonObject);
            }
        }
    }

}
