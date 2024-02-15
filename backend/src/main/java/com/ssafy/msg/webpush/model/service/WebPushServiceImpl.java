package com.ssafy.msg.webpush.model.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.webpush.model.dto.FCMTokenDto;
import com.ssafy.msg.webpush.model.mapper.WebPushMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebPushServiceImpl implements WebPushService{

    private final WebPushMapper webPushMapper;

    @Async
    @Override
    public void sendWebPush(int toId, String content) {
        // 수신자 fcm 토큰 조회
        String token = webPushMapper.getFCMTokenByUserId(toId);

        if (token != null){
            Message webPushMessage = Message.builder()
                    .putData("title", "MSG")
                    .putData("body", content)
                    .setToken(token)
                    .build();

            log.info("sendWebPush");
            log.info("");

            try {
                String response = FirebaseMessaging.getInstance().send(webPushMessage);
                log.info(response);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void registerFCMToken(FCMTokenDto fcmTokenDto) {
        webPushMapper.registerFCMToken(fcmTokenDto);
    }
}
