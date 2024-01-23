package com.ssafy.msg.message.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "메시지 수신에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private int id;
    private String roomId;
    private String userEmailId;
    private String sendTime;
    private String dataType;
    private String text;
    private String url;
    private String uuid;
}
