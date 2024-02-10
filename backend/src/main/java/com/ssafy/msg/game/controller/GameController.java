package com.ssafy.msg.game.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.ssafy.msg.chat.model.dto.CreateRoomDto;
import com.ssafy.msg.chat.model.dto.OpponentDto;
import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.game.exception.GroupRoomDuplicateException;
import com.ssafy.msg.game.exception.GroupRoomFullException;
import com.ssafy.msg.game.exception.GroupRoomNotFoundException;
import com.ssafy.msg.game.model.dto.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Game", description = "게임 관련 API")
public class GameController {

    private final GameService gameService;

    @GetMapping("/room/participant")
    @Operation(summary = "방 참가자 리스트 조회", description = "roomId에 따른 방 참가자 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getParticipants(@RequestParam("roomId") String roomId){
        log.info("getParticipants() -> roomId : {}", roomId);

        try {
            List<ParticipantDto> list = gameService.getParticipants(roomId);
            log.info("getParticipants() -> list : {}", list);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getParticipants() -> error : {}", e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getParticipants() -> end");
        }

    }

    @GetMapping("/user/rate")
    @Operation(summary = "유저 승패 가져오기", description = "userId를 입력받아 유저의 게임 통계를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserGameRateResultDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getUserWin(int userId){
        log.info("getUserWin() start");

        try{
            UserGameRateResultDto result = gameService.getUserRate(userId);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            log.error("getUserWin() -> error : {}", e);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getUserWin() end");
        }
    }

    @PostMapping(value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "사진 검증 api", description = "사진이 해당 조건에 부함하면 true 아니면 false를 이유와 함께 리턴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AiResultDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> analyzeImage(@RequestParam("image") MultipartFile imageFile, @RequestParam("condition") String condition) throws Exception{
        log.info("analyzeImage() start");
        try {
            AiResultDto result = gameService.analyzeImage(imageFile, condition);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("analyzeImage() -> error : {}", e);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("analyzeImage() e");
        }

    }

    @GetMapping("/participant")
    @Operation(summary = "유저 participant 조회", description = "userId과 roomId를 이용해 해당 해당 유저의 participant 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getParticipant(HttpServletRequest request, @RequestParam String roomId) {
        int userId = (int) request.getAttribute("id");

        log.info("getParticipant() -> roomId : {}", roomId);
        ParticipantResponseDto participantDto = null;

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

    @GetMapping("/room/num")
    @Operation(summary = "살아있는 참가자 리스트와 데일리 미션 성공 여부", description = "roomId를 입력받아 게임방 내의 살아있는 참가자만 데일리 미션 성공 여부와 함께 리턴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AliveParticipantDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getAliveParticipants(@RequestParam("roomId") String roomId){
        log.info("getAliveParticipants() -> roomId : {}", roomId);

        try {
            List<AliveParticipantDto> list = gameService.getAliveParticipant(roomId);
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
            GetRoomVoteResult result = gameService.getRoomVote(userId, roomId);
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
            @ApiResponse(responseCode = "200", description = "조회 성공"),
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
            , description = "participantId를 입력받아 진행 중인 미션을 리턴합니다. 유저가 죽었다면 null을 리턴합니다.")
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
            String result = gameService.vote(voteReceiveDto);
            log.info("submitVote() -> done");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("submitVote() -> error : {}", e.toString());

            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        } finally {
            log.info("submitVote() -> end");
        }

    }

    @GetMapping("/ability")
    @Operation(summary = "능력 사용 여부", description = "유저의 participantId를 입력받아 능력을 사용할 수 있는지 리턴. 대상을 선택하는 능력이라면 flagTarget = true 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AbilityTargetResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content)})
    public ResponseEntity<?> abilityAvailability(@RequestParam("participantId") int participantId){
        log.info("abilityAvailability() start");

        try {
            AbilityTargetResponseDto resultDto = gameService.getAbilityTarget(participantId);
            log.info("abilityAvailability() resultDto : {}", resultDto);

            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("abilityAvailability() -> error : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("abilityAvailability() end");
        }
    }

    @PatchMapping(value = "/ability", produces = "text/pain;charset=utf-8")
    @Operation(summary = "능력을 사용합니다.", description = "유저의 participantId를 입력받아 능력을 사용합니다. 대상을 지정할 필요가 없는 능력은 default = -1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "사용 실패", content = @Content)})
    public ResponseEntity<?> useAbility(@RequestParam("participantId") Integer participantId, @RequestParam(value = "targetId", defaultValue = "-1") Integer targetId){
        log.info("useAbility() start");

        try {
            String result = gameService.useAbility(participantId, targetId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("useAbility() -> error : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("useAbility() end");
        }
    }


    @GetMapping(value = "/vote/pick", produces = "text/pain;charset=utf-8")
    @Operation(summary = "내 투표현황 api", description = "유저의 participantId를 입력받아 리턴합니다. 유저가 죽었다면 participant is dead 를 리턴합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content)})
    public ResponseEntity<?> getMyVote(@RequestParam("participantId") int participantId){
        log.info("getMyVote() -> start");
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
            @ApiResponse(responseCode = "204", description = "존재하지 않는 방", content = @Content),
            @ApiResponse(responseCode = "400", description = "사용자 설정 게임 참여 실패 (에러)", content = @Content),
            @ApiResponse(responseCode = "403", description = "인원 제한으로 실패", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 참여 중인 게임", content = @Content)})
    @PostMapping("/group/join")
    public ResponseEntity<?> enterGroupRoom(@Valid HttpServletRequest request, @RequestBody ApplyGroupRoomDto applyGroupRoomDto){
        log.info("enterGroupRoom() -> Start");
        int userId = (int) request.getAttribute("id");

        log.info("enterGroupRoom() -> Receive applyGroupRoomDto : {}", applyGroupRoomDto);

        EnterGroupRoomDto enterGroupRoomDto = EnterGroupRoomDto.builder()
                .userId(userId)
                .roomId(applyGroupRoomDto.getRoomId())
                .build();

        try {
            RoomDto roomDto = gameService.enterGroupRoom(enterGroupRoomDto);
            log.info("enterGroupRoom() -> Success");
            return new ResponseEntity<>(roomDto, HttpStatus.OK);
        } catch (GroupRoomDuplicateException e) {
            log.error("GroupRoomDuplicateException() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (GroupRoomFullException e) {
            log.error("GroupRoomFullException() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (GroupRoomNotFoundException e) {
            log.error("GroupRoomNotFoundException() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "사용자 설정 게임 초대", description = "사용자 설정 게임 초대")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "사용자 설정 게임 초대 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "사용자 설정 게임 초대 실패", content = @Content) })
    @PostMapping("/group/invite")
    public ResponseEntity<?> inviteGroupRoom(@Valid HttpServletRequest request, @RequestBody EnterGroupRoomDto enterGroupRoomDto) {
        log.info("inviteGroupRoom() -> Start");

        int userId = (int) request.getAttribute("id");

        try {
            gameService.inviteGroupRoom(userId, enterGroupRoomDto);
            log.info("inviteGroupRoom() -> Success");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("inviteGroupRoom() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("inviteGroupRoom() -> End");
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
