package com.ssafy.msg.notification.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    String id;
    int userId;
    String content;
    String createTime;
    int flagRead;
    String dataType; // noti, sub
}
