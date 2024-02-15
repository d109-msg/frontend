package com.ssafy.msg.webpush.model.service;

import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.webpush.model.dto.FCMTokenDto;

public interface WebPushService {

    void sendWebPush(int toId, String content);

    void registerFCMToken(FCMTokenDto fcmTokenDto);
}
