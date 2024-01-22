package com.ssafy.msg.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String id;
    private String dataType;    // 개인, 그룹, 랜덤, 대기
    private int lastMessageId;
    private String createTime;
    private String endTime;
    private String startTime;
    private String title;
    private String imageUrl;
}
