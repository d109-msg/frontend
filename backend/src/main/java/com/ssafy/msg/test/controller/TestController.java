package com.ssafy.msg.test.controller;


import com.ssafy.msg.message.model.dto.MessageIdDto;
import com.ssafy.msg.message.model.dto.MessageRequestDto;
import com.ssafy.msg.message.model.entity.MessageEntity;
import com.ssafy.msg.message.util.DateTimeUtil;
import com.ssafy.msg.notification.model.entity.NotificationEntity;
import com.ssafy.msg.message.model.repo.MessageRepository;
import com.ssafy.msg.notification.model.repo.NotificationRepository;
import com.ssafy.msg.notification.model.dto.NotificationIdDto;
import com.ssafy.msg.notification.model.dto.NotificationRequestDto;
import com.ssafy.msg.notification.model.dto.NotificationUserIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j

@Tag(name = "Test", description = "테스트 관련 API")
public class TestController {

    @Value("${server.env}")
    private String serverEnv;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server-address}")
    private String serverAddress;

    @Value("${server-name}")
    private String serverName;

    private final MessageRepository messageRepository;
    private final NotificationRepository notificationRepository;
    private final DateTimeUtil dateTimeUtil;

    @Operation(summary = "서버 상태 확인", description = "서버 상태 확인")
    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> responseData = new TreeMap<>();
        responseData.put("serverAddress", serverAddress);
        responseData.put("serverName", serverName);
        responseData.put("serverPort", serverPort);
        responseData.put("serverEnv", serverEnv);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "서버 종류 확인", description = "서버 종류 확인")
    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(serverEnv);
    }

    // 기본적으로 MongoDB 관련 Entity, Repository는 mongodb 패키지에서 관리
    // 기본적으로 Redis 관련 Entity, Repository는 redis 패키지에서 관리
    // 다만 Dto는 원래 패키지에서 관리 (예를 들어 MessageRequestDto는 message 패키지에서 관리)

    @Operation(summary = "메시지 저장", description = "메시지 저장")
    @PostMapping("/mongodb/message/save")
    public ResponseEntity<?> saveMessage(@RequestBody MessageRequestDto messageRequestDto) {

        // MessageRequestDto를 MessageEntity로 변환
        MessageEntity messageEntity = messageRequestDto.toEntity();
        messageEntity.setCreateTime(dateTimeUtil.getCurrentDateTime());

//        System.out.println(messageEntity);

        // messageRepository를 이용하여 messageEntity를 저장
        messageRepository.save(messageEntity);

//        System.out.println(messageEntity);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "일반 알림 저장", description = "일반 알림 저장")
    @PostMapping("/mongodb/notification/save")
    public ResponseEntity<?> saveNotification(@RequestBody NotificationRequestDto notificationRequestDto) {

        // NotificationRequestDto를 NotificationEntity로 변환
        NotificationEntity notificationEntity = notificationRequestDto.toEntity();
        notificationEntity.setCreateTime(dateTimeUtil.getCurrentDateTime());

//        System.out.println(notificationEntity);

        // notificationRepository를 이용하여 notificationEntity를 저장
        notificationRepository.save(notificationEntity);

//        System.out.println(notificationEntity);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "일반 알림 클릭 시 읽음 처리", description = "일반 알림 클릭 시 읽음 처리")
    @PatchMapping("/mongodb/notification/update")
    public ResponseEntity<?> updateNotification(@RequestBody NotificationIdDto notificationIdDto) {

        // NotificationIdDto를 이용하여 해당 알림을 찾아서 flagRead를 1로 변경
        NotificationEntity notificationEntity = notificationRepository.findById(notificationIdDto.getId()).orElse(null);
        notificationEntity.setFlagRead(1);

        // 변경된 notificationEntity를 저장 (기본적으로 Repository로 update하려면 save 메소드를 사용; 기존 Entity에서 해당 프로퍼티만 수정)
        notificationRepository.save(notificationEntity);

        // 변경된 notificationEntity 출력
//        System.out.println(notificationEntity);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "userId에 해당하는 회원의 알림 전체 조회", description = "userId에 해당하는 회원의 알림 전체 조회")
    @GetMapping("/mongodb/notification/{userId}")
    public ResponseEntity<?> getNotification(int userId) {

        List<NotificationEntity> notifications = notificationRepository.findByUserId(userId);

        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "roomId에 해당하는 채팅 전체 조회", description = "roomId에 해당하는 채팅 전체 조회")
    @GetMapping("/mongodb/message/{roomId}")
    public ResponseEntity<?> getMongo(String roomId) {

        List<MessageEntity> messages = messageRepository.findByRoomId(roomId);

        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Id에 해당하는 채팅 조회", description = "Id에 해당하는 채팅 조회")
    @PostMapping("/mongodb/message")
    public ResponseEntity<?> getMongoById(@RequestBody MessageIdDto messageIdDto) {

        MessageEntity message = messageRepository.findById(messageIdDto.getId()).orElse(null);

        return ResponseEntity.ok(message);
    }

    @Operation(summary = "알림 전체 읽음 처리", description = "알림 전체 읽음 처리")
    @PatchMapping("/mongodb/notification/update-all")
    public ResponseEntity<?> updateAllNotification(@RequestBody NotificationUserIdDto notificationUserIdDto) {

        // NotificationUserIdDto를 이용하여 해당 회원의 모든 알림을 찾아서 flagRead를 1로 변경
        List<NotificationEntity> notifications = notificationRepository.findByUserId(notificationUserIdDto.getUserId());
        for (NotificationEntity notification : notifications) {
            notification.setFlagRead(1);
        }

        // 변경된 notificationEntity를 저장 (기본적으로 Repository로 update하려면 save 메소드를 사용; 기존 Entity에서 해당 프로퍼티만 수정)
        notificationRepository.saveAll(notifications);

        // 변경된 notificationEntity 출력
//        System.out.println(notifications);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "시간 테스트", description = "시간 테스트")
    @GetMapping("/time")
    public ResponseEntity<?> time() {
        long epochTime = 1707119084074L; // Epoch time in milliseconds

        Instant instant = Instant.ofEpochMilli(epochTime);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        System.out.println(formattedDateTime);

        return ResponseEntity.ok(formattedDateTime);
    }


}
