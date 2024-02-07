package com.ssafy.msg.game.model.dto;

import com.ssafy.msg.game.util.GameUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantResponseDto {
    private Integer id;
    private String roomId;
    private Integer userId;
    private Integer lastMessageId;
    private Integer flagDie;
    private Integer flagWin;
    private String jobId;
    private String nickname;
    private String imageUrl;
    private Integer ability;
    private boolean flagMafia;

    public ParticipantResponseDto(ParticipantDto dto) {
        this.id = dto.getId();
        this.roomId = dto.getRoomId();
        this.userId = dto.getUserId();
        this.lastMessageId = dto.getLastMessageId();
        this.flagDie = dto.getFlagDie();
        this.flagWin = dto.getFlagWin();
        this.jobId = dto.getJobId();
        this.nickname = dto.getNickname();
        this.imageUrl = dto.getImageUrl();
        this.ability = dto.getAbility();

        this.flagMafia = GameUtil.getRoleType(this.jobId).equals("마피아");
    }
}
