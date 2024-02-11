package com.ssafy.msg.notification.model.service;

import com.ssafy.msg.article.model.dto.CommentDto;
import com.ssafy.msg.article.model.dto.CommentLikeDto;
import com.ssafy.msg.message.util.DateTimeUtil;
import com.ssafy.msg.notification.model.dto.ArticleInfoDto;
import com.ssafy.msg.notification.model.dto.CommentInfoDto;
import com.ssafy.msg.notification.model.dto.NotificationIdDto;
import com.ssafy.msg.notification.model.dto.NotificationResponseDto;
import com.ssafy.msg.notification.model.entity.NotificationEntity;
import com.ssafy.msg.notification.model.mapper.NotificationMapper;
import com.ssafy.msg.notification.model.repo.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
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
        List<NotificationEntity> notifications = notificationRepository.findByUserIdAndFlagRead(userId, 0);

        return notifications.stream()
                .map(NotificationEntity::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public void updateAllNotificationsFlagRead(int userId) {
        List<NotificationEntity> notifications = notificationRepository.findByUserIdAndFlagRead(userId, 0);
        for (NotificationEntity notification : notifications) {
            notification.setFlagRead(1);
        }

        notificationRepository.saveAll(notifications);
    }

    @Override
    public void updateNotificationFlagRead(NotificationIdDto notificationIdDto) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationIdDto.getId()).orElse(null);

        notificationEntity.setFlagRead(1);

        notificationRepository.save(notificationEntity);
    }

    @Override
    public void sendArticleLikeNotice(int fromId, int articleId) {
        // 누른 사람의 닉네임 조회
        String fromNickname = notificationMapper.getNicknameById(fromId);

        // 게시글 정보 조회
        ArticleInfoDto articleInfoDto = notificationMapper.getArticleInfoById(articleId);

        // 본인의 게시글에 본인이 좋아요를 누른 경우 제외
        if (fromId != articleInfoDto.getToId()){
            // MongoDB 저장
            NotificationEntity notificationEntity = NotificationEntity.builder()
                    .userId(articleInfoDto.getToId())
                    .fromNickname(fromNickname)
                    .articleId(articleId)
                    .imageUrl(articleInfoDto.getImageUrl())
                    .content(" 님이 회원님의 게시글을 좋아합니다.")
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
            ArticleInfoDto articleInfoDto = notificationMapper.getArticleInfoById(commentDto.getArticleId());

            if (commentDto.getUserId() != articleInfoDto.getToId()) {
                notificationEntity = NotificationEntity.builder()
                        .userId(articleInfoDto.getToId())
                        .fromNickname(fromNickname)
                        .articleId(commentDto.getArticleId())
                        .imageUrl(articleInfoDto.getImageUrl())
                        .content(" 님이 회원님의 게시글에 댓글을 남겼습니다.\n\"" + commentDto.getContent() + "\"")
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
            CommentInfoDto commentInfoDto = notificationMapper.getCommentInfoById(commentDto.getParentCommentId());

            if (commentDto.getUserId() != commentInfoDto.getToId()){
                notificationEntity = NotificationEntity.builder()
                        .userId(commentInfoDto.getToId())
                        .fromNickname(fromNickname)
                        .articleId(commentDto.getArticleId())
                        .imageUrl(commentInfoDto.getImageUrl())
                        .content(" 님이 회원님의 댓글에 답글을 남겼습니다.\n\""+commentDto.getContent()+"\"")
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
        CommentInfoDto commentInfoDto = notificationMapper.getCommentInfoById(commentLikeDto.getCommentId());

        // 본인의 댓글에 본인이 좋아요를 누른 경우 제외
        if (commentLikeDto.getUserId() != commentInfoDto.getToId()){
            // MongoDB 저장
            NotificationEntity notificationEntity = NotificationEntity.builder()
                    .userId(commentInfoDto.getToId())
                    .fromNickname(fromNickname)
                    .articleId(commentInfoDto.getArticleId())
                    .imageUrl(commentInfoDto.getImageUrl())
                    .content(" 님이 회원님의 댓글을 좋아합니다.")
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
    public void sendGameStartNotice(int userId, String title, String type) {
        String content = "새로운 " + type + " 게임이 시작되었습니다. - " + title;

        // MongoDB 저장
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .userId(userId)
                .imageUrl("https://team109testbucket.s3.ap-northeast-2.amazonaws.com/2c5954d7-2aec-4cac-9c67-9ada52a1eafb")
                .content(content)
                .createTime(dateTimeUtil.getCurrentDateTime())
                .flagRead(0)
                .dataType("noti") // noti, sub
                .build();

        notificationRepository.save(notificationEntity);

        // WebSocket/STOMP 메시지 전송
        sendingOperations.convertAndSend("/sub/"+userId, content);
        log.info(userId + " - " + content);
    }

    @Override
    public void sendRoomSubscribeRequest(int userId, String roomId) {

        NotificationResponseDto notificationResponseDto = NotificationResponseDto.builder()
                .userId(userId)
                .content(roomId)
                .dataType("sub") // noti, sub
                .build();

        // WebSocket/STOMP 메시지 전송
        sendingOperations.convertAndSend("/sub/"+userId, notificationResponseDto);
        log.info(notificationResponseDto.getUserId() + " - " + notificationResponseDto);
    }
}
