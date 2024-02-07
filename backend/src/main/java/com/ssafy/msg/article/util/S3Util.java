package com.ssafy.msg.article.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 이미지를 입력받아 s3에 업로드하고 uuid를 리턴
     * @param multipartFile 이미지를 입력 받음
     * @return String uuid를 리턴
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {

        String fileName = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        return fileName;
    }

    public String saveMessageImage(String base64Image){

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

        String fileName = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/png");

        amazonS3.putObject(bucket, fileName, inputStream, metadata);
        return fileName;

    }

    /**
     * uuid를 입력받아 url을 리턴하는 함수
     * @param uuid uuid를 입력
     * @return String url리턴
     *
     */
    public String getUrl(String uuid) {
        return amazonS3.getUrl(bucket, uuid).toString();
    }

    /**
     * uuid를 입력 받아 bucket에서 파일을 삭제
     * @param fileName
     *
     */
    public String deleteFile(String fileName) {
        log.info("deleteFile() -> Start");
        log.info("deleteFile() -> fileName : {}", fileName);

        String result = "";

        if(amazonS3.doesObjectExist(bucket, fileName)) {
            log.info("deleteFile() -> exist");

            DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
            amazonS3.deleteObject(request);

            result = "deleted file : ";
            log.info("deleteFile() -> Success");
        } else {
            log.error("deleteFile() -> doesn't exist");

            result = "file doesn't exist : ";
        }

        log.info("deleteFile() -> End");

        return result + fileName;
    }

    public Boolean isAvailable(String fileName){
        return amazonS3.doesObjectExist(bucket, fileName);
    }
}