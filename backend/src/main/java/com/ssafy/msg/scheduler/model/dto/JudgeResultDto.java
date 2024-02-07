package com.ssafy.msg.scheduler.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeResultDto {
    private Integer participantId;
    private Integer vote;
    private Integer ability;
}
