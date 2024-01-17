package com.ssafy.msg.user.util;

import com.ssafy.msg.user.model.dto.Oauth2Dto;

public interface Oauth2Util {
	String getAccessToken(String code) throws Exception;
	Oauth2Dto getUserInfo(String accessToken) throws Exception;
}
