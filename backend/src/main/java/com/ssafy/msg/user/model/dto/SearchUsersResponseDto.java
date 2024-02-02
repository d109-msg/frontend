package com.ssafy.msg.user.model.dto;

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
@Schema(description = "유저 검색에 대한 응답 dto")
public class SearchUsersResponseDto {
    @Schema(description = "검색된 유저 리스트")
    private List<SearchedUserDto> searchResult;

    @Schema(description = "다음 페이지를 가져오기 위한 URL")
    private String nextUrl;
}

