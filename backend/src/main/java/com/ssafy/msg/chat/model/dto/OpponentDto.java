package com.ssafy.msg.chat.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "채팅방 생성에 대한 요청 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpponentDto {
    @Schema(description = "상대방 아이디", nullable = false, example = "1")
    private int id;
}
