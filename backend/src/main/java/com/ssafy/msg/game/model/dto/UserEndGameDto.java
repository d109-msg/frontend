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
public class UserEndGameDto {
    @Schema(description = "직업", example = "시민")
    private String jobId;

    @Schema(description = "승리 여부", example = "1")
    private int flagWin;

    @Schema(description = "사망 여부", example = "0")
    private int flagDie;

//    @Schema(description = "끝난 시간", example = "몰라")
//    private String int;
}
