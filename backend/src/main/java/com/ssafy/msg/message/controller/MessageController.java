//package com.ssafy.msg.message.controller;
//
//import com.ssafy.msg.message.model.dto.ImageMessageDto;
//import com.ssafy.msg.message.model.dto.StompPrincipalDto;
//import com.ssafy.msg.message.model.dto.TextMessageDto;
//import com.ssafy.msg.message.model.service.MessageService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.security.Principal;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class MessageController {
//
//    private final MessageService messageService;
//
//    // 텍스트 메시지 전송
//    @MessageMapping("/message/text")
//    public void sendMessage(TextMessageDto textMessageDto, Principal principal){
//        if (principal instanceof StompPrincipalDto) {
//            StompPrincipalDto stompPrincipalDto = (StompPrincipalDto) principal;
//            int userId = stompPrincipalDto.getUserId();
//
//            messageService.sendTextMessage(textMessageDto, userId);
//        }
//    }
//
//    // 이미지 메시지 전송
//    @MessageMapping("/message/image")
//    public void sendImageMessage(ImageMessageDto imageMessageDto, Principal principal) throws IOException {
//        if (principal instanceof StompPrincipalDto) {
//            StompPrincipalDto stompPrincipalDto = (StompPrincipalDto) principal;
//            int userId = stompPrincipalDto.getUserId();
//
//            messageService.sendImageMessage(imageMessageDto, userId);
//        }
//    }
//
//}