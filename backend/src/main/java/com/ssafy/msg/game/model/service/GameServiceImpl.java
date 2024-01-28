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
    public boolean getRandomGameApplyStatus(int userId) throws Exception {
        boolean randomGameApplyStatus = gameMapper.getRandomGameApplyStatus(userId);
        return randomGameApplyStatus;
    }

    @Override
    public boolean applyRandomGame(int userId) throws Exception {
        boolean randomGameApplyStatus = gameMapper.getRandomGameApplyStatus(userId);
        if (randomGameApplyStatus == true){
            return false;
        }else{
            UserDto user = userMapper.findUserById(userId);
            gameMapper.applyRandomGame(user);
            return true;
        }
    }

    @Override
    public boolean cancelRandomGame(int userId) throws Exception {
        boolean randomGameApplyStatus = gameMapper.getRandomGameApplyStatus(userId);
        if (randomGameApplyStatus == true){
            gameMapper.deleteParticipant(userId);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getMyVote(int participantId) throws Exception {
        if(gameMapper.isAlive(participantId) != 0) { //죽었다면
            log.info("getMyVote() player is dead");
            return "participant is dead";
        }

        MyVoteDto dto = gameMapper.getMyVote(participantId);
        String job = gameMapper.getParticipantWithPId(participantId).getJobId();
        log.info("getMyVote() myVote : {}", dto);
        log.info("getMyVote() job : {}", job);

        if (getTime()) {
            //낮
            log.info("getMyVote() result : {}", dto.getNormalVote());
            return dto.getNormalVote();
        } else {
            if(job.equals("마피아")) {
                log.info("getMyVote() result : {}", dto.getMafiaVote());
                return dto.getMafiaVote();
            } else if(job.equals("의사")) {
                log.info("getMyVote() result : {}", dto.getDoctorVote());
                return dto.getDoctorVote();
            } else {
                return dto.getNormalVote();
            }
        }
    }

    @Override
    public boolean isParticipantInRoom(EnterGroupRoomDto enterGroupRoomDto) throws Exception {
        boolean isParticipantInRoom = gameMapper.isParticipantInRoom(enterGroupRoomDto);
        return isParticipantInRoom;
    }

    @Override
    public RoomDto createEnterGroupRoom(int userId) throws Exception {
        UserDto user = userMapper.findUserById(userId);

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
            UserDto user = userMapper.findUserById(enterGroupRoomDto.getUserId());

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

        for(int userId: roomStartReceiveDto.getUserList()){
            gameMapper.deleteParticipant(userId);
        }

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
    public List<VoteResponseDto> getRoomVote(int userId, String roomId) throws Exception {
        ParticipantDto participantDto = gameMapper.getParticipant(new ParticipantReceiveDto(userId, roomId));

        String job = participantDto.getJobId();
        List<VoteResultDto> list = gameMapper.getRoomVote(roomId);

        log.info("getRoomVote() -> job : {}", job);
        log.info("getRoomVote() -> list : {}", list);
        log.info("getRoomVote() -> roomId : {}", roomId);

        List<VoteResponseDto> responseList = new ArrayList<>();

        for(VoteResultDto vote : list){
            VoteResponseDto voteResult = VoteResponseDto.builder()
                    .id(vote.getId())
                    .nickname(vote.getNickname())
                    .imageUrl(vote.getImageUrl())
                    .build();
            if (getTime()) { // 낮일 때
                voteResult.setVoteCount(vote.getNormalVoteCount());
            } else { //밤일 때
                if (job.equals("의사")) { //의사일 때
                    voteResult.setVoteCount(vote.getDoctorVoteCount());
                } else if (job.equals("마피아")) { //마피아일 때
                    voteResult.setVoteCount(vote.getMafiaVoteCount());
                }
            }

            responseList.add(voteResult);
        }


        log.info("getRoomVote() resultList : {}", responseList);

        return responseList;
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
    public List<AliveParticipantDto> getAliveParticipant(String roomId) throws Exception {
        return gameMapper.getAliveParticipants(roomId);
    }

    /**
     * participantId를 입력받아 현재 진행 중인 미션을 리턴
     * @param participantId
     * @return MissionResultDto 현재 진행 중인 미션 (일반, 마피아)
     * @throws Exception
     */
    @Override
    public MissionResultDto getMyMission(int participantId) throws Exception {
        if(gameMapper.isAlive(participantId) != 0) {
            log.info("getMyMission() participant is dead");
            return null;
        } else {
            return gameMapper.getMyMission(participantId);
        }
    }

    /**
     * 직업과 participantID, targetId를 입력받아 시간에 따른 투표를 반영합니다.
     * @param voteReceiveDto 직업과 participantID, targetId를 입력받아
     * @throws Exception
     */
    @Override
    public void vote(VoteReceiveDto voteReceiveDto) throws Exception {
        if(gameMapper.isAlive(voteReceiveDto.getParticipantId()) != 0) {
            log.info("vote() participant is dead");
            return;
        }

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

    /**
     * roomId와 day를 입력받아 해당 room에서 수행한적 없는 미션을 랜덤으로 골라
     * 모든 participant에게 새로운 daily미션을 입력받은 day로 만든다.
     * @param roomId 새로운 미션을 분배할 roomId 입력
     * @param day   새로 만들 미션의 날짜
     * @throws Exception
     */
    @Override
    public void createNewMission(String roomId, int day) throws Exception {
        log.info("createNewMission() -> roomId : {}", roomId);
        log.info("createNewMission() -> day : {}", day);

        //입력받은 게임방에서 나온적 없는 미션 중 하나를 랜덤으로 고릅니다.
        int randMission = gameMapper.getRandomMission(roomId);
        log.info("createNewMission() randMissing : {}", randMission);

        //선택된 랜덤 게임을 입력받은 roomId의 모든 participant에게 보내준다.
        NewMissionDto newMissionDto = NewMissionDto.builder().roomId(roomId).missionId(randMission).day(day).build();
        gameMapper.createNewMission(newMissionDto);

        log.info("createNewMission() -> new mission created");
    }
}
