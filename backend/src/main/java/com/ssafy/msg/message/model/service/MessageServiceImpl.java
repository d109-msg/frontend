//package com.ssafy.msg.message.model.service;
//
//import com.ssafy.msg.article.util.S3Util;
//import com.ssafy.msg.game.model.dto.ParticipantDto;
//import com.ssafy.msg.message.model.dto.ImageMessageDto;
//import com.ssafy.msg.message.model.dto.MessageResponseDto;
//import com.ssafy.msg.message.model.dto.TextMessageDto;
//import com.ssafy.msg.message.model.mapper.MessageMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MessageServiceImpl implements MessageService{
//
//    private final SimpMessageSendingOperations sendingOperations;
//    private final S3Util s3Util;
//    private final MessageMapper messageMapper;
//
//    @Override
//    public void sendTextMessage(TextMessageDto textMessageDto, String emailId) {
//        // DB 저장 로직 구현 필요
//        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
//                .id(0)
//                .roomId(textMessageDto.getRoomId())
//                .userEmailId(emailId)
//                .sendTime(textMessageDto.getSendTime())
//                .dataType("text")
//                .text(textMessageDto.getText()).build();
//        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
//    }
//
//    @Override
//    public void sendImageMessage(ImageMessageDto imageMessageDto, String emailId) throws IOException {
//        String uuid = s3Util.saveFile(imageMessageDto.getImage());
//        String url = s3Util.getUrl(uuid);
//
//        // DB 저장 로직 구현 필요
//        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
//                .id(0)
//                .roomId(imageMessageDto.getRoomId())
//                .userEmailId(emailId)
//                .sendTime(imageMessageDto.getSendTime())
//                .dataType("image")
//                .url(url)
//                .uuid(uuid).build();
//        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
//    }
//
//    @Override
//    public void sendEnterNotice(ParticipantDto participantDto){
//        // DB 저장 로직 구현 필요
//        // Time 추가 로직 구현 필요
//        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
//                .id(0)
//                .roomId(participantDto.getRoomId())
//                .userEmailId("enterNotice")
//                .sendTime("")
//                .dataType("text")
//                .text(participantDto.getNickname()+"님이 입장하였습니다.").build();
//        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
//    }
//
//    @Override
//    public void sendDayNotice(String dayOrNight, String roomId) throws SQLException {
//        // DB 저장 로직 구현 필요
//        // Time 추가 로직 구현 필요
//        int day = messageMapper.calDay(roomId) + 1;
//        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
//                .id(0)
//                .roomId(roomId)
//                .userEmailId("dayNotice")
//                .sendTime("")
//                .dataType("text")
//                .text(day+"일차 " + dayOrNight + "이 되었습니다.").build();
//        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
//    }
//}
