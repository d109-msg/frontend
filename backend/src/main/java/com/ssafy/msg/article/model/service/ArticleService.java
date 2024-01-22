package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.ArticleDto;

public interface ArticleService {
    void createArticle(ArticleDto articleDto) throws Exception; // 게시물 입력 정보를 받아옴

}
