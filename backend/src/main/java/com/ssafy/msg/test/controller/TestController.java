package com.ssafy.msg.test.controller;


import com.ssafy.msg.redis.model.repo.RefreshTokenRepository;
import com.ssafy.msg.test.model.dto.TestMongo;
import com.ssafy.msg.test.model.dto.TestRedis;
import com.ssafy.msg.test.model.repo.TestMongoRepository;
import com.ssafy.msg.test.model.repo.TestRedisRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j

@Tag(name = "Test", description = "테스트 관련 API")
public class TestController {

    @Value("${server.env}")
    private String serverEnv;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server-address}")
    private String serverAddress;

    @Value("${server-name}")
    private String serverName;

    private final TestMongoRepository testMongoRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/mongodb")
    public String testMongo() {
//        testMongoRepository.deleteAll();
//
//        testMongoRepository.save(TestMongo.builder().firstName("Alice").lastName("Smith").build());
//        testMongoRepository.save(TestMongo.builder().firstName("Bob").lastName("Smith").build());
//
//        System.out.println("TestDtos found with findAll():");
//        System.out.println("-------------------------------");
//        for (TestMongo testMongo : testMongoRepository.findAll()) {
//            System.out.println(testMongo);
//        }
//
//        System.out.println();
//        System.out.println("TestDto found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(testMongoRepository.findByFirstName("Alice"));
//
//        System.out.println("TestDtos found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (TestMongo testMongo : testMongoRepository.findByLastName("Smith")) {
//            System.out.println(testMongo);
//        }
        return testMongoRepository.findAll().toString();
    }


    @GetMapping("/redis")
    public String testRedis() {
//        TestRedis testRedis = new TestRedis(1L, "refreshToken");
//        testRedisRepository.save(testRedis);

        return refreshTokenRepository.findAll().toString();
    }

    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> responseData = new TreeMap<>();
        responseData.put("serverAddress", serverAddress);
        responseData.put("serverName", serverName);
        responseData.put("serverPort", serverPort);
        responseData.put("serverEnv", serverEnv);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {

        return ResponseEntity.ok(serverEnv);
    }
}
