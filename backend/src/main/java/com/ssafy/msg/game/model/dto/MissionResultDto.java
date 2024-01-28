package com.ssafy.msg.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionResultDto {
    @Schema(description = "일반 시민 미션", example = "자연 사진")
    String normalMission;

    @Schema(description = "마피아 미션", example = "나무 사진")
    String mafiaMission;
}
