package com.ssafy.msg.article.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
@Slf4j
public class OpenAiUtil {

    @Value("${open-ai.url}")
    private String OPENAI_API_URL;

    @Value("${open-ai.secret-key}")
    private String API_KEY;

    /**
     *
     * 이미지와 조건을 입력받아 비교하여 일치한다면 true,
     * 일치하지 않는다면 false와 이유를 리턴함
     *
     * @param imageFile multipartfile 이미지를 입력받음
     * @param condition String 조건을 입력받음
     * @return true, false 이유
     * @throws Exception
     */
    public String analyzeImage(MultipartFile imageFile, String condition) throws Exception{
        String base64Image = encodeImageToBase64(imageFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        //If the picture and conditions match, answer True, or False, Reason:.
        //If this condition and photo match, true If not, tell me false and reason together in Korean. like this. false, reason: .

        String payload = String.format("{ \"model\": \"gpt-4-vision-preview\", " +
                "\"messages\": [ " +
                "{ \"role\": \"user\", " +
                "\"content\": [ " +
                "{ \"type\": \"text\", " +
                "\"text\": \"If the photo and condition match, answer True or False, Reason : A simple answer for the reason in Korean. condition : %s\" }, " +
                "{ \"type\": \"image_url\", \"image_url\": { \"url\": \"data:image/jpeg;base64,%s\" } } ] } ]," +
                " \"max_tokens\": 300 }", condition, base64Image);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(OPENAI_API_URL, request, String.class);
    }

    private String encodeImageToBase64(MultipartFile imageFile) throws IOException {
        byte[] imageBytes = imageFile.getBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

}
