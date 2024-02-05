package com.ssafy.msg.webpush.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebPushDto {
    private String token;
    private String title;
    private String body;
}
