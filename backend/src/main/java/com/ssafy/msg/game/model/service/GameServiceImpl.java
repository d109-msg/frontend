package com.ssafy.msg.game.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.chat.model.mapper.ChatMapper;
import com.ssafy.msg.game.model.dto.*;
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
    public List<String> getRandomNicknames(int limit) throws Exception {
        List<String> list = new ArrayList<>();

        List<RandomNameDto> result = gameMapper.getRandomNicknames(limit);

        for(RandomNameDto name : result) {
            if (name != null) {
                list.add(name.getFirstName() + " " + name.getLastName() + " " + name.getImgUrl());
            }
        }

        return list;
    }

    @Override
    public RandomNameDto getRandomRoomName() throws Exception{
        return gameMapper.getRandomRoomName();
    }

    /**
     * dto에 roomId와 userEmail을 담은 리스트를 담아
     * 해당 유저들을 room의 participants로 만들어 준다
     * 랜덤 이름과 아이콘을 부여하고 직업을 배정한다.
     * 인원은 6~7명을 기준으로 한다.
     * @param roomStartReceiveDto roomId와 userList를 담은 Dto
     * @return List partcipants를 리턴한다.
     */
    @Override
    public List<ParticipantDto> gameStart(RoomStartReceiveDto roomStartReceiveDto) throws Exception{
        int numOfPlayers = roomStartReceiveDto.getUserList().size();
        String roomId = roomStartReceiveDto.getRoomId();
        List<RandomNameDto> randomNicknames = null;
        List<ParticipantDto> result = new ArrayList<>();

        log.info("gameStart() -> numOfPlayers : {}", numOfPlayers);


        randomNicknames = gameMapper.getRandomNicknames(numOfPlayers);


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

        int resultCnt = gameMapper.insertParticipants(result);

        log.info("gameStart() resultCnt : {}", resultCnt);

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
    public List<RoomDto> getUserRooms(String userEmail) throws Exception{
        return gameMapper.getUserRooms(userEmail);
    }

    /**
     * roomId와 userEmail을 입력받아 해당 방의 유저가 지금 볼 수 있는 투표 현황을 리턴한다.
     * @param userEmail 유저의 email
     * @param roomId roomId
     * @return 모든 유저가 각 몇 표를 받았는지의 정보가 담긴 list return
     */
    @Override
    public List<VoteResultDto> getRoomVote(String userEmail, String roomId) throws Exception {
        return gameMapper.getRoomVote(roomId);
    }

    /**
     * userEmail과 roomId를 입력받아 해당 유저의 participant를 리턴
     * @param userEmail
     * @param roomId
     * @return participant 리턴
     */
    @Override
    public ParticipantDto getParticipant(String userEmail, String roomId) throws Exception {
        return gameMapper.getParticipant(new ParticipantReceiveDto(userEmail, roomId));
    }
}
