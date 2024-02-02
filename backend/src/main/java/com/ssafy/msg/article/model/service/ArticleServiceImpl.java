package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.*;
import com.ssafy.msg.article.model.mapper.ArticleMapper;
import com.ssafy.msg.article.util.S3Util;
import com.ssafy.msg.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;


    private final S3Util s3Util;

    // 게시물 작성

    /**
     * userId와 (미션 방 일때) roomId를 받아서
     * @param articleDto
     * @throws Exception
     */
    @Override
    public void createArticle(ArticleDto articleDto) throws Exception {
        log.info("(service) Start");

        if (articleDto.getRoomId().isEmpty()) { // 일반 게시물 작성 (room_id 가 비어있을 때)
            articleDto.setRoomId(null); // 룸 id 가 비어 있으면 null 값을 넣어줌
            articleMapper.createArticle(articleDto);
            log.info("(service) 일반 게시물 작성 성공 articleId: {}", articleDto.getId());

            // 유저가 올린 사진을 리스트로 저장해 왔는데 이것을 for문으로 돌면서 하나씩 s3에 저장
            for (MultipartFile multipartFile: articleDto.getArticleImageList()) {
                String uuid = s3Util.saveFile(multipartFile);
                String url = s3Util.getUrl(uuid);


                // articleImage table 에 사진 정보 저장
                articleMapper.insertArticleImage(new ArticleImageDto(articleDto.getId(), url, uuid, 0));
            }
        } else { // 미션 게시물 작성
            articleMapper.createArticle(articleDto);
            log.info("(service) 미션 게시물 작성 성공 articleId: {}", articleDto.getId());
            boolean isFirst = true; // 첫번째 사진인지를 구분
            for (MultipartFile multipartFile : articleDto.getArticleImageList()) {
                String uuid = s3Util.saveFile(multipartFile);
                String url = s3Util.getUrl(uuid);

                if (isFirst) { // 첫번째 사진은 flag_mission 체크
                    articleMapper.insertArticleImage(new ArticleImageDto(articleDto.getId(), url, uuid, 1));
                    isFirst = false; // 첫 번째 요소를 처리한 후에는 false로 설정
                } else {
                    articleMapper.insertArticleImage(new ArticleImageDto(articleDto.getId(), url, uuid, 0));
                }
            }
        }
        log.info("(service) end");
    }

    @Override
    public RoomFeedResponseDto getFeedByRoomId(ArticleByRoomIdDto articleByRoomIdDto) throws Exception {
        log.info("getFeedByRoomId() roomId : {}", articleByRoomIdDto.getRoomId());
        log.info("getFeedByRoomId() limit : {}", articleByRoomIdDto.getLimit());
        log.info("getFeedByRoomId() offset : {}", articleByRoomIdDto.getOffset());

        List<RoomArticleResultDto> list = articleMapper.getArticleListByRoomId(articleByRoomIdDto);
        List<RoomArticleResponseDto> resultList = new ArrayList<>();

        if(list.isEmpty()){
            return null;
        }

        for(RoomArticleResultDto dto : list) {
            resultList.add(new RoomArticleResponseDto(dto));
        }

        int offset = articleByRoomIdDto.getOffset() + articleByRoomIdDto.getLimit();
        String nextUrl = articleByRoomIdDto.getCurrentUrl() +"?roomId=" + articleByRoomIdDto.getRoomId() + "?offset=" + offset + "&limit=" + articleByRoomIdDto.getLimit() ;

        return RoomFeedResponseDto.builder().articles(resultList).nextUrl(nextUrl).build();
    }

