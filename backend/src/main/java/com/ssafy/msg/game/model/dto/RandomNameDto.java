package com.ssafy.msg.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RandomNameDto {
    private String firstName;
    private String lastName;
}
