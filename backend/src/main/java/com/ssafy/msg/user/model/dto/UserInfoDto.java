package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원정보 조회에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
	@Schema(description = "닉네임", nullable = false)
	private String nickname;
	
	@Schema(description = "이메일 아이디", nullable = false)
	private String emailId;
	
	@Schema(description = "Oauth2 제공자", nullable = false)
	private String provider;
	
	@Schema(description = "회원 식별 아이디", nullable = true)
	private String identifier;
	
	@Schema(description = "관리자 여부", nullable = false)
	private int flagAdmin;
	
	@Schema(description = "최근 로그인 시간", nullable = false)
	private String signInTime;
	
	@Schema(description = "회원가입 시간", nullable = false)
	private String signUpTime;
	
	@Schema(description = "비공개 여부", nullable = false)
	private int flagPrivate;
	
	@Schema(description = "프로필 이미지", nullable = false)
    private String imageUrl;
	
}
