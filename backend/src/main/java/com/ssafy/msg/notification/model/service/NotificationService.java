package com.ssafy.msg.notification.model.service;

import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.article.model.dto.CommentLikeDto;
import com.ssafy.msg.notification.model.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    // 알림 목록 조회
    List<NotificationResponseDto> getNotificationsById(int userId);

    // 알림 전체 읽음 처리

    // 알림 개별 읽음 처리



    // 게시물 좋아요
    void sendArticleLikeNotice(int fromId, int articleId);

    // 게시물 댓글
    void sendCommentNotice(CommentDto commentDto);

    // 댓글 좋아요
    void sendCommentLikeNotice(CommentLikeDto commentLikeDto);


    // 게임 시작
    void sendGameStartNotice();


}
