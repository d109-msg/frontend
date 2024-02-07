package com.ssafy.msg.message.model.entity;

import com.ssafy.msg.message.model.dto.MessageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class MessageEntity {

    @Id
    private String id;

    @Indexed
    private String roomId;
    private int userId;

    private String createTime;
    private String content;
    private int flagMafia;
    private List<MessageImageEntity> messageImageEntities;

    private String noticeType; // day, night
    private String dataType; // chat, image, notice, invite

    public MessageResponseDto toDto() {
        return MessageResponseDto.builder()
                .id(this.id)
                .roomId(this.roomId)
                .userId(this.userId)
                .flagMafia(this.flagMafia)
                .createTime(this.createTime)
                .content(this.content)
                .noticeType(this.noticeType)
                .dataType(this.dataType)
                .messageImageDtos(this.messageImageEntities != null ? this.messageImageEntities.stream()
                        .map(MessageImageEntity::toDto)
                        .toList() : null)
                .build();
    }
}
