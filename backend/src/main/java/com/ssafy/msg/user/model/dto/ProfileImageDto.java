package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "프로필 이미지 수정에 대한 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImageDto {

	@Schema(description = "프로필 이미지 uuid", nullable = false)
	private String imageUuid;
	
	@Schema(description = "프로필 이미지 url", nullable = false)
	private String imageUrl;
	
	@Schema(description = "이메일 아이디", nullable = false)
	private String emailId;
}
