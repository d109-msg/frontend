package com.ssafy.msg.article.model.service;

import com.ssafy.msg.article.model.dto.ArticleCreateDto;
import com.ssafy.msg.article.model.dto.ArticleDto;
import com.ssafy.msg.article.model.dto.ArticleImageDto;
import com.ssafy.msg.article.model.mapper.ArticleMapper;
import com.ssafy.msg.article.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private final ArticleMapper articleMapper;

    private final S3Util s3Util;

    @Override
    public void createArticle(ArticleDto articleDto) throws Exception {
        log.info("서비스 시작");

        if (articleDto.getRoomId() == null) { // 일반 게시물 작성 (room_id 가 null)
            articleMapper.createArticle(articleDto);
            log.info("service 일반 게시물 작성 성공 articleId: {}", articleDto.getId());

            // 유저가 올린 사진을 리스트로 저장해 왔는데 이것을 for문으로 돌면서 하나씩 s3에 저장
            for (MultipartFile multipartFile: articleDto.getArticleImageList()) {
                String uuid = s3Util.saveFile(multipartFile);
                String url = s3Util.getUrl(uuid);
                articleMapper.insertArticleImage(new ArticleImageDto(articleDto.getId(), articleDto.getId(), url, uuid, 0));
            }
        } else { // 미션 게시물 작성
            articleMapper.createMissionArticle(articleDto);
            log.info("service 미션 게시물 작성 성공 articleId: {}", articleDto.getId());

            boolean isFirst = true;
            for (MultipartFile multipartFile : articleDto.getArticleImageList()) {
                String uuid = s3Util.saveFile(multipartFile);
                String url = s3Util.getUrl(uuid);

                if (isFirst) {
                    articleMapper.insertArticleImage(new ArticleImageDto(articleDto.getId(), articleDto.getId(), url, uuid, 1));
                    isFirst = false; // 첫 번째 요소를 처리한 후에는 false로 설정
                } else {
                    articleMapper.insertArticleImage(new ArticleImageDto(articleDto.getId(), articleDto.getId(), url, uuid, 0));
                }
            }

        }
        log.info("사진 저장 성공 insertArticleImage() -> articleImageList: {}", articleDto.getArticleImageList());

    }

}
