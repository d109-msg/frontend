package com.ssafy.msg.notification.model.mapper;

import com.ssafy.msg.notification.model.dto.ArticleInfoDto;
import com.ssafy.msg.notification.model.dto.CommentInfoDto;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.stream.events.Comment;

@Mapper
public interface NotificationMapper {
    String getNicknameById(int id);

    ArticleInfoDto getArticleInfoById(int articleId);

    CommentInfoDto getCommentInfoById(int commentId);

}
