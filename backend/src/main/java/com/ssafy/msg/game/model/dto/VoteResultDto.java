package com.ssafy.msg.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteResultDto {
    private int id;
    private String imageUrl;
    private String nickname;
    private int normalVoteCount;
    private int mafiaVoteCount;
    private int doctorVoteCount;
}
