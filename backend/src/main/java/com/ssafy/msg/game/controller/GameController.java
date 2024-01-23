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
            @ApiResponse(responseCode = "201", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getParticipant(HttpServletRequest request, @RequestParam String roomId) {
        String emailId = (String) request.getAttribute("emailId");

        log.info("getParticipant() -> roomId : {}", roomId);
        ParticipantDto participantDto = null;

        try {
            participantDto = gameService.getParticipant(emailId, roomId);
            log.info("getParticipant() participant : {}", participantDto);

            return new ResponseEntity<>(participantDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getParticipant() error : {}", e.toString());
            return new ResponseEntity<>(participantDto, HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getParticipant() -> end");
        }
    }

    @GetMapping("/room/vote")
    @Operation(summary = "유저 현재 방의 투표 현황 조회"
            , description = "userEmail과 roomId를 이용해 해당 room의 투표 현황 조회\n낮과 밤에 따라 볼 수 없는 투표 수는 -1로 표시")
    public ResponseEntity<?> getRoomVote(HttpServletRequest request, @RequestParam String roomId) {
        String email = (String) request.getAttribute("emailId");

        try {
            List<VoteResultDto> result = gameService.getRoomVote(email, roomId);
            log.info("getRoomVote() -> result : {}", result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getRoomVote() -> error : {}", e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getRoomVote() -> end");
        }

    }

    @GetMapping(value = "/room/list")
    @Operation(summary = "유저의 진행 중인 게임 리스트를 반환", description = "userEmail을 이용해 user의 room list 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getUserRooms(HttpServletRequest request){
        String emailId = (String) request.getAttribute("emailId");

        log.info("getUserRooms() -> email : {}", emailId);
        try {
            List<RoomDto> list = gameService.getUserRooms(emailId);
            log.info("getUserRooms() list : {}", list);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e){
            log.error("getUserRooms() -> error : {}", e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("getUserRooms() -> end");
        }
    }

    @Operation(summary = "랜덤 게임 참여 신청", description = "랜덤 게임 참여 신청")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "랜덤 게임 참여 신청 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "랜덤 게임 참여 신청 실패", content = @Content) })
    @PostMapping("/random")
    public ResponseEntity<?> applyRandomGame(@Valid HttpServletRequest request) {
        log.info("applyRandomGame() -> Start");

        String emailId = (String) request.getAttribute("emailId");

        try {
            gameService.applyRandomGame(emailId);
            log.info("applyRandomGame() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("applyRandomGame() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("applyRandomGame() -> End");
        }
    }

    @Operation(summary = "사용자 설정 게임 생성", description = "사용자 설정 게임 생성")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "사용자 설정 게임 생성 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "사용자 설정 게임 생성 실패", content = @Content) })
    @PostMapping("/group")
    public ResponseEntity<?> createGroupRoom(@Valid HttpServletRequest request) {
        log.info("createGroupRoom() -> Start");

        String emailId = (String) request.getAttribute("emailId");

        RoomDto roomDto = null;

        try {
            roomDto = gameService.createEnterGroupRoom(emailId);
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
        String emailId = (String) request.getAttribute("emailId");

        log.info("enterGroupRoom() -> Receive applyGroupRoomDto : {}", applyGroupRoomDto);

        EnterGroupRoomDto enterGroupRoomDto = EnterGroupRoomDto.builder()
                .emailId(emailId)
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

    @PostMapping("/room/participant")
    @Operation(summary = "참가자 이름, 직업 배정", description = "roomId와 userList 입력받아 각 user의 participants를 만듦")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "배정 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoomStartReceiveDto.class)) }),
            @ApiResponse(responseCode = "400", description = "배정 실패", content = @Content) })
    public ResponseEntity<?> roomStart(@RequestBody RoomStartReceiveDto roomStartReceiveDto){
        log.info("roomStart() -> start");
        log.info("roomStart() -> dto : {}", roomStartReceiveDto);

        try {
            List<ParticipantDto> result = gameService.gameStart(roomStartReceiveDto);
            log.info("roomStart() -> result : {}", result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("roomStart() -> error : {}", e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("roomStart() -> end");
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
