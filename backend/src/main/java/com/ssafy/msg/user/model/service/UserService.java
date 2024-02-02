package com.ssafy.msg.user.model.service;

import com.ssafy.msg.user.model.dto.*;

import java.util.List;

public interface UserService {

	UserDto findUserByEmailId(String emailId) throws Exception;

	void signInWithOauth2(UserDto userDto) throws Exception;

	void signUp(SignUpDto signUpDto) throws Exception;

	void signIn(SignInDto signInDto, UserDto userDto) throws Exception;

	void signOut(int id) throws Exception;

	void withdraw(int id) throws Exception;

	UserInfoDto getUserInfo(int id) throws Exception;

	void updateNickname(UserDto userDto) throws Exception;

	void resetPassword(UserDto userDto, String randomPassword) throws Exception;

	void updateIdentifier(UserDto userDto) throws Exception;

	void updateImage(ProfileImageDto profileImageDto) throws Exception;

	void updatePassword(UpdatePasswordDto updatePasswordDto, UserDto userDto) throws Exception;

	void follow(FollowDetailDto followDetailDto) throws Exception;

	FollowFindDto findFollow(FollowDetailDto followDetailDto) throws Exception;

	void unfollow(FollowDetailDto followDetailDto) throws Exception;

	List<FollowUserDto> getFollowList(FollowParamDto followParamDto) throws Exception;

	UserDto findUserById(int id) throws Exception;

	void signUpWithOauth2(UserDto userDto) throws Exception;

	void updateBio(BioDto bioDto) throws Exception;

	GetBioDto getBio(BioDto bioDto) throws Exception;

	List<SearchedUserDto> searchUsers(SearchUsersParamDto searchUsersParamDto) throws Exception;
}
