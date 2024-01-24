package com.ssafy.msg.game.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.*;

import java.util.List;

public interface GameService {
    void applyRandomGame(int userId) throws Exception;

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
}

