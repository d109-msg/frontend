package com.ssafy.msg.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbilityTargetResponseDto {
    @Schema(description = "사용 가능 여부에 대한 상태", defaultValue = "true")
    private boolean status;
    
    @Schema(description = "상태에 대한 설명", defaultValue = "대상을 선택하면 마피아 여부를 알려줍니다.")
    private String message;
    
    @Schema(description = "선택 대상이 필요한 능력인지 아닌지 리턴합니다.", defaultValue = "true")
    private boolean flagTarget;
}
