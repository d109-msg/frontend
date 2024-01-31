package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {
    void createArticle(ArticleDto articleDto) throws Exception; // 게시물 입력 정보를 받아옴

    List<ArticleWithUrlDto> getArticles(Integer userId) throws Exception; // 전체 게시물 리스트 받아옴

    ArticleDetailDto getArticleDetail(ArticleDto articleDto) throws Exception; // 게시물 하나 조회

    List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws Exception;

    @Transactional
    void articleLike(ArticleDto articleDto) throws Exception;

    @Transactional
    void commentLike(CommentLikeDto commentLikeDto) throws Exception;

    void createComment(CommentDto commentDto) throws Exception;

    List<CommentDto> getComments(CommentDto commentDto) throws Exception;

    int isLike(ArticleDto articleDto) throws Exception;

    List<LikeUserListDto> getLikeUserList(int articleId) throws Exception;

    List<CommentLikeUserListDto> getCommentLikeUserList(int commentId) throws Exception;

}
