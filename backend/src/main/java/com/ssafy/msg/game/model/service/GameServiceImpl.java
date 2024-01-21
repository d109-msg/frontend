package com.ssafy.msg.game.model.service;

import com.ssafy.msg.game.model.dto.RandomNameDto;
import com.ssafy.msg.game.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameMapper gameMapper;

    @Override
    public List<String> getRandomNicknames(int limit) {
        List<String> list = new ArrayList<>();

        try {
            List<RandomNameDto> result = gameMapper.getRandomNicknames(limit);
            log.info("getRandomNicknames() -> result : {}", result);

            for(RandomNameDto name : result) {
                if (name != null) {
                    list.add(name.getFirstName() + " " + name.getLastName());
                }
            }

            log.info("getRandomNicknames() -> list : {}", list);
        } catch (Exception e) {
            log.error("getRandomNicknames() -> error : ", e);
        } finally {
            log.info("getRandomNicknames() -> end");
        }

        return list;
    }

    @Override
    public String getRandomRoomName() {
        String name = "";

        try {
            name = gameMapper.getRandomRoomName();
            log.info("getRandomNicknames() -> result : {}", name);

        } catch (Exception e) {
            log.error("getRandomNicknames() -> error : ", e);
        } finally {
            log.info("getRandomNicknames() -> end");
        }

        return name;
    }


}
