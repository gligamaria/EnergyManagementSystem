package com.example.messageProducer.controllers;

import com.example.messageProducer.entities.HourlyConsumption;
import com.example.messageProducer.repositories.ConsumptionRepository;
import com.example.messageProducer.services.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/hourlyConsumption")
public class HourlyConsumptionController {

    private final ConsumptionService consumptionService;
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public HourlyConsumptionController(ConsumptionService consumptionService, ConsumptionRepository consumptionRepository) {
        this.consumptionService = consumptionService;
        this.consumptionRepository = consumptionRepository;
    }

    @GetMapping( value = "/{id}")
    @ResponseBody
    public List<Float> getDevices(@PathVariable("id") String deviceId) {
        List<HourlyConsumption> allConsumptions = consumptionService.getAll();
        List<Float> deviceConsumption = new ArrayList<>();
        for(HourlyConsumption consumption:allConsumptions){
            if(consumption.getDeviceID().toString().equals(deviceId))
                deviceConsumption.add(consumption.getValue());
        }
        return deviceConsumption;
    }
}
