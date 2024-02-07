package com.ssafy.msg.message.model.dto;

import com.ssafy.msg.message.model.entity.MessageEntity;
import com.ssafy.msg.message.model.entity.MessageImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {

    private String roomId;
    private int flagMafia;
    private String content;
    private List<String> base64Images;

//    public MessageEntity toEntity() {
//        MessageEntity messageEntity = MessageEntity.builder()
//                .roomId(this.roomId)
//                .userId(this.userId)
//                .content(this.content)
//                .build();
//
//        if (this.messageImageDtos != null) {
//            List<MessageImageEntity> messageImageEntities = this.messageImageDtos.stream()
//                    .map(imageDto -> MessageImageEntity.builder()
//                            .url(imageDto.getUrl())
//                            .uuid(imageDto.getUuid())
//                            .build())
//                    .collect(Collectors.toList());
//            messageEntity.setMessageImageEntities(messageImageEntities);
//        }
//
//        return messageEntity;
//    }
}
