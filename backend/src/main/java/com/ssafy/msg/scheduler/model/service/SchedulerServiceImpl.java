package com.ssafy.msg.scheduler.model.service;

import com.ssafy.msg.game.model.dto.MissionResultDto;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.mapper.GameMapper;
import com.ssafy.msg.game.model.service.GameService;
import com.ssafy.msg.game.util.GameUtil;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.scheduler.model.dto.JudgeResultDto;
import com.ssafy.msg.scheduler.model.dto.UpdateWinFlagDto;
import com.ssafy.msg.scheduler.model.mapper.SchedulerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService{

    private final SchedulerMapper schedulerMapper;
    private final GameMapper gameMapper;

    private final GameService gameService;
    private final MessageService messageService;

    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void gameAM8() throws Exception {


        // strart_time은 not null이지만 end_time이 null인 roomId 조회
        List<String> unendRoom = schedulerMapper.getUnendRoom();
        for (String roomId: unendRoom) {
            //낮으로 바꾸기
            schedulerMapper.updateFlagNight(roomId, 0);
            // 투표 결과 처리
            manageMafiaDoctorVote(roomId);
        }


        // 7명이 모두 모였지만 start_time이 null인 그룹방 roomId 조회
        List<String> unstartRoom = schedulerMapper.getUnstartRoom();
        for (String roomId: unstartRoom){
            gameService.startGroupGame(roomId, gameMapper.getParticipantsInRoom(roomId));
        }

        // 대기방 인원 체크 및 게임 시작
        gameService.startRandomGame();
        //낮으로 바꾸기
        schedulerMapper.updateStaticFlagNight(0);

    }

    @Scheduled(cron = "0 0 18 * * ?")
    @Override
    public void gamePM6() throws SQLException {
        // end_time이 null인 roomId 조회
        List<String> unendRoom = schedulerMapper.getUnendRoom();

        for (String roomId: unendRoom) {
            // 미션 미수행자 알림
            List<ParticipantDto> nonCompleters = schedulerMapper.getNonCompleter(roomId);

            int countNonCompleter = nonCompleters.size();
            if (countNonCompleter > 0){
                StringBuilder message = new StringBuilder(nonCompleters.get(0).getNickname());
                for (int i = 1; i<countNonCompleter; i++){
                    message.append("님, ").append(nonCompleters.get(i).getNickname());
                }
                message.append("님이 아직 미션을 수행하지 않았습니다.");
                messageService.sendGameNotice(roomId, message.toString());
            }
        }
    }

    @Scheduled(cron = "0 0 20 * * ?")
    @Override
    public void gamePM8() throws Exception {


        // end_time이 null인 roomId 조회
        List<String> unendRoom = schedulerMapper.getUnendRoom();

        for (String roomId: unendRoom) {
            //밤으로 바꾸기
            schedulerMapper.updateFlagNight(roomId, 1);

            // 투표 결과 처리 및 미션 미수행자 관리
            manageNormalVote(roomId);

            //밤 능력 초기화


        }

        //밤으로 바꾸기
        schedulerMapper.updateStaticFlagNight(1);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void gameAM12() throws SQLException {
        // end_time이 전날인 roomId의 available change
        schedulerMapper.updateFlagAvailable();

    }

    public void nightAbilityReset(String roomId) throws Exception{
        //스파이, 훼방꾼, 건달의 ability가 1 보다 크다면(능력을 사용했다면) -1으로 바꾼다
        //경찰, 미치광이 ability 0으로 초기화
        gameMapper.getAliveParticipants(roomId);
    }

    /**
     * 마피아로 지목되어 처형당할 때 처리
     * @param roomId
     * @param participantDto
     * @param flagMission
     * @throws Exception
     */
    public void killByVote(String roomId, ParticipantDto participantDto, boolean flagMission) throws Exception {
        if(flagMission) {
            //미션을 했을 때
            if(participantDto.getJobId().equals("정치인") && participantDto.getAbility() == 0){
                //정치인일 때
                messageService.sendGameNotice(roomId, participantDto.getNickname() + "님은 부패한 힘을 이용해 처형의 위기에서 벗어났습니다.");
                gameMapper.setAbility(participantDto.getId(), -1);
                return;
            } else if (participantDto.getJobId().equals("변장술사") && participantDto.getAbility() > 0) {
                //변장술사일 때
                //변장 로직
                messageService.sendGameNotice(roomId, participantDto.getNickname() + "님은 변장술사였습니다. 처형을 피하고 다른 사람의 신분으로 활동을 재개합니다.");
                gameMapper.setAbility(participantDto.getId(), -1);
                return;
            } else {
                //둘 다 아니라면 죽습니다.
                schedulerMapper.killParticipant(participantDto.getId());
            }
        }

        if (GameUtil.getRoleType(participantDto.getJobId()).equals("마피아")){
            messageService.sendGameNotice(roomId, participantDto.getNickname() + "님은 마피아였습니다!");
        } else {
            messageService.sendGameNotice(roomId, participantDto.getNickname() + "님은 선량한 시민이었습니다.");
        }
    }

    /**
     * 마피아 지목 결과 (PM 8)
     * @param roomId
     * @throws SQLException
     */
    public void manageNormalVote(String roomId) throws Exception {

        List<Integer> normalVote = schedulerMapper.getNormalVoteResult(roomId);
        int countNormalVote = normalVote.size();

        // 미션 미수행자 리스트
        List<ParticipantDto> nonCompleters = schedulerMapper.getNonCompleter(roomId);
        List<Integer> nonCompletersId = nonCompleters.stream().map(ParticipantDto::getId).toList();

        Integer day = gameMapper.getMaxDayByRoomId(roomId);
        JudgeResultDto judgeResult = null;

        if(day != null) {
            judgeResult = schedulerMapper.getJudgeAbility(roomId, day);
            log.info("manageNormalVote() 판사의 선택 : {}", judgeResult);
        }

        if(judgeResult != null && judgeResult.getAbility() > 0) {
            //판사가 능력을 사용했을 때
            int targetId = judgeResult.getVote();

            ParticipantDto participantDto = null;
            int index = nonCompletersId.indexOf(targetId);

            //판사 ability -1
            gameMapper.setAbility(judgeResult.getParticipantId(), -1);
            boolean flagMission = false;
            // 판사가 선택한 대상이 미션 수행자인 경우
            if (index == -1) {
                flagMission = true;
                participantDto = schedulerMapper.getParticipant(targetId);
            }
            // 판사가 선택한 대상이 미션 미수행자인 경우
            else {
                log.info("manageNormalVote() 판사 선택도 받고 미션도 안하고");
                schedulerMapper.manageNonCompleter(targetId);
                participantDto = nonCompleters.get(index);
                nonCompleters.remove(index);
            }

            messageService.sendGameNotice(roomId, "판사가 권력을 이용해 " + participantDto.getNickname() + "님에게 사형을 선고했습니다.");

            killByVote(roomId, participantDto, flagMission);
        }
        else if (countNormalVote != 1) {
            //판사 능력 안씀
            // 최종 마피아 의심 대상 다수
            messageService.sendGameNotice(roomId, "아무도 죽지 않았습니다.");
        } else {
            if (normalVote.get(0) == null){
                // 투표 null
                messageService.sendGameNotice(roomId, "아무도 죽지 않았습니다.");
            }else{
                int targetId = normalVote.get(0);
                ParticipantDto participantDto;

                int index = nonCompletersId.indexOf(targetId);
                boolean flagMission = false;
                // 최종 마피아 의심 대상이 미션 수행자인 경우
                if (index == -1) {
                    flagMission = true;
                    participantDto = schedulerMapper.getParticipant(targetId);
                }
                // 최종 마피아 의심 대상이 미션 미수행자인 경우
                else {
                    schedulerMapper.manageNonCompleter(targetId);
                    participantDto = nonCompleters.get(index);
                    nonCompleters.remove(index);
                }

                messageService.sendGameNotice(roomId, participantDto.getNickname() + "님이 마피아로 지목 당했습니다.");

                killByVote(roomId, participantDto, flagMission);
            }


        }


        for(ParticipantDto nonCompleter: nonCompleters){
            schedulerMapper.manageNonCompleter(nonCompleter.getId());
            if (nonCompleter.getJobId().equals("마피아")){
                messageService.sendGameNotice(roomId, "마피아 " + nonCompleter.getNickname() + "님이 실종되었습니다.");
            } else{
                messageService.sendGameNotice(roomId, "시민 " + nonCompleter.getNickname() + "님이 실종되었습니다.");
            }
        }

        manageGameEnd(roomId, true);

    }

    @Override
    public int getFlagNight() throws Exception {
        return schedulerMapper.getFlagNight();
    }

    /**
     * 기자의 선택 결과 (AM 8)
     * roomId를 입력받아 해당 방의 기자에게 선택당한 사람을 출력합니다.
     * @param roomId
     * @throws Exception
     */
    public void noticeReporterVote(String roomId) throws Exception {
        log.info("noticeReporterVote() roomId : {}", roomId);
        int day = gameMapper.getMaxDayByRoomId(roomId);
        Integer targetId = schedulerMapper.getReporterVoteResult(roomId, day);
        log.info("noticeReporterVote() target : {}", targetId);

        if(targetId != null){
            ParticipantDto target = gameMapper.getParticipantWithPId(targetId);

            log.info("noticeReporterVote() target 이름 : {}", target.getNickname());
            log.info("noticeReporterVote() target 직업 : {}", target.getJobId());
            messageService.sendGameNotice(roomId, target.getNickname() + "님의 직업은 " + target.getJobId() + "입니다.");
            //피드?
        }
    }

    /**
     * 의사와 타겟 지목 결과 (AM 8)
     * @param roomId
     * @throws SQLException
     */
    public void manageMafiaDoctorVote(String roomId) throws Exception {
        List<Integer> mafiaVotes = schedulerMapper.getMafiaVoteResult(roomId);
        List<Integer> doctorVotes = schedulerMapper.getDoctorVoteResult(roomId);

        log.info(String.valueOf(mafiaVotes.size()));

        // 마피아 의견 갈림
        if (mafiaVotes.size() != 1 || mafiaVotes.get(0) == null){
            messageService.sendGameNotice(roomId, "평화로운 밤이었습니다.");

            //기자 발표
            noticeReporterVote(roomId);
            gameService.newDayMission(roomId);
        } else {
            log.info(mafiaVotes.toString());

            ParticipantDto target = schedulerMapper.getParticipant(mafiaVotes.get(0));

            if(target.getJobId().equals("군인") && target.getAbility() == 0){
                //군인일 때
                gameMapper.setAbility(target.getId(), -1);
                messageService.sendGameNotice(roomId, "마피아가 " + target.getNickname() + "님을 죽이려 시도했지만 방탄복이 " + target.getNickname() + "님을 살렸습니다.");

                //기자 발표
                noticeReporterVote(roomId);
                gameService.newDayMission(roomId);
            } else if (doctorVotes.size() == 1 && Objects.equals(doctorVotes.get(0), target.getId())) {
                // 의사 의견 통일 -> 살리기
                messageService.sendGameNotice(roomId, "마피아가 " + target.getNickname() + "님을 죽이려 시도했지만 의사의 치료를 받아 살아났습니다!");

                //기자 발표
                noticeReporterVote(roomId);
                gameService.newDayMission(roomId);
            } else {
                // 유저 flag_die 변경, 시민이었습니다 or 마피아였습니다
                schedulerMapper.killParticipant(target.getId());
                messageService.sendGameNotice(roomId, target.getNickname() + "님이 마피아에게 당했습니다.");

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
        // 모두 동시에 죽는다면 => 무승부
        int aliveParticipant = schedulerMapper.getAliveParticipant(roomId);
        if (aliveParticipant == 0){
            endGame(-1, roomId);
        }else{
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
                    if (isNight){
                        // [알림] n일차 밤이 되었습니다.
                        // PM 마피아와 의사 투표 알림
                        messageService.sendDayNotice("밤", roomId);
                        messageService.sendGameNotice(roomId, "타겟을 지정해주세요.");
                        messageService.sendGameNotice(roomId, "살릴 사람을 선택해주세요.");
                    }else {
                        //기자 발표
                        noticeReporterVote(roomId);
                        gameService.newDayMission(roomId);
                    }
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
        }else if (isMafiaWin == 0){
            messageService.sendGameNotice(roomId, "시민이 승리하였습니다.");
        }else {
            messageService.sendGameNotice(roomId, "모두 패배하였습니다.");
        }

        messageService.sendEndNotice(roomId);
    }

}
