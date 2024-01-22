package com.ssafy.msg.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 설정 게임 참여에 대한 요청 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyGroupRoomDto {
    private String roomId;
}
