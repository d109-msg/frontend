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

    ArticleDetailDto getArticleDetail(ArticleDto articleDto) throws SQLException;

    List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws SQLException;

    Boolean selectArticleLike(ArticleDto articleDto) throws SQLException;

    void insertArticleLike(ArticleDto articleDto) throws SQLException;

    void deleteArticleLike(ArticleDto articleDto) throws SQLException;

    int getLikeCount(int articleId) throws SQLException;

    void createComment(CommentDto commentDto) throws SQLException;

}
