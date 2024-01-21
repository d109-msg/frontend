package com.ssafy.msg.game.controller;

import com.ssafy.msg.game.model.service.GameService;
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

    //test
    @GetMapping("/nickname")
    public ResponseEntity<?> randomNicknameTest(@RequestParam("num") int num){
        log.info("randomNicknameTest() -> num : {}", num);
        List<String> names = gameService.getRandomNicknames(num);
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    //test
    @GetMapping("/room/name")
    public ResponseEntity<?> randomRoomNameTest(){
        log.info("randomNicknameTest() -> start");
        String name = gameService.getRandomRoomName();
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}
