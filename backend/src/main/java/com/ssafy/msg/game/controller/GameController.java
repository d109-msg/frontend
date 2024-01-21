package com.ssafy.msg.game.controller;

import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.game.model.dto.RoomStartReceiveDto;
import com.ssafy.msg.game.model.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        String name = gameService.getRandomRoomName();
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}
