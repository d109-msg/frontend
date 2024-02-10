package com.ssafy.msg.message.controller;

import com.ssafy.msg.message.model.dto.MessageIdDto;
import com.ssafy.msg.message.model.dto.MessageRequestDto;
import com.ssafy.msg.message.model.dto.MessageScrollResponseDto;
import com.ssafy.msg.message.model.dto.StompPrincipalDto;
import com.ssafy.msg.message.model.entity.MessageEntity;
import com.ssafy.msg.message.model.repo.MessageRepository;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.notification.model.dto.NotificationIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j

@Tag(name="Message", description="메시지 관련 API")
public class MessageController {

    private final MessageService messageService;
    private final MessageRepository messageRepository;

    @MessageMapping("/message")
    public void sendMessage(MessageRequestDto messageRequestDto, Principal principal){
        if (principal instanceof StompPrincipalDto) {
            StompPrincipalDto stompPrincipalDto = (StompPrincipalDto) principal;
            int userId = stompPrincipalDto.getUserId();

            try {
                messageService.sendMessage(messageRequestDto, userId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Operation(summary = "roomId에 해당하는 채팅 무한스크롤 조회", description = "roomId에 해당하는 채팅 무한스크롤 조회")
    @GetMapping("/message/scroll")
    public ResponseEntity<?> getMessagesByScroll(
            @Parameter(description = "방 아이디") @RequestParam(value = "room-id", required = true) String roomId,
            @Parameter(description = "로딩 기준이 되는 메시지 아이디") @RequestParam(value = "offset", required = false, defaultValue = "start") String offset,
            @Parameter(description = "요청당 로딩할 메시지 수") @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        log.info("roomId: " + roomId);
        log.info("offset: " + offset);
        log.info("limit: " + limit);

        List<MessageEntity> messages = null;

        // offset이 "start"이면 첫 요청이므로 첫 번째 메시지부터 limit개의 메시지를 가져옴 (역순으로 정렬시킨 상태에서 limit개의 메시지를 가져와서 최신 메시지부터 보여줌)
        if ("start".equals(offset)) {
            messages = messageRepository.findMessagesByRoomIdOrderByDescending(roomId, limit);
        } else {
            ObjectId objectId = new ObjectId(offset);
            messages = messageRepository.findMessagesByRoomIdAndIdLessThan(roomId, objectId, limit);
        }
        // 메시지가 없으면 null 반환 (무한스크롤 끝)
        if (messages.isEmpty()) {
            return null;
        }

        // 다음 요청 URL
        String nextUrl = "?room-id=" + roomId + "&offset=" + messages.get(messages.size() - 1).getId() + "&limit=" + limit;
        MessageScrollResponseDto messageScrollResponseDto = MessageScrollResponseDto.builder()
                .messageResponseDtos(messages.stream()
                        .map(MessageEntity::toDto)
                        .collect(Collectors.toList()))
                .nextUrl(nextUrl)
                .build();
        return ResponseEntity.ok(messageScrollResponseDto);
    }


//    @Operation(summary = "채팅방 lastMessageId 수정", description = "roomId와 messageId를 통해 유저가 마지막으로 본 메시지 업데이트")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "채팅방 lastMessageId 수정 성공", content = @Content),
//            @ApiResponse(responseCode = "400", description = "채팅방 lastMessageId 수정 실패", content = @Content) })
//    @PatchMapping("/message/lastMessageId")
//    public ResponseEntity<?> updateLastMessageId(@Valid HttpServletRequest request, @RequestBody MessageIdDto messageIdDto) {
//        log.info("updateLastMessageId() -> Start");
//
//        int userId = (int) request.getAttribute("id");
//
//
//        try {
//            notificationService.updateNotificationFlagRead(notificationIdDto);
//            log.info("updateLastMessageId() -> Success");
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("updateLastMessageId() -> Exception : {}", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } finally {
//            log.info("updateLastMessageId() -> End");
//        }
//    }
}