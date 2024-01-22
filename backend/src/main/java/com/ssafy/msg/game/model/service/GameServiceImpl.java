package com.ssafy.msg.game.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.chat.model.mapper.ChatMapper;
import com.ssafy.msg.game.model.dto.EnterGroupRoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RandomNameDto;
import com.ssafy.msg.game.model.dto.RoomStartReceiveDto;
import com.ssafy.msg.game.model.mapper.GameMapper;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameMapper gameMapper;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    @Override
    public void applyRandomGame(String emailId) throws Exception {
        UserDto user = userMapper.findUserByEmailId(emailId);
        gameMapper.applyRandomGame(user);
    }

    @Override
    public boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception {
        boolean isParticipantInRoom = gameMapper.isParticipantInRoom(enterGroupRoomDto);
        return isParticipantInRoom;
    }

    @Override
    public RoomDto createEnterGroupRoom(String emailId) throws Exception {
        UserDto user = userMapper.findUserByEmailId(emailId);

        String roomId = UUID.randomUUID().toString();

        RandomNameDto randomNameDto = getRandomRoomName();

        RoomDto roomDto = RoomDto.builder()
                .id(roomId)
                .dataType("그룹")
                .title(randomNameDto.getFirstName()+" "+randomNameDto.getLastName())
                .imageUrl(randomNameDto.getImgUrl())
                .build();

        chatMapper.createRoom(roomDto);

        ParticipantDto participant = ParticipantDto.builder()
                .roomId(roomId)
                .userEmailId(emailId)
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .build();

        chatMapper.enterRoom(participant);

        return chatMapper.getRoom(roomId);
    }

    @Override
    public RoomDto enterGroupRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception {

        RoomDto roomDto = chatMapper.getRoom(enterGroupRoomDto.getRoomId());

        if (roomDto != null){
            UserDto user = userMapper.findUserByEmailId(enterGroupRoomDto.getEmailId());

            ParticipantDto participant = ParticipantDto.builder()
                    .roomId(enterGroupRoomDto.getRoomId())
                    .userEmailId(user.getEmailId())
                    .imageUrl(user.getImageUrl())
                    .nickname(user.getNickname())
                    .build();

            chatMapper.enterRoom(participant);
        }
        return roomDto;
    }

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
    public RandomNameDto getRandomRoomName() {
        RandomNameDto dto = null;

        try {
            dto = gameMapper.getRandomRoomName();
            log.info("getRandomNicknames() -> result : {}", dto);

        } catch (Exception e) {
            log.error("getRandomNicknames() -> error : ", e);
        } finally {
            log.info("getRandomNicknames() -> end");
        }

        return dto;
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

    /**
     * user email을 입력받아 user의 rooms 중, type이 "랜덤", "그룹"인 게임방 리스틀 리턴
     * @param userEmail
     * @return Rooms list를 반환
     */
    @Override
    public List<RoomDto> getUserRooms(String userEmail) {
        List<RoomDto> list = null;

        try {
            log.info("call gameMapper.getUserRooms : email : {}", userEmail);
            list = gameMapper.getUserRooms(userEmail);
        } catch (Exception e) {
            log.error("getRoomList() call mapper : ", e);
        }

        log.info("getUserRooms() list : {}", list);
        return list;
    }

}
