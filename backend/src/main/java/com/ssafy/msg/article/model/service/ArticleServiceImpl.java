package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.ArticleDetailDto;
import com.ssafy.msg.article.model.dto.ArticleDto;
import com.ssafy.msg.article.model.dto.ArticleImageDto;
import com.ssafy.msg.article.model.dto.ArticleWithUrlDto;
import com.ssafy.msg.article.model.mapper.ArticleMapper;
import com.ssafy.msg.article.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;


    private final S3Util s3Util;

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
    public List<ArticleWithUrlDto> getArticles(int userId) throws Exception {
        log.info("(ArticleServiceImpl) 게시물조회 시작");
        return articleMapper.getArticles(userId);

    }

    @Override
    public ArticleDetailDto getArticleDetail(int articleId) throws Exception {
        log.info("(ArticleServiceImpl) getArticleDetail 시작(이미지 제외)");
        ArticleDetailDto articleDetailDto = articleMapper.getArticleDetail(articleId);
        List<String> urls = new ArrayList<>();
        log.info("(ArticleServiceImpl) 여기까지는 됐을까 articleDetailDto(): {}", articleDetailDto);
        for (ArticleImageDto ai : articleMapper.getArticleImages(articleId)) {
            urls.add(ai.getUrl());
        }
        articleDetailDto.setUrls(urls);

        log.info("(ArticleServiceImpl) 게시물 상세보기 끝");
        return articleDetailDto;

    }

    @Override
    public List<ArticleDetailDto> getFeedArticleList(int userId) throws Exception {
        log.info("(ArticleServiceImpl) 피드 게시물 리스트 조회 시작");
        return articleMapper.getFeedArticleList(userId);

    }
}
