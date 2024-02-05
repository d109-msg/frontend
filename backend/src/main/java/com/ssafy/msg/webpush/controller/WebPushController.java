package com.ssafy.msg.webpush.controller;


import com.ssafy.msg.webpush.model.dto.FCMTokenDto;
import com.ssafy.msg.webpush.model.dto.FCMTokenOnlyDto;
import com.ssafy.msg.webpush.model.service.WebPushService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller 설정
 * 1. @RestController로 REST API 컨트롤러 등록
 * 2. @RequestMapping으로 URI 매핑
 * 3. 의존성 주입이 필요한 경우 @RequiredArgsConstructor로 생성자 주입 (UserController에서는 jwtUtil, oauth2Utils, userService에 대한 의존성 주입)
 * 4. @Slf4j로 Logger 사용 (log.info, log.error, log.error 등으로 로그 출력)
 */
@RestController
@RequestMapping("/webpush")
@RequiredArgsConstructor
@Slf4j

@Tag(name="WebPush", description="웹 푸시 알림 관련 API")
public class WebPushController {

    private final WebPushService webPushService;

    @Operation(summary = "FCM 토큰 등록", description = "FCM 토큰 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FCM 토큰 등록 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "FCM 토큰 등록 실패", content = @Content) })
    @PostMapping
    public ResponseEntity<?> registerFCMToken(@Valid HttpServletRequest request, @RequestBody FCMTokenOnlyDto token) {
        log.info("registerFCMToken() -> Start");
        log.info("registerFCMToken() -> Receive token : {}", token);

        int id = (int) request.getAttribute("id");

        FCMTokenDto fcmTokenDto = FCMTokenDto.builder()
                .userId(id)
                .token(token.getToken()).build();

        try {
            webPushService.registerFCMToken(fcmTokenDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("registerFCMToken() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("registerFCMToken() -> End");
        }
    }


}
