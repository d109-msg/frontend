package com.ssafy.msg.game.controller;

import com.amazonaws.services.ec2.model.GetTransitGatewayRouteTablePropagationsRequest;
import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.model.dto.*;
import com.ssafy.msg.game.model.mapper.GameMapper;
import com.ssafy.msg.game.model.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Game", description = "게임 관련 API")
public class GameController {

    private final GameService gameService;

    @GetMapping("/participant")
    @Operation(summary = "유저 participant 조회", description = "userEmail과 roomId를 이용해 해당 해당 유저의 participant 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getParticipant(HttpServletRequest request, @RequestParam String roomId) {
        int userId = (int) request.getAttribute("id");

        log.info("getParticipant() -> roomId : {}", roomId);
        ParticipantDto participantDto = null;

        try {
            participantDto = gameService.getParticipant(userId, roomId);
            log.info("getParticipant() participant : {}", participantDto);

            return new ResponseEntity<>(participantDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getParticipant() error : {}", e.toString());
            return new ResponseEntity<>(participantDto, HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getParticipant() -> end");
        }
    }

    @GetMapping("/room/alive")
    @Operation(summary = "살아있는 참가자 리스트", description = "roomId를 입력받아 게임방 내의 살아있는 참가자만 리턴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getAliveParticipants(@RequestParam("roomId") String roomId){
        log.info("getAliveParticipants() -> roomId : {}", roomId);

        try {
            List<ParticipantDto> list = gameService.getAliveParticipant(roomId);
            log.info("getAliveParticipants() -> list : {}", list);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAliveParticipants() -> error : {}", e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getAliveParticipants() -> end");
        }

    }

    @GetMapping("/vote/room")
    @Operation(summary = "유저 현재 방의 투표 현황 조회"
            , description = "userId와 roomId를 이용해 해당 room의 투표 현황 조회\n현재 볼 수 있는 투표만 리턴합니다.\n 죽은 사람의 투표는 반영하지 않고, 죽은 사람이 받은 투표 수도 보여주지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VoteResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getRoomVote(HttpServletRequest request, @RequestParam String roomId) {
        int userId = (int) request.getAttribute("id");

        try {
            List<VoteResponseDto> result = gameService.getRoomVote(userId, roomId);
            log.info("getRoomVote() -> result : {}", result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getRoomVote() -> error : {}", e.toString());

            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getRoomVote() -> end");
        }

    }

    @GetMapping(value = "/room/list")
    @Operation(summary = "유저의 진행 중인 게임 리스트를 반환", description = "userId을 이용해 user의 room list 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getUserRooms(HttpServletRequest request){
        int userId = (int) request.getAttribute("id");

        log.info("getUserRooms() -> id : {}", userId);
        try {
            List<RoomDto> list = gameService.getUserRooms(userId);
            log.info("getUserRooms() list : {}", list);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception  e){
            log.error("getUserRooms() -> error : {}", e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getUserRooms() -> end");
        }
    }

    @GetMapping("/room/mission")
    @Operation(summary = "유저 현재 방의 미션 조회"
            , description = "participantId를 입력받아 진행 중인 미션을 리턴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResultDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getMyMission(@RequestParam("participantId") int participantId){
        log.info("getMyMission() participantId : {}", participantId);

        try {
            MissionResultDto missionResultDto = gameService.getMyMission(participantId);
            log.info("getMyMission() missionResultDto : {}", missionResultDto);

            return new ResponseEntity<>(missionResultDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getMyMission() -> error : {}", e);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getMyMission() end");
        }
    }

    @PatchMapping("/vote")
    @Operation(summary = "투표 api", description = "유저의 participantId, 직업, targetParticipantId를 입력받아 투표를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "투표 실패", content = @Content)})
    public ResponseEntity<?> submitVote(@RequestBody VoteReceiveDto voteReceiveDto) {
        log.info("submitVote() -> participant : {}", voteReceiveDto.getParticipantId());
        log.info("submitVote() -> target : {}", voteReceiveDto.getTargetId());
        log.info("submitVote() -> job : {}", voteReceiveDto.getJobId());

        try {
            gameService.vote(voteReceiveDto);
            log.info("submitVote() -> done");

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("submitVote() -> error : {}", e.toString());

            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } finally {
            log.info("submitVote() -> end");
        }

    }

    @GetMapping(value = "/vote/pick", produces = "text/pain;charset=utf-8")
    @PatchMapping("/vote")
    @Operation(summary = "내 투표현황 api", description = "유저의 participantId를 입력받아 리턴합니다. 유저가 죽었다면 participant is dead 를 리턴합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content)})
    public ResponseEntity<?> getMyVote(@RequestParam("participantId") int participantId){
        log.info("getMyVote() -> s");
        try {
            String result = gameService.getMyVote(participantId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getMyVote() -> error : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getMyVote() -> end");
        }

    }

    @Operation(summary = "랜덤 게임 참여 신청 여부 조회", description = "랜덤 게임 참여 신청 여부 조회")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "랜덤 게임 참여 신청 여부 조회 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "랜덤 게임 참여 신청 여부 조회 실패", content = @Content) })
    @GetMapping("/random")
    public ResponseEntity<?> getRandomGameApplyStatus(@Valid HttpServletRequest request) {
        log.info("getRandomGameApplyStatus() -> Start");

        int userId = (int) request.getAttribute("id");

        try {
            boolean randomGameApplyStatus = gameService.getRandomGameApplyStatus(userId);
            log.info("getRandomGameApplyStatus() -> Success");
            return new ResponseEntity<>(randomGameApplyStatus, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getRandomGameApplyStatus() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getRandomGameApplyStatus() -> End");
        }
    }

    @Operation(summary = "랜덤 게임 참여 신청", description = "랜덤 게임 참여 신청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 게임 참여 신청 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "랜덤 게임 참여 신청 실패", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 랜덤 게임 참여 신청 중", content = @Content)})
    @PostMapping("/random")
    public ResponseEntity<?> applyRandomGame(@Valid HttpServletRequest request) {
        log.info("applyRandomGame() -> Start");

        int userId = (int) request.getAttribute("id");

        try {
            boolean result = gameService.applyRandomGame(userId);
            if (result == true){
                log.info("applyRandomGame() -> Success");
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                log.info("applyRandomGame() -> Fail (중복 신청)");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            log.error("applyRandomGame() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("applyRandomGame() -> End");
        }
    }

    @Operation(summary = "랜덤 게임 참여 취소", description = "랜덤 게임 참여 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 게임 참여 취소 성공", content = @Content),
            @ApiResponse(responseCode = "204", description = "랜덤 게임 참여 신청 상태가 아님", content = @Content),
            @ApiResponse(responseCode = "400", description = "랜덤 게임 참여 취소 실패", content = @Content) })
    @DeleteMapping("/random")
    public ResponseEntity<?> cancelRandomGame(@Valid HttpServletRequest request) {
        log.info("cancelRandomGame() -> Start");

        int userId = (int) request.getAttribute("id");

        try {
            boolean result = gameService.cancelRandomGame(userId);
            if (result == true){
                log.info("cancelRandomGame() -> Success");
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                log.info("cancelRandomGame() -> Fail (신청 중 X)");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error("cancelRandomGame() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("cancelRandomGame() -> End");
        }
    }

    @Operation(summary = "사용자 설정 게임 생성", description = "사용자 설정 게임 생성")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "사용자 설정 게임 생성 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "사용자 설정 게임 생성 실패", content = @Content) })
    @PostMapping("/group")
    public ResponseEntity<?> createGroupRoom(@Valid HttpServletRequest request) {
        log.info("createGroupRoom() -> Start");

        int userId = (int) request.getAttribute("id");

        RoomDto roomDto = null;

        try {
            roomDto = gameService.createEnterGroupRoom(userId);
            log.info("createGroupRoom() -> Success");
            return new ResponseEntity<>(roomDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("createGroupRoom() -> Exception : {}", e);
            return new ResponseEntity<>(roomDto, HttpStatus.BAD_REQUEST);
        } finally {
            log.info("createGroupRoom() -> End");
        }
    }

    @Operation(summary = "사용자 설정 게임 참여", description = "사용자 설정 게임 참여")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 설정 게임 참여 성공", content = @Content),
            @ApiResponse(responseCode = "204", description = "사용자 설정 게임 존재하지 않음", content = @Content),
            @ApiResponse(responseCode = "400", description = "사용자 설정 게임 참여 실패", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 참여 중인 게임", content = @Content)})
    @PostMapping("/group/join")
    public ResponseEntity<?> enterGroupRoom(@Valid HttpServletRequest request, @RequestBody ApplyGroupRoomDto applyGroupRoomDto) {
        log.info("enterGroupRoom() -> Start");
        int userId = (int) request.getAttribute("id");

        log.info("enterGroupRoom() -> Receive applyGroupRoomDto : {}", applyGroupRoomDto);

        EnterGroupRoomDto enterGroupRoomDto = EnterGroupRoomDto.builder()
                .userId(userId)
                .roomId(applyGroupRoomDto.getRoomId())
                .build();
        RoomDto roomDto = null;

        try {
            boolean isParticipantInRoom = gameService.isParticipantInRoom(enterGroupRoomDto);

            if (isParticipantInRoom == true){
                return new ResponseEntity<>(roomDto, HttpStatus.CONFLICT);
            } else{
                try {
                    roomDto = gameService.enterGroupRoom(enterGroupRoomDto);
                    log.info("enterGroupRoom() -> Success");
                    if (roomDto != null){
                        return new ResponseEntity<>(roomDto, HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>(roomDto, HttpStatus.NO_CONTENT);
                    }

                } catch (Exception e) {
                    log.error("enterGroupRoom() -> Exception : {}", e);
                    return new ResponseEntity<>(roomDto, HttpStatus.BAD_REQUEST);
                } finally {
                    log.info("enterGroupRoom() -> End");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    //test
    @PostMapping("/mission/test")
    public ResponseEntity<?> insertRandomMission(@RequestBody NewMissionDto dto) {
        try {
            gameService.createNewMission(dto.getRoomId(), dto.getDay());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("insertRandomMission() -> error : {}", e);
            return null;
        }

    }

    //test
    @GetMapping("/nicknametest")
    public ResponseEntity<?> randomNicknameTest(@RequestParam("num") int num){
        log.info("randomNicknameTest() -> num : {}", num);

        try {
            List<String> names = gameService.getRandomNicknames(num);
            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception e) {
            log.error("randomNicknameTest() -> error : {}", e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("randomNicknameTest() end");
        }

    }

    //test
    @GetMapping(value = "/room/nametest")
    public ResponseEntity<?> randomRoomNameTest(){
        log.info("randomNicknameTest() -> start");
        
        try {
            RandomNameDto dto = gameService.getRandomRoomName();
            log.info("randomRoomNameTest() randomName : {}", dto);

            return new ResponseEntity<>(dto.toString(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("randomRoomNameTest() -> error : {}", e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        
    }
}
