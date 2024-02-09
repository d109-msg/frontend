package com.ssafy.msg.notification.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfoDto {
    private int toId;   // 게시물 작성자 아이디 (알림 대상)
    private String imageUrl;    // 게시물의 첫 번째 사진
}
