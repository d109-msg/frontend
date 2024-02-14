package com.ssafy.msg.message.model.mapper;

import com.ssafy.msg.chat.model.dto.CreateRoomDto;
import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.message.model.dto.LastMessageUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface MessageMapper {
    int calDay(String roomId) throws SQLException;
    void updateLastMessageId(LastMessageUpdateDto lastMessageUpdateDto) throws SQLException;
}
