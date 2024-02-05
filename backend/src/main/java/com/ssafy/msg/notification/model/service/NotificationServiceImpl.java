package com.ssafy.msg.notification.model.service;

import com.ssafy.msg.article.util.S3Util;
import com.ssafy.msg.game.model.dto.ParticipantDto;
import com.ssafy.msg.message.model.dto.ImageMessageDto;
import com.ssafy.msg.message.model.dto.MessageResponseDto;
import com.ssafy.msg.message.model.dto.TextMessageDto;
import com.ssafy.msg.message.model.mapper.MessageMapper;
import com.ssafy.msg.message.model.service.MessageService;
import com.ssafy.msg.notification.model.dto.NotificationDto;
import com.ssafy.msg.notification.model.dto.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
}
