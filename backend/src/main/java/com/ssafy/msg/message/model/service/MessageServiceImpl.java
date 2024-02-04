package com.ssafy.msg.message.model.service;

import com.ssafy.msg.article.util.S3Util;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.message.model.dto.ImageMessageDto;
import com.ssafy.msg.message.model.dto.MessageResponseDto;
import com.ssafy.msg.message.model.dto.TextMessageDto;
import com.ssafy.msg.message.model.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final SimpMessageSendingOperations sendingOperations;
    private final S3Util s3Util;
    private final MessageMapper messageMapper;

    @Override
    public void sendTextMessage(TextMessageDto textMessageDto, int userId) {
        // DB 저장 로직 구현 필요
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(textMessageDto.getRoomId())
                .userId(userId)
                .sendTime("")
                .dataType("text")
                .text(textMessageDto.getText()).build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
    }

    @Override
    public void sendImageMessage(ImageMessageDto imageMessageDto, int userId) throws IOException {
        String uuid = s3Util.saveFile(imageMessageDto.getImage());
        String url = s3Util.getUrl(uuid);

        // DB 저장 로직 구현 필요
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(imageMessageDto.getRoomId())
                .userId(userId)
                .sendTime("")
                .dataType("image")
                .url(url)
                .uuid(uuid).build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
    }

    @Override
    public void sendEnterNotice(ParticipantDto participantDto){
        // DB 저장 로직 구현 필요
        // Time 추가 로직 구현 필요
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(participantDto.getRoomId())
                .userId(0)
                .noticeType("en")
                .sendTime("")
                .dataType("text")
                .text(participantDto.getNickname()+"님이 입장하였습니다.").build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
    }

    @Override
    public void sendDayNotice(String dayOrNight, String roomId) throws SQLException {
        // DB 저장 로직 구현 필요
        // Time 추가 로직 구현 필요
        int day = messageMapper.calDay(roomId) + 1;
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(roomId)
                .userId(0)
                .noticeType("gameNotice")
                .sendTime("")
                .dataType("text")
                .text(day+"일차 " + dayOrNight + "이 되었습니다.").build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);

        log.info(messageResponseDto.getRoomId() + " - " + messageResponseDto.getText());
    }

    @Override
    public void sendStartNotice(String roomId) throws SQLException {
        // DB 저장 로직 구현 필요
        // Time 추가 로직 구현 필요
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(roomId)
                .userId(0)
                .noticeType("gameNotice")
                .sendTime("")
                .dataType("text")
                .text("게임이 시작되었습니다.").build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);

        log.info(messageResponseDto.getRoomId() + " - " + messageResponseDto.getText());
    }

    @Override
    public void sendEndNotice(String roomId) throws SQLException {
        // DB 저장 로직 구현 필요
        // Time 추가 로직 구현 필요
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(roomId)
                .userId(0)
                .noticeType("gameNotice")
                .sendTime("")
                .dataType("text")
                .text("게임이 종료되었습니다.").build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);

        log.info(messageResponseDto.getRoomId() + " - " + messageResponseDto.getText());
    }

    @Override
    public void sendGameNotice(String roomId, String text) throws SQLException {
        // DB 저장 로직 구현 필요
        // Time 추가 로직 구현 필요
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .id(0)
                .roomId(roomId)
                .userId(0)
                .noticeType("gameNotice")
                .sendTime("")
                .dataType("text")
                .text(text).build();
        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);

        log.info(messageResponseDto.getRoomId() + " - " + messageResponseDto.getText());
    }
}