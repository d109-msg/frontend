package com.ssafy.msg.article.controller;


import com.ssafy.msg.article.model.dto.ArticleCreateDto;
import com.ssafy.msg.article.model.dto.ArticleDetailDto;
import com.ssafy.msg.article.model.dto.ArticleDto;
import com.ssafy.msg.article.model.dto.ArticleWithUrlDto;
import com.ssafy.msg.article.model.service.ArticleService;
import com.ssafy.msg.article.util.OpenAiUtil;
import com.ssafy.msg.article.util.S3Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Slf4j

@Tag(name = "Article", description = "게시물 관련 API")
public class ArticleController {
    private final ArticleService articleService;

    private final OpenAiUtil openAiUtil;

    private final S3Util s3Util;

    @PostMapping(value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String analyzeImageTest(@RequestParam("image") MultipartFile imageFile, @RequestParam("condition") String condition) throws Exception{
        return openAiUtil.analyzeImage(imageFile, condition);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "게시물 작성", description = "이미지추가와 내용 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공"),
            @ApiResponse(responseCode = "400", description = "게시물 작성 실패", content = @Content) })
    public ResponseEntity<?> createArticle(@ModelAttribute ArticleCreateDto articleCreateDto, HttpServletRequest request) {
        log.info("(controller) create Start");
        log.info("(controller) 클라이언트에서 받아온 articleCreateDto : {}", articleCreateDto);

        String emailId = (String) request.getAttribute("emailId");
        ArticleDto articleDto = ArticleDto.builder().userEmailId(emailId).articleImageList(articleCreateDto.getArticleImageList()).content(articleCreateDto.getContent()).roomId(articleCreateDto.getRoomId()).build();

        try {
            articleService.createArticle(articleDto); // 클라이언트로부터 받은 정보를 서비스에 입력
            log.info("(controller) articleService.createArticle 호출 -> Success");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("(controller) articleService.createArticle 호출 에러", e);
            return new ResponseEntity<>("게시물 작성 실패", HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(controller) -> End");
        }
    }

    @GetMapping(value = "/myprofile")
    @Operation(summary = "전체 게시물", description = "전체 게시물 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 리스트 조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleWithUrlDto.class)) }),
            @ApiResponse(responseCode = "400", description = "게시물 리스트 조회 실패", content = @Content) })
    public ResponseEntity<?> getArticles(HttpServletRequest request) {

        String emailId = (String) request.getAttribute("emailId");

        try {
            List<ArticleWithUrlDto> articleWithUrlDtoList = articleService.getArticles(emailId);
            log.info("(ArticleController) 게시물 조회 성공");
            return new ResponseEntity<>(articleWithUrlDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("(Exception) ", e);
            return new ResponseEntity<>("게시물 작성 실패", HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) getArticles -> end");
        }

    }

    @GetMapping(value = "/detail")
    @Operation(summary = "게시물 상세", description = "게시물 상세 내용 보기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 상세 조회 성공", content ={
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDetailDto.class)) }),
            @ApiResponse(responseCode = "400", description = "게시물 상세 조회 실패", content = @Content) })
    public ResponseEntity<?> getArticleDetail(@RequestParam("articleId") int articleId) {
        log.info("(ArticleController) 게시물 상세보기 시작");

        try {
            log.info("(ArticleController) 게시물 상세조회 성공");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("(ArticleController) 게시물 상세 조회 실패", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) getArticleDetail end");
        }

    }



}
