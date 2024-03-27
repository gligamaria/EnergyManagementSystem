package com.example.messageProducer.config;

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
        // /ws the websocket endpoint
        // sockJS is used to enable fallback options for browsers that don't support websocket
        registry.addEndpoint ("/ws").setAllowedOrigins ("http://localhost:4200").withSockJS();
    }

    @Override
    public void configureMessageBroker (MessageBrokerRegistry registry) {
        // enable simple message broker that will send messages
        // to ALL clients that are subscribed to this "/topic"
        registry.enableSimpleBroker("/topic");
        // same but it is a one-to-one communication
        registry.setApplicationDestinationPrefixes("/app");
    }
}
