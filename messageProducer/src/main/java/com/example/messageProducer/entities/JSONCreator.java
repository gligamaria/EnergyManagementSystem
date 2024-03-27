package com.example.messageProducer.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

@Component
public class JSONCreator {

    private final ObjectMapper objectMapper;
    private final String deviceID = "4564b765-c6bd-4bec-857c-01269c36e3aa";

    public JSONCreator() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectNode createJson(String value) {
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("value", value);
        jsonObject.put("deviceID", deviceID);
        jsonObject.put("currentTime", System.currentTimeMillis());
        return jsonObject;
    }
}
