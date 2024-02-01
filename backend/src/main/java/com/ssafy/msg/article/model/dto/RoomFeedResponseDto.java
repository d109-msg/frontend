package com.ssafy.msg.article.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "게임방 Feed 목록 조회에 대한 응답 DTO")
public class RoomFeedResponseDto {
    @Schema(description = "feed 리스트")
    private List<RoomArticleResponseDto> articles;

    @Schema(description = "다음 페이지를 가져오기 위한 URL")
    private String nextUrl;
}
