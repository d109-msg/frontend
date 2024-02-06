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
public class MessageDto {
    private int id;
    private String roomId;
    private int userId;
    private String noticeType;
    private String createTime;
    private String dataType;

    private List<MessageImageDto> messageImageDtos;
}
