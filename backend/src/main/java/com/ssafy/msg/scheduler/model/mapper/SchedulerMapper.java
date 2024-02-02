package com.ssafy.msg.scheduler.model.mapper;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.scheduler.model.dto.UpdateWinFlagDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface SchedulerMapper {
    List<Integer> getWaitingUsersId() throws SQLException;
    void createRoom(RoomDto roomDto) throws SQLException;

    List<String> getUnendRoom() throws SQLException;

    List<String> getUnstartRoom() throws SQLException;

    void updateFlagAvailable() throws SQLException;

    List<Integer> getNormalVoteResult(String roomId) throws SQLException;

    List<Integer> getMafiaVoteResult(String roomId) throws SQLException;

    List<Integer> getDoctorVoteResult(String roomId) throws SQLException;

    void killParticipant(int id) throws SQLException;

    void manageNonCompleter(int id) throws SQLException;

    ParticipantDto getParticipant(int id) throws  SQLException;

    int getAliveParticipant(String roomId) throws SQLException;

    int getAliveMafia(String roomId) throws SQLException;

    int isMafiaWin(String roomId) throws SQLException;

    void updateWinFlag(UpdateWinFlagDto updateWinFlagDto) throws SQLException;

    void updateEndTime(String roomId) throws SQLException;

    List<ParticipantDto> getNonCompleter(String roomId) throws SQLException;
}
