package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "본인정보 조회에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
	@Schema(description = "유저 아이디", nullable = false)
	private int id;

	@Schema(description = "닉네임", nullable = false)
	private String nickname;
	
	@Schema(description = "이메일 아이디", nullable = false)
	private String emailId;
	
	@Schema(description = "Oauth2 제공자", nullable = false)
	private String provider;
	
	@Schema(description = "회원 식별 아이디", nullable = true)
	private String identifier;
	
	@Schema(description = "회원 식별 아이디 수정 여부 (1번만 수정 가능)", nullable = true)
	private String flagIdentifier;
	
	@Schema(description = "관리자 여부", nullable = false)
	private int flagAdmin;
	
	@Schema(description = "최근 로그인 시간", nullable = false)
	private String signInTime;
	
	@Schema(description = "회원가입 시간", nullable = false)
	private String signUpTime;
	
	@Schema(description = "비공개 여부", nullable = false)
	private int flagPrivate;
	
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
	
}
