package com.ssafy.msg.message.model.service;

import com.ssafy.msg.article.util.S3Util;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.message.model.dto.*;
import com.ssafy.msg.message.model.entity.MessageEntity;
import com.ssafy.msg.message.model.mapper.MessageMapper;
import com.ssafy.msg.message.model.repo.MessageRepository;
import com.ssafy.msg.message.util.DateTimeUtil;
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
    private final DateTimeUtil dateTimeUtil;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public void sendTextMessage(TextMessageDto textMessageDto, int userId) {
        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(textMessageDto.getRoomId())
                .userId(userId)
                .flagMafia(textMessageDto.getFlagMafia())
                .dataType("chat")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content(textMessageDto.getText()).build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }

    @Override
    public void sendImageMessage(ImageMessageDto imageMessageDto, int userId) throws IOException {
        String uuid = s3Util.saveFile(imageMessageDto.getImage());
        String url = s3Util.getUrl(uuid);

        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(imageMessageDto.getRoomId())
                .userId(userId)
                .flagMafia(imageMessageDto.getFlagMafia())
                .dataType("chat")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .build();

        MessageImageDto messageImageDto = MessageImageDto.builder()
                .uuid(uuid)
                .url(url)
                .build();

        // DB 저장 로직 구현 필요
//        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
//                .id(0)
//                .roomId(imageMessageDto.getRoomId())
//                .userId(userId)
//                .sendTime("")
//                .dataType("image")
//                .url(url)
//                .uuid(uuid).build();
//        sendingOperations.convertAndSend("/sub/"+messageResponseDto.getRoomId(), messageResponseDto);
    }

    @Override
    public void sendEnterNotice(ParticipantDto participantDto){
        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(participantDto.getRoomId())
                .userId(1)
                .dataType("notice")
                .noticeType("enter")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content(participantDto.getNickname()+"님이 입장하였습니다.").build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }

    @Override
    public void sendInvitation(int userId, String chatRoomId, String gameRoomId) {
        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(chatRoomId)
                .userId(userId)
                .dataType("invite")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content("초대 코드를 확인하세요. " + gameRoomId).build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }

    @Override
    public void sendDayNotice(String dayOrNight, String roomId) throws SQLException {
        int day = messageMapper.calDay(roomId) + 1;

        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(roomId)
                .userId(1)
                .dataType("notice")
                .noticeType("day")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content(day+"일차 " + dayOrNight + "이 되었습니다.").build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }

    @Override
    public void sendStartNotice(String roomId) throws SQLException {
        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(roomId)
                .userId(1)
                .dataType("notice")
                .noticeType("game")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content("게임이 시작되었습니다.").build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }

    @Override
    public void sendEndNotice(String roomId) throws SQLException {
        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(roomId)
                .userId(1)
                .dataType("notice")
                .noticeType("game")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content("게임이 종료되었습니다.").build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }

    @Override
    public void sendGameNotice(String roomId, String text) throws SQLException {
        MessageEntity messageEntity = MessageEntity.builder()
                .roomId(roomId)
                .userId(1)
                .dataType("notice")
                .noticeType("game")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .content(text).build();

        messageRepository.save(messageEntity);

        sendingOperations.convertAndSend("/sub/"+messageEntity.toDto().getRoomId(), messageEntity.toDto());
        log.info(messageEntity.toDto().getRoomId() + " - " + messageEntity.toDto().getContent());
    }
}
