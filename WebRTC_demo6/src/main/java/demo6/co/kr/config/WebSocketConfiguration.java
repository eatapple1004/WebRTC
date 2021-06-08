package demo6.co.kr.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.WebSocketHandlerMapping;

import org.springframework.web.socket.config.annotation.DelegatingWebSocketConfiguration;

import demo6.co.kr.handler.SocketHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{
	
	List<ServletWebSocketHandlerRegistration>registrations = new CopyOnWriteArrayList<>(); 
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println("demo6 Wbesocket is working");
		registry.addHandler(new SocketHandler(), "/socket").setAllowedOrigins("*");
	}
	
	
	@Bean
    public WebSocketHandler webSocketHandler(){
        return new SocketHandler();
    }
	
}
