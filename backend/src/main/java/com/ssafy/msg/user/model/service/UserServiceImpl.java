package com.ssafy.msg.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.msg.user.exception.PasswordNotMatchException;
import com.ssafy.msg.user.model.dto.Oauth2Dto;
import com.ssafy.msg.user.model.dto.SignInDto;
import com.ssafy.msg.user.model.dto.SignUpDto;
import com.ssafy.msg.user.model.dto.UpdateDto;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.dto.UserInfoDto;
import com.ssafy.msg.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto findUserByEmailId(String emailId) throws Exception {
		return userMapper.findUserByEmailId(emailId);
	}

	@Override
	public void signUpWithOauth2(Oauth2Dto oauth2Dto) throws Exception {
		userMapper.signUpWithOauth2(oauth2Dto);
	}

	@Override
	public void signInWithOauth2(Oauth2Dto oauth2Dto) throws Exception {
		userMapper.signInWithOauth2(oauth2Dto);
	}

	@Override
	public void signUp(SignUpDto signUpDto) throws Exception {
		String encodedPassword = passwordEncoder.encode(signUpDto.getEmailPassword());
		signUpDto.setEmailPassword(encodedPassword);
		userMapper.signUp(signUpDto);
	}

	@Override
	public void signIn(SignInDto signInDto, UserDto userDto) throws Exception {
		if (!passwordEncoder.matches(signInDto.getEmailPassword(), userDto.getEmailPassword())) {
			throw new PasswordNotMatchException();
		} else {
			userMapper.signIn(userDto);
		}
	}

	@Override
	public void signOut(String emailId) throws Exception {
		userMapper.signOut(emailId);
	}

	@Override
	public void withdraw(String emailId) throws Exception {
		userMapper.withdraw(emailId);
	}

	@Override
	public UserInfoDto getUserInfo(String emailId) throws Exception {
		return userMapper.getUserInfo(emailId);
	}

	@Override
	public void updateUserInfo(UserDto userDto) throws Exception {
		userMapper.updateUserInfo(userDto);
	}

}
