package com.ssafy.msg.game.model.service;

import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RandomNameDto;
import com.ssafy.msg.game.model.dto.RoomStartReceiveDto;
import com.ssafy.msg.game.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameMapper gameMapper;

    @Override
    public List<String> getRandomNicknames(int limit) {
        List<String> list = new ArrayList<>();

        try {
            List<RandomNameDto> result = gameMapper.getRandomNicknames(limit);
            log.info("getRandomNicknames() -> result : {}", result);

            for(RandomNameDto name : result) {
                if (name != null) {
                    list.add(name.getFirstName() + " " + name.getLastName() + " " + name.getImgUrl());
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
            RandomNameDto dto = gameMapper.getRandomRoomName();
            name = dto.getFirstName() + " " + dto.getLastName() + " " + dto.getImgUrl();
            log.info("getRandomNicknames() -> result : {}", name);

        } catch (Exception e) {
            log.error("getRandomNicknames() -> error : ", e);
        } finally {
            log.info("getRandomNicknames() -> end");
        }

        return name;
    }

    @Override
    public List<ParticipantDto> gameStart(RoomStartReceiveDto roomStartReceiveDto) {
        int numOfPlayers = roomStartReceiveDto.getUserList().size();
        String roomId = roomStartReceiveDto.getRoomId();
        List<RandomNameDto> randomNicknames = null;
        List<ParticipantDto> result = new ArrayList<>();

        log.info("gameStart() -> numOfPlayers : {}", numOfPlayers);

        try {
             randomNicknames = gameMapper.getRandomNicknames(numOfPlayers);
        } catch (Exception e) {
            log.error("gameStart() call mapper error: ", e);
        }

        List<String> randomJobs = getJobs(numOfPlayers);

        for(int i = 0; i  < numOfPlayers; i++) {
            ParticipantDto participant = ParticipantDto.builder()
                    .roomId(roomId)
                    .userEmailId(roomStartReceiveDto.getUserList().get(i))
                    .nickname(randomNicknames.get(i).getFirstName() + " " + randomNicknames.get(i).getLastName())
                    .jobId(randomJobs.get(i))
                    .imageUrl(randomNicknames.get(i).getImgUrl())
                    .build();

            result.add(participant);
        }
        log.info("gameStart() -> result : {}", result);

        int resultCnt = -1;

        try {
            resultCnt = gameMapper.insertParticipants(result);
        } catch (Exception e) {
            log.error("gameStart() call insertParticipants error : ", e);
        } finally {
            log.info("gameStart() resultCnt : {}", resultCnt);
        }

        return result;
    }

    /**
     * 숫자 만큼 역할을 배정하여 랜덤으로 섞은 리스트를 리턴
     * 6~7명을 상정하여 마피아2 의사1 고정
     * @param num 역할의 수
     * @return 무작위 리스트
     */
    @Override
    public List<String> getJobs(int num) {
        List<String> result = new ArrayList<>();

        result.add("마피아");
        result.add("마피아");
        result.add("의사");

        for(int i = 0; i < num - 3; i++){
            result.add("시민");
        }

        Collections.shuffle(result);

        return result;
    }


}
