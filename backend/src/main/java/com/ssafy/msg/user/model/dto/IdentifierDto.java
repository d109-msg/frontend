package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "식별자 수정에 대한 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentifierDto {
	
    @Schema(description = "식별자", nullable = false, example = "hongssafy")
    @NotEmpty(message = "식별자를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-힣]{2,20}$", message = "식별자는 2-20자의 영문, 한글, 숫자만 가능하며 공백은 허용되지 않습니다.")
    private String identifier;
}
