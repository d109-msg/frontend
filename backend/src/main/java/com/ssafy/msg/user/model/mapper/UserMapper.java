package com.ssafy.msg.user.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.msg.user.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	UserDto findUserByEmailId(String emailId) throws SQLException;

	void signInWithOauth2(UserDto userDto) throws SQLException;

	void signUp(SignUpDto signUpDto) throws SQLException;

	void signIn(UserDto userDto) throws SQLException;

	void signOut(int id) throws SQLException;

	void withdraw(int id) throws SQLException;

	UserInfoDto getUserInfo(int id) throws SQLException;

	void updateNickname(UserDto userDto) throws SQLException;

	void updatePassword(UserDto userDto) throws SQLException;

	void updateIdentifier(UserDto userDto) throws SQLException;

	void updateImage(ProfileImageDto profileImageDto) throws SQLException;

	void follow(FollowDetailDto followDetailDto) throws SQLException;

	FollowFindDto findFollow(FollowDetailDto followDetailDto) throws SQLException;

	void unfollow(FollowDetailDto followDetailDto) throws SQLException;

	List<FollowUserDto> getFollowList(FollowParamDto followParamDto) throws SQLException;

	UserDto findUserById(int id) throws SQLException;

	void signUpWithOauth2(UserDto userDto) throws SQLException;

	void updateBio(BioDto bioDto) throws SQLException;

	GetBioDto getBio(BioDto bioDto) throws SQLException;

	List<SearchedUserDto> searchUsers(SearchUsersParamDto searchUsersParamDto) throws SQLException;

    TargetUserInfoDto getTargetUserInfo(int targetId) throws SQLException;

	Integer getIsFollow(int targetId, int id) throws SQLException;

	List<String> getRooms(int id) throws SQLException;

    List<FollowUserDto> getFollowListAll(FollowParamDto followParamDto) throws SQLException;
}
