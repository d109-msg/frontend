package com.ssafy.msg.message.interceptor;

import com.ssafy.msg.message.model.dto.StompPrincipalDto;
import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Map;

// @Order: Spring Security보다 인터셉터의 우선 순위 올리기
@Component
@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

    @Value("${header.authorization}")
    private String authorization;

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // StompHeaderAccessor를 사용해 STOMP 헤더에 접근
        // StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message); => 왜 안되는지 확인 필요
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // 웹소켓 세션 ID
        String sessionId = headerAccessor.getSessionId();
        log.info("WebSocket session ID: " + sessionId);

        Principal principal = headerAccessor.getUser();
        log.info(message.toString());
        log.info("Principal in preSend: " + principal);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())){
            String token = headerAccessor.getFirstNativeHeader(authorization);
            if(jwtUtil.verify(token, "access-token")){
                int id = jwtUtil.getId(token);
                StompPrincipalDto principalDto = StompPrincipalDto.builder()
                        .userId(id)
                        .userStringId(Integer.toString(id))
                        .build();
                headerAccessor.setUser(principalDto);
            } else{
                throw new TokenInvalidException();
            }
            log.info("Princial CONNECT: " + String.valueOf(headerAccessor.getUser()));
        }else if(StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())){
            log.info("Princial SUBSCRIBE: " + String.valueOf(headerAccessor.getUser()));
        }


        return message;
    }
}
