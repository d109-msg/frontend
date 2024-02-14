package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 접속 여부는 추후 필요하면 추가
 */
@Schema(description = "팔로워(to)/팔로잉(from) 회원")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowUserDto {
	@Schema(description = "팔로우 테이블 번호", nullable = false)
	private int id;

	@Schema(description = "유저 아이디", nullable = false)
	private int userId;
	
	@Schema(description = "닉네임", nullable = false)
	private String nickname;
	
	@Schema(description = "이메일 아이디", nullable = false)
	private String emailId;
	
	@Schema(description = "프로필 이미지 url", nullable = false)
    private String imageUrl;
	
	@Schema(description = "프로필 이미지 uuid", nullable = false)
	private String imageUuid;
	
	@Schema(description = "회원 식별 아이디", nullable = true)
	private String identifier;

	@Schema(description = "팔로잉 여부", nullable = false)
	private int isFollow;
}
