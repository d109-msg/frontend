package com.ssafy.msg.message.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "텍스트 메시지 전송에 대한 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageDto {
    private String roomId;
    private String sendTime;
    private String text;
}
