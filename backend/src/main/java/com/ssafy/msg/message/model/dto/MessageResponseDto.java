package com.ssafy.msg.message.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "메시지 수신에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private String id;
    private String roomId;
    private int userId;
    private int flagMafia;
    private String content;
    private String noticeType;
    private String createTime;
    private String dataType;

    private List<MessageImageDto> messageImageDtos;
}
