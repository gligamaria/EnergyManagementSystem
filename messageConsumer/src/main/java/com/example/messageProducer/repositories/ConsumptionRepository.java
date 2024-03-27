package com.example.messageProducer.repositories;

import com.example.messageProducer.entities.HourlyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsumptionRepository extends JpaRepository<HourlyConsumption, UUID> {
}
