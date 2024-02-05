package com.ssafy.msg.notification.model.dto;

import com.ssafy.msg.notification.model.entity.NotificationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {

    private int userId;
    private String content;

    public NotificationEntity toEntity() {
        return NotificationEntity.builder()
                .userId(userId)
                .content(content)
                .build();
    }
}
