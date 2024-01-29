package com.ssafy.msg.scheduler.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWinFlagDto {
    private int isMafiaWin;
    private String roomId;
}
