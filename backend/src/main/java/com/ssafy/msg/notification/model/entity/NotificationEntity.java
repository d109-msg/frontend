package com.ssafy.msg.notification.model.entity;

import com.ssafy.msg.notification.model.dto.NotificationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class NotificationEntity {

    @Id
    private String id;

    @Indexed
    private int userId;

    private String fromNickname;

    private int articleId;

    private String imageUrl;

    private String content;

    private int flagRead;

    private String createTime;

    private String dataType; // noti, sub

    public NotificationResponseDto toDto() {
        return NotificationResponseDto.builder()
                .id(id)
                .userId(userId)
                .fromNickname(fromNickname)
                .articleId(articleId)
                .imageUrl(imageUrl)
                .content(content)
                .createTime(createTime)
                .flagRead(flagRead)
                .dataType(dataType)
                .build();
    }
}
