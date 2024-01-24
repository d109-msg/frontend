package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원 팔로우/취소에 대한 요청 DTO")
@Data
@NoArgsConstructor	
@AllArgsConstructor
@Builder
public class FollowDto {

	@Schema(description = "아이디 번호", nullable = false)
	private int id;
}
