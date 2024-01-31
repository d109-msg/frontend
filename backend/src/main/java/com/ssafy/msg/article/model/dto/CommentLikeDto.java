package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDto {
    private String imageUrl;
    private String nickname;

    private int id;
    private int commentId;
    private int userId;
    private String createTime;
}
