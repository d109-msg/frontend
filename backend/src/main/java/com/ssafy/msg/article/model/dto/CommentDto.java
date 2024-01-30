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
    private int id;
    private String nickname;
    private int userId;
    private int articleId;
    private Integer parentCommentId;
    private String content;
    private String createTime;

    private int commentLikeCount;

}
