package com.ssafy.msg.test.controller;


import com.ssafy.msg.message.model.dto.MessageIdDto;
import com.ssafy.msg.message.model.dto.MessageRequestDto;
import com.ssafy.msg.message.model.dto.MessageScrollResponseDto;
import com.ssafy.msg.message.model.entity.MessageEntity;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.message.util.DateTimeUtil;
import com.ssafy.msg.notification.model.entity.NotificationEntity;
import com.ssafy.msg.message.model.repo.MessageRepository;
import com.ssafy.msg.notification.model.repo.NotificationRepository;
import com.ssafy.msg.notification.model.dto.NotificationIdDto;
import com.ssafy.msg.notification.model.dto.NotificationUserIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    private final MessageService messageService;

    /*
    서버 상태 확인 start =============================================================
     */
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
    /*
    서버 상태 확인 end =============================================================
     */



    /*
    메시지 api start =============================================================
     */
    @Operation(summary = "메시지 저장 및 전송", description = "메시지 저장 및 전송")
    @PostMapping("/send/message")
    public ResponseEntity<?> sendMessage( @RequestBody MessageRequestDto messageRequestDto) {

        int id = 1;

        try {
            messageService.sendMessage(messageRequestDto, id);
            return ResponseEntity.ok("success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    @Operation(summary = "메시지 저장", description = "메시지 저장")
//    @PostMapping("/mongodb/message/save")
//    public ResponseEntity<?> saveMessage(@RequestBody MessageRequestDto messageRequestDto) {
//
//        // MessageRequestDto를 MessageEntity로 변환
//        MessageEntity messageEntity = messageRequestDto.toEntity();
//
//        // 실제로는 프론트 측에서 dataType을 적어서 줘야 할 듯함 (아님 말고..)
//        // 공지의 경우 여기에서 set하고 저장
//        messageEntity.setDataType("chat"); // chat, image, notice
//
//        messageEntity.setCreateTime(dateTimeUtil.getCurrentDateTime());
//
//        // messageRepository를 이용하여 messageEntity를 저장
//        messageRepository.save(messageEntity);
//
//        return ResponseEntity.ok("success");
//    }

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
    /*
    메시지 api end =============================================================
     */



    /*
    알림 api start =============================================================
     */
    @Operation(summary = "일반 알림 저장", description = "일반 알림 저장")
    @PostMapping("/mongodb/notification/save")
    public ResponseEntity<?> saveNotification() {

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .userId(1)
                .content("알림 테스트")
                .createTime(dateTimeUtil.getCurrentDateTime())
                .flagRead(0)
                .dataType("noti") // noti, sub
                .build();

        notificationRepository.save(notificationEntity);

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

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "userId에 해당하는 회원의 알림 전체 조회", description = "userId에 해당하는 회원의 알림 전체 조회")
    @GetMapping("/mongodb/notification/{userId}")
    public ResponseEntity<?> getNotification(int userId) {

        List<NotificationEntity> notifications = notificationRepository.findByUserId(userId);

        return ResponseEntity.ok(notifications);
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

        return ResponseEntity.ok("success");
    }
    /*
    알림 api end =============================================================
     */

    /*
    채팅 무한스크롤 start =============================================================
     */
    @Operation(summary = "roomId에 해당하는 채팅 무한스크롤 조회", description = "roomId에 해당하는 채팅 무한스크롤 조회")
    @GetMapping("/mongodb/message/scroll")
    public ResponseEntity<?> getMessagesByScroll(
            @Parameter(description = "방 아이디") @RequestParam(value = "room-id", required = true) String roomId,
            @Parameter(description = "로딩한 누적 메시지 수") @RequestParam(value = "offset", required = false, defaultValue = "-1") Integer offset,
            @Parameter(description = "요청당 로딩할 메시지 수") @RequestParam(value = "limit", required = false, defaultValue = "29") Integer limit) {
        log.info("roomId: " + roomId);
        log.info("offset: " + offset);
        log.info("limit: " + limit);

        List<MessageEntity> messages = null;
        Pageable pageable = null;

        // offset이 -1이면 첫 요청이므로 첫 번째 메시지부터 limit개의 메시지를 가져옴 (역순으로 정렬시킨 상태에서 limit개의 메시지를 가져와서 최신 메시지부터 보여줌)
        if (offset == -1) {
            pageable = PageRequest.of(0, limit);
        } else {
            // offset이 -1이 아니면 offset부터 limit개의 메시지를 가져옴
            pageable = PageRequest.of(offset, limit);
        }
        messages = messageRepository.findMessagesByRoomIdWithLimit(roomId, pageable);
        // 메시지가 없으면 null 반환 (무한스크롤 끝)
        if (messages.isEmpty()) {
            return null;
        }

        Collections.reverse(messages);

        // 다음 요청 URL
        String nextUrl = "?room-id=" + roomId + "&offset=" + (offset + limit) + "&limit=" + limit;
        MessageScrollResponseDto messageScrollResponseDto = MessageScrollResponseDto.builder()
                .messageResponseDtos(messages.stream()
                        .map(MessageEntity::toDto)
                        .collect(Collectors.toList()))
                .nextUrl(nextUrl)
                .build();
        return ResponseEntity.ok(messageScrollResponseDto);
    }


    /*
    채팅 무한스크롤 end =============================================================
     */


    /*
    시간 테스트 start =============================================================
     */
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
    /*
    시간 테스트 end =============================================================
     */


}
