package com.ssafy.msg.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private int id;
	private String emailId;
	private String emailPassword;
	private String nickname;
	private String provider;
	private String identifier;
	private int flagAdmin;
	private String refreshToken;
	private String signInTime;
	private String signUpTime;
	private int flagPrivate;
	private int flagIdentifier;
	private String imageUrl;
	private String imageUuid;
}
