package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "액세스 토큰 재발급에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {
	@Schema(description = "JWT Access Token", nullable = false)
	private String accessToken;
}
