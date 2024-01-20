package com.ssafy.msg.user.model.service;

import com.ssafy.msg.user.model.dto.Oauth2Dto;
import com.ssafy.msg.user.model.dto.SignInDto;
import com.ssafy.msg.user.model.dto.SignUpDto;
import com.ssafy.msg.user.model.dto.UpdateDto;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.dto.UserInfoDto;

public interface UserService {

	UserDto findUserByEmailId(String emailId) throws Exception;

	void signUpWithOauth2(Oauth2Dto oauth2Dto) throws Exception;

	void signInWithOauth2(Oauth2Dto oauth2Dto) throws Exception;

	void signUp(SignUpDto signUpDto) throws Exception;

	void signIn(SignInDto signInDto, UserDto userDto) throws Exception;

	void signOut(String emailId) throws Exception;

	void withdraw(String emailId) throws Exception;

	UserInfoDto getUserInfo(String emailId) throws Exception;

	void updateUserInfo(UserDto userDto) throws Exception;

	void resetPassword(UserDto userDto) throws Exception;

}
