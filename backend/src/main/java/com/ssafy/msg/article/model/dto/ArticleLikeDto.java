package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleLikeDto {
    //유저 정보
    private String nickname;
    private String imageUrl;

    private int id;
    private int articleId;
    private int userId;
    private String createTime;

}
