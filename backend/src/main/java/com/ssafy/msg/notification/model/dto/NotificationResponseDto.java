package com.ssafy.msg.notification.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "알림 목록 조회에 대한 응답 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    String id;
    int userId;
    String content;
    String createTime;
    int flagRead;
    String dataType; // noti, sub
}
