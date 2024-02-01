package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomArticleResultDto {
    private int articleId;
    private int userId;
    private int participantId;
    private String nickname;
    private String iconUrl;
    private String content;
    private String createTime;
    private String urls;
}
