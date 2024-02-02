package com.ssafy.msg.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SearchedUserDto {
    private int userId;
    private String userEmail;
    private String nickname;
    private String imageUrl;
    private int isFollow;
    private int isMyFollower;
}
