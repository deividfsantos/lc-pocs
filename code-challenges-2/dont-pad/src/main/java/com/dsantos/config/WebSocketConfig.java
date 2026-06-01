package com.dsantos.config;

import com.dsantos.handler.PadWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final PadWebSocketHandler padWebSocketHandler;

    @Autowired
    public WebSocketConfig(PadWebSocketHandler padWebSocketHandler) {
        this.padWebSocketHandler = padWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(padWebSocketHandler, "/ws/**").setAllowedOrigins("*");
    }
}
