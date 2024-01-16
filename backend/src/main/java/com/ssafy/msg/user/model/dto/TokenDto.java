package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
	
	@Schema(description = "액세스 토큰", nullable = false)
	private String accessToken;
	
	@Schema(description = "리프레시 토큰", nullable = false)
	private String refreshToken;
}
