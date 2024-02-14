package com.ssafy.msg.message.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastMessageUpdateDto {
    private int userId;
    private String roomId;
    private String messageId;
}
