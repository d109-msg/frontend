package com.ssafy.msg.game.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.*;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

public interface GameService {
    boolean getRandomGameApplyStatus(int userId) throws Exception;

    boolean applyRandomGame(int userId) throws Exception;

    boolean cancelRandomGame(int userId) throws Exception;

    boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    RoomDto createEnterGroupRoom(int userId) throws Exception;

    RoomDto enterGroupRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    List<String> getRandomNicknames(int limit) throws Exception;

    RandomNameDto getRandomRoomName() throws Exception;

    List<ParticipantDto> gameStart(RoomStartReceiveDto roomStartReceiveDto) throws Exception;

    List<String> getJobs(int num);

    List<RoomDto> getUserRooms(int userId) throws Exception;

    List<VoteResultDto> getRoomVote(int userId, String roomId) throws Exception;

    ParticipantDto getParticipant(int userId, String roomId) throws Exception;

    List<ParticipantDto> getAliveParticipant(String roomId) throws Exception;

    void vote(VoteReceiveDto voteReceiveDto) throws Exception;

    boolean getTime();

    String getMyVote(int participantId) throws Exception;

    void createNewMission(String roomId, int day) throws Exception;
}

