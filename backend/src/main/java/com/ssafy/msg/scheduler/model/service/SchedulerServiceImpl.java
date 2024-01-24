package com.ssafy.msg.scheduler.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.chat.model.mapper.ChatMapper;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RandomNameDto;
import com.ssafy.msg.game.model.dto.RoomStartReceiveDto;
import com.ssafy.msg.game.model.service.GameService;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.scheduler.model.mapper.SchedulerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService{

    private final SchedulerMapper schedulerMapper;

    private final GameService gameService;
    private final MessageService messageService;

    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void gameAM8() throws Exception {
        // waiting room의 인원 확인 및 룸 생성과 배정
        // 모든 신규 룸의 start_time 해당일로 설정
        // [알림] 게임 시작 알림
        startRandomGame();

        // [알림] n일차 아침이 되었습니다.
        // - 미션 분배

        // end_time이 null인 roomId
        // day가 가장 큰 daily_mission
        // [DB] 투표 결과 적용
        // [알림] 아무도 죽지 않았습니다.
        // [알림] __님이 사망했습니다.
        // [알림] __님이 의사의 힐을 받아 살아났습니다.
        // -> 투표 결과 게임이 종료된다면
        // [DB] end_time change
        // [알림] 게임 종료 알림
        // -> 투표 결과 게임이 종료되지 않는다면
        // [알림] n일차 아침이 되었습니다.
        // - 미션 분배
    }

    @Scheduled(cron = "0 0 18 * * ?")
    @Override
    public void gamePM6() {
        // 미션 미수행자 알림

    }

    @Scheduled(cron = "0 0 20 * * ?")
    @Override
    public void gamePM8() {
        // end_time이 null인 roomId
        // day가 가장 큰 daily_mission
        // [DB] 투표 결과 적용
        // [알림] 아무도 죽지 않았습니다.
        // [알림] __님이 처형당했습니다.
        // [알림] 마피아와 의사 투표 시작 알림
        // -> 투표 결과 게임이 종료된다면
        // [DB] end_time change
        // [알림] 게임 종료 알림
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void gameAM12() {
        // end_time이 전날인 roomId
        // [DB] available change
    }


    // 인원 확인 및 룸 생성, 게임 시작 준비
    public void startRandomGame() throws Exception {
        // 대기방 유저 조회
        List<Integer> waitingUsersId = schedulerMapper.getWaitingUsersId();

        // 생성 가능한 방의 수 계산
        int availableRoom = waitingUsersId.size()/7;

        // 대기방의 유저 7명을 받아와, 방을 만들고 배정 - 방의 수만큼 반복
        for(int i = 0; i < availableRoom; i++){
            RandomNameDto randomRoomName = gameService.getRandomRoomName();

            String roomId = UUID.randomUUID().toString();
            RoomDto roomDto = RoomDto.builder()
                    .id(roomId)
                    .dataType("랜덤")
                    .title(randomRoomName.getFirstName() + " " + randomRoomName.getLastName())
                    .imageUrl(randomRoomName.getImgUrl())
                    .build();
            schedulerMapper.createRoom(roomDto);

            RoomStartReceiveDto roomStartReceiveDto = new RoomStartReceiveDto(roomId, waitingUsersId.subList(i*7, i*7+7));

            gameService.gameStart(roomStartReceiveDto);
            noticeGameStart(roomId);
        }
    }

    // 미션 분배


    // 게임 시작 알림
    public void noticeGameStart(String roomId) throws SQLException {
        messageService.sendStartNotice(roomId);
    }

    // 게임 종료 알림
    public void noticeGameEnd(String roomId) throws SQLException {
        messageService.sendEndNotice(roomId);
    }

    // 마피아 지목 결과 (PM 8)
    public void manageNormalVote(){
        // end_time이 null인 roomId 조회

    }

    // 의사와 타겟 지목 결과 (AM 8)
    public void manageMafiaDoctorVote(){

    }
}
