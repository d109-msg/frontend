package com.ssafy.msg.article.model.dto;

import com.ssafy.msg.user.model.dto.FollowUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Schema(description = "Feed 목록 조회에 대한 응답 DTO")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FeedResponseDto {
    @Schema(description = "feed 목록")
    private List<ArticleDetailDto> articleDetailDtos;

    @Schema(description = "다음 페이지를 가져오기 위한 URL")
    private String nextUrl;
}
