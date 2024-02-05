package com.ssafy.msg.webpush.model.service;

import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.webpush.model.dto.FCMTokenDto;

public interface WebPushService {

    // 게시물 좋아요
    void sendArticleLikeWebPush(int fromId, int articleId);

    // 게시물 댓글
    void sendCommentWebPush(CommentDto commentDto);

    // 댓글 좋아요
    void sendCommentLikeWebPush(int fromId, int articleId);


    // 게임 시작
    void sendGameStartWebPush();

    void registerFCMToken(FCMTokenDto fcmTokenDto);
}
