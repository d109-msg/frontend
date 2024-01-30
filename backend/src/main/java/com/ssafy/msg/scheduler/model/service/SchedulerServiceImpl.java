package com.ssafy.msg.scheduler.model.service;

import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RandomNameDto;
import com.ssafy.msg.game.model.dto.RoomStartReceiveDto;
import com.ssafy.msg.game.model.dto.VoteResultDto;
import com.ssafy.msg.game.model.mapper.GameMapper;
import com.ssafy.msg.game.model.service.GameService;
import com.ssafy.msg.message.model.mapper.MessageMapper;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.scheduler.model.dto.NonCompleterDto;
import com.ssafy.msg.scheduler.model.dto.UpdateWinFlagDto;
import com.ssafy.msg.scheduler.model.mapper.SchedulerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService{

    private final SchedulerMapper schedulerMapper;
    private final MessageMapper messageMapper;

    private final GameService gameService;
    private final MessageService messageService;

    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void gameAM8() throws Exception {

        // end_time이 null인 roomId 조회
        List<String> unendRoom = schedulerMapper.getUnendRoom();

        for (String roomId: unendRoom) {
            // 투표 결과 처리
            manageMafiaDoctorVote(roomId);;
        }

        startRandomGame();

    }

    @Scheduled(cron = "0 0 18 * * ?")
    @Override
    public void gamePM6() throws SQLException {
        // end_time이 null인 roomId 조회
        List<String> unendRoom = schedulerMapper.getUnendRoom();

        for (String roomId: unendRoom) {
            // 미션 미수행자 알림
            List<NonCompleterDto> nonCompleterDtos = schedulerMapper.getNonCompleter(roomId);

            int countNonCompleter = nonCompleterDtos.size();
            if (countNonCompleter > 0){
                String message = nonCompleterDtos.get(0).getNickname();
                for (int i = 1; i<countNonCompleter; i++){
                    message += "님, " + nonCompleterDtos.get(i).getNickname();
                }
                message += "님이 아직 미션을 수행하지 않았습니다.";
                messageService.sendGameNotice(roomId, message);
            }
        }
    }

    @Override
    public void gamePM7() throws Exception {

    }

    @Scheduled(cron = "0 0 20 * * ?")
    @Override
    public void gamePM8() throws Exception {

        // end_time이 null인 roomId 조회
        List<String> unendRoom = schedulerMapper.getUnendRoom();

        for (String roomId: unendRoom) {
            //미션 미이행자 관리
            manageNonCompleter(roomId);
            // 투표 결과 처리
            manageNormalVote(roomId);
        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void gameAM12() {
        // end_time이 전날인 roomId
        // [DB] available change
    }



    /**
     * 대기방 인원 확인 및 룸 생성 + 직업 배정 + 시작 알림
     * @param
     * @return void
     */
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
            messageService.sendStartNotice(roomId);

            newDayMission(roomId);

        }
    }

    /**
     * 아침 알림 + 새로운 미션 배정
     * @param roomId
     * @throws Exception
     */
    public void newDayMission(String roomId) throws Exception {
        // [알림] n일차 아침이 되었습니다.
        // AM 시민 투표 알림
        messageService.sendDayNotice("아침", roomId);
        messageService.sendGameNotice(roomId, "마피아 의심자를 지목해주세요.");

        int day = messageMapper.calDay(roomId) + 1;
        gameService.createNewMission(roomId, day);
    }


    /**
     * 미션 미수행자 kill(flag_die 수정) + 알림
     * @param roomId
     * @return void
     */
    public void manageNonCompleter(String roomId) throws SQLException {
        // 미션 미이행자 리스트
        List<NonCompleterDto> nonCompleterDtos = schedulerMapper.getNonCompleter(roomId);
        log.info(nonCompleterDtos.toString());

        for(NonCompleterDto nonCompleterDto: nonCompleterDtos){
            schedulerMapper.killParticipant(nonCompleterDto.getParticipantId());
            if (nonCompleterDto.getJobId().equals("마피아")){
                messageService.sendGameNotice(roomId, "마피아 " + nonCompleterDto.getNickname()+"님이 실종되었습니다.");
            } else{
                messageService.sendGameNotice(roomId, "시민 " +nonCompleterDto.getNickname()+"님이 실종되었습니다.");
            }
        }

    }

    /**
     * 마피아 지목 결과 (PM 8)
     * @param roomId
     * @throws SQLException
     */
    public void manageNormalVote(String roomId) throws Exception {

        List<Integer> normalVote = schedulerMapper.getNormalVoteResult(roomId);

        // 모두 다 죽은 경우 고려하기

        if (normalVote.size() != 1) {
            // 최종 마피아 의심 대상 다수 or 투표 null
            messageService.sendGameNotice(roomId, "아무도 죽지 않았습니다.");
        } else{
            int participantId = normalVote.get(0);
            // 유저 flag_die 변경, 시민이었습니다 or 마피아였습니다
            // -> voteResultDtos.get(0).id가 participant_id인 participant의 flag_die 변경
            schedulerMapper.killParticipant(participantId);
            // -> 해당 id의 직업 조회
            ParticipantDto participantDto = schedulerMapper.getParticipant(participantId);
            if (participantDto.getJobId().equals("마피아")){
                messageService.sendGameNotice(roomId, participantDto.getNickname()+"님은 마피아였습니다!");
            } else{
                messageService.sendGameNotice(roomId, "선량한 시민 " +participantDto.getNickname()+"님이 처형당했습니다.");
            }

            manageGameEnd(roomId, true);

        }

    }


    /**
     * 의사와 타겟 지목 결과 (AM 8)
     * @param roomId
     * @throws SQLException
     */
    public void manageMafiaDoctorVote(String roomId) throws Exception {
        List<Integer> mafiaVote = schedulerMapper.getMafiaVoteResult(roomId);

        // 마피아 의견 갈림
        if (mafiaVote.size() != 1){
            messageService.sendGameNotice(roomId, "평화로운 밤이었습니다.");
        }else{
            // 마피아 * 의사 지목 대상 매칭 여부
            ParticipantDto participantDto = schedulerMapper.getParticipant(mafiaVote.get(0));
            int doctorVote = schedulerMapper.getDoctorVoteResult(roomId);
            if (mafiaVote.get(0) == doctorVote){
                messageService.sendGameNotice(roomId, participantDto.getNickname() + "님이 의사의 힐을 받아 살아났습니다!");
            }else{
                // 유저 flag_die 변경, 시민이었습니다 or 마피아였습니다
                // participant의 flag_die 변경
                schedulerMapper.killParticipant(mafiaVote.get(0));
                messageService.sendGameNotice(roomId, participantDto.getNickname()+"님이 마피아에게 당했습니다.");

                manageGameEnd(roomId, false);
            }
        }
    }

    /**
     * 게임 종료 여부 판별 (PM 8, AM 8)
     * @param roomId
     * @param isNight
     * @throws SQLException
     */
    public void manageGameEnd(String roomId, boolean isNight) throws Exception {
        // 죽지 않은 마피아 수가 0이라면 => 시민 승리
        int aliveMafia = schedulerMapper.getAliveMafia(roomId);
        if (aliveMafia == 0){
            endGame(0, roomId);
        }else{
            int isMafiaWin = schedulerMapper.isMafiaWin(roomId);
            if (isMafiaWin == 1){
                // 죽지 않은 마피아 수 >= 죽지 않은 시민 수 => 마피아 승리
                endGame(1, roomId);
            }else{
                // 죽지 않은 마피아 수 < 죽지 않은 시민 수 => 게임 진행
                if (isNight == true){
                    // [알림] n일차 밤이 되었습니다.
                    // PM 마피아와 의사 투표 알림
                    messageService.sendDayNotice("밤", roomId);
                    messageService.sendGameNotice(roomId, "타겟을 지정해주세요.");
                    messageService.sendGameNotice(roomId, "살릴 사람을 선택해주세요.");
                }else {
                    newDayMission(roomId);
                }
            }
        }
    }

    /**
     * 게임 종료
     * @param isMafiaWin
     * @param roomId
     * @throws SQLException
     */
    public void endGame(int isMafiaWin, String roomId) throws SQLException {
        // 해당 roomId의 participant들의 flag_win 변경
        schedulerMapper.updateWinFlag(UpdateWinFlagDto.builder()
                .isMafiaWin(isMafiaWin)
                .roomId(roomId).build());
        // 해당 roomId의 end_time 변경
        schedulerMapper.updateEndTime(roomId);
        // 게임 종료 메시지 전달
        if (isMafiaWin == 1){
            messageService.sendGameNotice(roomId, "마피아가 승리하였습니다.");
        }else {
            messageService.sendGameNotice(roomId, "시민이 승리하였습니다.");
        }
        messageService.sendEndNotice(roomId);

        // roomId available change
    }
}
