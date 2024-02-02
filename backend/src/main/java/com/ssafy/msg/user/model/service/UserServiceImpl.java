package com.ssafy.msg.user.model.service;

import java.util.List;

import com.ssafy.msg.user.model.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.msg.user.exception.PasswordNotMatchException;
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
	public void signInWithOauth2(UserDto userDto) throws Exception {
		userMapper.signInWithOauth2(userDto);
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
	public void signOut(int id) throws Exception {
		userMapper.signOut(id);
	}

	@Override
	public void withdraw(int id) throws Exception {
		userMapper.withdraw(id);
	}

	@Override
	public UserInfoDto getUserInfo(int id) throws Exception {
		return userMapper.getUserInfo(id);
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

	@Override
	public List<FollowUserDto> getFollowList(FollowParamDto followParamDto) throws Exception {
		return userMapper.getFollowList(followParamDto);
	}

	@Override
	public UserDto findUserById(int id) throws Exception {
		return userMapper.findUserById(id);
	}

	@Override
	public void signUpWithOauth2(UserDto userDto) throws Exception {
		userMapper.signUpWithOauth2(userDto);
	}

	@Override
	public void updateBio(BioDto bioDto) throws Exception {
		userMapper.updateBio(bioDto);
	}

	@Override
	public GetBioDto getBio(BioDto bioDto) throws Exception {
		return userMapper.getBio(bioDto);
	}

	@Override
	public List<SearchedUserDto> searchUsers(SearchUsersParamDto searchUsersParamDto) throws Exception {
		return userMapper.searchUsers(searchUsersParamDto);
	}
}
