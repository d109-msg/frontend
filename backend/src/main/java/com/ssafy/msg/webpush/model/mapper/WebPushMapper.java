package com.ssafy.msg.webpush.model.mapper;

import com.ssafy.msg.webpush.model.dto.FCMTokenDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebPushMapper {
    void registerFCMToken(FCMTokenDto fcmTokenDto);

    String getFCMTokenByUserId(int id);
}
