package com.ssafy.msg.article.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DeleteArticleDto {
    private int userId;
    private int articleId;
}
