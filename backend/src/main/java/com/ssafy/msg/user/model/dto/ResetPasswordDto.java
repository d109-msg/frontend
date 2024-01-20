package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "임시 비밀번호 발송에 대한 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDto {
	@Schema(description = "이메일 아이디", nullable = false, example = "hong@ssafy.com")
	@NotEmpty(message = "이메일을 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식에 맞지 않습니다.")
	private String emailId;
}
