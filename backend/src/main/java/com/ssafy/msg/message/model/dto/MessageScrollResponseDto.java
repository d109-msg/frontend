package com.ssafy.msg.message.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "메시지 스크롤에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageScrollResponseDto {

    @Schema(description = "메시지 리스트")
    private List<MessageResponseDto> messageResponseDtos;

    @Schema(description = "다음 요청 URL")
    private String nextUrl;
}
