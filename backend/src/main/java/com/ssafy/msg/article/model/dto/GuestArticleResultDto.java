package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GuestArticleResultDto {
    private int articleId;
    private int userId;
    private String nickname;
    private String emailId;
    private String imageUrl;
    private String content;
    private String creatTime;
    private String modifyTime;
    private int likesCount;
    private String urls;

}
