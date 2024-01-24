package com.ssafy.msg.user.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Schema(description = "팔로워(to)/팔로잉(from) 목록 페이지 정보")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FollowParamDto {
	
	@Schema(description = "아이디 번호")
	private int id;

	@Schema(description = "키워드")
	private String keyword;
	
	@Schema(description = "시작 타겟 번호")
	private int offset;
	
	@Schema(description = "페이지당 타겟 개수")
	private int limit;
	
	@Schema(description = "팔로우/팔로잉 여부")
	private String type;
}
