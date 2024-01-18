package com.ssafy.msg.article.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = originalFilename + UUID.randomUUID();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
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
    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3.deleteObject(request);
    }
}