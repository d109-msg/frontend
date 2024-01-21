package com.ssafy.msg.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.msg.user.exception.PasswordNotMatchException;
import com.ssafy.msg.user.model.dto.FollowDetailDto;
import com.ssafy.msg.user.model.dto.FollowFindDto;
import com.ssafy.msg.user.model.dto.Oauth2Dto;
import com.ssafy.msg.user.model.dto.ProfileImageDto;
import com.ssafy.msg.user.model.dto.SignInDto;
import com.ssafy.msg.user.model.dto.SignUpDto;
import com.ssafy.msg.user.model.dto.UpdatePasswordDto;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.dto.UserInfoDto;
import com.ssafy.msg.user.model.mapper.UserMapper;
import com.ssafy.msg.user.util.EmailUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final EmailUtil emailUtil;
	
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
	public void updateNickname(UserDto userDto) throws Exception {
		userMapper.updateNickname(userDto);
	}

	@Override
	public void resetPassword(UserDto userDto, String randomPassword) throws Exception {
		String encodedPassword = passwordEncoder.encode(userDto.getEmailPassword());
		userDto.setEmailPassword(encodedPassword);
		userMapper.updatePassword(userDto);
		userDto.setEmailPassword(randomPassword);
		emailUtil.sendTempPassword(userDto);
	}

	@Override
	public void updateIdentifier(UserDto userDto) throws Exception {
		userMapper.updateIdentifier(userDto);
	}

	@Override
	public void updateImage(ProfileImageDto profileImageDto) throws Exception {
		userMapper.updateImage(profileImageDto);
	}

	@Override
	public void updatePassword(UpdatePasswordDto updatePasswordDto, UserDto userDto) throws Exception {
		if (!passwordEncoder.matches(updatePasswordDto.getOldEmailPassword(), userDto.getEmailPassword())) {
			throw new PasswordNotMatchException();
		} else {
			String encodedPassword = passwordEncoder.encode(updatePasswordDto.getNewEmailPassword());
			userDto.setEmailPassword(encodedPassword);
			userMapper.updatePassword(userDto);
		}
	}

	@Override
	public void follow(FollowDetailDto followDetailDto) throws Exception {
		userMapper.follow(followDetailDto);
	}

	@Override
	public FollowFindDto findFollow(FollowDetailDto followDetailDto) throws Exception {
		return userMapper.findFollow(followDetailDto);
	}

	@Override
	public void unfollow(FollowDetailDto followDetailDto) throws Exception {
		userMapper.unfollow(followDetailDto);
	}
}
