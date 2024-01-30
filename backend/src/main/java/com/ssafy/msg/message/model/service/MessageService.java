package com.ssafy.msg.message.model.service;

import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.message.model.dto.ImageMessageDto;
import com.ssafy.msg.message.model.dto.TextMessageDto;

import java.io.IOException;
import java.sql.SQLException;

public interface MessageService {

    void sendTextMessage(TextMessageDto textMessageDto, int userId);

    void sendImageMessage(ImageMessageDto imageMessageDto, int userId) throws IOException;

    // 사용자 설정 게임 입장 메시지 전송
    void sendEnterNotice(ParticipantDto participantDto);

    // 게임
    void sendDayNotice(String time, String roomId) throws SQLException;
    void sendStartNotice(String roomId) throws  SQLException;
    void sendEndNotice(String roomId) throws  SQLException;
}
