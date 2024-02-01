package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {
    void createArticle(ArticleDto articleDto) throws Exception; // 게시물 입력 정보를 받아옴

    // 게시물 수정
    void updateArticle(UpdateArticleDto updateArticleDto) throws Exception;

    // 게시물 삭제
    void deleteArticle(DeleteArticleDto deleteArticleDto) throws Exception;

    List<ArticleWithUrlDto> getArticles(Integer userId) throws Exception; // 전체 게시물 리스트 받아옴

    ArticleDetailDto getArticleDetail(ArticleDto articleDto) throws Exception; // 게시물 하나 조회

    List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws Exception;

    @Transactional
    void articleLike(ArticleDto articleDto) throws Exception;

    @Transactional
    void commentLike(CommentLikeDto commentLikeDto) throws Exception;

    void createComment(CommentDto commentDto) throws Exception;

    void updateComment(UpdateCommentDto updateCommentDto) throws Exception;

    void deleteComment(DeleteCommentDto deleteCommentDto) throws Exception;

    List<CommentDto> getComments(CommentDto commentDto) throws Exception;

    // 게시물 좋아요 여부
    int isLike(ArticleDto articleDto) throws Exception;

    // 댓글 좋아요 여부
    int isCommentLike(CommentLikeDto commentLikeDto) throws Exception;

    List<LikeUserListDto> getLikeUserList(int articleId) throws Exception;

    List<CommentLikeUserListDto> getCommentLikeUserList(int commentId) throws Exception;

    // 기본 제공 게시물 불러오기
    List<ArticleDetailDto> getDefaultFeedList() throws Exception;

    // 게시물 신고
    void reportArticle(ArticleReportDto articleReportDto) throws Exception;

    // 신고 리스트 보기
    List<ArticleReportDto> getArticleReports(int userId) throws Exception;

}
