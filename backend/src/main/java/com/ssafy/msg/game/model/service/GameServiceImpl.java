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

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameMapper gameMapper;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    @Override
    public void applyRandomGame(int userId) throws Exception {
        UserDto user = userMapper.findUserByEmailId(userId);
        gameMapper.applyRandomGame(user);
    }

    @Override
    public boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception {
        boolean isParticipantInRoom = gameMapper.isParticipantInRoom(enterGroupRoomDto);
        return isParticipantInRoom;
    }

    @Override
    public RoomDto createEnterGroupRoom(int userId) throws Exception {
        UserDto user = userMapper.findUserByEmailId(userId);

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
                .userId(userId)
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
            UserDto user = userMapper.findUserByEmailId(enterGroupRoomDto.getUserId());

            ParticipantDto participant = ParticipantDto.builder()
                    .roomId(enterGroupRoomDto.getRoomId())
                    .userId(user.getId())
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
                    .userId(roomStartReceiveDto.getUserList().get(i))
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
     * @param userId 유저 id
     * @return Rooms list를 반환
     */
    @Override
    public List<RoomDto> getUserRooms(int userId) throws Exception{
        return gameMapper.getUserRooms(userId);
    }

    /**
     * roomId와 userEmail을 입력받아 해당 방의 유저가 지금 볼 수 있는 투표 현황을 리턴한다.
     * @param userId 유저의 id
     * @param roomId roomId
     * @return 모든 유저가 각 몇 표를 받았는지의 정보가 담긴 list return
     */
    @Override
    public List<VoteResultDto> getRoomVote(int userId, String roomId) throws Exception {
        ParticipantDto participantDto = gameMapper.getParticipant(new ParticipantReceiveDto(userId, roomId));

        String job = participantDto.getJobId();
        List<VoteResultDto> list = gameMapper.getRoomVote(roomId);

        log.info("getRoomVote() -> job : {}", job);
        log.info("getRoomVote() -> list : {}", list);
        log.info("getRoomVote() -> roomId : {}", roomId);

        if (getTime()) {
            log.info("getRoomVote() 현재 시간은 08:00과 20:00 사이입니다.");
            for(VoteResultDto vote : list){
                vote.setDoctorVoteCount(-1);
                vote.setMafiaVoteCount(-1);
            }
        } else {
            log.info("getRoomVote() 현재 시간은 08:00과 20:00 사이가 아닙니다.");
            for(VoteResultDto vote : list){
                vote.setNormalVoteCount(-1); //밤일 때, 시민 투표 가림

                if(!job.equals("의사")) { //의사가 아니라면, 의사 투표 가림
                    vote.setDoctorVoteCount(-1);
                }
                if(!job.equals("마피아")) { //마피아가 아니라면, 마피아 투표 가림
                    vote.setMafiaVoteCount(-1);
                }
            }
        }
        log.info("getRoomVote() changed list : {}", list);

        return list;
    }

    /**
     * userEmail과 roomId를 입력받아 해당 유저의 participant를 리턴
     * @param userId 유저  id
     * @param roomId 룸 id
     * @return participant 리턴
     */
    @Override
    public ParticipantDto getParticipant(int userId, String roomId) throws Exception {
        return gameMapper.getParticipant(new ParticipantReceiveDto(userId, roomId));
    }

    /**
     * roomId를 입력받아 살아있는 participant 리스트를 리턴
     * @param roomId roomId
     * @return list participantList
     * @throws Exception
     */
    @Override
    public List<ParticipantDto> getAliveParticipant(String roomId) throws Exception {
        return gameMapper.getAliveParticipants(roomId);
    }

    @Override
    public void vote(VoteReceiveDto voteReceiveDto) throws Exception {
        if(getTime()){
            //낮 08:00 - 20:00
            //시민 투표
            log.info("vote() -> normalVote : {}", voteReceiveDto.getTargetId());
            gameMapper.normalVote(voteReceiveDto.getParticipantId(), voteReceiveDto.getTargetId());
        } else {
            //밤
            if (voteReceiveDto.getJobId().equals("마피아")){
                //마피아 투표
                log.info("vote() -> mafiaVote : {}", voteReceiveDto.getTargetId());
                gameMapper.mafiaVote(voteReceiveDto.getParticipantId(), voteReceiveDto.getTargetId());
            } else if(voteReceiveDto.getJobId().equals("의사")){
                //의사 투표
                log.info("vote() -> doctorVote : {}", voteReceiveDto.getTargetId());
                gameMapper.doctorVote(voteReceiveDto.getParticipantId(), voteReceiveDto.getTargetId());
            }
        }
    }

    /**
     * 현재 시간으로 08:00 - 20:00 사이라면 true, 아니라면 false를 리턴하는 함수
     * @return 08:00 - 20:00 true, else false
     */
    @Override
    public boolean getTime() {
        // 서울 표준시 기준으로 현재 시간을 가져옵니다.
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        log.info("getTime() -> now : {}", now);
        // 시작 시간 (08:00)과 종료 시간 (20:00)을 설정합니다.
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        // 현재 시간이 시작 시간과 종료 시간 사이인지 확인합니다.
        if (now.toLocalTime().isAfter(startTime) && now.toLocalTime().isBefore(endTime)) {
            log.info("getRoomVote() 현재 시간은 08:00과 20:00 사이입니다.");
            return true;
        } else {
            log.info("getRoomVote() 현재 시간은 08:00과 20:00 사이가 아닙니다.");
            return false;
        }
    }
}