// 게시물 수정

    @Override
    public void updateArticle(UpdateArticleDto updateArticleDto) throws Exception {
        articleMapper.updateArticle(updateArticleDto);
    }

    @Override
    public void deleteArticle(DeleteArticleDto deleteArticleDto) throws Exception {
        articleMapper.deleteArticle(deleteArticleDto);
    }

    @Override
    public List<ArticleWithUrlDto> getArticles(Integer userId) throws Exception {
        log.info("(ArticleServiceImpl) 게시물조회 시작");

        userId = articleMapper.getUserId(userId);

        if (userId != null) {
            return articleMapper.getArticles(userId);
        } else {
            throw new UserNotFoundException();
        }

    }

    @Override
    public ArticleDetailDto getArticleDetail(ArticleDto articleDto) throws Exception {
        log.info("(ArticleServiceImpl) getArticleDetail 시작(이미지 제외)");
        ArticleDetailDto articleDetailDto = articleMapper.getArticleDetail(articleDto);

        articleDetailDto.setLikeCount(articleMapper.getLikeCount(articleDto.getId())); // 좋아요 수 넣어주기
        articleDetailDto.setIsLike(isLike(articleDto)); // 좋아요 여부 알려주기

        // 댓글 리스트 넣어주기
        articleDetailDto.setCommentList(getComments(CommentDto.builder().articleId(articleDto.getId()).build()));

        List<String> urls = new ArrayList<>();

        for (ArticleImageDto ai : articleMapper.getArticleImages(articleDto.getId())) {
            urls.add(ai.getUrl());
        }
        articleDetailDto.setUrls(urls);

        log.info("(ArticleServiceImpl) 게시물 상세보기 끝");
        return articleDetailDto;

    }

    @Override
    public List<ArticleDetailDto> getFeedArticleList(FeedParamDto feedParamDto) throws Exception {
        log.info("(ArticleServiceImpl) getFeed 피드 게시물 리스트 조회 시작");
        List<ArticleDetailDto> articleList = articleMapper.getFeedArticleList(feedParamDto);

        List<ArticleDetailDto> feedArticleList = new ArrayList<>();

        for (ArticleDetailDto at : articleList) { // 받아온 팔로우하는 사람들의 게시물 리스트를 받아서 돌린다
            ArticleDto articleDto = ArticleDto.builder().id(at.getArticleId()).userId(feedParamDto.getUserId()).build();
            ArticleDetailDto articleDetail = getArticleDetail(articleDto);
            at.setUrls(articleDetail.getUrls());

            at.setIsLike(articleDetail.getIsLike());
            at.setLikeCount(articleDetail.getLikeCount());

            feedArticleList.add(at);

        }
        return feedArticleList;

    }

    // 비로그인 혹은 팔로워 없을 때 보여주는 게시물 가져오기
    @Override
    public List<ArticleDetailDto> getDefaultFeedList() throws Exception {
        List<ArticleDetailDto> articleList = articleMapper.getDefaultFeedList();
        List<ArticleDetailDto> defaultFeedList = new ArrayList<>();

        for (ArticleDetailDto at : articleList) { // 받아온 게시물 리스트를 받아서 돌린다
            ArticleDto articleDto = ArticleDto.builder()
                    .id(at.getArticleId())
                    .build();

            ArticleDetailDto articleDetail = getArticleDetail(articleDto);

            at.setUrls(articleDetail.getUrls());
            at.setIsLike(articleDetail.getIsLike());
            at.setLikeCount(articleDetail.getLikeCount());

            defaultFeedList.add(at);

        }
        return defaultFeedList;
    }

    // 게시물 좋아요 시작
    @Override
    @Transactional
    public void articleLike(ArticleDto articleDto) throws Exception {
        log.info("(ArticleServiceImpl) 게시물 좋아요 시작");
        /*
        좋아요 목록을 조회 해서 지금 유저의 id 가 목록에 있으면 삭제하고 없으면 넣어줌
         */

        if (articleMapper.selectArticleLike(articleDto)) {
            articleMapper.deleteArticleLike(articleDto);

            log.info("(ArticleServiceImpl) 좋아요 삭제");

        } else {
            articleMapper.insertArticleLike(articleDto);

            log.info("(ArticleServiceImpl) 좋아요 추가");
        }

    }

    @Override
    public List<LikeUserListDto> getLikeUserList(int articleId) throws Exception {
        log.info("(ArticleServiceImpl) 좋아요 유저 리스트 조회 시작");

        return articleMapper.getLikeUserList(articleId);
    }

    // 게시물 좋아요 끝

    // 댓글 좋아요 서비스
    @Override
    @Transactional
    public void commentLike(CommentLikeDto commentLikeDto) throws Exception {
        log.info("(ArticleServiceImpl) 댓글 좋아요 시작");

        if (articleMapper.selectCommentLike(commentLikeDto)) {
            articleMapper.deleteCommentLike(commentLikeDto);

            log.info("(ArticleServiceImpl) 댓글 좋아요 삭제");

        } else {
            articleMapper.insertCommentLike(commentLikeDto);

            log.info("(ArticleServiceImpl) 댓글 좋아요 추가");
        }

    }

    @Override
    public List<CommentLikeUserListDto> getCommentLikeUserList(int commentId) throws Exception {

        return articleMapper.getCommentLikeUserList(commentId);
    }

    // 댓글 crud 시작
    @Override
    public void createComment(CommentDto commentDto) throws Exception {
        log.info("(ArticleServiceImpl) 댓글 작성 서비스 시작");
        articleMapper.createComment(commentDto);

    }

    /**
     * content와 commentId를 받아 댓글 내용 수정
     * @param updateCommentDto
     * @throws Exception
     */
    @Override
    public void updateComment(UpdateCommentDto updateCommentDto) throws Exception {
        articleMapper.updateComment(updateCommentDto);
    }

    @Override
    public void deleteComment(DeleteCommentDto deleteCommentDto) throws Exception {
        articleMapper.deleteComment(deleteCommentDto);
    }

    @Override
    public List<CommentDto> getComments(CommentDto commentDto) throws Exception {
        List<CommentDto> commentDtos = articleMapper.getComments(commentDto);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (CommentDto cd : commentDtos) {
            cd.setCommentLikeCount(articleMapper.getCommentLikeCount(cd.getId()));

            CommentLikeDto commentLikeDto = CommentLikeDto.builder().commentId(cd.getId()).userId(cd.getUserId()).build();
            cd.setIsCommentLike(isCommentLike(commentLikeDto));

            commentDtoList.add(cd);
        }

        return commentDtoList;

    }

    // 댓글 crud 끝

    // 게시물 조아요 여부
    @Override
    public int isLike(ArticleDto articleDto) throws Exception {

        // 게시물 좋아요 목록에 접속 유저가 있으면 1 없으면 0 리턴
        if (articleMapper.selectArticleLike(articleDto)) {
            return 1;
        } else {
            return 0;
        }
    }

    // 댓글 좋아요 여부
    @Override
    public int isCommentLike(CommentLikeDto commentLikeDto) throws Exception {

        if (articleMapper.selectCommentLike(commentLikeDto)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void reportArticle(ArticleReportDto articleReportDto) throws Exception {
        articleMapper.reportArticle(articleReportDto);
    }

    @Override
    public List<ArticleReportDto> getArticleReports(int userId) throws Exception {
        return articleMapper.getArticleReports(userId);
    }
}


