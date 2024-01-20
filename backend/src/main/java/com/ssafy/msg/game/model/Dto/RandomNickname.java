package com.ssafy.msg.game.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomNickname {
    private String first_nickname;
    private String last_nickname;
}
