//package com.ssafy.msg.config;
//
//import com.ssafy.msg.message.interceptor.FilterChannelInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSocketMessageBroker
//public class MessageConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final FilterChannelInterceptor filterChannelInterceptor;
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // stomp 접속 url -> /ws-stomp
//        registry.addEndpoint("ws-stomp")//연결될 엔드포인트
//                .withSockJS(); //SocketJS를 연결한다는 설정
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // 메시지를 구독하는 요청 url -> 메시지를 받을 때
//        registry.enableSimpleBroker("/sub");
//        // 메시지를 발행하는 요청 url -> 메시지를 보낼 때
//        registry.setApplicationDestinationPrefixes("/pub");
//    }
//
//    // STOMP 연결 시도 시 호출되는 메소드
//    // -> 연결을 시도하면 FilterChannelInterceptor 실행
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration){
//        registration.interceptors(filterChannelInterceptor);
//    }
//}
