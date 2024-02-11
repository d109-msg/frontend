package com.ssafy.msg.message.model.service;

import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.message.model.dto.LastMessageUpdateDto;
import com.ssafy.msg.message.model.dto.MessageRequestDto;

import java.io.IOException;
import java.sql.SQLException;

public interface MessageService {

    void sendMessage(MessageRequestDto messageRequestDto, int userId) throws IOException;

    // 사용자 설정 게임 입장 메시지 전송
    void sendEnterNotice(ParticipantDto participantDto);
    // 사용자 설정 게임 초대 메시지 전송
    void sendInvitation(int userId, String chatRoomId, String gameRoomId);

    // 게임 공지 메시지
    void sendDayNotice(String time, String roomId) throws SQLException;
    void sendStartNotice(String roomId) throws  SQLException;
    void sendEndNotice(String roomId) throws  SQLException;
    void sendGameNotice(String room_id, String text) throws  SQLException;

    void updateLastMessageId(LastMessageUpdateDto lastMessageUpdateDto) throws SQLException;

}
