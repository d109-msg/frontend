package com.ssafy.msg.scheduler.model.mapper;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.scheduler.model.dto.JudgeResultDto;
import com.ssafy.msg.scheduler.model.dto.RandomMafiaDto;
import com.ssafy.msg.scheduler.model.dto.RoomIdTitleDto;
import com.ssafy.msg.scheduler.model.dto.UpdateWinFlagDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface SchedulerMapper {

    int getFlagNight() throws SQLException;
    void updateStaticFlagNight(int flagNight) throws SQLException;
    void updateFlagNight(String roomId, int flagNight) throws SQLException;
    List<Integer> getWaitingUsersId() throws SQLException;
    void createRoom(RoomDto roomDto) throws SQLException;

    List<String> getUnendRoom() throws SQLException;

    List<RoomIdTitleDto> getUnstartRoom() throws SQLException;

    void updateFlagAvailable() throws SQLException;

    JudgeResultDto getJudgeAbility(String roomId, Integer day) throws SQLException;

    Integer getGangsterVoteResult(int participantId) throws SQLException;

    Integer getReporterVoteResult(String roomId, int day) throws SQLException;

    List<Integer> getNormalVoteResult(String roomId) throws SQLException;

    List<Integer> getMafiaVoteResult(String roomId) throws SQLException;

    List<Integer> getDoctorVoteResult(String roomId) throws SQLException;

    void killParticipant(int id) throws SQLException;

    ParticipantDto getMyNormalVoteId(int participantId, int day) throws SQLException;

    void manageNonCompleter(int id) throws SQLException;

    ParticipantDto getParticipant(int id) throws  SQLException;

    int getAliveParticipant(String roomId) throws SQLException;

    int getAliveMafia(String roomId) throws SQLException;

    int isMafiaWin(String roomId) throws SQLException;

    void updateWinFlag(UpdateWinFlagDto updateWinFlagDto) throws SQLException;

    void updateEndTime(String roomId) throws SQLException;

    List<ParticipantDto> getNonCompleter(String roomId) throws SQLException;

    RandomMafiaDto getOneRandomMafia(String roomId) throws SQLException;

    void updateNickname(String roomId) throws SQLException;
}
