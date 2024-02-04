package com.ssafy.msg.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GuestArticleResponseDto {
    private int articleId;
    private int userId;
    private String nickname;
    private String emailId;
    private String imageUrl;
    private String content;
    private String creatTime;
    private String modifyTime;
    private int likesCount;
    private List<String> urls;

    // 댓글 관련
    private List<CommentDto> commentList;

    // result로 부터 받아온 String urls 를 List로 바꿔주는 작업
    public GuestArticleResponseDto(GuestArticleResultDto dto) {
        this.articleId = dto.getArticleId();
        this.userId = dto.getUserId();
        this.nickname = dto.getNickname();
        this.emailId = dto.getEmailId();
        this.imageUrl = dto.getImageUrl();
        this.content = dto.getContent();
        this.creatTime = dto.getCreatTime();
        this.modifyTime = dto.getModifyTime();
        this.likesCount = dto.getLikesCount();

        if (dto.getUrls() != null) { // 이거는 더미 데이터에 이미지가 없어서 그럼 실재 구현 때는 무조건 url이 있을 것임

        String[] urlArray = dto.getUrls().split(",");
        this.urls = Arrays.asList(urlArray);
        }
    }

    public void setCommentList(List<CommentDto> list){
        commentList = new ArrayList<>();
        if (list != null || !list.isEmpty()) {
            this.commentList.addAll(list);
        }
    }
}
