package com.example.chatBE.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry) {
        registry.addEndpoint ("/ws").setAllowedOrigins ("http://localhost:4200").withSockJS();
    }

    @Override
    public void configureMessageBroker (MessageBrokerRegistry registry) {
        // same but it is a one-to-one communication
        registry.setApplicationDestinationPrefixes("/app");

        // enable simple message broker that will send messages
        // to ALL clients that are subscribed to this "/topic"
        registry.enableSimpleBroker("/common", "/user");

        registry.setUserDestinationPrefix("/user");
    }
}
