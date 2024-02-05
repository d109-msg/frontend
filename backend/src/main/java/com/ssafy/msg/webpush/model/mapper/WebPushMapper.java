package com.ssafy.msg.webpush.model.mapper;

import com.ssafy.msg.webpush.model.dto.FCMTokenDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebPushMapper {
    String getFCMTokenByArticleId(int id);

    String getNicknameById(int id);

    String getFCMTokenByParentCommentId(int id);

    void registerFCMToken(FCMTokenDto fcmTokenDto);
}
