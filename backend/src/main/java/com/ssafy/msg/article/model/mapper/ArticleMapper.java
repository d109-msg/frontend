package com.ssafy.msg.article.model.mapper;

import com.ssafy.msg.article.model.dto.*;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

public interface ArticleMapper {

    void createArticle(ArticleDto articleDto) throws SQLException;

    void insertArticleImage(ArticleImageDto articleImageDto) throws SQLException;

    List<ArticleImageDto> getArticleImages(int articleId) throws SQLException;

    List<ArticleWithUrlDto> getArticles(int userId) throws SQLException;

    ArticleDetailDto getArticleDetail(int articleId) throws SQLException;

    List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws SQLException;

    Boolean selectArticleLike(ArticleLikeDto articleDto) throws SQLException;

    void insertArticleLike(ArticleLikeDto articleLikeDto) throws SQLException;

    void deleteArticleLike(ArticleLikeDto articleLikeDto) throws SQLException;

    int getLikeCount(int articleId) throws SQLException;

    void createComment(CommentDto commentDto) throws SQLException;

}
