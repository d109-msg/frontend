package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomArticleResponseDto {
    private int articleId;
    private int userId;
    private int participantId;
    private String nickname;
    private String iconUrl;
    private String content;
    private String createTime;
    private List<String> urls;

    public RoomArticleResponseDto(RoomArticleResultDto dto) {
        this.articleId = dto.getArticleId();
        this.userId = dto.getUserId();
        this.participantId = dto.getParticipantId();
        this.nickname = dto.getNickname();
        this.iconUrl = dto.getIconUrl();
        this.content = dto.getContent();
        this.createTime = dto.getCreateTime();

        String[] urlArray = dto.getUrls().split(",");
        // 배열을 리스트로 변환
        this.urls = Arrays.asList(urlArray);
    }
}
