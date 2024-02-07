package com.ssafy.msg.message.controller;

import com.ssafy.msg.message.model.dto.MessageRequestDto;
import com.ssafy.msg.message.model.dto.StompPrincipalDto;
import com.ssafy.msg.message.model.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/message")
    public void sendMessage(MessageRequestDto messageRequestDto, Principal principal){
        if (principal instanceof StompPrincipalDto) {
            StompPrincipalDto stompPrincipalDto = (StompPrincipalDto) principal;
            int userId = stompPrincipalDto.getUserId();

            try {
                messageService.sendMessage(messageRequestDto, userId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}