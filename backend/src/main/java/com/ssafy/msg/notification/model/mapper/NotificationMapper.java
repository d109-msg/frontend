package com.ssafy.msg.notification.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface NotificationMapper {
    String getNicknameById(int id);

    int getUserIdByArticleId(int articleId);

    int getUserIdByCommentId(int commentId);
}
