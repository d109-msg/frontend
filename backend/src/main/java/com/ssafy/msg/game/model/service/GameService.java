package com.ssafy.msg.game.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.*;

import java.util.List;

public interface GameService {
    void applyRandomGame(String emailId) throws Exception;

    boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    RoomDto createEnterGroupRoom(String emailId) throws Exception;

    RoomDto enterGroupRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    List<String> getRandomNicknames(int limit);

    RandomNameDto getRandomRoomName();

    List<ParticipantDto> gameStart(RoomStartReceiveDto roomStartReceiveDto);

    List<String> getJobs(int num);

    List<RoomDto> getUserRooms(String userEmail);

    List<VoteResultDto> getRoomVote(String userEmail, String roomId);

    ParticipantDto getParticipant(String userEmail, String roomId);
}

