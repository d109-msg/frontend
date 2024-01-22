package com.ssafy.msg.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private int id;
    private int roomId;
    private int userEmailId;
    private String sendTime;
    private String dataType;
}
