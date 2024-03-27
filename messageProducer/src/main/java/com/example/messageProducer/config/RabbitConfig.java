package com.example.messageProducer.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue(){return new Queue("fericire", true);}

    @Bean
    DirectExchange exchange(){ return new DirectExchange("testexchange");}

    @Bean
    Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("fericire");
    }

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("");
        connectionFactory.setUsername("");
        connectionFactory.setPassword("");
        connectionFactory.setPort(5671);
        connectionFactory.setVirtualHost("axbccuwn");
        return connectionFactory;

    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("Message sent failed: " + cause);
            }
        });
        return template;
    }
}
