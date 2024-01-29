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

    @Schema(description = "진행되는 날", example = "2")
    int day;

    @Schema(description = "시도 횟수", example = "3")
    int cntTry;

    @Schema(description = "데일리미션 성공 여부 1성공 0실패", example = "0")
    int flagSuccess;
}
