package com.ssafy.msg.article.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Schema(description = "feed 목록 페이지 정보")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FeedParamDto {
    @Schema(description = "아이디 번호")
    private int userId;

    @Schema(description = "시작 타겟 번호")
    private int offset;

    @Schema(description = "페이지당 타겟 개수")
    private int limit;

    private String currentUrl;
}
