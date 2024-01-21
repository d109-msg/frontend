package com.ssafy.msg.game.model.service;

import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RoomStartReceiveDto;

import java.util.List;

public interface GameService {
    List<String> getRandomNicknames(int limit);

    String getRandomRoomName();

    List<ParticipantDto> gameStart(RoomStartReceiveDto roomStartReceiveDto);

    List<String> getJobs(int num);
}

