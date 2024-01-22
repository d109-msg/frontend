package com.ssafy.msg.article.model.mapper;

import com.ssafy.msg.article.model.dto.ArticleDto;
import com.ssafy.msg.article.model.dto.ArticleImageDto;

import java.sql.SQLException;

public interface ArticleMapper {

    void createArticle(ArticleDto articleDto) throws SQLException;

    void createMissionArticle(ArticleDto articleDto) throws SQLException;

    void insertArticleImage(ArticleImageDto articleImageDto) throws SQLException;

}
