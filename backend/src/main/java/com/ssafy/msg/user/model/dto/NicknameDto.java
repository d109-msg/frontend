package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "닉네임 수정에 대한 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NicknameDto {
	
	@Schema(description = "닉네임", nullable = false, example = "홍싸피")
	@NotEmpty(message = "닉네임을 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9ㄱ-힣 ]{1,20}$", message = "닉네임은 1-20자의 영문, 한글, 숫자, 공백만 가능합니다.")
	private String nickname;
}
