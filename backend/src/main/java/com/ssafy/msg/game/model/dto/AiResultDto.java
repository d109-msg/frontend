package com.ssafy.msg.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiResultDto {
    @Schema(description = "사진 검증 통과 여부", example = "false")
    private boolean result;

    @Schema(description = "result에 대한 이유", example = "사진에 나무가 없습니다.")
    private String reason;
}
