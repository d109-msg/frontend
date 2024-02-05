package com.ssafy.msg.webpush.model.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.webpush.model.dto.FCMTokenDto;
import com.ssafy.msg.webpush.model.mapper.WebPushMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebPushServiceImpl implements WebPushService{

    private final WebPushMapper webPushMapper;

    @Override
    public void sendArticleLikeWebPush(int fromId, int articleId) {
        // 게시자 id에 따른 fcm 토큰 조회
        String token = webPushMapper.getFCMTokenByArticleId(articleId);
        // 누른 사람의 닉네임 조회
        String nickname = webPushMapper.getNicknameById(fromId);

        Message webPushMessage = Message.builder()
                .putData("title", "MSG")
                .putData("body", nickname + " 님이 회원님의 게시글을 좋아합니다.")
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(webPushMessage);
            log.info(response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCommentWebPush(CommentDto commentDto) {
        // 게시자 id에 따른 fcm 토큰 조회
        String token = webPushMapper.getFCMTokenByArticleId(commentDto.getArticleId());
        // 댓글 작성자의 사람 닉네임 조회
        String nickname = webPushMapper.getNicknameById(commentDto.getUserId());
        // parent 댓글 작성자의 fcm 토큰 조회


        // 게시물의 댓글/대댓글 -> 게시물 작성자에게 알림
        Message message1 = Message.builder()
                .putData("title", "MSG")
                .putData("body", nickname + " 님이 회원님의 게시글에 댓글을 남겼습니다.\n\""+commentDto.getContent()+"\"")
                .setToken(token)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message1);
            log.info(response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }

        log.info(String.valueOf(commentDto.getParentCommentId()));

        if (commentDto.getParentCommentId() != null){
            // 댓글의 대댓글 -> 댓글 작성자에게도 알림
            String commentToken = webPushMapper.getFCMTokenByParentCommentId(commentDto.getParentCommentId());

            Message message2 = Message.builder()
                    .putData("title", "MSG")
                    .putData("body", nickname + " 님이 회원님의 댓글에 답글을 남겼습니다.\n\""+commentDto.getContent()+"\"")
                    .setToken(commentToken)
                    .build();
            try {
                String response = FirebaseMessaging.getInstance().send(message2);
                log.info(response);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void sendCommentLikeWebPush(int fromId, int articleId) {
        // 게시자 id에 따른 fcm 토큰 조회
        String token = webPushMapper.getFCMTokenByArticleId(articleId);
        // 누른 사람의 닉네임 조회
        String nickname = webPushMapper.getNicknameById(fromId);

        Message webPushMessage = Message.builder()
                .putData("title", "MSG")
                .putData("body", nickname + " 님이 회원님의 댓글을 좋아합니다.")
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(webPushMessage);
            log.info(response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendGameStartWebPush() {

    }

    @Override
    public void registerFCMToken(FCMTokenDto fcmTokenDto) {
        webPushMapper.registerFCMToken(fcmTokenDto);
    }
}
