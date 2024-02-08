package com.ssafy.msg.notification.model.service;

import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.article.model.dto.CommentLikeDto;
import com.ssafy.msg.message.util.DateTimeUtil;
import com.ssafy.msg.notification.model.dto.NotificationResponseDto;
import com.ssafy.msg.notification.model.entity.NotificationEntity;
import com.ssafy.msg.notification.model.mapper.NotificationMapper;
import com.ssafy.msg.notification.model.repo.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final DateTimeUtil dateTimeUtil;
    private final SimpMessageSendingOperations sendingOperations;

    @Override
    public List<NotificationResponseDto> getNotificationsById(int userId) {
        List<NotificationEntity> notifications = notificationRepository.findByUserId(userId);

        return notifications.stream()
                .map(NotificationEntity::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public void sendArticleLikeNotice(int fromId, int articleId) {
        // 누른 사람의 닉네임 조회
        String fromNickname = notificationMapper.getNicknameById(fromId);

        // 게시자 아이디 조회
        int toId = notificationMapper.getUserIdByArticleId(articleId);

        // 본인의 게시글에 본인이 좋아요를 누른 경우 제외
        if (fromId != toId){
            // MongoDB 저장
            NotificationEntity notificationEntity = NotificationEntity.builder()
                    .userId(toId)
                    .content(fromNickname + " 님이 회원님의 게시글을 좋아합니다.")
                    .createTime(dateTimeUtil.getCurrentDateTime())
                    .flagRead(0)
                    .dataType("noti") // noti, sub
                    .build();

            notificationRepository.save(notificationEntity);

            // WebSocket/STOMP 메시지 전송
            sendingOperations.convertAndSend("/sub/"+notificationEntity.toDto().getUserId(), notificationEntity.toDto());
            log.info(notificationEntity.toDto().getUserId() + " - " + notificationEntity.toDto());

            // WebPush
        }
    }

    @Override
    public void sendCommentNotice(CommentDto commentDto) {
        // 댓글 작성자 닉네임 조회
        String fromNickname = notificationMapper.getNicknameById(commentDto.getUserId());

        NotificationEntity notificationEntity = null;

        // 게시물 작성자에게 알림
        // -> 게시자 아이디와, 댓글 작성자 아이디가 일치하지 않을 때만 알림
        if (commentDto.getParentCommentId() == null){
            // 게시자 아이디 조회
            int toId = notificationMapper.getUserIdByArticleId(commentDto.getArticleId());

            if (commentDto.getUserId() != toId) {
                notificationEntity = NotificationEntity.builder()
                        .userId(toId)
                        .content(fromNickname + " 님이 회원님의 게시글에 댓글을 남겼습니다.\n\"" + commentDto.getContent() + "\"")
                        .createTime(dateTimeUtil.getCurrentDateTime())
                        .flagRead(0)
                        .dataType("noti") // noti, sub
                        .build();
            }
        }
        // parent 댓글 작성자에게 알림
        // -> parent 댓글 작성자 아이디와, 댓글 작성자 아이디가 일치하지 않을 때만 알림
        else{
            // parent 댓글 작성자 아이디 조회
            int parentId = notificationMapper.getUserIdByCommentId(commentDto.getParentCommentId());

            if (commentDto.getUserId() != parentId){
                notificationEntity = NotificationEntity.builder()
                        .userId(parentId)
                        .content(fromNickname + " 님이 회원님의 댓글에 답글을 남겼습니다.\n\""+commentDto.getContent()+"\"")
                        .createTime(dateTimeUtil.getCurrentDateTime())
                        .flagRead(0)
                        .dataType("noti") // noti, sub
                        .build();
            }
        }

        if (notificationEntity != null){
            // MongoDB 저장
            notificationRepository.save(notificationEntity);

            // WebSocket/STOMP 메시지 전송
            sendingOperations.convertAndSend("/sub/"+notificationEntity.toDto().getUserId(), notificationEntity.toDto());
            log.info(notificationEntity.toDto().getUserId() + " - " + notificationEntity.toDto());

            // WebPush
        }
    }

    @Override
    public void sendCommentLikeNotice(CommentLikeDto commentLikeDto) {
        // 누른 사람의 닉네임 조회
        String fromNickname = notificationMapper.getNicknameById(commentLikeDto.getUserId());

        // 댓글 작성자 아이디 조회
        int toId = notificationMapper.getUserIdByCommentId(commentLikeDto.getCommentId());

        // 본인의 댓글에 본인이 좋아요를 누른 경우 제외
        if (commentLikeDto.getUserId() != toId){
            // MongoDB 저장
            NotificationEntity notificationEntity = NotificationEntity.builder()
                    .userId(toId)
                    .content(fromNickname + " 님이 회원님의 댓글을 좋아합니다.")
                    .createTime(dateTimeUtil.getCurrentDateTime())
                    .flagRead(0)
                    .dataType("noti") // noti, sub
                    .build();

            notificationRepository.save(notificationEntity);

            // WebSocket/STOMP 메시지 전송
            sendingOperations.convertAndSend("/sub/"+notificationEntity.toDto().getUserId(), notificationEntity.toDto());
            log.info(notificationEntity.toDto().getUserId() + " - " + notificationEntity.toDto());

            // WebPush
        }
    }

    @Override
    public void sendGameStartNotice() {

    }
}
