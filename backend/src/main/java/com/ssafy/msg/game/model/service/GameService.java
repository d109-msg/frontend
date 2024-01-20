package com.ssafy.msg.game.model.service;

import com.ssafy.msg.game.model.Dto.RandomNickname;

public interface GameService {
    RandomNickname[] getRandomNicknames(int limit);
}
