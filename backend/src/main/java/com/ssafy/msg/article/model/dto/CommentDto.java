package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    // 유저 정보
    private String nickname;
    private String imageUrl;

    private int id;
    private int userId;
    private int articleId;
    private Integer parentCommentId;
    private String content;
    private String createTime;

    private int commentLikeCount;

}
