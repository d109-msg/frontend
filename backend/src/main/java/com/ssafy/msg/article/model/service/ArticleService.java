package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.*;

import java.util.List;

public interface ArticleService {
    ArticleDetailDto createArticle(ArticleDto articleDto) throws Exception; // 게시물 입력 정보를 받아옴

    List<ArticleWithUrlDto> getArticles(int userId) throws Exception; // 전체 게시물 리스트 받아옴

    ArticleDetailDto getArticleDetail(int articleId) throws Exception; // 게시물 하나 조회

    List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws Exception;

    void articleLike(ArticleLikeDto articleLikeDto) throws Exception;

    CommentDto createComment(CommentDto commentDto) throws Exception;

}
