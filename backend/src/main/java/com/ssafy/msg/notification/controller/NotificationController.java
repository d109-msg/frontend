package com.ssafy.msg.notification.controller;

import com.ssafy.msg.notification.model.dto.NotificationIdDto;
import com.ssafy.msg.notification.model.service.NotificationService;
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
import java.util.Map;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Slf4j

@Tag(name="Notification", description="알림 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 조회", description = "회원 아이디에 따른 알림 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 목록 조회 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "알림 목록 조회 실패", content = @Content) })
    @GetMapping
    public ResponseEntity<?> getNotificationsById(HttpServletRequest request) {
        log.info("getNotificationsById() -> Start");

        int id = (int) request.getAttribute("id");
        log.info("getNotificationsById() -> Receive id : {}", id);


        try {
            log.info("getNotificationsById() -> Success");
            return new ResponseEntity<>(notificationService.getNotificationsById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getNotificationsById() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "전체 알림 읽음 처리", description = "회원 아이디를 통해 전체 알림 읽음 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 알림 읽음 처리 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "전체 알림 읽음 처리 실패", content = @Content) })
    @PatchMapping("/all")
    public ResponseEntity<?> updateAllNotificationsFlagRead(@Valid HttpServletRequest request) {
        log.info("updateAllNotificationsFlagRead() -> Start");

        int id = (int) request.getAttribute("id");
        log.info("updateAllNotificationsFlagRead() -> Receive id : {}", id);

        try {
            notificationService.updateAllNotificationsFlagRead(id);
            log.info("updateAllNotificationsFlagRead() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("updateAllNotificationsFlagRead() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("updateAllNotificationsFlagRead() -> End");
        }
    }

    @Operation(summary = "개별 알림 읽음 처리", description = "Notification 아이디를 통해 해당 알림 읽음 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개별 알림 읽음 처리 성공", content = @Content),
            @ApiResponse(responseCode = "400", description = "개별 알림 읽음 처리 실패", content = @Content) })
    @PatchMapping("/single")
    public ResponseEntity<?> updateNotificationFlagRead(@Valid HttpServletRequest request, @RequestBody NotificationIdDto notificationIdDto) {
        log.info("updateNotificationFlagRead() -> Start");

        try {
            notificationService.updateNotificationFlagRead(notificationIdDto);
            log.info("updateNotificationFlagRead() -> Success");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("updateNotificationFlagRead() -> Exception : {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("updateNotificationFlagRead() -> End");
        }
    }
}
