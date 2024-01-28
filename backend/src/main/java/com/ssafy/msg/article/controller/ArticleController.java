package com.ssafy.msg.article.controller;


import com.ssafy.msg.article.model.dto.*;
import com.ssafy.msg.article.model.service.ArticleService;
import com.ssafy.msg.article.util.OpenAiUtil;
import com.ssafy.msg.article.util.S3Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

        int id = (int) request.getAttribute("id");
        ArticleDto articleDto = ArticleDto.builder().userId(id).articleImageList(articleCreateDto.getArticleImageList()).content(articleCreateDto.getContent()).roomId(articleCreateDto.getRoomId()).build();

        try {
            ArticleDetailDto articleDetailDto = articleService.createArticle(articleDto); // 클라이언트로부터 받은 정보를 서비스에 입력
            log.info("(controller) articleService.createArticle 호출 -> Success");
            return new ResponseEntity<>(articleDetailDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("(controller) articleService.createArticle 호출 에러", e);
            return new ResponseEntity<>("게시물 작성 실패", HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(controller) -> End");
        }
    }

    @GetMapping(value = "/profile")
    @Operation(summary = "프로필 게시물", description = "프로필 게시물 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 리스트 조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleWithUrlDto.class)) }),
            @ApiResponse(responseCode = "400", description = "게시물 리스트 조회 실패", content = @Content) })
    public ResponseEntity<?> getArticles(@RequestParam("userId") int userId) {

        try {
            List<ArticleWithUrlDto> articleWithUrlDtoList = articleService.getArticles(userId);
            log.info("(ArticleController) 게시물 조회 성공");
            return new ResponseEntity<>(articleWithUrlDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("(Exception) ", e);
            return new ResponseEntity<>("게시물 작성 실패", HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) getArticles -> end");
        }

    }

    @GetMapping(value = "")
    @Operation(summary = "게시물 상세", description = "게시물 상세 내용 보기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 상세 조회 성공", content ={
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDetailDto.class)) }),
            @ApiResponse(responseCode = "400", description = "게시물 상세 조회 실패", content = @Content) })
    public ResponseEntity<?> getArticleDetail(@RequestParam("articleId") int articleId) {
        log.info("(ArticleController) 게시물 상세보기 시작");

        try {
            ArticleDetailDto articleDetailDto = articleService.getArticleDetail(articleId);
            log.info("(ArticleController) 게시물 상세조회 성공");
            return new ResponseEntity<>(articleDetailDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("(ArticleController) 게시물 상세 조회 실패", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) getArticleDetail end");
        }

    }

    @GetMapping(value = "/feed")
    @Operation(summary = "피드 게시물 리스트", description = "피드에 보여줄 게시물 리스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "피드 게시물 조회 성공", content ={
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDetailDto.class)) }),
            @ApiResponse(responseCode = "400", description = "피드 게시물 조회 실패", content = @Content) })
    public ResponseEntity<?> getFeedArticleList(HttpServletRequest request,
            @Parameter(description = "마지막으로 로딩한 타겟") @RequestParam(value = "offset", required = false) Integer offset,
            @Parameter(description = "페이지당 타겟 개수") @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit) {
        log.info("getFollowList() -> Start");
        log.info("getFollowList() -> Receive offset : {}", offset);
        log.info("getFollowList() -> Receive limit : {}", limit);

        int userId = (Integer) request.getAttribute("id");
        if (offset == null) {
            offset = Integer.MAX_VALUE;
        }

        FeedParamDto feedParamDto = FeedParamDto.builder()
                .userId(userId)
                .offset(offset)
                .limit(limit)
                .build();

        try {
            List<ArticleDetailDto> articleDetailDtos = articleService.getFeedArticleList(feedParamDto);
            int lastId = articleDetailDtos.get(articleDetailDtos.size() -1).getArticleId();

            String currentUrl = request.getRequestURL().toString();
            String nextUrl = currentUrl + "?offset=" + lastId + "&limit=" + limit ;

            FeedResponseDto feedResponseDto = FeedResponseDto.builder()
                    .articleDetailDtos(articleDetailDtos)
                    .nextUrl(nextUrl)
                    .build();
            log.info("(ArticleController) getFeedArticleList() 성공");

            return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("(ArticleController) 피드 게시물 조회 실패", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) getFeedArticleList end");
        }
    }

    @PostMapping(value = "like")
    @Operation(summary = "게시물 좋아요", description = "게시물 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 성공", content ={
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleLikeDto.class)) }),
            @ApiResponse(responseCode = "400", description = "좋아요 실패", content = @Content) })
    public ResponseEntity<?> articleLike(@RequestParam("articleId") int articleId, HttpServletRequest request) {
        log.info("(ArticleController) articleLike() -> 게시물 좋아요 시작");


        int userId = (Integer) request.getAttribute("id");
        ArticleLikeDto articleLikeDto = ArticleLikeDto.builder().
                userId(userId).
                articleId(articleId).
                build();

        try {
            articleService.articleLike(articleLikeDto);
            log.info("(ArticleController) 좋아요 누르기 성공");
            return new ResponseEntity<>(articleLikeDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("(ArticleController) 좋아요 누르기 실패", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) 좋아요 누르기 끝");
        }

    }

    @PostMapping(value = "/comment")
    @Operation(summary = "댓글 작성", description = "댓글 작성하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공" , content ={
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentCreateDto.class)) }),
            @ApiResponse(responseCode = "400", description = "댓글 작성 실패", content = @Content) })
    public ResponseEntity<?> createComment(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request) {
        log.info("(controller) createComment() 댓글 작성 시작");

        CommentDto commentDto = CommentDto.builder()
                .userId((Integer) request.getAttribute("id"))
                .articleId(commentCreateDto.getArticleId())
                .content(commentCreateDto.getContent())
                .parentCommentId(commentCreateDto.getParentCommentId())
                .build();

        try {
            articleService.createComment(commentDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("(controller) 댓글 작성 실패", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            log.info("(ArticleController) 끝");
        }

    }


}
