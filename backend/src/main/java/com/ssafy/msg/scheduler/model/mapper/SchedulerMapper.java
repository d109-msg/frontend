package com.ssafy.msg.scheduler.model.mapper;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface SchedulerMapper {
    List<Integer> getWaitingUsersId() throws SQLException;
    void createRoom(RoomDto roomDto) throws SQLException;

    List<String> get

}
