package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailDto {
    private int articleId;
    private int userId;
    private String content;
    private String createTime;
    private int flagPrivate;
    private String modifyTime;
    private String roomId;
    private List<String> urls;
    private List<MultipartFile> articleImageList;

    // 좋아요
    private int likeCount;
    private int isLike;

    // 댓글 관련
    private List<CommentDto> commentList;

}
