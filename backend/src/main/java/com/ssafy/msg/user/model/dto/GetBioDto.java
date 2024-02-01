package com.ssafy.msg.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "자기소개글 조회를 위한 DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBioDto {
    private int Id;
    private String bio;
    private String nickname;
    private String emailId;
}
