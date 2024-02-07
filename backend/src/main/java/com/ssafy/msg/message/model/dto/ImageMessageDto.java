package com.ssafy.msg.message.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "이미지 메시지 전송에 대한 요청 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageMessageDto {
    private String roomId;
    private int flagMafia;
    private MultipartFile image;
}
