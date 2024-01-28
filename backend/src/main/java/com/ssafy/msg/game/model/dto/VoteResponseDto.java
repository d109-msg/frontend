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
@Schema(description = "투표 현황 응답")
public class VoteResponseDto {
    @Schema(description = "participantId", example = "1")
    private int id;

    @Schema(description = "유저의 iconUrl", example = "url")
    private String imageUrl;

    @Schema(description = "participant nickname", example = "배고픈 젠킨스")
    private String nickname;

    @Schema(description = "득표 수", example = "3")
    private int voteCount;
}
