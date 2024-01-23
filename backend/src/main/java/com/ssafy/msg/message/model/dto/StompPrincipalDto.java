package com.ssafy.msg.message.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StompPrincipalDto implements Principal {
    private String userEmailId;

    @Override
    public String getName() {
        return userEmailId;
    }
}
