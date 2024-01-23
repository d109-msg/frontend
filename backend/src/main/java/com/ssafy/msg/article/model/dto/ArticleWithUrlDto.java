package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWithUrlDto {
    private int id; //article_id 입니다.
    private String userEmailId;
    private String content;
    private String createTime;
    private int flagPrivate;
    private String modifyTime;
    private String roomId;
    private String url;

}
