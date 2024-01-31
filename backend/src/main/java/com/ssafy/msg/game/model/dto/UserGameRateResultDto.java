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
@Schema(description = "유저의 게임 플레이 통계를 위한 dto")
public class UserGameRateResultDto {
    @Schema(description = "총 플레이 횟수", example = "10")
    int totalGameCnt;

    @Schema(description = "총 승리 횟수", example = "5")
    int totalWinCnt;

    @Schema(description = "시민 플레이 횟수", example = "5")
    int civilGameCnt;

    @Schema(description = "시민 승리 횟수", example = "3")
    int civilWinCnt;

    @Schema(description = "마피아 플레이 횟수", example = "5")
    int mafiaGameCnt;

    @Schema(description = "마피아 승리 횟수", example = "2")
    int mafiaWinCnt;
}
