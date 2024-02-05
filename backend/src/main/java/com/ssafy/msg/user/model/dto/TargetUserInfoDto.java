package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "타인정보 조회에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetUserInfoDto {
    @Schema(description = "유저 아이디", nullable = false)
    private int id;

    @Schema(description = "닉네임", nullable = false)
    private String nickname;

    @Schema(description = "이메일 아이디", nullable = false)
    private String emailId;

    @Schema(description = "회원 식별 아이디", nullable = true)
    private String identifier;

    @Schema(description = "프로필 이미지 url", nullable = false)
    private String imageUrl;

    @Schema(description = "프로필 이미지 uuid", nullable = false)
    private String imageUuid;

    @Schema(description = "자기소개", nullable = true)
    private String bio;

    @Schema(description = "게시물 갯수", nullable = false)
    private int articleCount;

    @Schema(description = "나를 팔로우 하고 있는 수")
    private int followerCount;

    @Schema(description = "내가 팔로우 하고 있는 수")
    private int followingCount;

    @Schema(description = "접속 유저의 팔로우 여부")
    private int isFollow;
}
