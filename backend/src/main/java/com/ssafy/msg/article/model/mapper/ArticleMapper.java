package com.ssafy.msg.article.model.mapper;

import com.ssafy.msg.article.model.dto.ArticleDetailDto;
import com.ssafy.msg.article.model.dto.ArticleDto;
import com.ssafy.msg.article.model.dto.ArticleImageDto;
import com.ssafy.msg.article.model.dto.ArticleWithUrlDto;

import java.sql.SQLException;
import java.util.List;

public interface ArticleMapper {

    void createArticle(ArticleDto articleDto) throws SQLException;

    void insertArticleImage(ArticleImageDto articleImageDto) throws SQLException;

    List<ArticleWithUrlDto> getArticles(String emailId) throws SQLException;

    List<ArticleDetailDto> getArticleDetail(int articleId) throws SQLException;

}
