package com.ssafy.msg.game.controller;

import com.ssafy.msg.chat.model.dto.RoomDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Game", description = "게임 관련 API")
public class GameController {

    private final GameService gameService;

    @GetMapping(value = "/room/list")
    @Operation(summary = "유저의 진행 중인 게임 리스트를 반환", description = "userEmail을 이용해 user의 room list 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDto.class)) }),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content) })
    public ResponseEntity<?> getUserRooms(HttpServletRequest request){
        String emailId = (String) request.getAttribute("emailId");

        log.info("getUserRooms() -> email : {}", emailId);

        List<RoomDto> list = gameService.getUserRooms(emailId);

        if(list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("잘못된 요청", HttpStatus.BAD_REQUEST);
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

        List<ParticipantDto> result = gameService.gameStart(roomStartReceiveDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    //test
    @GetMapping("/nickname")
    public ResponseEntity<?> randomNicknameTest(@RequestParam("num") int num){
        log.info("randomNicknameTest() -> num : {}", num);
        List<String> names = gameService.getRandomNicknames(num);
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    //test
    @GetMapping(value = "/room/name", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> randomRoomNameTest(){
        log.info("randomNicknameTest() -> start");
        RandomNameDto dto = gameService.getRandomRoomName();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
