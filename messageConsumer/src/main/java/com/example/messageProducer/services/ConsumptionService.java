package com.example.messageProducer.services;

import com.example.messageProducer.entities.HourlyConsumption;
import com.example.messageProducer.repositories.ConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionService.class);
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    public void insert(HourlyConsumption consumption) {
        HourlyConsumption newConsumption = consumptionRepository.save(consumption);
        LOGGER.debug("New hourly consumption for device with id {} was inserted in db", newConsumption.getDeviceID());
    }

    public List<HourlyConsumption> getAll() {
        return this.consumptionRepository.findAll();
    }
}
