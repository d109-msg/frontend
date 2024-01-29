package com.ssafy.msg.scheduler.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonCompleterDto {
    private int participantId;
    private String nickname;
    private String jobId;
    private String imageUrl;
}
