package com.ssafy.msg.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "투표의 정보를 받기위한 DTO")
public class VoteReceiveDto {
    @Schema(description = "투표하는 유저의 participantId", example = "1")
    private int participantId;

    @Schema(description = "투표하는 유저의 직업", example = "시민")
    private String jobId;

    @Schema(description = "투표 대상자의 participantId", example = "2")
    private int targetId;
}
