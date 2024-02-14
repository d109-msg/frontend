package com.ssafy.msg.scheduler.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomIdTitleDto {
    private String roomId;
    private String title;
}
