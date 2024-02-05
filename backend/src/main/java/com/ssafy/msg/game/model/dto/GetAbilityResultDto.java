package com.ssafy.msg.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAbilityResultDto {
    private int flagSuccess;
    private int flagDie;
    private String jobId;
    private int ability;
    private int flagNight;
}
