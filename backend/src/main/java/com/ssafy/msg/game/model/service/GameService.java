package com.ssafy.msg.game.model.service;

import com.ssafy.msg.chat.model.dto.CreateRoomDto;
import com.ssafy.msg.chat.model.dto.OpponentDto;
import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GameService {

    List<ParticipantDto> getParticipants(String roomId) throws Exception;

    boolean getRandomGameApplyStatus(int userId) throws Exception;

    boolean applyRandomGame(int userId) throws Exception;

    boolean cancelRandomGame(int userId) throws Exception;

    boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    RoomDto createEnterGroupRoom(int userId) throws Exception;

    RoomDto enterGroupRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    void inviteGroupRoom(int userId, EnterGroupRoomDto enterGroupRoomDto) throws Exception;

    List<String> getRandomNicknames(int limit) throws Exception;

    RandomNameDto getRandomRoomName() throws Exception;

    List<ParticipantDto> randomGameStart(RoomStartReceiveDto roomStartReceiveDto) throws Exception;

    List<String> getJobs(int num);

    List<RoomDto> getUserRooms(int userId) throws Exception;

    List<VoteResponseDto> getRoomVote(int userId, String roomId) throws Exception;

    ParticipantDto getParticipant(int userId, String roomId) throws Exception;

    List<AliveParticipantDto> getAliveParticipant(String roomId) throws Exception;

    String vote(VoteReceiveDto voteReceiveDto) throws Exception;

    boolean getTime(int start, int end);

    String getMyVote(int participantId) throws Exception;

    void createNewMission(String roomId, int day) throws Exception;

    MissionResultDto getMyMission(int participantId) throws Exception;

    AiResultDto analyzeImage(MultipartFile imageFile, String condition) throws Exception;

    Integer getMaxDay(int participantId) throws Exception;

    UserGameRateResultDto getUserRate(int userId) throws Exception;

    int completeMission(int userId, String roomId) throws Exception;

    void startRandomGame() throws Exception;

    void startGroupGame(String roomId, List<Integer> participantList) throws Exception;

    void newDayMission(String roomId) throws Exception;

    AbilityTargetResponseDto getAbilityTarget(int participantId) throws Exception;

    String useAbility(int participantId, int targetId) throws Exception;
}

