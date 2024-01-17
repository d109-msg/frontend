package com.ssafy.msg.user.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.msg.user.model.dto.Oauth2Dto;
import com.ssafy.msg.user.model.dto.SignUpDto;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.dto.UserInfoDto;

@Mapper
public interface UserMapper {

	UserDto findUserByEmailId(String emailId) throws SQLException;

	void signUpWithOauth2(Oauth2Dto oauth2Dto) throws SQLException;

	void signInWithOauth2(Oauth2Dto oauth2Dto) throws SQLException;

	void signUp(SignUpDto signUpDto) throws SQLException;

	void signIn(UserDto userDto) throws SQLException;

	void signOut(String emailId) throws SQLException;

	void withdraw(String emailId) throws SQLException;

	UserInfoDto getUserInfo(String emailId) throws SQLException;

	void updateUserInfo(UserDto userDto) throws SQLException;
}
