package com.ssafy.msg.notification.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoDto {
    private int toId;   // 댓글 작성자 아이디 (알림 대상)
    private String imageUrl;    // 게시물의 첫 번째 사진
    private int articleId;  // 게시물 아이디
}
