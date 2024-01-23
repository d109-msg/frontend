package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.ArticleDto;
import com.ssafy.msg.article.model.dto.ArticleImageDto;
import com.ssafy.msg.article.model.dto.ArticleWithUrlDto;

import java.util.List;

public interface ArticleService {
    void createArticle(ArticleDto articleDto) throws Exception; // 게시물 입력 정보를 받아옴

    List<ArticleWithUrlDto> getArticles(String emailId) throws Exception; // 전체 게시물 리스트 받아옴

}
