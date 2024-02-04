package com.ssafy.msg.article.model.mapper;

import com.ssafy.msg.article.model.dto.*;

import java.sql.SQLException;
import java.util.List;

public interface ArticleMapper {

    void createArticle(ArticleDto articleDto) throws SQLException;

    void updateArticle(UpdateArticleDto updateArticleDto) throws SQLException;

    //게시물 삭제
    void deleteArticle(DeleteArticleDto deleteArticleDto) throws SQLException;

    void insertArticleImage(ArticleImageDto articleImageDto) throws SQLException;

    List<ArticleImageDto> getArticleImages(int articleId) throws SQLException;

    List<ArticleWithUrlDto> getArticles(int userId) throws SQLException;

    ArticleDetailDto getArticleDetail(ArticleDto articleDto) throws SQLException;

    List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws SQLException;

    List<RoomArticleResultDto> getArticleListByRoomId(ArticleByRoomIdDto articleByRoomIdDto) throws SQLException;

    // 기본 게시물 조회
    List<ArticleDetailDto> getDefaultFeedList() throws SQLException;

    // 게시물 좋아요 시작
    Boolean selectArticleLike(ArticleDto articleDto) throws SQLException;

    void insertArticleLike(ArticleDto articleDto) throws SQLException;

    void deleteArticleLike(ArticleDto articleDto) throws SQLException;

    int getLikeCount(int articleId) throws SQLException;

    List<LikeUserListDto> getLikeUserList(int articleId) throws SQLException;
    // 게시물 좋아요 끝 --------------------------------------

    // 댓글 좋아요 시작 --------------------------------------
    Boolean selectCommentLike(CommentLikeDto commentLikeDto) throws SQLException;

    void insertCommentLike(CommentLikeDto commentLikeDto) throws SQLException;

    void deleteCommentLike(CommentLikeDto commentLikeDto) throws SQLException;

    int getCommentLikeCount(int commentId) throws SQLException;

    List<CommentLikeUserListDto> getCommentLikeUserList(int commentId) throws SQLException;

    // 댓글 좋아요 끝 -------------------------------------

    void createComment(CommentDto commentDto) throws SQLException;

    void updateComment(UpdateCommentDto updateCommentDto) throws SQLException;

    void deleteComment(DeleteCommentDto deleteCommentDto) throws SQLException;

    List<CommentDto> getComments(CommentDto commentDto) throws SQLException;

    Integer getUserId(Integer userId) throws SQLException;

    void reportArticle(ArticleReportDto articleReportDto) throws SQLException;

    List<ArticleReportDto> getArticleReports(int userId) throws SQLException;

    List<GuestArticleResultDto> getGuestFeed(FeedParamDto feedParamDto) throws SQLException;


}
