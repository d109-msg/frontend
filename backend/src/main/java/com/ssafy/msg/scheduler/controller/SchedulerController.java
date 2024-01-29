package com.ssafy.msg.scheduler.controller;


import com.ssafy.msg.chat.model.dto.CreateRoomDto;
import com.ssafy.msg.chat.model.dto.OpponentDto;
import com.ssafy.msg.chat.model.dto.RoomDto;
import com.ssafy.msg.chat.model.service.ChatService;
import com.ssafy.msg.scheduler.model.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

/*
 * Controller 설정
 * 1. @RestController로 REST API 컨트롤러 등록
 * 2. @RequestMapping으로 URI 매핑
 * 3. 의존성 주입이 필요한 경우 @RequiredArgsConstructor로 생성자 주입 (UserController에서는 jwtUtil, oauth2Utils, userService에 대한 의존성 주입)
 * 4. @Slf4j로 Logger 사용 (log.info, log.error, log.error 등으로 로그 출력)
 */
@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
@Slf4j

/*
 * Swagger 설정
 * 1. @Tag로 API를 그룹화 (회원 CRUD API를 User로 그룹화, 게시물 CRUD API를 Article로 그룹화 등)
 */
@Tag(name="Scheduler Test", description="스케쥴러 테스트")
public class SchedulerController {

    /*
     * 요청 처리 메소드 설정
     * 1. @GetMapping, @PostMapping, @PatchMapping, @PutMapping, @DeleteMapping으로 HTTP 요청 메소드와 URI 매핑
     */

    /*
     * Swagger 설정
     * 1. @Operation으로 API 요약과 설명 작성
     * 2. @ApiResponses로 ApiResponse 그룹화
     * 3. @ApiResponse로 HTTP 응답 코드, 설명, 응답 DTO 작성
     * 4. 데이터를 DTO로 받는 것이 아니라 URL의 PathVariable이나 RequestParam으로 받는 경우 @Parameter로 설명 작성
     */

    /*
     * 1. 메소드 시작 시 로그 출력
     * 2. 받은 파라미터 출력 (Receive code, Receive provider)
     */

    /*
     * 1. 에러 발생할 수 있을 때 try-catch-finally 구문 사용
     * 2. 중간중간 로그 출력은 하면 좋지만 너무 많으면 가독성 떨어지므로 적당히 하는 것이 좋을 듯함 (애매하면 그냥 처음과 끝에만 출력)
     * 3. 에러 처리 시에는 중요도에 따라 log.warn, log.error로 나눠서 로그 출력
     */

    private final SchedulerService schedulerService;



    @Operation(summary = "오전 8시 랜덤 게임 배정 테스트", description = "오전 8시 랜덤 게임 배정 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 게임 배정 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "랜덤 게임 배정 실패", content = @Content) })
    @GetMapping("/8am")
    public ResponseEntity<?> testAM8(HttpServletRequest request) {
        log.info("testAM8() -> Start");

        int id = (int) request.getAttribute("id");
        log.info("testAM8() -> Receive id : {}", id);

        try {
            schedulerService.gameAM8();
            log.info("testAM8() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("testAM8() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "오후 6시 미션 미수행자 알림 테스트", description = "오후 6시 미션 미수행자 알림 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 게임 배정 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "랜덤 게임 배정 실패", content = @Content) })
    @GetMapping("/6pm")
    public ResponseEntity<?> testPM6(HttpServletRequest request) {
        log.info("testPM6() -> Start");

        int id = (int) request.getAttribute("id");
        log.info("testPM6() -> Receive id : {}", id);

        try {
            schedulerService.gamePM6();
            log.info("testPM6() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("testPM6() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "오후 8시 랜덤 게임 투표 결과 테스트", description = "오후 8시 랜덤 게임 투표 결과 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 게임 배정 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "랜덤 게임 배정 실패", content = @Content) })
    @GetMapping("/8pm")
    public ResponseEntity<?> testPM8(HttpServletRequest request) {
        log.info("testPM8() -> Start");

        int id = (int) request.getAttribute("id");
        log.info("testPM8() -> Receive id : {}", id);

        try {
            schedulerService.gamePM8();
            log.info("testPM8() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("testPM8() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "미션 미이행자 flag_die 수정 테스트", description = "미션 미이행자 flag_die 수정 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 게임 배정 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "랜덤 게임 배정 실패", content = @Content) })
    @GetMapping("/nonCompleter")
    public ResponseEntity<?> testNonCompleter(HttpServletRequest request) {
        log.info("testNonCompleter() -> Start");

        int id = (int) request.getAttribute("id");
        log.info("testNonCompleter() -> Receive id : {}", id);

        try {
            schedulerService.manageNonCompleter("71a7d310-fe99-4c5d-b886-dce4bbcfe27a");
            log.info("testNonCompleter() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("testNonCompleter() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
