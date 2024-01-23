package com.ssafy.msg.user.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Schema(description = "팔로워(to)/팔로잉(from) 목록 조회에 대한 응답 DTO")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FollowResponseDto {

    @Schema(description = "팔로우(to)/팔로잉(from) 회원 목록")
    private List<FollowUserDto> followUserList;

    @Schema(description = "다음 페이지를 가져오기 위한 URL")
    private String nextUrl;
	
}
