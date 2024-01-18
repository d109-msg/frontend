package com.ssafy.msg.article.controller;

import com.ssafy.msg.article.util.OpenAiUtil;
import com.ssafy.msg.article.util.S3Util;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Slf4j

@Tag(name = "Article", description = "게시물 API")
public class ArticleController {

    private final OpenAiUtil openAiUtil;

    private final S3Util s3Util;

    @PostMapping(value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String analyzeImageTest(@RequestParam("image") MultipartFile imageFile, @RequestParam("condition") String condition) throws Exception{
        return openAiUtil.analyzeImage(imageFile, condition);
    }

}
