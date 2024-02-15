package com.ssafy.msg.message.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastMessageUpdateRequestDto {
    private String roomId;
    private String messageId;
}
