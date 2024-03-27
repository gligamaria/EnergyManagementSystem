package com.example.messageProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageProducerApplication {
	public static void main(String[] args){
		SpringApplication.run(MessageProducerApplication.class, args);

	}

}
