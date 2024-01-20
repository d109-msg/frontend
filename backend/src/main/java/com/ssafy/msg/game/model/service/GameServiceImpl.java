package com.ssafy.msg.game.model.service;

import com.ssafy.msg.game.model.Dto.RandomNickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    @Override
    public RandomNickname[] getRandomNicknames(int limit) {
        return null;
    }
}
