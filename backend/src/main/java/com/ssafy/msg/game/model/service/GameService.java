package com.ssafy.msg.game.model.service;

import java.util.List;

public interface GameService {
    List<String> getRandomNicknames(int limit);

    String getRandomRoomName();
}

