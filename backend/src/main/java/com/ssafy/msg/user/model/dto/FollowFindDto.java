package com.ssafy.msg.user.model.dto;

import lombok.Data;

@Data
public class FollowFindDto {
	private int id;
	private String fromUserEmailId;
	private String toUserEmailId;
	private String createTime;
}
